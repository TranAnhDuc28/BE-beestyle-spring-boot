package com.datn.beestyle.service.scheduler;

import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.PromotionRepository;
import com.datn.beestyle.repository.VoucherRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StatusUpdateScheduler {
    private final PromotionRepository promotionRepository;
    private final VoucherRepository voucherRepository;

    public StatusUpdateScheduler(PromotionRepository promotionRepository, VoucherRepository voucherRepository) {
        this.promotionRepository = promotionRepository;
        this.voucherRepository = voucherRepository;
    }

//    @Scheduled(cron = "0 0 * * * ?") // Chạy mỗi giờ
    public void updateStatuses() {

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        List<Promotion> promotions = promotionRepository.findAll();
        for (Promotion promotion : promotions) {
            if (currentTimestamp.after(promotion.getEndDate()) || currentTimestamp.before(promotion.getStartDate())) {
                promotion.setStatus(Status.INACTIVE.getValue());
            } else {
                promotion.setStatus(Status.ACTIVE.getValue());
            }
        }
        promotionRepository.saveAll(promotions);


        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            if (currentTimestamp.after(voucher.getEndDate()) || currentTimestamp.before(voucher.getStartDate())) {
                voucher.setStatus(Status.INACTIVE.getValue());
            } else {
                voucher.setStatus(Status.ACTIVE.getValue());
            }
        }
        voucherRepository.saveAll(vouchers);
    }
}
