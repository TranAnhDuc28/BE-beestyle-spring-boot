package com.datn.beestyle.service.staff;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.StaffResponse;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.entity.user.Staff;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.StaffRepository;
import com.datn.beestyle.service.mail.MailService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service

public class StaffService
    extends GenericServiceAbstract<Staff,Integer, CreateStaffRequest, UpdateStaffRequest, StaffResponse>
    implements IStaffService{

    private final StaffRepository staffRepository;
    private final MailService mailService;

    public StaffService(IGenericRepository<Staff, Integer> entityRepository, IGenericMapper<Staff, CreateStaffRequest,
            UpdateStaffRequest, StaffResponse> mapper, EntityManager entityManager,
                        StaffRepository staffRepository, MailService mailService) {
        super(entityRepository, mapper, entityManager);
        this.staffRepository = staffRepository;
        this.mailService = mailService;
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
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên với ID: " + id));

        if (!existingStaff.getEmail().equals(request.getEmail()) &&
                staffRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        if (!existingStaff.getUsername().equals(request.getUsername()) &&
                staffRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username đã được đăng kí");
        }

        if (!existingStaff.getPhoneNumber().equals(request.getPhoneNumber()) &&
                staffRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã được đăng kí");
        }
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
    public PageResponse<?> getAllByKeywordAndStatusAndGender(Pageable pageable, String status,String gender,String keyword) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        Integer statusValue = null;
        if(status != null) {
            Status statusEnum = Status.fromString(status.toUpperCase());
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }
        Integer genderValue = null;
        if (gender != null) {
            Gender genderEnum = Gender.fromString(gender);
            if(genderEnum!=null) genderValue = genderEnum.getValue();
        }
        PageRequest pageRequest = PageRequest.of(page , pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Staff> staffPage = staffRepository.findByKeywordContainingAndStatusAndGender(pageRequest,statusValue,genderValue,keyword);
        List<StaffResponse> staffResponseList = mapper.toEntityDtoList(staffPage.getContent());

        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(staffPage.getTotalElements())
                .totalPages(staffPage.getTotalPages())
                .items(staffResponseList)
                .build();

    }

    @Override
    public StaffResponse create(CreateStaffRequest request) {
        if (staffRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if(staffRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("Username đã được đăng kí");
        }
        if(staffRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new IllegalArgumentException("Số điện thoại đã được đăng kí");
        }

        // Tạo mật khẩu ngẫu nhiên và gán vào request
        String generatedPassword = UUID.randomUUID().toString().substring(0, 8);
        request.setPassword(generatedPassword);

        // Chuyển request sang entity
        Staff entity = mapper.toCreateEntity(request);

        // Lưu entity vào cơ sở dữ liệu
        Staff savedEntity = entityRepository.save(entity);
        log.info("staff",entity.getFullName());

        if (savedEntity.getId() != null) {
            try {
                // Gửi email thông báo tài khoản
                mailService.sendLoginStaffEmail(entity);
                log.info("Registration email sent successfully to {}", entity.getEmail());
            } catch (Exception e) {
                // Log lỗi nếu gửi email thất bại
                log.error("Failed to send registration email to {}: {}", entity.getEmail(), e.getMessage());
            }
        }

        // Chuyển entity đã lưu thành DTO để trả về
        return mapper.toEntityDto(savedEntity);
    }

}
