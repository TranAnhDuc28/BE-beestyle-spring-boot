package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum PaymentMethod {
    CASH_ON_DELIVERY(0),
    BANK_TRANSFER(1);

    private final int value;
    PaymentMethod(int value) {
        this.value = value;
    }

    public static PaymentMethod valueOf(int value) {
        PaymentMethod paymentMethod = resolve(value);
        if (paymentMethod == null) {
            throw new IllegalArgumentException("No matching constant for [" + value + "]");
        }
        return paymentMethod;
    }

    @Nullable
    public static PaymentMethod resolve(int value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.value == value) {
                return paymentMethod;
            }
        }
        return null;
    }

}
