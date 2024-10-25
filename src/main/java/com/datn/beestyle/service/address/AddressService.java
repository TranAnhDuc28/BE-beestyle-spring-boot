package com.datn.beestyle.service.address;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.dto.address.UpdateAddressRequest;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.repository.AddressRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class AddressService
extends GenericServiceAbstract<Address,Integer, CreateAddressRequest, UpdateAddressRequest, AddressResponse>
implements IAddressService{

    private final AddressRepository addressRepository;

    public AddressService(IGenericRepository<Address, Integer> entityRepository, IGenericMapper<Address, CreateAddressRequest, UpdateAddressRequest, AddressResponse> mapper, EntityManager entityManager, AddressRepository addressRepository) {
        super(entityRepository, mapper, entityManager);
        this.addressRepository = addressRepository;
    }


    @Override
    protected List<CreateAddressRequest> beforeCreateEntities(List<CreateAddressRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateAddressRequest> beforeUpdateEntities(List<UpdateAddressRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateAddressRequest request) {

    }

    @Override
    protected void beforeUpdate(Integer integer, UpdateAddressRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateAddressRequest request, Address entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateAddressRequest request, Address entity) {

    }

    @Override
    protected String getEntityName() {
        return null;
    }
}
