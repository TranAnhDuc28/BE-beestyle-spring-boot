package com.datn.beestyle.service.customer;


import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService
    extends GenericServiceAbstract<Customer,Integer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse>
    implements ICustomerService{

    private final CustomerRepository customerRepository;

    public CustomerService(IGenericRepository<Customer, Integer> entityRepository,
                           IGenericMapper<Customer, CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse> mapper,
                           EntityManager entityManager, CustomerRepository customerRepository) {
        super(entityRepository, mapper, entityManager);
        this.customerRepository = customerRepository;
    }


    @Override
    public PageResponse<?> getAll(Pageable pageable) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        PageRequest pageRequest = PageRequest.of(page , pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Customer> customerPage = customerRepository.findAll(pageRequest);
        List<CustomerResponse> customerResponseList = mapper.toEntityDtoList(customerPage.getContent());
        return PageResponse.builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .items(customerResponseList)
                .build();

    }

    @Override
    protected List<CreateCustomerRequest> beforeCreateEntities(List<CreateCustomerRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateCustomerRequest> beforeUpdateEntities(List<UpdateCustomerRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateCustomerRequest request) {
//        Customer customer = new Customer();
//        customer.setFullName(request.getFullName());
//        customer.setDateOfBirth(request.getDateOfBirth());
//        customer.setGender(request.getGender());
//        customer.setPhoneNumber(request.getPhoneNumber());
//        customer.setEmail(request.getEmail());
//        customer.setPassword(request.getPassword());
//        customer.setDeleted(request.isDeleted());
//
//        // Kiểm tra nếu ShoppingCart không tồn tại thì bỏ qua phần xử lý này
////        if (request.getShoppingCart() != null && request.getShoppingCart().getId() != null) {
////            ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, request.getShoppingCart().getId());
////            if (shoppingCart != null) {
////                shoppingCart.setCustomer(customer);
////                customer.setShoppingCart(shoppingCart);
////                entityManager.merge(shoppingCart);
////            }
////        }
//
//        entityManager.persist(customer);
    }

    @Override
    protected void beforeUpdate(UpdateCustomerRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateCustomerRequest request, Customer entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateCustomerRequest request, Customer entity) {

    }

    @Override
    protected String getEntityName() {
        return "Customer";
    }



}
