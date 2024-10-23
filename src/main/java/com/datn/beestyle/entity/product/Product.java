package com.datn.beestyle.entity.product;

import com.datn.beestyle.entity.Auditable;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;


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
    short gender;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    short status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {ALL})
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {ALL})
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {ALL})
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    Material material;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {ALL})
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
