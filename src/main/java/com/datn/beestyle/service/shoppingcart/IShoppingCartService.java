package com.datn.beestyle.service.shoppingcart;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.cart.ShoppingCartRequest;
import com.datn.beestyle.dto.cart.ShoppingCartResponse;
import com.datn.beestyle.entity.cart.ShoppingCart;

import java.util.List;

public interface IShoppingCartService extends
        IGenericService<ShoppingCart, Long, ShoppingCartRequest, ShoppingCartRequest, ShoppingCartResponse> {
    void createCartItemOnline(List<ShoppingCartRequest> cartRequests);
}
