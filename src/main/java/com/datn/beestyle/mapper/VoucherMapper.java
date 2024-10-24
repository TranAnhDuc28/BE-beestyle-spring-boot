package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherMapper extends IGenericMapper<Voucher, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse> {

    @Mapping(target = "status", source = ".", qualifiedByName = "statusName")
    @Override
    VoucherResponse toEntityDto(Voucher entity);

    @Mapping(target = "status", constant = "1")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    Voucher toCreateEntity(CreateVoucherRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Voucher entity, UpdateVoucherRequest request);

    @Override
    List<VoucherResponse> toEntityDtoList(List<Voucher> entityList);

    @Named("statusId")
    default int statusId(UpdateVoucherRequest request) {
        return Status.valueOf(request.getStatus()).getValue(); // Chuyển đổi tên status sang giá trị

    }

    @Named("statusName")
    default String statusName(Voucher voucher) {
        return Status.valueOf(voucher.getStatus()).name(); // Chuyển đổi giá trị status sang tên
    }
}
