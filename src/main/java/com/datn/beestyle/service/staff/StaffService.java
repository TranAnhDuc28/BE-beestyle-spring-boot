package com.datn.beestyle.service.staff;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.StaffResponse;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.entity.user.Staff;
import com.datn.beestyle.repository.StaffRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StaffService
    extends GenericServiceAbstract<Staff,Integer, CreateStaffRequest, UpdateStaffRequest, StaffResponse>
    implements IStaffService{

    private final StaffRepository staffRepository;

    public StaffService(IGenericRepository<Staff, Integer> entityRepository, IGenericMapper<Staff, CreateStaffRequest,
            UpdateStaffRequest, StaffResponse> mapper, EntityManager entityManager,
                        StaffRepository staffRepository) {
        super(entityRepository, mapper, entityManager);
        this.staffRepository = staffRepository;
    }

    @Override
    protected List<CreateStaffRequest> beforeCreateEntities(List<CreateStaffRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateStaffRequest> beforeUpdateEntities(List<UpdateStaffRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateStaffRequest request) {

    }

    @Override
    protected void beforeUpdate(Integer id, UpdateStaffRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateStaffRequest request, Staff entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateStaffRequest request, Staff entity) {

    }

    @Override
    protected String getEntityName() {
        return "Staff";
    }
}
