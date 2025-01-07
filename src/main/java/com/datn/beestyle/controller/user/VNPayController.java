package com.datn.beestyle.controller.user;

import com.datn.beestyle.config.VNPayConfig;
import com.datn.beestyle.dto.vnpay.PaymentRequest;
import com.datn.beestyle.util.AppUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class VNPayController extends VNPayConfig {

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestBody PaymentRequest request
    ) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan don hang: " + request.getOrderId();
        String vnp_OrderType = "billpayment";
        String vnp_TxnRef = AppUtils.generateOrderTrackingNumber();
        String vnp_IpAddr = request.getIpAddress();
        Long amount = request.getAmount() * 100;
        String vnp_Amount = String.valueOf(amount);

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
    public void handleVNPayReturn(
            @RequestParam Map<String, String> params,
            HttpServletResponse response
    ) throws IOException {
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
            String orderNumber = params.get("vnp_TxnRef");
            response.sendRedirect("http://localhost:3000/vnpay/success?order=" + orderNumber);
        } else if ("24".equals(vnp_ResponseCode)) {
            // Người dùng hủy giao dịch
            response.sendRedirect("http://localhost:3000/vnpay/cancel");
        } else {
            // Các lỗi khác
            response.sendRedirect("http://localhost:3000/vnpay/error");
        }
    }

    @PostMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestBody Map<String, Object> pendingOrderData) {
        System.out.println(pendingOrderData.toString());
        return ResponseEntity.ok().body(pendingOrderData);
    }
}