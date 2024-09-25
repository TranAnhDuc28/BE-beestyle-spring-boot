package com.datn.beestyle.service.material.customer;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
        extends GenericServiceAbstract<Customer, Integer, CreateCustomerRequest, UpdateCustomerRequest
                , CustomerResponse>
        implements ICustomerService{

    private final CustomerRepository customerRepository;

    public CustomerService(IGenericRepository<Customer, Integer> entityRepository, IGenericMapper<Customer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> mapper, EntityManager entityManager, CustomerRepository customerRepository) {
        super(entityRepository, mapper, entityManager);
        this.customerRepository = customerRepository;
    }


    @Override
    protected List<CreateCustomerRequest> beforeCreateEntities(List<CreateCustomerRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateCustomerRequest> beforeUpdateEntities(List<UpdateCustomerRequest> requests) {
        return null;
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


}