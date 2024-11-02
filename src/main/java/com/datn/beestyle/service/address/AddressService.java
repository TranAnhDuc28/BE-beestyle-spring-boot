package com.datn.beestyle.service.address;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.dto.address.UpdateAddressRequest;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.exception.ResourceNotFoundException;
import com.datn.beestyle.repository.AddressRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class AddressService
extends GenericServiceAbstract<Address,Long, CreateAddressRequest, UpdateAddressRequest, AddressResponse>
implements IAddressService{

    private final AddressRepository addressRepository;

    public AddressService(IGenericRepository<Address, Long> entityRepository, IGenericMapper<Address, CreateAddressRequest, UpdateAddressRequest, AddressResponse> mapper, EntityManager entityManager, AddressRepository addressRepository) {
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
        // Kiểm tra nếu không có bản ghi nào với isDefault = true
        boolean existsDefaultAddress = addressRepository.existsByCustomerIdAndIsDefaultTrue(request.getCustomer().getId());

        // Nếu chưa có bản ghi nào có isDefault = true, đặt isDefault của bản ghi mới là true
        request.setDefault(!existsDefaultAddress);
    }

    @Override
    protected void beforeUpdate(Long aLong, UpdateAddressRequest request) {

    }


    @Override
    public void beforeUpdateIsDefault(Long id, UpdateAddressRequest request) {
        // Lấy địa chỉ hiện tại từ database
        Address currentAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

        // Cập nhật các trường khác từ request
        currentAddress.setAddressName(request.getAddressName());
        currentAddress.setCity(request.getCity());
        currentAddress.setDistrict(request.getDistrict());
        currentAddress.setCommune(request.getCommune());

        // Kiểm tra nếu yêu cầu là để đặt địa chỉ này thành mặc định
        if (request.isDefault()) {
            // Nếu địa chỉ hiện tại không phải là mặc định, cập nhật các địa chỉ khác thành false
            if (!currentAddress.isDefault()) {
                addressRepository.updateIsDefaultFalseForOtherAddresses(currentAddress.getCustomer().getId(), id);
            }
            // Đặt địa chỉ hiện tại thành mặc định
            currentAddress.setDefault(true);
        } else {
            // Nếu không đánh dấu là mặc định, đảm bảo rằng trường isDefault được thiết lập đúng
            currentAddress.setDefault(false);
        }
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

    @Override
    public PageResponse<?> getAllByCustomerId(Pageable pageable, Long customerId) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;


        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "isDefault")
                        .and(Sort.by(Sort.Direction.DESC, "createdAt", "id")));

        Page<Address> addressPage = addressRepository.findByCustomerId(pageRequest,customerId);
        List<AddressResponse> addressResponseList = mapper.toEntityDtoList(addressPage.getContent());

        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(addressPage.getTotalElements())
                .totalPages(addressPage.getTotalPages())
                .items(addressResponseList)
                .build();
    }
}
