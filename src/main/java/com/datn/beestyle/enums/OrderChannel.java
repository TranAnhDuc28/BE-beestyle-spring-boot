package com.datn.beestyle.enums;

import lombok.Getter;

@Getter
public enum OrderChannel {
    ONLINE(0),
    OFFLINE(1);

    private final int id;
    OrderChannel(int id) {
        this.id = id;
    }

}
