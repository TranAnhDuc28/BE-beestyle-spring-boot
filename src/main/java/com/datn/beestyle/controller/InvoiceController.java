package com.datn.beestyle.controller;

import com.datn.beestyle.dto.invoice.InvoiceRequest;
import com.datn.beestyle.util.InvoicePDFExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoicePDFExporter invoicePDFExporter;

    public InvoiceController() {
        this.invoicePDFExporter = new InvoicePDFExporter(); // Khởi tạo trực tiếp
    }

    /**
     * API xem trước hóa đơn dưới dạng PDF
     */
    @PostMapping("/preview")
    public ResponseEntity<byte[]> previewInvoice(@RequestBody InvoiceRequest request) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            invoicePDFExporter.exportInvoice(request, outputStream);
            byte[] pdfBytes = outputStream.toByteArray();

            if (pdfBytes.length == 0) {
                logger.warn("Preview PDF content is empty.");
                return ResponseEntity.noContent().build();
            }

            // Tên file với ID đơn hàng và ngày tháng
            String fileName = "Invoice_" + request.getOrderId()  + ".pdf";

            logger.info("Invoice preview generated successfully for order: {}", request.getOrderId());

            // Thiết lập các header cho response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            headers.add("Access-Control-Allow-Headers", "Content-Disposition");
//            headers.add("Access-Control-Allow-Origin", "*");  // Đảm bảo CORS nếu frontend và backend ở domain khác

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error generating invoice preview: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * API tạo file hóa đơn PDF và tải về
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) {
        try {
            logger.info("Request to generate invoice for order: {}", request.getOrderId());

            // Xuất file PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            invoicePDFExporter.exportInvoice(request, outputStream);

            byte[] pdfContent = outputStream.toByteArray();

            if (pdfContent.length == 0) {
                logger.error("Generated PDF content is empty for order: {}", request.getOrderId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // Đặt tên file theo mã hóa đơn
            String fileName = "Invoice_" + request.getOrderId() + ".pdf";
            logger.info("Invoice generated successfully: {}", fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error generating invoice PDF for order: {}", request.getOrderId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
