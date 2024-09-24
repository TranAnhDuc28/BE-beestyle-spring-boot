package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends IGenericRepository<Customer, Integer> {
    @Query("""
            select m.email, m.fullName from Customer m where :fullname is null or m.fullName like concat('%', :fullname, '%')
            """)
    Page<Customer> findAllByName(Pageable pageable, String fullname );
}
