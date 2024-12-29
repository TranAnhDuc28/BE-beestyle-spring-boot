package com.datn.beestyle.repository.statistics;

import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.statistics.InventoryResponse;
import com.datn.beestyle.dto.statistics.RevenueStatisticsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Slf4j
public class StatisticsRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<RevenueStatisticsDTO> findRevenueByDate(Date startDate, Date endDate, Pageable pageable) {
        String sql = """
                  SELECT DATE(o.payment_date) AS date, 
                         SUM(oi.sale_price * oi.quantity) AS revenue,
                         SUM(oi.quantity) AS quantity
                  FROM `order` o
                  JOIN `order_item` oi ON o.id = oi.order_id
                  WHERE o.payment_date BETWEEN :startDate AND :endDate
                    AND o.order_status = 6
                  GROUP BY DATE(o.payment_date)
                """;

        // Query for data
        Query query = entityManager.createNativeQuery(sql, "RevenueStatisticsDTOMapping");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        // Pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<RevenueStatisticsDTO> results = query.getResultList();

        // Query for total count
        String countSql = """
                  SELECT COUNT(*) 
                  FROM (SELECT DATE(o.payment_date) 
                        FROM `order` o
                        JOIN `order_item` oi ON o.id = oi.order_id
                        WHERE o.payment_date BETWEEN :startDate AND :endDate
                          AND o.order_status = 6
                        GROUP BY DATE(o.payment_date)) AS temp
                """;

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("startDate", startDate);
        countQuery.setParameter("endDate", endDate);

        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }

    public Page<RevenueStatisticsDTO> findRevenueByPeriod(String period, Pageable pageable, String periodValue) {
        // Validate period to avoid SQL injection
        if (!List.of("day", "month", "year", "range").contains(period)) {
            throw new IllegalArgumentException("Invalid period. Must be 'day', 'month', 'year', or 'range'.");
        }

        // Nếu periodValue là null hoặc rỗng, lấy thời gian hiện tại theo kiểu ngày, tháng, năm
        if (periodValue == null || periodValue.isEmpty()) {
            switch (period) {
                case "day":
                    periodValue = LocalDate.now().toString(); // Lấy ngày hiện tại (yyyy-MM-dd)
                    break;
                case "month":
                    periodValue = String.valueOf(LocalDate.now().getYear()); // Lấy năm hiện tại
                    break;
                case "year":
                    periodValue = String.valueOf(LocalDate.now().getYear()); // Lấy năm hiện tại
                    break;
                case "range":
                    // Cố định ngày bắt đầu và kết thúc là hôm nay
                    periodValue = LocalDate.now().toString() + "," + LocalDate.now().toString();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period. Must be 'day', 'month', 'year', or 'range'.");
            }
        }

        // Dynamically build the SELECT and WHERE clause based on the period
        String selectClause = "";
        String groupByClause = "";
        String whereClause = "";
        String orderBy = "";

        switch (period) {
            case "day":
                selectClause = "DATE(o.payment_date) AS period";
                groupByClause = "DATE(o.payment_date)";
                whereClause = String.format(
                        "DATE(o.payment_date) BETWEEN DATE_SUB('%s', INTERVAL 6 DAY) AND '%s'",
                        periodValue, periodValue
                );
                orderBy = "DATE(o.payment_date) ASC";
                break;
            case "month":
                selectClause = "DATE_FORMAT(o.payment_date, '%Y-%m') AS period";
                groupByClause = "DATE_FORMAT(o.payment_date, '%Y-%m')";
                whereClause = "YEAR(o.payment_date) = '" + periodValue + "'"; // '2024'
                orderBy = "DATE_FORMAT(o.payment_date, '%Y-%m') ASC";
                break;
            case "year":
                selectClause = "YEAR(o.payment_date) AS period";
                groupByClause = "YEAR(o.payment_date)";
                whereClause = "YEAR(o.payment_date) = '" + periodValue + "'"; // '2024'
                orderBy = "YEAR(o.payment_date) ASC";
                break;
            case "range":
                // Split periodValue into startDate and endDate
                String[] dateRange = periodValue.split(",");
                if (dateRange.length != 2) {
                    throw new IllegalArgumentException("Invalid range format. Expected 'startDate,endDate' (e.g., '2024-01-01,2024-12-31').");
                }
                String startDate = dateRange[0];
                String endDate = dateRange[1];

                selectClause = "'ranger' AS period";
                whereClause = String.format(
                        "DATE(o.payment_date) BETWEEN '%s' AND '%s'",
                        startDate, endDate
                );
                // Không sử dụng SELECT và GROUP BY trong "range"
                orderBy = "DATE(o.payment_date) ASC";  // Chỉ có ORDER BY ở đây
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + period);
        }

        // Query for data (with or without GROUP BY based on period)
        String sql = String.format("""
        SELECT %s, 
               SUM(oi.sale_price * oi.quantity) AS revenue,
               SUM(oi.quantity) AS quantity
          FROM `order` o
          JOIN `order_item` oi ON o.id = oi.order_id
          WHERE o.order_status = 6 AND %s
          %s
          ORDER BY %s;
    """, selectClause, whereClause,
                period.equals("range") ? "" : "GROUP BY " + groupByClause, orderBy);

        Query query = entityManager.createNativeQuery(sql, "RevenueByPeriodMapping");

        // Apply pagination using Pageable directly
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<RevenueStatisticsDTO> results = query.getResultList();

        // Query for total count (without GROUP BY for range)
        String countSql = String.format("""
        SELECT COUNT(*) 
          FROM `order` o
          JOIN `order_item` oi ON o.id = oi.order_id
          WHERE o.order_status = 6 AND %s
    """, whereClause);

        Query countQuery = entityManager.createNativeQuery(countSql);
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        // Return Page
        return new PageImpl<>(results, pageable, totalElements);
    }



    public Page<RevenueStatisticsDTO> findOrderStatusByPeriod(String period, Pageable pageable, String periodValue) {
        if (!List.of("day", "month", "year", "range").contains(period)) {
            throw new IllegalArgumentException("Invalid period. Must be 'day', 'month', 'year', or 'range'.");
        }

        if (periodValue == null || periodValue.isEmpty()) {
            switch (period) {
                case "day":
                    periodValue = LocalDate.now().toString();
                    break;
                case "month":
                    periodValue = String.valueOf(LocalDate.now().getYear());
                    break;
                case "year":
                    periodValue = String.valueOf(LocalDate.now().getYear());
                    break;
                case "range":
                    periodValue = LocalDate.now().toString() + "," + LocalDate.now().toString();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period.");
            }
        }

        String selectClause = "";
        String groupByClause = "";
        String whereClause = "";
        String orderBy = "";

        switch (period) {
            case "day":
                selectClause = "DATE(o.payment_date) AS period";
                groupByClause = "DATE(o.payment_date)";
                whereClause = String.format(
                        "DATE(o.payment_date) BETWEEN DATE_SUB('%s', INTERVAL 6 DAY) AND '%s'",
                        periodValue, periodValue
                );
                orderBy = "DATE(o.payment_date) ASC";
                break;
            case "month":
                selectClause = "DATE_FORMAT(o.payment_date, '%Y-%m') AS period";
                groupByClause = "DATE_FORMAT(o.payment_date, '%Y-%m')";
                whereClause = "YEAR(o.payment_date) = '" + periodValue + "'";
                orderBy = "DATE_FORMAT(o.payment_date, '%Y-%m') ASC";
                break;
            case "year":
                selectClause = "YEAR(o.payment_date) AS period";
                groupByClause = "YEAR(o.payment_date)";
                whereClause = "YEAR(o.payment_date) = '" + periodValue + "'";
                orderBy = "YEAR(o.payment_date) ASC";
                break;
            case "range":
                String[] dateRange = periodValue.split(",");
                if (dateRange.length != 2) {
                    throw new IllegalArgumentException("Invalid range format. Expected 'startDate,endDate'.");
                }
                String startDate = dateRange[0];
                String endDate = dateRange[1];
                selectClause = "'range' AS period";
                whereClause = String.format(
                        "DATE(o.payment_date) BETWEEN '%s' AND '%s'",
                        startDate, endDate
                );
                orderBy = "DATE(o.payment_date) ASC";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + period);
        }

        // Cập nhật SELECT để chỉ đếm hóa đơn thành công và thất bại
        String sql = String.format("""
        SELECT %s, 
               COUNT(CASE WHEN o.order_status IN (1, 6) THEN 1 END) AS total_success,
               COUNT(CASE WHEN o.order_status = 7 THEN 1 END) AS total_failed
          FROM `order` o
          WHERE %s
          %s
          ORDER BY %s;
    """, selectClause, whereClause,
                period.equals("range") ? "" : "GROUP BY " + groupByClause, orderBy);

        Query query = entityManager.createNativeQuery(sql, "OrderStatusByPeriodMapping");

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<RevenueStatisticsDTO> results = query.getResultList();

        // Đếm tổng số dòng (không GROUP BY cho range)
        String countSql = String.format("""
        SELECT COUNT(*) 
          FROM `order` o
          WHERE %s
    """, whereClause);

        Query countQuery = entityManager.createNativeQuery(countSql);
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }






    public Page<InventoryResponse> filterProductVariantsByStock(Pageable pageable, int stock) {
        // Truy vấn chính để lấy dữ liệu

        String sql = """
                    SELECT 
                        pv.id AS id, 
                        p.id AS productId, 
                        p.product_name AS productName, 
                        pv.sku AS sku, 
                        c.id AS colorId,
                        c.color_code AS colorCode,
                        c.color_name AS colorName, 
                        s.id AS sizeId,
                        s.size_name AS sizeName, 
                        pv.quantity_in_stock AS quantityInStock,
                        pi.image_url AS imageUrl
                    FROM 
                        product_variant pv
                    JOIN 
                        product p ON pv.product_id = p.id
                    LEFT JOIN 
                        color c ON pv.color_id = c.id
                    LEFT JOIN 
                        size s ON pv.size_id = s.id
                    LEFT JOIN 
                        product_image pi ON p.id = pi.product_id AND pi.is_default = 1
                    WHERE 
                        pv.quantity_in_stock <= ?
                """;


        Query query = entityManager.createNativeQuery(sql, "ProductVariantResponseMapping");
        query.setParameter(1, stock); // Thiết lập tham số

        // Pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<InventoryResponse> results = query.getResultList();

        // Truy vấn để đếm tổng số phần tử
        String countSql = """
                    SELECT COUNT(*)
                    FROM product_variant pv
                    WHERE pv.quantity_in_stock <= ?
                """;

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter(1, stock); // Thiết lập tham số
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        // Trả về kết quả phân trang
        return new PageImpl<>(results, pageable, totalElements);
    }

    public Page<InventoryResponse> TopSellingProduct(Pageable pageable, int top) {
        // Truy vấn chính để lấy dữ liệu với LIMIT và OFFSET
        String sql = """
            SELECT
                pv.id AS id, 
                p.id AS productId, 
                p.product_name AS productName, 
                pv.sku AS sku, 
                c.id AS colorId,
                c.color_code AS colorCode,
                c.color_name AS colorName, 
                s.id AS sizeId,
                s.size_name AS sizeName, 
                pi.image_url AS imageUrl,
                SUM(oi.quantity) AS totalQuantitySold
            FROM
                product_variant pv
            INNER JOIN
                product p ON pv.product_id = p.id
            LEFT JOIN
                color c ON pv.color_id = c.id
            LEFT JOIN
                size s ON pv.size_id = s.id
            INNER JOIN
                order_item oi ON pv.id = oi.product_variant_id
            INNER JOIN
                `order` o ON oi.order_id = o.id
            LEFT JOIN
                product_image pi ON p.id = pi.product_id AND pi.is_default = 1
            WHERE
                o.order_status IN (1, 6)
            GROUP BY
                pv.id, p.id, p.product_name, c.color_name, s.size_name, pv.sku, pi.image_url
            ORDER BY
                totalQuantitySold DESC
            LIMIT :limit OFFSET :offset
            """;

        // Tạo câu truy vấn với tham số LIMIT và OFFSET
        Query query = entityManager.createNativeQuery(sql, "ProductVariantResponseMapping2");

        // Thiết lập tham số LIMIT và OFFSET cho câu truy vấn
        query.setParameter("limit", top);  // Lấy top N sản phẩm bán chạy nhất
        query.setParameter("offset", pageable.getOffset());  // Dịch chuyển tới vị trí của trang

        @SuppressWarnings("unchecked")
        List<InventoryResponse> results = query.getResultList();

        // Truy vấn để đếm tổng số phần tử (tổng số sản phẩm bán chạy nhất)
        String countSql = """
            SELECT COUNT(DISTINCT p.id)
            FROM
                product_variant pv
            INNER JOIN
                product p ON pv.product_id = p.id
            LEFT JOIN
                color c ON pv.color_id = c.id
            LEFT JOIN
                size s ON pv.size_id = s.id
            INNER JOIN
                order_item oi ON pv.id = oi.product_variant_id
            INNER JOIN
                `order` o ON oi.order_id = o.id
            WHERE
                o.order_status IN (1, 6)
            """;

        Query countQuery = entityManager.createNativeQuery(countSql);
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();

        // Trả về kết quả phân trang
        return new PageImpl<>(results, pageable, totalElements);
    }
}
