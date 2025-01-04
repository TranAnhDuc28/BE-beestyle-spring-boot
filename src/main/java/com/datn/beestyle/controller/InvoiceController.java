package com.datn.beestyle.controller;

import com.datn.beestyle.dto.invoice.InvoiceRequest;
import com.datn.beestyle.util.InvoicePDFExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoicePDFExporter invoicePDFExporter;

    public InvoiceController() {
        this.invoicePDFExporter = new InvoicePDFExporter(); // Tạo đối tượng trực tiếp
    }

    @PostMapping("/preview")
    public ResponseEntity<byte[]> previewInvoice(@RequestBody InvoiceRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
        headers.add("Content-Type", "application/pdf");
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            invoicePDFExporter.exportInvoice(request, outputStream); // Xuất file PDF

            // Kiểm tra xem có dữ liệu hay không
            byte[] pdfContent = outputStream.toByteArray();
            if (pdfContent.length == 0) {
                logger.error("Generated PDF content is empty.");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK); // Trả về PDF dưới dạng byte[]
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) {
        try {
            // Log dữ liệu nhận được
            logger.info("Request to generate invoice: {}", request);

            // Tạo ByteArrayOutputStream để ghi dữ liệu PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            invoicePDFExporter.exportInvoice(request, outputStream);

            // Kiểm tra xem có dữ liệu hay không
            byte[] pdfContent = outputStream.toByteArray();
            if (pdfContent.length == 0) {
                logger.error("Generated PDF content is empty.");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Trả về file PDF dưới dạng byte[]
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
            headers.add("Content-Type", "application/pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Log và xử lý lỗi chi tiết hơn
            logger.error("Error generating PDF: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
