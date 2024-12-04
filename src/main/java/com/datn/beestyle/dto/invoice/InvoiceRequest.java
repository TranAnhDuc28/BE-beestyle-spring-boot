package com.datn.beestyle.dto.invoice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class InvoiceRequest {
    private String orderId;
    private String customerName;
    private String orderDate;
    private List<Product> products;

    @Getter
    @Setter
    public static class Product {

        private String productName;
        private int quantity;
        private double price;


    }
}
