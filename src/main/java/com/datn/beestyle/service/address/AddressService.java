package com.datn.beestyle.service.address;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.dto.address.UpdateAddressRequest;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.exception.ResourceNotFoundException;
import com.datn.beestyle.repository.AddressRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public AddressResponse setUpdateIsDefault(Long id, UpdateAddressRequest request) {
        // Kiểm tra nếu yêu cầu đặt địa chỉ này làm mặc định
        if (request.isDefault()) {
            Address currentAddress = addressRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

            // Nếu địa chỉ hiện tại chưa được đặt là mặc định, tiến hành cập nhật
            if (!currentAddress.isDefault()) {
                // Đặt các địa chỉ khác của khách hàng này thành không mặc định
                addressRepository.updateIsDefaultFalseForOtherAddresses(currentAddress.getCustomer().getId(), id);

                // Đặt địa chỉ hiện tại thành mặc định
                currentAddress.setDefault(true);
                return mapper.toEntityDto(addressRepository.save(currentAddress));  // Lưu và trả về AddressResponse
            }
        } else {
            // Nếu không cần mặc định, chỉ cập nhật `isDefault` thành false
            Address addressToUpdate = addressRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

            addressToUpdate.setDefault(false); // Đảm bảo địa chỉ này không là mặc định

            // Thực hiện cập nhật khác từ request (nếu cần thiết)
            // addressToUpdate.setAddressName(request.getAddressName());

            return mapper.toEntityDto(addressRepository.save(addressToUpdate));
        }

        // Nếu không cần cập nhật gì, trả về địa chỉ hiện tại đã ở trạng thái mặc định
        return mapper.toEntityDto(addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id)));
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
                Sort.by(Sort.Direction.DESC, "id"));


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
