package com.datn.beestyle.service.customer;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.RegisterCustomerRequest;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import org.springframework.data.domain.Pageable;

public interface ICustomerService
        extends IGenericService<Customer, Long, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> {
    PageResponse<?> getAllByKeywordAndStatusAndGender(Pageable pageable, String status, String gender, String keyword);

    Customer getCustomerByEmail(String email);

    CustomerResponse createByOwner(RegisterCustomerRequest request);
}
