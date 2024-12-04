package com.datn.beestyle.util;

import com.datn.beestyle.dto.invoice.InvoiceRequest;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InvoicePDFExporter {

    public void exportInvoice(InvoiceRequest request, OutputStream out) {
        try {
            // Kiểm tra nếu products là null thì khởi tạo nó là danh sách rỗng
            List<InvoiceRequest.Product> products = request.getProducts();
            if (products == null) {
                products = List.of(); // Khởi tạo danh sách rỗng nếu products là null
            }

            // Tạo đối tượng PdfWriter và PdfDocument
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Tạo font với file TTF để hỗ trợ tiếng Việt (ví dụ sử dụng Noto Sans)
            PdfFont font = PdfFontFactory.createFont(getClass().getResource("/NotoSans-Regular.ttf").toString(), PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // **1. Thêm tiêu đề**
            Paragraph beestyleTitle = new Paragraph("BEESTYLE")
                    .setFont(font) // Sử dụng font hỗ trợ tiếng Việt
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30)  // Cỡ chữ lớn hơn cho tên thương hiệu
                    .setBold();
            document.add(beestyleTitle);

            Paragraph title = new Paragraph("HÓA ĐƠN MUA HÀNG")
                    .setFont(font) // Sử dụng font hỗ trợ tiếng Việt
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold();
            document.add(title);

            // **2. Thông tin đơn hàng**
            document.add(new Paragraph("\n")); // Thêm khoảng trống
            document.add(new Paragraph("Mã đơn hàng: " + request.getOrderId()).setFont(font).setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Tên khách hàng: " + request.getCustomerName()).setFont(font).setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Thời gian: " + request.getOrderDate()).setFont(font).setTextAlignment(TextAlignment.LEFT));

            // **3. Thêm địa chỉ quán**
            document.add(new Paragraph("\n")); // Thêm khoảng trống
            document.add(new Paragraph("Địa chỉ quán: 123 Đường ABC, Quận 1, TP.HCM").setFont(font).setTextAlignment(TextAlignment.LEFT));

            // **4. Bảng chi tiết sản phẩm**
            document.add(new Paragraph("\n")); // Thêm khoảng trống
            float[] columnWidths = {3, 1, 2, 2}; // Độ rộng các cột
            Table table = new Table(columnWidths);

// Cài đặt bảng chiếm toàn bộ chiều rộng trang
            table.setWidth(500);

// Header
            table.addHeaderCell("Tên sản phẩm").setFont(font).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell("Số lượng").setFont(font).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell("Đơn giá").setFont(font).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell("Thành tiền").setFont(font).setTextAlignment(TextAlignment.CENTER);

            double totalAmount = 0;
            Locale vietnamLocale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);

// Thêm từng sản phẩm vào bảng
            for (InvoiceRequest.Product product : products) {
                table.addCell(product.getProductName()).setFont(font);
                table.addCell(String.valueOf(product.getQuantity())).setFont(font);
                table.addCell(currencyFormatter.format(product.getPrice())).setFont(font);
                double itemTotal = product.getQuantity() * product.getPrice();
                table.addCell(currencyFormatter.format(itemTotal)).setFont(font);
                totalAmount += itemTotal;
            }
            document.add(table);

            // **5. Tổng tiền**
            document.add(new Paragraph("\n")); // Thêm khoảng trống
            Paragraph total = new Paragraph("Tổng tiền: " + currencyFormatter.format(totalAmount))
                    .setFont(font)  // Đảm bảo font này được áp dụng cho phần tổng tiền
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold()
                    .setFontSize(14);
            document.add(total);

            // **6. Lời cảm ơn**
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Cảm ơn quý khách đã mua hàng!").setFont(font).setTextAlignment(TextAlignment.CENTER));

            // Đóng tài liệu
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
