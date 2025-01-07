package com.datn.beestyle.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartCheckRequest {
    private Long id;
    private Integer quantity;
}
