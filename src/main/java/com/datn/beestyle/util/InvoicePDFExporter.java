package com.datn.beestyle.util;

import com.datn.beestyle.dto.invoice.InvoiceRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.enums.DiscountType;
import com.datn.beestyle.repository.OrderItemRepository;
import com.datn.beestyle.repository.OrderRepository;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class InvoicePDFExporter {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public void exportInvoice(Long orderId, OutputStream out) {
        try {
            List<OrderResponse> listOrder = orderRepository.findOrderById(orderId);
            if (listOrder == null || listOrder.isEmpty()) {
                throw new IllegalArgumentException("Không có hóa đơn với ID: " + orderId);
            }
            List<OrderItemResponse> listOrderItem = orderItemRepository.findAllByOrderId(orderId);
            if (listOrderItem == null) {
                listOrderItem = List.of(); // Khởi tạo danh sách rỗng nếu products là null
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
                    .setFontSize(24).setBold()
                    .setMarginTop(0) // Khoảng cách trên đoạn văn
                    .setMarginBottom(5)); // Khoảng cách dưới đoạn văn

            document.add(new Paragraph("SĐT: 0123456789")
                    .setFont(font).setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginTop(0) // Khoảng cách trên đoạn văn
                    .setMarginBottom(5)); // Khoảng cách dưới đoạn văn

            document.add(new Paragraph("Địa chỉ: Phương Canh, Nam Từ Liêm, Hà Nội")
                    .setFont(font).setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginTop(0) // Khoảng cách trên đoạn văn
                    .setMarginBottom(10)); // Khoảng cách dưới đoạn văn

            // Đường kẻ ngang
            LineSeparator lineSeparator = new LineSeparator(new SolidLine());
            lineSeparator.setWidth(500);
            document.add(lineSeparator);

            Paragraph titleHeader = new Paragraph("HÓA ĐƠN BÁN HÀNG")
                    .setFont(font)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20) // Giảm cỡ chữ tiêu đề
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
            OrderResponse order = listOrder.get(0);

            /// Thêm nội dung bên trái (Tên khách hàng)
            paragraph.add("Tên khách hàng: " + order.getCustomerName());
            paragraph.add(new Tab());
            paragraph.add("Mã hóa đơn: " + order.getOrderTrackingNumber());
            paragraph.add("\n");
//            paragraph.add("Địa chỉ: " + order.getShippingAddress().getAddressName()+","
//                    +order.getShippingAddress().getDistrict()+","
//                    +order.getShippingAddress().getCity());
            paragraph.add(new Tab());
            paragraph.add("N: " + order.getShippingAddressId());
            paragraph.add("Ngày tạo: " + order.getCreatedAt());
            paragraph.add("\n");
            paragraph.add("Số điện thoại: " + order.getPhoneNumber());
// Thêm vào tài liệu
            document.add(paragraph);

            // **3. Dòng nội dung đơn hàng**
            double totalAmount = 0;
            int totalQuantity = 0; // Biến tổng số lượng

// **Hiển thị dòng "Nội dung đơn hàng" trước bảng**
            document.add(new Paragraph("Nội dung đơn hàng: ")
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setBold().setFontSize(14));

// **5. Bảng chi tiết sản phẩm**
            document.add(new Paragraph("\n"));
            float[] columnWidths = {0.5f, 2f, 1.5f, 1f, 1.5f}; // Chia độ rộng cột: Cột "Tên sản phẩm" chiếm nhiều không gian hơn
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));  // Đặt tổng chiều rộng bảng bằng 100% chiều rộng trang


// Header
            table.addHeaderCell("STT").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Tên sản phẩm").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Đơn giá").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Số lượng").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            table.addHeaderCell("Thành tiền").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10);

            Locale vietnamLocale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);

            int stt = 1;

            for (OrderItemResponse orderItem : listOrderItem) {
                // Thêm thông tin sản phẩm vào bảng
                table.addCell(String.valueOf(stt++)) // Thêm số thứ tự
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(10);
                table.addCell(orderItem.getProductName()).setFont(font).setFontSize(10);
                table.addCell(currencyFormatter.format(orderItem.getSalePrice())).setFont(font).setFontSize(10);
                table.addCell(String.valueOf(orderItem.getQuantity())).setFont(font).setFontSize(10);

                BigDecimal salePrice = orderItem.getSalePrice();  // Giá bán dưới dạng BigDecimal
                BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());  // Số lượng dưới dạng BigDecimal

                // Tính tổng tiền cho một sản phẩm
                BigDecimal itemTotal = salePrice.multiply(quantity);
                table.addCell(currencyFormatter.format(itemTotal)).setFont(font).setFontSize(10);

                // Cập nhật tổng số tiền và số lượng
                totalAmount += itemTotal.doubleValue();  // Convert BigDecimal to double for totalAmount
                totalQuantity += orderItem.getQuantity();
            }

            // Thêm bảng vào tài liệu
            document.add(table);


            // **Tổng số lượng**
            document.add(new Paragraph("Tổng số lượng sản phẩm: " + totalQuantity).setFont(font).setBold().setTextAlignment(TextAlignment.LEFT).setFontSize(10));
//            if (order.getVoucherInfo().getDiscountType() == ) {
//                // Giảm giá tiền mặt
//                discountAmount = order.getDis(); // Giảm giá bằng số tiền (ví dụ: 50,000 VNĐ)
//                totalAmount -= discountAmount;  // Trừ vào tổng tiền
//            } else if (order.getVoucherInfo().getDiscountType() == DiscountType.PERCENTAGE) {
//                // Giảm giá phần trăm
//                double discountPercentage = order.getDis(); // Giảm giá theo phần trăm
//                double discountAmountPercentage = totalAmount * (discountPercentage / 100);  // Tính giảm giá theo phần trăm
//
//                // Giảm giá tối đa (ví dụ: 200,000 VNĐ)
//                double maxDiscount = 200000;  // Giảm tối đa 200,000 VNĐ
//                discountAmount = Math.min(discountAmountPercentage, maxDiscount);  // Giới hạn giảm giá không vượt quá maxDiscount
//
//                totalAmount -= discountAmount;  // Trừ vào tổng tiền
//            }
            // **6. Tổng tiền và các khoản khác**
            document.add(new Paragraph("Tổng tiền hàng: " + currencyFormatter.format(totalAmount)).setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Giảm giá:"+ order.getVoucherInfo()).setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Phí ship: 0 đ").setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));
            document.add(new Paragraph("Tổng hóa đơn: " + currencyFormatter.format(totalAmount))
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));

            // **7. Thông tin thanh toán**
            document.add(new Paragraph("Phương thức thanh toán: " + order.getPaymentMethod())
                    .setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(10));

            // **8. Tổng thanh toán**
            Paragraph total = new Paragraph("Tổng thanh toán: " + currencyFormatter.format(totalAmount))
                    .setFont(font).setTextAlignment(TextAlignment.RIGHT).setFontSize(12).setBold();
            document.add(total);

            // **9. Lời cảm ơn**
            document.add(new Paragraph("Cảm ơn quý khách đã mua hàng!").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(10));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
