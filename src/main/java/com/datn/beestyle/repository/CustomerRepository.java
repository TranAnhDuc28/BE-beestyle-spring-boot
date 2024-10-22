package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends IGenericRepository<Customer,Integer> {
    @Query("""
            select c from Customer c 
            where 
                (:name is null or c.fullName like concat('%', :name, '%')) and
                (:status is null or c.status = :status)
            """)
    Page<Customer> findByNameContainingAndStatus(Pageable pageable,
                                                 @Param("name") String name,
                                        @Param("status") Integer status);
}
