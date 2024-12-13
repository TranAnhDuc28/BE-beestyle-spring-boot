package com.datn.beestyle.repository.product;

import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.user.UserProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {

    Page<ProductResponse> filterProduct(Pageable pageable, List<Integer> categoryIds, Integer genderProduct, List<Integer> brandIds,
                                        List<Integer> materialIds, BigDecimal minPrice, BigDecimal maxPrice, Integer status);
}
