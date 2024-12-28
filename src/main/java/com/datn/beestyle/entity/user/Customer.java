package com.datn.beestyle.entity.user;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.BaseEntity;
import com.datn.beestyle.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Table(name = "customer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends BaseEntity<Long> {

    @Column(name = "full_name")
    String fullName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    LocalDate dateOfBirth;

    @Column(name = "gender")
    int gender;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "email")
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    int status;

//    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
//    ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer", cascade = ALL, fetch = FetchType.LAZY)
    Set<Address> addresses = new HashSet<>();

    public void addAddress(Address address) {
        if (address != null) {
            if (addresses == null) {
                addresses = new HashSet<>();
            }
            addresses.add(address);
            address.setCustomer(this); // save customer id
        }
    }
}
