package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.user.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends IGenericRepository<Staff,Integer> {
    @Query("""
            select s from Staff s 
            where 
                (:name is null or s.fullName like concat('%', :name, '%')) and
                (:status is null or s.status = :status)
            """)
    Page<Staff> findByNameContainingAndStatus(Pageable pageable,
                                                 @Param("name") String name,
                                                 @Param("status") Integer status);
}
