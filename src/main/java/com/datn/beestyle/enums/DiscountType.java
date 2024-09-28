package com.datn.beestyle.enums;

import lombok.Getter;

@Getter
public enum DiscountType {
    PERCENTAGE(0),
    CASH(1);

    private final int id;
    DiscountType(int id) {
        this.id = id;
    }
}
