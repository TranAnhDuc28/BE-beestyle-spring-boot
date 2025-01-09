package com.datn.beestyle.service.customer;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.user.Staff;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.exception.DuplicateEmailException;
import com.datn.beestyle.repository.CustomerRepository;
import com.datn.beestyle.repository.StaffRepository;
import com.datn.beestyle.service.mail.MailService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CustomerService
        extends GenericServiceAbstract<Customer, Long, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse>
        implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final MailService mailService;

    public CustomerService(IGenericRepository<Customer, Long> entityRepository,
                           IGenericMapper<Customer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> mapper,
                           EntityManager entityManager, CustomerRepository customerRepository, StaffRepository staffRepository, MailService mailService) {
        super(entityRepository, mapper, entityManager);
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
        this.mailService = mailService;
    }


    @Override
    protected List<CreateCustomerRequest> beforeCreateEntities(List<CreateCustomerRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateCustomerRequest> beforeUpdateEntities(List<UpdateCustomerRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateCustomerRequest request) {
        request.setPassword("");
    }

    @Override
    protected void beforeUpdate(Long id, UpdateCustomerRequest request) {
        request.setPassword("");
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id));

        if (!existingCustomer.getEmail().equals(request.getEmail()) &&
                (staffRepository.existsByEmail(request.getEmail()) || customerRepository.existsByEmail(request.getEmail()) )) {
            throw new IllegalArgumentException("Email đã được đăng kí");
        }

        if (!existingCustomer.getPhoneNumber().equals(request.getPhoneNumber()) &&
                staffRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã được đăng kí");
        }
    }

    @Override
    protected void afterConvertCreateRequest(CreateCustomerRequest request, Customer entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateCustomerRequest request, Customer entity) {

    }

    @Override
    protected String getEntityName() {
        return "Customer";
    }


    @Override
    public PageResponse<?> getAllByKeywordAndStatusAndGender(Pageable pageable, String status, String gender, String keyword) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        Integer statusValue = null;
        if (status != null) {
            Status statusEnum = Status.fromString(status);
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }
        Integer genderValue = null;
        if (gender != null) {
            Gender genderEnum = Gender.fromString(gender);
            if (genderEnum != null) genderValue = genderEnum.getValue();
        }
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Customer> customerPage =
                customerRepository.findByKeywordContainingAndStatusAndGender(pageRequest, statusValue, genderValue, keyword);
        List<CustomerResponse> customerResponseList = mapper.toEntityDtoList(customerPage.getContent());

        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .items(customerResponseList)
                .build();
    }


    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail()) || staffRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email đã đã được đăng kí.");
        }
        if(customerRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new IllegalArgumentException("Số điện thoại đã được đăng kí");
        }

        if(request.getPassword() == null) {
            // Tạo mật khẩu ngẫu nhiên và gán vào request
            String generatedPassword = UUID.randomUUID().toString().substring(0, 8);
            request.setPassword(generatedPassword);
        }

        // Chuyển request sang entity
            Customer entity = mapper.toCreateEntity(request);

            // Lưu entity vào cơ sở dữ liệu
            Customer savedEntity = entityRepository.save(entity);
            log.info("customer", entity.getFullName());

            if (savedEntity.getId() != null) {
                try {
                    // Gửi email thông báo tài khoản
                    mailService.sendLoginCustomerEmail(entity);
                    log.info("Registration email sent successfully to {}", entity.getEmail());
                } catch (Exception e) {
                    // Log lỗi nếu gửi email thất bại
                    log.error("Failed to send registration email to {}: {}", entity.getEmail(), e.getMessage());
                }
            }
            log.info("password: {}", request.getPassword());
        return mapper.toEntityDto(savedEntity);
    }
}
