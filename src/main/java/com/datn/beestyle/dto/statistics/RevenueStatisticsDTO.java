package com.datn.beestyle.dto.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevenueStatisticsDTO {
    private Date date;  // Ngày hoặc tháng hoặc năm
    private BigDecimal revenue;  // Doanh thu
    private Long quantity;
    private Integer month;
    private Integer year;
    private String productName;
    private BigDecimal salePrice;
    private String period;

    // Constructors, getters, setters
    public RevenueStatisticsDTO(Date date, BigDecimal revenue, Long quantity) {
        this.date = date;
        this.revenue = revenue;
        this.quantity = quantity;
    }
    public RevenueStatisticsDTO(String period, BigDecimal revenue, Long quantity) {
        this.period = period;
        this.revenue = revenue;
        this.quantity = quantity;
    }

    public RevenueStatisticsDTO(Integer month, Integer year, BigDecimal revenue, Long quantity) {
        this.month = month;
        this.year = year;
        this.revenue = revenue;
        this.quantity = quantity;
    }
//    public RevenueStatisticsDTO(String productName,BigDecimal salePrice,Long quantity) {
//        this.productName = productName;
//        this.salePrice = salePrice;
//        this.quantity = quantity;
//    }



}

