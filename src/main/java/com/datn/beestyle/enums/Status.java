package com.datn.beestyle.enums;

import lombok.Getter;

@Getter
public enum Status {

    ACTIVE(1),
    INACTIVE(0);

    private final int id;
    Status(int id) {
        this.id = id;
    }

    public static Status fromId(short id) {
        for (Status type : Status.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id status: " + id);
    }
}
