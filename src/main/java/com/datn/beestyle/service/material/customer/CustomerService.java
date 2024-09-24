package com.datn.beestyle.service.material.customer;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.IGenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
        extends IGenericServiceAbstract<Customer, Integer, CreateCustomerRequest, UpdateCustomerRequest
        , CustomerResponse>
        implements ICustomerService{

    private final CustomerRepository customerRepository;

    protected CustomerService(IGenericRepository<Customer, Integer> entityRepository, IGenericMapper<Customer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> mapper, CustomerRepository customerRepository) {
        super(entityRepository, mapper);
        this.customerRepository = customerRepository;
    }

    @Override
    protected void beforeCreate(CreateCustomerRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateCustomerRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateCustomerRequest request, Customer entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateCustomerRequest request, Customer entity) {

    }

    @Override
    protected String getEntityName() {
        return null;
    }

    @Override
    public PageResponse<?> searchByFullName(Pageable pageable, String fullname) {
        Page<Customer> customerPage = customerRepository.findAllByName(pageable, fullname);
        List<CustomerResponse> customerResponseList = mapper.toEntityDtoList(customerPage.getContent());

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber()+1)
                .pageSize(pageable.getPageSize())
                .items(customerResponseList)
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }
}