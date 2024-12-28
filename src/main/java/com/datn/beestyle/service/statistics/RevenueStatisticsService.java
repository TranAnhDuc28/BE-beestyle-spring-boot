package com.datn.beestyle.service.statistics;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.statistics.RevenueStatisticsDTO;
import com.datn.beestyle.repository.statistics.StatisticsRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    public PageResponse<List<RevenueStatisticsDTO>> getRevenueByPeriod(String period, Pageable pageable) {


        Page<RevenueStatisticsDTO> revenueStatisticsDTOPages = statisticsRepository.findRevenueByPeriod(period,pageable);

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
}
