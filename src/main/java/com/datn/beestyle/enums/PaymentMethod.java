package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum PaymentMethod {
    CASH_ON_DELIVERY(0),
    BANK_TRANSFER(1),
    COD_AND_BANK_TRANSFER(2);

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

    @Nullable
    public static PaymentMethod fromString(String value) {
        try {
            return PaymentMethod.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    @Nullable
    public static String fromInteger(Integer value) {
        if (value == null) return null;
        try {
            PaymentMethod paymentMethod = PaymentMethod.resolve(value);
            return paymentMethod != null ? paymentMethod.name() : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

}
