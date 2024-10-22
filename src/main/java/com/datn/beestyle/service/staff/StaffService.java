package com.datn.beestyle.service.staff;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.StaffResponse;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.user.Staff;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.StaffRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public PageResponse<?> getAllByFullName(Pageable pageable, String fullName, String status) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        Integer statusValue = null;
        if(status != null) {
            Status statusEnum = Status.fromString(status.toUpperCase());
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }
        PageRequest pageRequest = PageRequest.of(page , pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Staff> staffPage = staffRepository.findByNameContainingAndStatus(pageRequest,fullName,statusValue);
        List<StaffResponse> staffResponseList = mapper.toEntityDtoList(staffPage.getContent());

        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(staffPage.getTotalElements())
                .totalPages(staffPage.getTotalPages())
                .items(staffResponseList)
                .build();

    }
}
