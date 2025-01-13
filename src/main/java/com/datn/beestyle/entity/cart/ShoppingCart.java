package com.datn.beestyle.entity.cart;

import com.datn.beestyle.entity.BaseEntity;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    String cartCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variant_id", referencedColumnName = "id")
    ProductVariant productVariant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "sale_price")
    BigDecimal salePrice;

    @Column(name = "discounted_price")
    BigDecimal discountedPrice;
}
