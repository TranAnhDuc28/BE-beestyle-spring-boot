package com.datn.beestyle.util;

import com.datn.beestyle.dto.invoice.InvoiceRequest;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InvoicePDFExporter {

    public void exportInvoice(InvoiceRequest request, OutputStream out) {
        try {
            List<InvoiceRequest.Product> products = request.getProducts();
            if (products == null) {
                products = List.of(); // Khởi tạo danh sách rỗng nếu products là null
            }

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont(
                    Objects.requireNonNull(
                            getClass().getResource("/NotoSans-Regular.ttf")).toString(),
                    PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
            );

            // **1. Thêm tiêu đề "HÓA ĐƠN THANH TOÁN"**

            // Thông tin công ty
            document.add(new Paragraph("BEESTYLE")
                    .setFont(font).setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20).setBold());
            document.add(new Paragraph("Phương Canh, Từ Liêm, Hà Nội, Việt Nam")
                    .setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
            document.add(new Paragraph("SĐT: 0123456789").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10));

            // Đường kẻ ngang
            LineSeparator lineSeparator = new LineSeparator(new SolidLine());
            lineSeparator.setWidth(500);
            document.add(lineSeparator);

            Paragraph titleHeader = new Paragraph("HÓA ĐƠN THANH TOÁN")
                    .setFont(font)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(18) // Giảm cỡ chữ tiêu đề
                    .setBold();
            document.add(titleHeader);

            // Lấy chiều rộng của trang và trừ đi phần lề (nếu có)
            float pageWidth = pdfDoc.getDefaultPageSize().getWidth();
            float marginRight = 60; // Ví dụ: lề phải 36
            float marginLeft = 36;  // Ví dụ: lề trái 36

            // Tính chiều rộng thực tế sử dụng được
            float usableWidth = pageWidth - marginLeft - marginRight;

            // Tạo Paragraph
            Paragraph paragraph = new Paragraph();
            paragraph.setFont(font).setFontSize(10);

            // Đặt TabStops
            paragraph.addTabStops(new TabStop(usableWidth, TabAlignment.RIGHT));

            // Thêm nội dung
            paragraph.add("Mã hóa đơn: " + request.getOrderId()); // Nội dung bên trái
            paragraph.add(new Tab()); // Tab để đẩy nội dung tiếp theo sang bên phải
            paragraph.add("Khách hàng: " + request.getCustomerName()); // Nội dung bên phải

            // Thêm Paragraph vào tài liệu
            document.add(paragraph);

            document.add(new Paragraph("Thu ngân: ")
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));


            String orderDate = request.getOrderDate(); // Giả sử là "yyyy-MM-dd HH:mm:ss"
            // Sử dụng định dạng mới
            SimpleDateFormat fullDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            Date date = fullDateFormat.parse(orderDate); // Chuyển đổi thành đối tượng Date

            // Định dạng lại ngày và giờ
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            String formattedDate = dateFormat.format(date); // Định dạng ngày
            String formattedTime = timeFormat.format(date); // Định dạng giờ

            // Tạo Paragraph
            Paragraph dateTime = new Paragraph();
            dateTime.setFont(font).setFontSize(10);

            // Đặt TabStops
            dateTime.addTabStops(new TabStop(usableWidth, TabAlignment.RIGHT));

            // Thêm nội dung vào Paragraph
            dateTime.add("Ngày: " + formattedDate); // Nội dung bên trái
            dateTime.add(new Tab()); // Đẩy nội dung tiếp theo sang bên phải
            dateTime.add("Giờ: " + formattedTime); // Nội dung bên phải

            // Thêm Paragraph vào tài liệu
            document.add(dateTime);


            // **3. Dòng nội dung đơn hàng**
            document.add(new Paragraph("Nội dung đơn hàng: ").setFont(font).setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(10));

            // **5. Bảng chi tiết sản phẩm**
            document.add(new Paragraph("\n"));
            float[] columnWidths = {2, 1, 1, 2}; // Điều chỉnh độ rộng cột để vừa với trang
            Table table = new Table(columnWidths);
            table.setWidth(500);

            // Header
            table.addHeaderCell("Tên sản phẩm").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Giá").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Số lượng").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Thành tiền").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);

            double totalAmount = 0;
            int totalQuantity = 0; // Biến tổng số lượng
            Locale vietnamLocale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);

            for (InvoiceRequest.Product product : products) {
                table.addCell(product.getProductName()).setFont(font).setFontSize(10);
                table.addCell(currencyFormatter.format(product.getPrice())).setFont(font).setFontSize(10);
                table.addCell(String.valueOf(product.getQuantity())).setFont(font).setFontSize(10);
                double itemTotal = product.getQuantity() * product.getPrice();
                table.addCell(currencyFormatter.format(itemTotal)).setFont(font).setFontSize(10);
                totalAmount += itemTotal;
                totalQuantity += product.getQuantity();
            }
            document.add(table);

            // **Tổng số lượng**
            document.add(new Paragraph("Tổng số lượng sản phẩm: " + totalQuantity).setFont(font).setBold().setTextAlignment(TextAlignment.LEFT).setFontSize(10));

            // **6. Tổng tiền và các khoản khác**
            document.add(new Paragraph("Tổng tiền hàng: " + currencyFormatter.format(totalAmount)).setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Giảm giá: 0 đ").setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Phí ship: 0 đ").setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Tổng hóa đơn: " + currencyFormatter.format(totalAmount))
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));

            // **7. Thông tin thanh toán**
            document.add(new Paragraph("Phương thức thanh toán: " + request.getPaymentMethod())
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));

            // **8. Tổng thanh toán**
            Paragraph total = new Paragraph("Tổng thanh toán: " + currencyFormatter.format(totalAmount))
                    .setFont(font).setTextAlignment(TextAlignment.RIGHT).setFontSize(12).setBold();
            document.add(total);

            // **9. Lời cảm ơn**
            document.add(new Paragraph("Cảm ơn quý khách đã mua hàng!").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
