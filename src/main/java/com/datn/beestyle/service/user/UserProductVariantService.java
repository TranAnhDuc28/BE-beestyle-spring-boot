package com.datn.beestyle.service.user;

import com.datn.beestyle.dto.cart.CartCheckRequest;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.image.ImageReponse;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.repository.ColorRepository;
import com.datn.beestyle.repository.ImageRepository;
import com.datn.beestyle.repository.ProductVariantRepository;
import com.datn.beestyle.repository.SizeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ImageRepository imageRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    public UserProductVariantService(
            ProductVariantRepository productVariantRepository,
            ImageRepository imageRepository, ColorRepository colorRepository,
            SizeRepository sizeRepository
    ) {
        this.productVariantRepository = productVariantRepository;
        this.imageRepository = imageRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    public ProductVariantResponse getProductVariantUser(Long productId, String colorCode, Long sizeId) {
        List<Object[]> results = productVariantRepository.getProductVariantData(productId, colorCode, sizeId);

        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("Product variant not found");
        }

        Object[] result = results.get(0);

        Long id = (Long) result[0];
        Long productIdR = (Long) result[1];
        String productCode = (String) result[2];
        String productName = (String) result[3];
        BigDecimal salePrice = (BigDecimal) result[4];
        BigDecimal discountPrice = (BigDecimal) result[5];
        Integer discountValue = (Integer) result[6];
        String sku = (String) result[7];
        String categoryName = (String) result[8];
        String brandName = (String) result[9];
        Integer quantity = (Integer) result[10];
        String colorCodeResult = (String) result[11];
        String colorName = (String) result[12];
        String sizeName = (String) result[13];
        String description = (String) result[14];

        List<ImageReponse> images = productId != null ?
                this.imageRepository.getImageByProductIds(productId) : null;

        return new ProductVariantResponse(
                id, productIdR, productCode, productName, salePrice, discountPrice,
                discountValue, sku, categoryName, brandName, quantity,
                colorCodeResult, colorName, sizeName, description, images
        );
    }

    public List<ColorResponse> getAllColorLists(Long productId) {
        return this.colorRepository.findAllByProductVariant(productId);
    }

    public List<SizeResponse> getAllSizeByPdAndColor(Long productId, String colorCode) {
        return this.sizeRepository.findAllByProductVariant(productId, colorCode);
    }

    public List<ProductVariantResponse> getProductVariantByIds(
            List<CartCheckRequest> cartItemsRequest
    ) {
        List<Long> productVariantIds = cartItemsRequest.stream()
                .filter(cart -> cart != null && cart.getId() != null)
                .map(CartCheckRequest::getId)
                .distinct()
                .collect(Collectors.toList());

        if (productVariantIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Object[]> results = this.productVariantRepository.getProductVariantDataByIds(productVariantIds);

        Map<Long, Object[]> resultsMap = results.stream()
                .collect(Collectors.toMap(result -> (Long) result[0], result -> result));

        List<ProductVariantResponse> productVariants = new ArrayList<>();
        for (CartCheckRequest cart : cartItemsRequest) {
            if (cart != null && cart.getId() != null) {
                Object[] result = resultsMap.get(cart.getId());
                if (result != null) {
                    try {
                        Long id = (Long) result[0];
                        Long productId = (Long) result[1];
                        String productName = (String) result[2];
                        BigDecimal salePrice = (BigDecimal) result[3];
                        BigDecimal discountPrice = (BigDecimal) result[4];
                        Integer discountValue = (Integer) result[5];
                        String sku = (String) result[6];
                        Integer quantity = (Integer) result[7];
                        String colorName = (String) result[8];
                        String sizeName = (String) result[9];

                        ProductVariantResponse pv = new ProductVariantResponse(
                                id, productId, productName, salePrice, discountPrice,
                                discountValue, sku, quantity, colorName, sizeName, null
                        );
                        productVariants.add(pv);
                    } catch (ClassCastException e) {
                        System.err.println("Lỗi ép kiểu dữ liệu cho ProductVariant ID: " + cart.getId() +
                                ". Chi tiết: " + e.getMessage());
                    }
                } else {
                    System.err.println("Không tìm thấy dữ liệu cho ProductVariant ID: " + cart.getId());
                }
            }
        }
        return productVariants;
    }
}
