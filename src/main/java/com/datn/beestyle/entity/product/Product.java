package com.datn.beestyle.entity.product;

import com.datn.beestyle.entity.Auditable;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Table(name = "product")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends Auditable<Long> {

    @Column(name = "product_name")
    String productName;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "min_price")
    BigDecimal minPrice;

    @Column(name = "max_price")
    BigDecimal maxPrice;

    @Column(name = "description")
    String description;

    @Column(name = "deleted")
    boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    Material material;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<ProductVariant> productVariants = new ArrayList<>();

    public void addProductVariant(ProductVariant productVariant) {
        if (productVariant != null) {
            if (productVariants == null) {
                productVariants = new ArrayList<>();
            }
            productVariants.add(productVariant);
            productVariant.setProduct(this);
        }
    }

}
