package com.datn.beestyle.entity.order;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.Auditable;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Table(name = "`order`")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends Auditable<Long> {

    @Column(name = "order_tracking_number")
    String orderTrackingNumber;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "shipping_fee")
    BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "total_amount")
    BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp paymentDate;

    @Column(name = "payment_method")
    @Enumerated(EnumType.ORDINAL)
    PaymentMethod paymentMethod;

    @Column(name = "order_channel")
    @Enumerated(EnumType.ORDINAL)
    OrderChannel orderChannel;

<<<<<<< HEAD
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    OrderStatus status;
=======
    @Column(name = "order_status")
    int orderStatus;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84

    @Column(name = "note")
    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    Voucher voucher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    Address shippingAddress;

    public void initializeOrder(String trackingNumber, Long createdBy) {
        this.voucher = null;
        this.shippingAddress = null;
        this.paymentDate = null;
        this.customer = null;
        this.phoneNumber = "";
        this.totalAmount = BigDecimal.ZERO;
        this.shippingFee = BigDecimal.ZERO;
        this.paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
        this.status = OrderStatus.PENDING;
        this.orderChannel = OrderChannel.OFFLINE;
        this.orderTrackingNumber = trackingNumber;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(createdBy);
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = ALL, fetch = FetchType.LAZY)
    List<OrderItem> orderItems = new ArrayList<>();
}
