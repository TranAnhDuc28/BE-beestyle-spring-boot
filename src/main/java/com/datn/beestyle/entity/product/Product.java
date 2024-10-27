package com.datn.beestyle.entity.product;

import com.datn.beestyle.entity.Auditable;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    short status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Material material;

    @JsonManagedReference
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
