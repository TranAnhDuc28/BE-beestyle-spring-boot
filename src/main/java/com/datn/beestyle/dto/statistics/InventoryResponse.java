package com.datn.beestyle.dto.statistics;

import com.datn.beestyle.dto.product.attributes.image.ImageReponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryResponse {
    Long id;
    Long productId;
    String sku;
    String productName;
    Long colorId;
    String colorCode;
    String colorName;
    Long sizeId;
    String sizeName;
    Integer quantityInStock;
    String imageUrl;

    public InventoryResponse(Long id, Long productId, String productName,
                             String sku, Long colorId,String colorCode, String colorName,
                             Long sizeId, String sizeName,
                             Integer quantityInStock, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.colorId = colorId;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.quantityInStock = quantityInStock;
        this.imageUrl = imageUrl;
    }
}
