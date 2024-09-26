package com.datn.beestyle.entity;

import com.datn.beestyle.entity.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntity<Integer> {

    @Column(name = "category_name")
    String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Category> categoryChildren = new ArrayList<>();

    @Column(name = "description")
    String description;

    @Column(name = "deleted")
    boolean deleted;

    public void addCategoryChildren(Category children) {
        if (children != null) {
            if (categoryChildren == null) {
                categoryChildren = new ArrayList<>();
            }
            categoryChildren.add(children);
            children.setParent(this);
        }
    }
}
