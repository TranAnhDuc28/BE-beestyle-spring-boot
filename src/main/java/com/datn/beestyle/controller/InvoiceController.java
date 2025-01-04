//package com.datn.beestyle.controller;
//
//import com.datn.beestyle.dto.invoice.InvoiceRequest;
//import com.datn.beestyle.util.InvoicePDFExporter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//
//@RestController
//@RequestMapping("/invoice")
//public class InvoiceController {
//
//    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
//    private final InvoicePDFExporter invoicePDFExporter;
//
//    public InvoiceController() {
//        this.invoicePDFExporter = new InvoicePDFExporter(); // Tạo đối tượng trực tiếp
//    }
//
//    @PostMapping("/preview")
//    public ResponseEntity<byte[]> previewInvoice(@RequestBody InvoiceRequest request) {
//        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            invoicePDFExporter.exportInvoice(id, outputStream); // Xuất file PDF
//            byte[] pdfBytes = outputStream.toByteArray();
//            return ResponseEntity.ok().body(pdfBytes); // Trả về PDF dưới dạng byte[]
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @PostMapping("/generate")
//    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) {
//        try {
//            // Log dữ liệu nhận được
//            logger.info("Request to generate invoice: {}", request);
//
//            // Tạo ByteArrayOutputStream để ghi dữ liệu PDF
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            invoicePDFExporter.exportInvoice(request, outputStream);
//
//            // Kiểm tra xem có dữ liệu hay không
//            byte[] pdfContent = outputStream.toByteArray();
//            if (pdfContent.length == 0) {
//                logger.error("Generated PDF content is empty.");
//                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            // Trả về file PDF dưới dạng byte[]
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
//            headers.add("Content-Type", "application/pdf");
//
//            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            // Log và xử lý lỗi chi tiết hơn
//            logger.error("Error generating PDF: {}", e.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
package com.datn.beestyle.controller;

import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.repository.OrderItemRepository;
import com.datn.beestyle.util.InvoicePDFExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
public class InvoiceController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoicePDFExporter invoicePDFExporter;
    /**
     * API để xem trước hóa đơn PDF
     *
     * @param orderId ID của đơn hàng
     * @return ResponseEntity chứa file PDF
     */
    @GetMapping("/invoice/preview/{orderId}")
    public ResponseEntity<byte[]> previewInvoice(@PathVariable Long orderId) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Gọi phương thức để xuất hóa đơn PDF
            invoicePDFExporter.exportInvoice(orderId, byteArrayOutputStream);

            // Trả về file PDF
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf") // Loại file PDF
                    .body(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * API để xuất hóa đơn PDF từ thông tin đơn hàng
     *
     * @param orderId ID của đơn hàng
     * @return ResponseEntity chứa file PDF
     */
    @GetMapping("/invoice/{orderId}")
    public ResponseEntity<byte[]> exportInvoice(@PathVariable Long orderId) {
        try {
            // Tạo OutputStream để chứa dữ liệu PDF
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Gọi phương thức để xuất hóa đơn PDF
            invoicePDFExporter.exportInvoice(orderId, byteArrayOutputStream);

            // Tạo header cho file trả về
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice_" + orderId + ".pdf");

            // Trả về file PDF dưới dạng byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(byteArrayOutputStream.toByteArray());

        } catch (Exception e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
