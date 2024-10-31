package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.product.attributes.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends IGenericRepository<Address,Long> {
    @Query("""
            select a from Address a 
            where 
                (:customerId is null or a.customer.id = :customerId) 
            """)
    Page<Address> findByCustomerId(Pageable pageable,@Param("customerId") Long customerId);

//    Kiểm tra sự tồn tại của isDefault = true
    boolean existsByIsDefaultTrue();

    boolean existsByCustomerIdAndIsDefaultTrue(Long customerId);


    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.customer.id = :customerId AND a.id <> :addressId")
    void updateIsDefaultFalseForOtherAddresses(Long customerId, Long addressId);
}
