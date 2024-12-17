package com.datn.beestyle.service.voucher;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.enums.DiscountStatus;
import com.datn.beestyle.enums.DiscountType;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.mapper.VoucherMapper;
import com.datn.beestyle.repository.VoucherRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class VoucherService
        extends GenericServiceAbstract<Voucher, Integer, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse>
        implements IVoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    protected VoucherService(IGenericRepository<Voucher, Integer> entityRepository,
                             IGenericMapper<Voucher, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse> mapper,
                             VoucherRepository voucherRepository, VoucherMapper voucherMapper, EntityManager entityManager) {
        super(entityRepository, mapper, entityManager);
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    @Override
    public PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status, String discountType, Timestamp startDate,Timestamp endDate) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        Integer statusValue = null;
        if (status != null) {
            DiscountStatus statusEnum = DiscountStatus.fromString(status.toUpperCase());
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }

        Integer discountTypeValue = null;
        if (discountType != null) {
            DiscountType discountTypeEnum = DiscountType.fromString(discountType.toUpperCase());
            if (discountTypeEnum != null) discountTypeValue = discountTypeEnum.getValue();
        }

        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Voucher> voucherPage = voucherRepository.findByNameContainingAndStatus(
                pageRequest,
                name,
                statusValue,
                discountTypeValue,
                startDate,
                endDate
        );
        List<VoucherResponse> voucherResponseList = mapper.toEntityDtoList(voucherPage.getContent());

        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .items(voucherResponseList)
                .build();
    }


    @Override
    public List<VoucherResponse> createVoucher(List<CreateVoucherRequest> requestList) {
        List<Voucher> voucherList = mapper.toCreateEntityList(requestList);
        return mapper.toEntityDtoList(voucherRepository.saveAll(voucherList));
    }

    public Page<Voucher> getVoucherByDateRange(Timestamp startDate, Timestamp endDate, Pageable pageable) {
        return voucherRepository.findByDateRange(startDate, endDate, pageable);

    }

    public PageResponse<?> getAll(Pageable pageable) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Voucher> voucherPage = voucherRepository.findAll(pageRequest);
        List<VoucherResponse> voucherResponseList = mapper.toEntityDtoList(voucherPage.getContent());

        return PageResponse.builder()
                .pageNo(voucherPage.getNumber() + 1)
                .pageSize(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .items(voucherResponseList)
                .build();
    }

    //    public Page<Voucher> getVoucherByDiscountType(Pageable pageable, String discountType) {
//        if (discountType != null) {
//            if (discountType.equals("PERCENTAGE")) {
//                return voucherRepository.findVouchersByDiscountType(DiscountType.PERCENTAGE, pageable);
//            } else if (discountType.equals("CASH")) {
//                return voucherRepository.findVouchersByDiscountType(DiscountType.CASH, pageable);
//            }
//        }
//        // Nếu không có discountType, trả về tất cả
//        return voucherRepository.findAll(pageable);
//    }
    public List<Voucher> getValidVouchers(BigDecimal totalAmount) {
        return voucherRepository.findValidVouchers(1, totalAmount);
    }

    @Override
    protected List<CreateVoucherRequest> beforeCreateEntities(List<CreateVoucherRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateVoucherRequest> beforeUpdateEntities(List<UpdateVoucherRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateVoucherRequest request) {
        // Logic kiểm tra trước khi tạo voucher nếu cần
    }

    @Override
    protected void beforeUpdate(Integer id, UpdateVoucherRequest request) {
        // Logic kiểm tra trước khi cập nhật voucher nếu cần
    }

    @Override
    protected void afterConvertCreateRequest(CreateVoucherRequest request, Voucher entity) {
        // Bổ sung logic sau khi chuyển đổi CreateVoucherRequest thành Voucher nếu cần
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateVoucherRequest request, Voucher entity) {
        // Bổ sung logic sau khi chuyển đổi UpdateVoucherRequest thành Voucher nếu cần
    }

    @Override
    protected String getEntityName() {
        return "Voucher";
    }

    @Override
    public List<VoucherResponse> getAllById(Set<Integer> integers) {
        return null;
    }
}
