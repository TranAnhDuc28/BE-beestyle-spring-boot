package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.enums.DiscountType;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherMapper extends IGenericMapper<Voucher, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse> {

    // Ánh xạ từ `Voucher` sang `VoucherResponse`
    @Mapping(target = "status", source = ".", qualifiedByName = "statusName")
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "convertDiscountTypeToInt") // Sử dụng phương thức mapToInt
    @Override
    VoucherResponse toEntityDto(Voucher entity);

    // Ánh xạ từ `CreateVoucherRequest` sang `Voucher`
    @Mapping(target = "status", constant = "1")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "convertIntToDiscountType") // Sử dụng phương thức mapToEnum
    @Override
    Voucher toCreateEntity(CreateVoucherRequest request);

    // Ánh xạ từ `UpdateVoucherRequest` sang `Voucher`
    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "convertIntToDiscountType") // Sử dụng phương thức mapToEnum
    @Override
    void toUpdateEntity(@MappingTarget Voucher entity, UpdateVoucherRequest request);

    @Override
    List<VoucherResponse> toEntityDtoList(List<Voucher> entityList);

    // Phương thức tùy chỉnh cho status
    @Named("statusId")
    default int statusId(UpdateVoucherRequest request) {
        return Status.valueOf(request.getStatus()).getValue();
    }

    @Named("statusName")
    default String statusName(Voucher voucher) {
        return Status.valueOf(voucher.getStatus()).name();
    }

    // Phương thức tùy chỉnh cho discountType từ int sang DiscountType
    @Named("convertIntToDiscountType")
    default DiscountType convertIntToDiscountType(int value) {
        return DiscountType.resolve(value);
    }

    // Phương thức tùy chỉnh cho discountType từ DiscountType sang int
    @Named("convertDiscountTypeToInt")
    default int convertDiscountTypeToInt(DiscountType discountType) {
        return discountType != null ? discountType.getValue() : 0;
    }
}
