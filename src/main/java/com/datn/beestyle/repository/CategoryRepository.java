package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.category.CategoryResponse;
import com.datn.beestyle.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends IGenericRepository<Category, Integer> {
    @Query(value = """
                    WITH RECURSIVE category_hierarchy AS (
                    SELECT id, category_name, slug, parent_category_id, level, priority
                    FROM category
                    WHERE deleted = FALSE AND parent_category_id IS NULL

                    UNION ALL
                        
                    SELECT c.id, c.category_name, c.slug, c.parent_category_id, c.level, c.priority
                    FROM category AS c
                    INNER JOIN category_hierarchy ch ON c.parent_category_id = ch.id
                    WHERE c.deleted = FALSE AND c.level <= 3
                    )
                    SELECT  id, category_name, slug, parent_category_id
                    -- ,level, priority 
                    FROM category_hierarchy
                    ORDER BY level, priority;
                """, nativeQuery = true)
    List<Object[]> findAllForUser();

    Category findBySlug(String slug);

    List<Category> findByParentCategory(Category category);
}

