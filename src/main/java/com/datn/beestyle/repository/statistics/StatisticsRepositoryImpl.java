    package com.datn.beestyle.repository.statistics;

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

        public Page<RevenueStatisticsDTO> findRevenueByPeriod(String period, Pageable pageable) {
            // Validate period to avoid SQL injection
            if (!List.of("day", "month", "year").contains(period)) {
                throw new IllegalArgumentException("Invalid period. Must be 'day', 'month', or 'year'.");
            }

            // Dynamically build the GROUP BY clause based on the period
            String groupByClause = switch (period) {
                case "day" -> "DATE(o.payment_date)";
                case "month" -> "CONCAT(YEAR(o.payment_date), '-', MONTH(o.payment_date))";
                case "year" -> "YEAR(o.payment_date)";
                default -> throw new IllegalStateException("Unexpected value: " + period);
            };

            // Build the WHERE clause dynamically to filter by current date, month, or year
            String whereClause = switch (period) {
                case "day" -> "o.payment_date >= CURRENT_DATE() AND o.payment_date < CURRENT_DATE() + INTERVAL 1 DAY";
                case "month" -> "YEAR(o.payment_date) = YEAR(CURRENT_DATE()) AND MONTH(o.payment_date) = MONTH(CURRENT_DATE())";
                case "year" -> "YEAR(o.payment_date) = YEAR(CURRENT_DATE())";
                default -> throw new IllegalStateException("Unexpected value: " + period);
            };

            // Query for data
            String sql = String.format("""
  SELECT %s AS period, 
         SUM(oi.sale_price * oi.quantity) AS revenue,
         SUM(oi.quantity) AS quantity
  FROM `order` o
  JOIN `order_item` oi ON o.id = oi.order_id
  WHERE o.order_status = 6 AND %s
  GROUP BY %s
""", groupByClause,whereClause, groupByClause);


            Query query = entityManager.createNativeQuery(sql, "RevenueByPeriodMapping");

            // Apply pagination
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

            List<RevenueStatisticsDTO> results = query.getResultList();

            // Query for total count
            String countSql = String.format("""
              SELECT COUNT(*) 
              FROM (SELECT %s 
                    FROM `order` o
                    JOIN `order_item` oi ON o.id = oi.order_id
                    WHERE o.order_status = 6 AND %s
                    GROUP BY %s) AS temp
            """, groupByClause,whereClause, groupByClause);

            Query countQuery = entityManager.createNativeQuery(countSql);
            long totalElements = ((Number) countQuery.getSingleResult()).longValue();

            // Return Page
            return new PageImpl<>(results, pageable, totalElements);
        }

    }
