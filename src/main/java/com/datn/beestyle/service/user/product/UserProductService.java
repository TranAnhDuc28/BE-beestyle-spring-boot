package com.datn.beestyle.service.user.product;

import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.image.ImageReponse;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.user.UserProductResponse;
import com.datn.beestyle.repository.ColorRepository;
import com.datn.beestyle.repository.ImageRepository;
import com.datn.beestyle.repository.ProductVariantRepository;
import com.datn.beestyle.repository.SizeRepository;
import com.datn.beestyle.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class UserProductService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ImageRepository imageRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    public UserProductService(ProductRepository productRepository, ProductVariantRepository productVariantRepository, ImageRepository imageRepository, ColorRepository colorRepository, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.imageRepository = imageRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
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

    public List<ImageReponse> getImageProducVariant(Long productId) {
        return this.imageRepository.getImageByProductVariant(productId);
    }

    public List<ColorResponse> getAllColorLists(Long productId) {
        return this.colorRepository.findAllByProductVariant(productId);
    }

    public List<SizeResponse> getAllSizeByPdAndColor(Long productId, String colorCode) {
        return this.sizeRepository.findAllByProductVariant(productId, colorCode);
    }

    public UserProductResponse getProductVariantUser(Long productId, String colorCode, Long sizeId) {
        List<Object[]> results = productVariantRepository.getProductVariantData(productId, colorCode, sizeId);

        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("Product variant not found");
        }

        Object[] result = results.get(0);

        Long id = (Long) result[0];
        String productCode = (String) result[1];
        String productName = (String) result[2];
        BigDecimal originalPrice = (BigDecimal) result[3];
        BigDecimal salePrice = (BigDecimal) result[4];
        String sku = (String) result[5];
        String categoryName = (String) result[6];
        String brandName = (String) result[7];
        Integer quantity = (Integer) result[8];
        String colorCodeResult = (String) result[9];
        String colorName = (String) result[10];
        String sizeName = (String) result[11];
        String description = (String) result[12];

        return new UserProductResponse(
                id, productCode, productName, originalPrice, salePrice,
                sku, categoryName, brandName, quantity, colorCodeResult,
                colorName, sizeName, description
        );
    }

}
