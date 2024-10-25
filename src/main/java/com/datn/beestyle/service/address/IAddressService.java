package com.datn.beestyle.service.address;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.dto.address.UpdateAddressRequest;
import com.datn.beestyle.entity.Address;

public interface IAddressService extends
        IGenericService<Address,Integer, CreateAddressRequest, UpdateAddressRequest, AddressResponse> {
}
