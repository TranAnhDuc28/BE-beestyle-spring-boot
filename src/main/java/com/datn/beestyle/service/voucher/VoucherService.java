package com.datn.beestyle.service.voucher;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.IGenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.mapper.VoucherMapper;
import com.datn.beestyle.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VoucherService
        extends IGenericServiceAbstract<Voucher, Integer, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse>
        implements IVoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    protected VoucherService(IGenericRepository<Voucher, Integer> entityRepository,
                             IGenericMapper<Voucher, CreateVoucherRequest, UpdateVoucherRequest, VoucherResponse> mapper,
                             VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        super(entityRepository, mapper);
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    @Override
    public PageResponse<?> searchByName(Pageable pageable, String code) {
        Page<Voucher> voucherPage = voucherRepository.findAllByVoucherCodeContaining(pageable, code);
        List<VoucherResponse> voucherResponseList = mapper.toEntityDtoList(voucherPage.getContent());
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
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

    @Override
    protected void beforeCreate(CreateVoucherRequest request) {
        // Logic kiểm tra trước khi tạo voucher nếu cần
    }

    @Override
    protected void beforeUpdate(UpdateVoucherRequest request) {
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
}
