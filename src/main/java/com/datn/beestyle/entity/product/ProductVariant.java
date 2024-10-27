package com.datn.beestyle.entity.product;

import com.datn.beestyle.entity.Auditable;
import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.entity.product.attributes.Size;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Table(name = "product_variant")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant extends Auditable<Long> {

    @Column(name = "sku")
    String sku;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Size size;

    @Column(name = "original_price")
    BigDecimal originalPrice = BigDecimal.ZERO;

    @Column(name = "sale_price")
    BigDecimal salePrice = BigDecimal.ZERO;

    @Column(name = "quantity_in_stock")
    int quantityInStock;

    @Column(name = "status")
    int status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "promotion_id", referencedColumnName = "id")
    Promotion promotion;
}
