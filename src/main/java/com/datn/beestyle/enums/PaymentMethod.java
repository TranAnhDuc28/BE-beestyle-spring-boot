package com.datn.beestyle.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH_ON_DELIVERY(0),
    BANK_TRANSFER(1);

    private final int id;
    PaymentMethod(int id) {
        this.id = id;
    }

}
