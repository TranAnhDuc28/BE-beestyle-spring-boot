package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends IGenericRepository<Customer, Integer> {
    @Query("""
            select c.email, c.fullName 
            from Customer c 
            where :name is null or c.fullName like concat('%', :name, '%')
            and c.deleted = :deleted
            """)
    @Override
    Page<Customer> findByNameContainingAndDeleted(Pageable pageable,
                                                  @Param("name") String name,
                                                  @Param("deleted") boolean deleted);
}
