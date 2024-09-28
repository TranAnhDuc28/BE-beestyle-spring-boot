package com.datn.beestyle.service.product.attributes.size;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.size.CreateSizeRequest;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.attributes.size.UpdateSizeRequest;
import com.datn.beestyle.entity.product.attributes.Size;
import org.springframework.data.domain.Pageable;

public interface ISizeService
        extends IGenericService<Size, Integer, CreateSizeRequest, UpdateSizeRequest, SizeResponse> {

    PageResponse<?> getAllByNameAndDeleted(Pageable pageable, String name, Short status);
}
