package com.datn.beestyle.service.staff;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.StaffResponse;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.entity.user.Staff;

public interface IStaffService
    extends IGenericService<Staff,Integer, CreateStaffRequest, UpdateStaffRequest, StaffResponse> {
}
