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
                :name is null or s.fullName like concat('%', :name, '%')
                 and s.deleted = :deleted
            """)
    @Override
    Page<Staff> findByNameContainingAndDeleted(Pageable pageable,
                                                  @Param("name") String name,
                                                  @Param("deleted") boolean deleted);
}
