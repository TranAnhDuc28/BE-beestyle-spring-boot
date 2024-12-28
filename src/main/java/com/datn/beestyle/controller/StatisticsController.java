package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.statistics.RevenueStatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/admin/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics Controller")
public class StatisticsController {
    @Autowired
    private RevenueStatisticsService revenueStatisticsService;

    @GetMapping("/revenue-by-date")
    public ApiResponse<?> getRevenueByDate(
            Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return new ApiResponse<>(HttpStatus.OK.value(), "Thống kê",
                revenueStatisticsService.getRevenueByDate(startDate, endDate,pageable));

    }

    @GetMapping("/revenue-by-period")
    public ApiResponse<?> getRevenueByPeriod(
            Pageable pageable,
            @RequestParam String period) {

        return new ApiResponse<>(HttpStatus.OK.value(), "Thống kê theo ngày, tháng, năm",
                revenueStatisticsService.getRevenueByPeriod(period,pageable));

    }

//    @GetMapping("/revenue-by-month")
//    public ApiResponse<?> getRevenueByMonth(
//            Pageable pageable,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//        return new ApiResponse<>(HttpStatus.OK.value(), "Thống kê",
//                revenueStatisticsService.getRevenueByMonth(startDate, endDate,pageable));
//
//    }
//    @GetMapping("/top-selling-products")
//    public ApiResponse<?> opSellingProducts(
//            Pageable pageable,
//            @RequestParam String filterType) {
//
//        return new ApiResponse<>(HttpStatus.OK.value(), "Thống kê",
//                revenueStatisticsService.getTopSellingProductsByFilterType(filterType,pageable));
//
//    }


}
