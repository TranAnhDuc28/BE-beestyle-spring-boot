package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper extends IGenericMapper<Product, CreateProductRequest, UpdateProductRequest, ProductResponse> {
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", defaultValue = "1")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Override
    Product toCreateEntity(CreateProductRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Product entity, UpdateProductRequest request);

    UserProductResponse toUserProductResponse(Product product);

    @Named("statusId")
    default int statusId(UpdateProductRequest request) {
        return Status.valueOf(request.getStatus()).getValue();
    }
}
