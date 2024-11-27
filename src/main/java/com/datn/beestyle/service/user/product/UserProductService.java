package com.datn.beestyle.service.user.product;

import com.datn.beestyle.dto.product.user.UserProductResponse;
import com.datn.beestyle.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserProductService {
    private final ProductRepository productRepository;

    public UserProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<UserProductResponse> getFeaturedProducts(Integer q) {
        return this.productRepository.getProductForUser(PageRequest.of(0, 8), q);
    }

    public Page<UserProductResponse> getSellerProducts() {
        return this.productRepository.getSellingProducts(PageRequest.of(0, 10));
    }

    public Page<UserProductResponse> getOfferProductUser() {
        return this.productRepository.getOfferingProducts(PageRequest.of(0, 9));
    }

    public List<UserProductResponse> findProductUser() {
        return this.productRepository.findAllProductUser();
    }
}
