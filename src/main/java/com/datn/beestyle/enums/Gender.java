package com.datn.beestyle.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    private final int id;
    Gender(int id) {
        this.id = id;
    }

}
