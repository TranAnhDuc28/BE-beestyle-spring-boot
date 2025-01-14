package com.datn.beestyle.entity;

import com.datn.beestyle.entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "shopping_cart")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCart extends BaseEntity<Long> {

    @Column(name = "cart_code")
    UUID cartCode;

    @Column(name = "quantity")
    BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "sale_price")
    BigDecimal salePrice = BigDecimal.ZERO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;
}
