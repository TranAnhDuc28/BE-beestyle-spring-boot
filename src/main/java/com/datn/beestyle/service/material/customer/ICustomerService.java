package com.datn.beestyle.service.material.customer;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface ICustomerService  {
    PageResponse<?> searchByFullName(Pageable pageable, String fullname);
}
