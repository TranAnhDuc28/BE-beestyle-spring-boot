package com.datn.beestyle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;

@MappedSuperclass
public abstract class Auditable<T extends Serializable> extends BaseEntity<T> {

    // user tạo mới record
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private T createdBy;

    // user update cuối cùng
    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private T updateBy;
}
