package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
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

    @Mapping(target = "status", source = ".", qualifiedByName = "statusName")
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "discountTypeName")
    @Override
    VoucherResponse toEntityDto(Voucher entity);

    @Mapping(target = "status", constant = "1")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "discountTypeId")
    @Override
    Voucher toCreateEntity(CreateVoucherRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "discountType", source = "discountType", qualifiedByName = "discountTypeId")
    @Override
    void toUpdateEntity(@MappingTarget Voucher entity, UpdateVoucherRequest request);

    @Override
    List<VoucherResponse> toEntityDtoList(List<Voucher> entityList);

    @Named("statusId")
    default int statusId(UpdateVoucherRequest request) {
        return Status.valueOf(request.getStatus()).getValue();
    }

    @Named("statusName")
    default String statusName(Voucher voucher) {
        Status status = Status.resolve(voucher.getStatus());
        return status != null ? status.name() : null;
    }

    @Named("discountTypeName")
    default String discountTypeName(int discountType) {
        DiscountType type = DiscountType.resolve(discountType);
        return type != null ? type.name() : null;
    }

    @Named("discountTypeId")
    default Integer discountTypeId(String discountType) {
        DiscountType type = DiscountType.fromString(discountType);
        return type != null ? type.getValue() : null;
    }
}
