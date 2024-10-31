package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum OrderChannel {
    ONLINE(0),
    OFFLINE(1);

    private final int value;
    OrderChannel(int value) {
        this.value = value;
    }

    public static OrderChannel valueOf(int value) {
        OrderChannel orderChannel = resolve(value);
        if (orderChannel == null) {
            throw new IllegalArgumentException("No matching constant for [" + value + "]");
        }
        return orderChannel;
    }

    @Nullable
    public static OrderChannel resolve(Integer value) {
        for (OrderChannel orderChannel : OrderChannel.values()) {
            if (orderChannel.value == value) {
                return orderChannel;
            }
        }
        return null;
    }
}
