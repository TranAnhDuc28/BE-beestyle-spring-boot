package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.StaffResponse;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.entity.user.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper extends IGenericMapper<Staff, CreateStaffRequest, UpdateStaffRequest, StaffResponse> {

    @Override
    StaffResponse toEntityDto(Staff entity);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status",constant = "1")
    @Override
    Staff toCreateEntity(CreateStaffRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Staff entity, UpdateStaffRequest request);
}
