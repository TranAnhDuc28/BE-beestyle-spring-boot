package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum PickupMethod {
    IN_STORE(0),
    DELIVERY(1);

    private final int value;
    PickupMethod(int value) {
        this.value = value;
    }

    public static PickupMethod valueOf(int value) {
        PickupMethod paymentMethod = resolve(value);
        if (paymentMethod == null) {
            throw new IllegalArgumentException("No matching constant for [" + value + "]");
        }
        return paymentMethod;
    }

    @Nullable
    public static PickupMethod resolve(int value) {
        for (PickupMethod pickupMethod : PickupMethod.values()) {
            if (pickupMethod.value == value) {
                return pickupMethod;
            }
        }
        return null;
    }

    @Nullable
    public static PickupMethod fromString(String value) {
        try {
            return PickupMethod.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    @Nullable
    public static String fromInteger(Integer value) {
        if (value == null) return null;
        try {
            PickupMethod pickupMethod = PickupMethod.resolve(value);
            return pickupMethod != null ? pickupMethod.name() : null;
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

}
