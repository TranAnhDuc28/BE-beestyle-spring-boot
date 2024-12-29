package com.datn.beestyle.controller.user;

import com.datn.beestyle.dto.vnpay.PaymentRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class VNPayController {
    @Value("${vnpay.tmn_code}")
    private String vnp_TmnCode;

    @Value("${vnpay.hash_secret}")
    private String vnp_HashSecret;

    @Value("${vnpay.pay_url}")
    private String vnp_PayUrl;

    @Value("${vnpay.return_url}")
    private String vnp_ReturnUrl;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan don hang: " + request.getOrderId();
        String vnp_OrderType = "billpayment";
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_IpAddr = request.getIpAddress();
        String vnp_Amount = String.valueOf(request.getAmount() * 100);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        if (request.getBankCode() != null && !request.getBankCode().isEmpty()) {
            vnp_Params.put("vnp_BankCode", request.getBankCode());
        }

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Build data to hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");
                query.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");
            }
        }
        hashData.deleteCharAt(hashData.length() - 1);
        query.deleteCharAt(query.length() - 1);

        // Compute hash
        String vnp_SecureHash = HmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = vnp_PayUrl + "?" + query.toString();
        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }
    @GetMapping("/return")
    public void handleVNPayReturn(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        // 1. Kiểm tra tính toàn vẹn của dữ liệu trả về
//        if (!isValidVNPayResponse(params)) {
//            // Redirect đến trang lỗi nếu dữ liệu không hợp lệ
//            response.sendRedirect("http://localhost:3000/vnpay/error");
//            return;
//        }

        // 2. Lấy mã phản hồi từ VNPay
        String vnp_ResponseCode = params.get("vnp_ResponseCode");

        // 3. Phân loại trạng thái giao dịch và redirect đến trang tương ứng
        if ("00".equals(vnp_ResponseCode)) {
            // Giao dịch thành công
            response.sendRedirect("http://localhost:3000/vnpay/success");
        } else if ("24".equals(vnp_ResponseCode)) {
            // Người dùng hủy giao dịch
            response.sendRedirect("http://localhost:3000/vnpay/cancel");
        } else {
            // Các lỗi khác
            response.sendRedirect("http://localhost:3000/vnpay/error");
        }

    }

    private String HmacSHA512(String key, String data) {
        try {
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] bytes = hmacSHA512.doFinal(data.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error while hashing data", ex);
        }
    }

    /**
     * Kiểm tra tính toàn vẹn của dữ liệu trả về từ VNPay bằng cách xác thực vnp_SecureHash.
     */
    private boolean isValidVNPayResponse(Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash"); // Loại bỏ hash để tính lại

        // Sắp xếp các tham số theo thứ tự key
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        // Tạo chuỗi dữ liệu để hash
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append("=").append(fieldValue).append("&");
            }
        }
        hashData.deleteCharAt(hashData.length() - 1);

        // Hash dữ liệu với key bí mật
        String computedHash = HmacSHA512(vnp_HashSecret, hashData.toString());

        // So sánh hash trả về với hash đã tính
        return computedHash.equals(vnp_SecureHash);
    }
}
