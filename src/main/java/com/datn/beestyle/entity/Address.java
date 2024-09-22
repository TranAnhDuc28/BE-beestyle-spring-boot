package com.datn.beestyle.entity;

import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.entity.user.Staff;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "tbl_address")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity<Long> {

    @Column(name = "address_name")
    String addressName;

    @Column(name = "city")
    String city;

    @Column(name = "district")
    String district;

    @Column(name = "address_name")
    String commune;

    @Column(name = "address_name")
    boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    Staff staff;
}
