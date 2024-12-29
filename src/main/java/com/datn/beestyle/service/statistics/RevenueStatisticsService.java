package com.datn.beestyle.service.statistics;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.statistics.InventoryResponse;
import com.datn.beestyle.dto.statistics.RevenueStatisticsDTO;
import com.datn.beestyle.repository.statistics.StatisticsRepositoryImpl;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RevenueStatisticsService {

    private final StatisticsRepositoryImpl statisticsRepository;

    public RevenueStatisticsService(StatisticsRepositoryImpl statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public PageResponse<List<RevenueStatisticsDTO>> getRevenueByDate(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

      Page<RevenueStatisticsDTO> revenueStatisticsDTOPages = statisticsRepository.findRevenueByDate(sqlStartDate,sqlEndDate,pageable);

        // Tạo và trả về đối tượng PageResponse với dữ liệu đã lấy
        return PageResponse.<List<RevenueStatisticsDTO>>builder()
                .pageNo(revenueStatisticsDTOPages.getNumber() + 1)
                .pageSize(revenueStatisticsDTOPages.getSize())
                .totalElements(revenueStatisticsDTOPages.getTotalElements())
                .totalPages(revenueStatisticsDTOPages.getTotalPages())
                .items(revenueStatisticsDTOPages.getContent())
                .build();
    }

    public PageResponse<List<RevenueStatisticsDTO>> getRevenueByPeriod(String period, Pageable pageable,String periodValue ) {


        // Nếu period là null, gán giá trị mặc định là "day"
        if (period == null) {
            period = "day";
        }

        // Nếu periodValue là null hoặc rỗng, gán giá trị mặc định là ngày hôm nay
        if (periodValue == null || periodValue.isEmpty()) {
            switch (period) {
                case "range":
                    // Nếu period là "range" và periodValue rỗng, tự động lấy ngày hôm nay làm ngày bắt đầu và kết thúc
                    periodValue = LocalDate.now().toString() + "," + LocalDate.now().toString();
                    break;
                default:
                    periodValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Định dạng ngày theo "yyyy-MM-dd"
            }
        }


        Page<RevenueStatisticsDTO> revenueStatisticsDTOPages = statisticsRepository.findRevenueByPeriod(period,pageable,periodValue);

        // Tạo và trả về đối tượng PageResponse với dữ liệu đã lấy
        return PageResponse.<List<RevenueStatisticsDTO>>builder()
                .pageNo(revenueStatisticsDTOPages.getNumber() + 1)
                .pageSize(revenueStatisticsDTOPages.getSize())
                .totalElements(revenueStatisticsDTOPages.getTotalElements())
                .totalPages(revenueStatisticsDTOPages.getTotalPages())
                .items(revenueStatisticsDTOPages.getContent())
                .build();
    }

    public PageResponse<List<RevenueStatisticsDTO>> getOrderStatusByPeriod(String period, Pageable pageable,String periodValue ) {


        // Nếu period là null, gán giá trị mặc định là "day"
        if (period == null) {
            period = "day";
        }

        // Nếu periodValue là null hoặc rỗng, gán giá trị mặc định là ngày hôm nay
        if (periodValue == null || periodValue.isEmpty()) {
            switch (period) {
                case "range":
                    // Nếu period là "range" và periodValue rỗng, tự động lấy ngày hôm nay làm ngày bắt đầu và kết thúc
                    periodValue = LocalDate.now().toString() + "," + LocalDate.now().toString();
                    break;
                default:
                    periodValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Định dạng ngày theo "yyyy-MM-dd"
            }
        }


        Page<RevenueStatisticsDTO> revenueStatisticsDTOPages = statisticsRepository.findOrderStatusByPeriod(period,pageable,periodValue);

        // Tạo và trả về đối tượng PageResponse với dữ liệu đã lấy
        return PageResponse.<List<RevenueStatisticsDTO>>builder()
                .pageNo(revenueStatisticsDTOPages.getNumber() + 1)
                .pageSize(revenueStatisticsDTOPages.getSize())
                .totalElements(revenueStatisticsDTOPages.getTotalElements())
                .totalPages(revenueStatisticsDTOPages.getTotalPages())
                .items(revenueStatisticsDTOPages.getContent())
                .build();
    }



//    public PageResponse<List<RevenueStatisticsDTO>> getRevenueByMonth(LocalDate startDate, LocalDate endDate, Pageable pageable) {
//        Date sqlStartDate = Date.valueOf(startDate);
//        Date sqlEndDate = Date.valueOf(endDate);
//
//        List<RevenueStatisticsDTO> statistics = (List<RevenueStatisticsDTO>) statisticsRepository.findRevenueByMonth(sqlStartDate,sqlEndDate,pageable);
//
//        return createPageResponse(statistics, pageable);
//    }


//
//    public PageResponse<List<RevenueStatisticsDTO>> getRevenueByYear(LocalDate startDate, LocalDate endDate, Pageable pageable) {
//        List<Object[]> results = orderRepository.findRevenueByYear(startDate, endDate);
//
//        List<RevenueStatisticsDTO> statistics = results.stream()
//                .map(result -> new RevenueStatisticsDTO(result[0].toString(), (BigDecimal) result[1]))
//                .collect(Collectors.toList());
//
//        return createPageResponse(statistics, pageable);
//    }

//    public PageResponse<List<RevenueStatisticsDTO>> getTopSellingProductsByFilterType(String filterType,Pageable pageable) {
//        List<RevenueStatisticsDTO> statistics = orderRepository.findTopSellingProductsByFilterType(pageable,filterType);
//        return createPageResponse(statistics, pageable);
//    }



    private PageResponse<List<RevenueStatisticsDTO>> createPageResponse(List<RevenueStatisticsDTO> statistics, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<RevenueStatisticsDTO> paginatedList;

        if (statistics.size() < startItem) {
            paginatedList = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, statistics.size());
            paginatedList = statistics.subList(startItem, toIndex);
        }

        Page<RevenueStatisticsDTO> page = new PageImpl<>(paginatedList, pageable, statistics.size());

        return PageResponse.<List<RevenueStatisticsDTO>>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .items(page.getContent())
                .build();
    }
    //Thống kê sản phẩm tồn
    public PageResponse<List<InventoryResponse>> getProductVariantsByStock(Pageable pageable, int stock) {

        // Lấy danh sách sản phẩm theo số lượng tồn kho
        Page<InventoryResponse> productVariantResponsePages = statisticsRepository.filterProductVariantsByStock(pageable, stock);

        // Trả về kết quả phân trang
        return PageResponse.<List<InventoryResponse>>builder()
                .pageNo(productVariantResponsePages.getNumber() + 1)
                .pageSize(productVariantResponsePages.getSize())
                .totalElements(productVariantResponsePages.getTotalElements())
                .totalPages(productVariantResponsePages.getTotalPages())
                .items(productVariantResponsePages.getContent())
                .build();
    }
    public PageResponse<List<InventoryResponse>> getTopSellingProduct(Pageable pageable, int top) {

        // Lấy danh sách sản phẩm theo số lượng tồn kho
        Page<InventoryResponse> productVariantResponsePages = statisticsRepository.TopSellingProduct(pageable, top);

        // Trả về kết quả phân trang
        return PageResponse.<List<InventoryResponse>>builder()
                .pageNo(productVariantResponsePages.getNumber() + 1)
                .pageSize(productVariantResponsePages.getSize())
                .totalElements(productVariantResponsePages.getTotalElements())
                .totalPages(productVariantResponsePages.getTotalPages())
                .items(productVariantResponsePages.getContent())
                .build();
    }

}
