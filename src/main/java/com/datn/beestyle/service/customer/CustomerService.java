package com.datn.beestyle.service.customer;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService
        extends GenericServiceAbstract<Customer, Long, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse>
        implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(IGenericRepository<Customer, Long> entityRepository,
                           IGenericMapper<Customer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> mapper,
                           EntityManager entityManager, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        super(entityRepository, mapper, entityManager);
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
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
    }

    @Override
    protected void beforeUpdate(Long id, UpdateCustomerRequest request) {
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




}
