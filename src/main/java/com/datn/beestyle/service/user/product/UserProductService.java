package com.datn.beestyle.service.user.product;

import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.image.ImageReponse;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.repository.ColorRepository;
import com.datn.beestyle.repository.ImageRepository;
import com.datn.beestyle.repository.ProductVariantRepository;
import com.datn.beestyle.repository.SizeRepository;
import com.datn.beestyle.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    private Page<ProductResponse> getProducts(Pageable pageable, Supplier<Page<Object[]>> dataSupplier) {
        Page<Object[]> rawDataPage = dataSupplier.get();
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Object[] row : rawDataPage.getContent()) {
            Long productId = ((Number) row[0]).longValue();
            String productName = (String) row[1];
            String imageUrl = (String) row[2];
            BigDecimal maxSalePrice = (BigDecimal) row[3];
            BigDecimal minDiscountedPrice = (BigDecimal) row[4];
            BigDecimal discountValue = row[5] == null ? BigDecimal.ZERO : new BigDecimal(row[5].toString());

            productResponses.add(
                    new ProductResponse(productId, productName, imageUrl, maxSalePrice, minDiscountedPrice, discountValue)
            );
        }

        return new PageImpl<>(productResponses, pageable, rawDataPage.getTotalElements());
    }

    public Page<ProductResponse> getFeaturedProducts(Pageable pageable, Integer q) {
        return getProducts(pageable, () -> productRepository.getFeaturedProductsData(q, pageable));
    }

    public Page<ProductResponse> getTopSellingProducts(Pageable pageable) {
        return getProducts(pageable, () -> productRepository.getTopSellingProductsData(pageable));
    }

    public Page<ProductResponse> getOfferProducts(Pageable pageable) {
        return getProducts(pageable, () -> productRepository.getOfferingProductsData(pageable));
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

    public ProductVariantResponse getProductVariantUser(Long productId, String colorCode, Long sizeId) {
        List<Object[]> results = productVariantRepository.getProductVariantData(productId, colorCode, sizeId);

        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("Product variant not found");
        }

        Object[] result = results.get(0);

        Long id = (Long) result[0];
        String productCode = (String) result[1];
        String productName = (String) result[2];
        BigDecimal salePrice = (BigDecimal) result[3];
        BigDecimal discountPrice = (BigDecimal) result[4];
        Integer discountValue = (Integer) result[5];
        String sku = (String) result[6];
        String categoryName = (String) result[7];
        String brandName = (String) result[8];
        Integer quantity = (Integer) result[9];
        String colorCodeResult = (String) result[10];
        String colorName = (String) result[11];
        String sizeName = (String) result[12];
        String description = (String) result[13];

        return new ProductVariantResponse(
                id, productCode, productName, salePrice, discountPrice,
                discountValue, sku, categoryName, brandName, quantity,
                colorCodeResult, colorName, sizeName, description
        );
    }

}
