package com.datn.beestyle.service.customer;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
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
