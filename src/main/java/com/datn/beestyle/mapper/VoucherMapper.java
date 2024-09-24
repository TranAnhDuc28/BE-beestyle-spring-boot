package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherMapper extends IGenericMapper<Voucher, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse> {
    // Mapping khi tạo mới Voucher từ CreateVoucherRequest
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", defaultValue = "false")
    @Override
    Voucher toCreateEntity(CreateVoucherRequest request);

    // Mapping khi cập nhật Voucher từ UpdateVoucherRequest
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Voucher entity, UpdateVoucherRequest request);

    // Mapping danh sách khi tạo mới nhiều Voucher
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", defaultValue = "false")
    @Override
    List<Voucher> toCreateEntityList(List<CreateVoucherRequest> dtoList);
    // Mapping từ Voucher sang VoucherResponse
    @Override
    VoucherResponse toEntityDto(Voucher entity);

    // Mapping danh sách Voucher sang VoucherResponse
    @Override
    List<VoucherResponse> toEntityDtoList(List<Voucher> entityList);
}
