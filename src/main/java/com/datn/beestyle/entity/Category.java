package com.datn.beestyle.entity;

import com.datn.beestyle.entity.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    Category parentCategory ;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Category> categoryChildren = new ArrayList<>();

    @Column(name = "slug")
    String slug;

    @Column(name = "level")
    int level;

    @Column(name = "priority")
    int priority;

    @Column(name = "deleted")
    boolean deleted;

    public void addCategoryChildren(Category children) {
        if (children != null) {
            if (categoryChildren == null) {
                categoryChildren = new ArrayList<>();
            }
            categoryChildren.add(children);
            children.setParentCategory(this);
        }
    }
}
