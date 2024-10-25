package com.datn.beestyle.service.customer;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGenericService<Customer,Integer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> {
    PageResponse<?> getAllByFullNameAndStatusAndGender(Pageable pageable, String fullName,String status,String gender);
}
