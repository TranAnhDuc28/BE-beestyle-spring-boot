package com.datn.beestyle.service.mail;

import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.user.Staff;
import com.datn.beestyle.repository.CustomerRepository;
import com.datn.beestyle.repository.OrderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Value("${spring.mail.from}")
    private String emailFrom;

    public String sendMail(String recipients, String subject, String content, MultipartFile[] files) {
        try {
            log.info("Sending email to: {}", recipients);

            // Tạo email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Thiết lập thông tin người gửi
            helper.setFrom(emailFrom, "Beestyle");

            // Đặt danh sách người nhận
            if (recipients.contains(",")) {
                helper.setTo(InternetAddress.parse(recipients));
            } else {
                helper.setTo(recipients);
            }

            // Đính kèm file nếu có
            if (files != null) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }

            // Thiết lập nội dung email
            helper.setSubject(subject);
            helper.setText(content, true);

            // Gửi email
            mailSender.send(message);

            log.info("Email sent successfully to: {}", recipients);
            return "Email sent successfully";
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error sending email to {}: {}", recipients, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
    public String sendThankYouEmail(Long id, MultipartFile[] files) throws MessagingException {

        List<OrderResponse> listOrder = orderRepository.findOrderById(id);
        if (listOrder == null || listOrder.isEmpty()) {
            throw new IllegalArgumentException("Không có hóa đơn với ID: " + id);
        }
        // Lấy ra order
        OrderResponse order = listOrder.get(0);
        Customer customer = customerRepository.getReferenceById(order.getCustomerId());
        try {


            // Tạo dữ liệu gửi vào template
            Context context = new Context();
            context.setVariable("customerName", order.getCustomerName());

            // Xử lý template để tạo nội dung HTML
            String htmlContent = templateEngine.process("thankYouEmail", context);


            // Tạo và cấu hình email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Đính kèm file nếu có
            if (files != null) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }
            helper.setFrom(emailFrom, "Beestyle");
            helper.setTo(customer.getEmail());
            helper.setSubject("Thank You for Your Purchase!");
            helper.setText(htmlContent, true);

            // Gửi email
            mailSender.send(message);
            return "Email sent successfully";
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error sending email to {}: {}", customer.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public String sendLoginStaffEmail(Staff staff) throws MessagingException {

        try {
            // Tạo dữ liệu gửi vào template
            Context context = new Context();
            context.setVariable("employeeName", staff.getFullName());
            context.setVariable("email", staff.getEmail());
            context.setVariable("password", staff.getPassword());
            context.setVariable("loginUrl", "http://localhost:3000/login");



            // Xử lý template để tạo nội dung HTML
            String htmlContent = templateEngine.process("loginStaffEmail", context);

            // Tạo và cấu hình email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailFrom, "Beestyle");
            helper.setTo(staff.getEmail());
            helper.setSubject("Wellcome to Beestyle!");
            helper.setText(htmlContent, true);

            // Gửi email
            mailSender.send(message);
            return "Email sent successfully";
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error sending email to {}: {}", staff.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
