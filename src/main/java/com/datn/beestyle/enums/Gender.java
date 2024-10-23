package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    private final int value;
    Gender(int value) {
        this.value = value;
    }

    public static Gender valueOf(int value) {
        Gender gender = resolve(value);
        if (gender == null) {
            throw new IllegalArgumentException("No matching constant for [" + value + "]");
        }
        return gender;
    }

    @Nullable
    public static Gender resolve(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        return null;
    }

    @Nullable
    public static Gender fromString(String status) {
        try {
            return Gender.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
