package com.datn.beestyle.entity.voucher;

import com.datn.beestyle.entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Table(name = "tbl_voucher_usage")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    Customer customer;

    @Column(name = "user_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Timestamp usedDate;
}
