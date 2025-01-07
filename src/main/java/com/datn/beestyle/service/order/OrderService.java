package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.address.AddressResponse;
<<<<<<< HEAD
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.exception.ResourceNotFoundException;
import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.service.address.AddressService;
import com.datn.beestyle.service.voucher.VoucherService;
=======
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.order.CreateOrderOnlineRequest;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.dto.order.item.CreateOrderItemOnlineRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.enums.*;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.mapper.OrderMapper;
import com.datn.beestyle.repository.AddressRepository;
import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.repository.ProductVariantRepository;
import com.datn.beestyle.service.address.IAddressService;
import com.datn.beestyle.service.customer.ICustomerService;
import com.datn.beestyle.service.voucher.IVoucherService;
>>>>>>> e8b22138f9a904dfd932b729f58e48ffc8365b78
import com.datn.beestyle.util.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
>>>>>>> e8b22138f9a904dfd932b729f58e48ffc8365b78

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
public class OrderService
        extends GenericServiceAbstract<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse>
        implements IOrderService {

    private final OrderRepository orderRepository;
<<<<<<< HEAD
    private final AddressService addressService;
    private final VoucherService voucherService;

    public OrderService(IGenericRepository<Order, Long> entityRepository,
                        IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest, OrderResponse> mapper,
                        EntityManager entityManager, OrderRepository orderRepository, AddressService addressService, VoucherService voucherService) {
        super(entityRepository, mapper, entityManager);
        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.voucherService = voucherService;
=======
    private final ICustomerService customerService;
    private final IVoucherService voucherService;
    private final IAddressService addressService;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;
    private final ProductVariantRepository productVariantRepository;

    public OrderService(IGenericRepository<Order, Long> entityRepository,
                        IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest, OrderResponse> mapper,
                        EntityManager entityManager, OrderRepository orderRepository, ICustomerService customerService,
                        IVoucherService voucherService, IAddressService addressService, AddressRepository addressRepository,
                        OrderMapper orderMapper, ProductVariantRepository productVariantRepository) {
        super(entityRepository, mapper, entityManager);
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.orderMapper = orderMapper;
        this.productVariantRepository = productVariantRepository;
>>>>>>> e8b22138f9a904dfd932b729f58e48ffc8365b78
    }

    public PageResponse<List<OrderResponse>> getOrdersFilterByFields(Pageable pageable, Map<String, String> filters) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by("createdAt", "id").descending());

        String keyword = filters.getOrDefault("keyword", null);

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        // Xử lý startDate
        try {
            if (filters.get("startDate") != null) {
                startDate = LocalDate.parse(filters.get("startDate")).atStartOfDay();
            }
        } catch (Exception e) {
            log.error("Định dạng ngày không hợp lệ cho startDate: " + filters.get("startDate"));
        }

        // Xử lý endDate
        try {
            if (filters.get("endDate") != null) {
                endDate = LocalDate.parse(filters.get("endDate")).atTime(LocalTime.MAX);
            }
        } catch (Exception e) {
            log.error("Định dạng ngày không hợp lệ cho endDate: " + filters.get("endDate"));
        }

        Integer month = filters.get("month") != null ? Integer.parseInt(filters.get("month")) : null;
        Integer year = filters.get("year") != null ? Integer.parseInt(filters.get("year")) : null;

        Integer orderChannelValue = null;
        String orderChannel = filters.getOrDefault("orderChannel", null);
        if (orderChannel != null) {
            OrderChannel orderChannelEnum = OrderChannel.fromString(orderChannel.toUpperCase());
            orderChannelValue = orderChannelEnum != null ? orderChannelEnum.getValue() : null;
        }

        List<Integer> orderStatusIdList = null;
        String orderStatusValues = filters.getOrDefault("orderStatus", null);
        if (orderStatusValues != null) {
            orderStatusIdList = AppUtils.handleStringIdsToIntegerIdList(orderStatusValues);
        }

        Page<OrderResponse> orderResponsePages = orderRepository.findAllByFields(pageRequest, keyword, startDate, endDate,
                month, year, orderChannelValue, orderStatusIdList);

        return PageResponse.<List<OrderResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(orderResponsePages.getTotalElements())
                .totalPages(orderResponsePages.getTotalPages())
                .items(orderResponsePages.getContent())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersPending() {
<<<<<<< HEAD
        return orderRepository.findOrdersByOrderChannelAndOrderStatus(OrderChannel.OFFLINE.getValue(), OrderStatus.PENDING.getValue());
    }

    /**
     * Lấy thông tin chi tiết đầy đủ nhất của hóa đơn
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderResponse getOrderByOrderId(Long orderId) {
        // lấy thông tin hóa đơn
        Optional<OrderResponse> order = orderRepository.findOrderById(orderId);
        if (order.isEmpty()) throw new ResourceNotFoundException(this.getEntityName() + " not found.");

        // lấy thông tin địa chỉ giao hàng
        Long addressId = order.get().getAddressId();
        if (addressId != null) {
            AddressResponse address = addressService.getDtoById(addressId);
            StringJoiner stringJoiner = new StringJoiner(", ");

            if (address.getAddressName() != null && !address.getAddressName().isEmpty()) {
                stringJoiner.add(address.getAddressName());
            }
            stringJoiner.add(address.getCommune());
            stringJoiner.add(address.getDistrict());
            stringJoiner.add(address.getCity());

            order.get().setShippingAddress(stringJoiner.toString());
        }

        // lấy thông tin voucher áp dụng
        Integer voucherId = order.get().getVoucherId();
        if (voucherId != null) {
            Voucher voucher = voucherService.getById(voucherId);
            order.get().setVoucherName(voucher.getVoucherName());
        }

        return order.get();
=======
        return orderRepository.findOrdersByOrderChannelAndOrderStatus(OrderChannel.OFFLINE.getValue(),
                OrderStatus.PENDING.getValue());
>>>>>>> e8b22138f9a904dfd932b729f58e48ffc8365b78
    }

    @Override
    public OrderResponse getOrderDetailById(Long id) {
        OrderResponse orderResponse = this.getDtoById(id);

        if (orderResponse.getCustomerId() != null) {
            CustomerResponse customerResponse = customerService.getDtoById(orderResponse.getCustomerId());
            orderResponse.setCustomerInfo(customerResponse);
        }

        // kiểm tra hóa đơn được áp dụng voucher không
        if (orderResponse.getVoucherId() != null) {
            VoucherResponse voucherResponse = voucherService.getDtoById(orderResponse.getVoucherId());
            orderResponse.setVoucherInfo(voucherResponse);
        }

        // Lấy địa chỉ giao hàng của hóa đơn
        if (orderResponse.getShippingAddressId() != null) {
            AddressResponse addressResponse = addressService.getDtoById(orderResponse.getShippingAddressId());
            orderResponse.setShippingAddress(addressResponse);
        }

        return orderResponse;
    }

    @Override
    public String changeOrderStatus(Long id, String status, String note) {
        // lấy và kiểm tra tồn tại đơn hàng
        Order order = this.getById(id);

        // Chuyển từ chuỗi sang enum
        OrderStatus orderStatus = OrderStatus.fromString(status);
        // kiểm tra key enum có chính xác
        if (orderStatus == null) {
            throw new IllegalArgumentException("Trạng thái hóa đơn " + status + " không hợp lệ.");
        }

        if (orderStatus == OrderStatus.CONFIRMED) {

        } else if (orderStatus == OrderStatus.CANCELLED) {

        } else if (orderStatus == OrderStatus.RETURNED) {

        }

        // Cập nhật trạng thái và lưu
        order.setOrderStatus(orderStatus.getValue());
        orderRepository.save(order);

        return order.getOrderTrackingNumber();
    }

    @Transactional
    @Override
    public OrderResponse createOrderOnline(CreateOrderOnlineRequest request) {
        // Tạo đơn hàng mới
        Order order = new Order();

        // Kiểm tra có sản phẩm nào trong giỏ được gửi về không
        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            throw new RuntimeException("Không có sản phẩm nào trong giỏ, vui lòng mua ít nhất 1 sản phẩm để tiến hành thanh toán.");
        }

        // Kiểm tra phương thức thanh toán
        if (request.getPaymentMethod() == null) {
            throw new InvalidDataException("Vui lòng chọn phương thức thanh toán.");
        }

        // Kiểm tra địa chỉ giao hàng đã nhập chưa
        if (!StringUtils.hasText(request.getShippingAddress())) {
            throw new InvalidDataException("Vui lòng nhập địa chỉ giao hàng");
        }

        // kiểm tra khách hàng có đăng nhập không
        if (request.getCustomerId() != null) {
            Customer customer = customerService.getById(request.getCustomerId());
            order.setCustomer(customer);
        }

        // kiểm tra tiền ship có được miễn phí hay không
        if (request.getOriginalAmount().compareTo(new BigDecimal(AppUtils.FREE_SHIPPING_THRESHOLD)) < 0) {
            throw new InvalidDataException("Tổng giá trị đơn hàng chưa đủ để miễn phí ship.");
        }
        order.setShippingFee(new BigDecimal(0));

        order.setOrderTrackingNumber(AppUtils.generateOrderTrackingNumber());
        order.setReceiverName(request.getReceiverName()); // tên người nhận
        order.setPhoneNumber(request.getPhoneNumber()); // số điện thoại nhận hàng
        order.setOrderChannel(OrderChannel.ONLINE.getValue());  // kênh online
        order.setOrderType(OrderType.DELIVERY.getValue()); // loại giao hàng
        order.setOrderStatus(OrderStatus.AWAITING_CONFIRMATION.getValue()); // trạng thái chờ xác nhận

        // kiểm tra Address
        // chuyển đổi chỗi JSON sang obj Address
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Address address = objectMapper.readValue(request.getShippingAddress().trim(), Address.class);
            order.setShippingAddress(address);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing address: " + e.getMessage());
        }

        String paymentMethod = request.getPaymentMethod();
        // nếu phương thức thanh toán là COD
        if (paymentMethod.equalsIgnoreCase(PaymentMethod.CASH.name()) ||
            paymentMethod.equalsIgnoreCase(PaymentMethod.CASH_AND_BANK_TRANSFER.name())) {
            PaymentMethod paymentMethodEnum = PaymentMethod.fromString(paymentMethod);
            order.setPaymentMethod(paymentMethodEnum.getValue());
            order.setPrepaid(false);
        } else if (request.getPaymentMethod().equalsIgnoreCase(PaymentMethod.BANK_TRANSFER.name())) { // nếu phương thức thanh toán là VNpay
            order.setPaymentMethod(PaymentMethod.BANK_TRANSFER.getValue());
            order.setPrepaid(true);
        }

        // kiểm tra hóa đơn có áp dụng voucher không
        if (request.getVoucherId() != null) {
            // kiểm tra voucher thỏa mãn sẽ lưu vào order
            Voucher voucher = this.validateVoucherForOrder(request.getOriginalAmount(), request.getDiscountAmount(),
                    request.getVoucherId());

            order.setVoucher(voucher);
        }

        // xử lý list order item của hóa đơn
        List<OrderItem> orderItems = handleOrderItemsOnline(request.getOrderItems());
        order.setOrderItems(orderItems);

        return orderMapper.toEntityDto(orderRepository.save(order));
    }


    /**
     * hàm update order
     *
     * @param orderId id hóa đơn
     * @param request nội dung hóa đơn được gửi từ client về server
     * @return trả ra hóa đơn đã được thanh toán thành công
     */
    @Transactional
    @Override
    public OrderResponse update(Long orderId, UpdateOrderRequest request) {
        // kiểm tra đơn hàng có tồn tại
        Order order = this.getById(orderId);

        // kiểm tra có phải khách lẻ không
        if (request.getCustomerId() != null) {
            Customer customer = customerService.getById(request.getCustomerId());

            // k phải khách lẻ lưu thông tin vào hóa đơn
            order.setCustomer(customer);
        }

        // kiểm tra hóa đơn có áp dụng voucher không
        if (request.getVoucherId() != null) {
            // kiểm tra voucher thỏa mãn sẽ lưu vào order
            Voucher voucher = this.validateVoucherForOrder(request.getOriginalAmount(), request.getDiscountAmount(),
                    request.getVoucherId());

            order.setVoucher(voucher);
        }

        // kiểm tra tiền ship nếu là đơn giao hàng
        if (request.getOrderType().equalsIgnoreCase(OrderType.DELIVERY.name())) {
            if (request.getOriginalAmount().compareTo(new BigDecimal(AppUtils.FREE_SHIPPING_THRESHOLD)) < 0) {
                throw new InvalidDataException("Tổng giá trị đơn hàng chưa đủ để miễn phí ship.");
            }
            order.setShippingFee(new BigDecimal(0));
        }

        // kiểm tra tổng tiền phải trả cho đơn hàng trả về có chính xác không
        // trả về true tính toán chính xác, ngược lại false trả về lỗi
        if (!this.validateTotalAmount(request.getOriginalAmount(), request.getDiscountAmount(),
                request.getShippingFee(), request.getTotalAmount())) {
            throw new InvalidDataException("Tổng tiền cần trả cho đơn hàng không chính xác.");
        }
        // chính xác thì cập nhật tổng tiền thanh toán vào hóa đơn
        order.setTotalAmount(request.getTotalAmount());

        // kiểm tiền khách trả đã đủ chưa
        if (this.validatePaymentAmount(request.getTotalAmount(), request.getAmountPaid())) {
            throw new InvalidDataException("Tiền khách trả không đủ.");
        }

        // Kiểm tra địa chỉ giao hàng có tồn tại không
        if (request.getShippingAddressId() != null) {
            Address shippingAddress = addressService.getById(request.getShippingAddressId());
            order.setShippingAddress(shippingAddress);
        }

        // cập nhật thời gian thanh toán đơn hàng
        order.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderType(OrderType.fromString(request.getOrderType()).getValue());
        order.setPaymentMethod(PaymentMethod.fromString(request.getPaymentMethod()).getValue());
        order.setOrderChannel(OrderChannel.fromString(request.getOrderChannel()).getValue());
        order.setOrderStatus(OrderStatus.fromString(request.getOrderStatus()).getValue());
        order.setNote(request.getNote());

        // Lưu thông tin đơn hàng đã cập nhật vào cơ sở dữ liệu
        return orderMapper.toEntityDto(orderRepository.save(order));
    }


    @Override
    protected List<CreateOrderRequest> beforeCreateEntities(List<CreateOrderRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateOrderRequest> beforeUpdateEntities(List<UpdateOrderRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateOrderRequest request) {
        int countOrderPending = orderRepository.countByCreatedByAndAndOrderStatus(1L, OrderStatus.PENDING.getValue());
        if (countOrderPending >= 20)
            throw new InvalidDataException("Hóa đơn chờ tạo tối đa 20, vui lòng sử dụng để tiếp tục tạo! ");
    }

    @Override
    protected void beforeUpdate(Long id, UpdateOrderRequest request) {

    }

    /**
     * sau khi convert data request sang entity order để tạo hóa đơn chờ bán hàng tại quầy
     *
     * @param request
     * @param entity
     */
    @Override
    protected void afterConvertCreateRequest(CreateOrderRequest request, Order entity) {
        entity.setOrderTrackingNumber(AppUtils.generateOrderTrackingNumber());
        entity.setOrderChannel(OrderChannel.OFFLINE.getValue());

        // kiểm tra loại hóa đơn cho kênh bán hàng OFFLINE
        if (request.getOrderType().equalsIgnoreCase(OrderType.DELIVERY.name())) {
            OrderType orderType = OrderType.fromString(request.getOrderType());
            entity.setOrderType(orderType.getValue());
        } else {
            entity.setOrderType(OrderType.IN_STORE_PURCHASE.getValue());
        }

        // mặc định khi tạo hóa đơn chờ thì phương thức thanh toán là CASH
        entity.setPaymentMethod(PaymentMethod.CASH.getValue());
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateOrderRequest request, Order entity) {

    }

    @Override
    protected String getEntityName() {
        return "Order";
    }


    /**
     * Kiểm tra tính đúng đắn của số tiền cần thanh toán.
     *
     * @param originalAmount Giá trị gốc của đơn hàng.
     * @param discountAmount Số tiền giảm giá từ voucher.
     * @param shippingFee    Phí vận chuyển.
     * @param totalAmount    Số tiền tổng cộng (đã tính giảm giá và phí vận chuyển).
     * @return true nếu tổng tiền tính toán bằng với totalAmount, false nếu không.
     */
    private boolean validateTotalAmount(BigDecimal originalAmount, BigDecimal discountAmount,
                                        BigDecimal shippingFee, BigDecimal totalAmount) {
        // Tính toán lại totalAmount từ các giá trị
        BigDecimal calculatedTotalAmount = originalAmount
                .subtract(discountAmount) // subtract (-)
                .add(shippingFee); // add (+)

        // So sánh totalAmount tính toán với tổng tiền gửi vào
        return calculatedTotalAmount.compareTo(totalAmount) == 0;
    }


    /**
     * Hàm kiểm tra tiền khách trả có bằng hoặc lớn hơn số tiền cần thanh toán không
     *
     * @param totalAmount Tiền cần thanh toán
     * @param amountPaid  Tiền khách trả
     * @return true nếu tiền thanh toán lớn hơn hoặc bằng totalAmount, false nếu không.
     */
    private boolean validatePaymentAmount(BigDecimal totalAmount, BigDecimal amountPaid) {
        // Kiểm tra tiền khách trả có lớn hơn hoặc bằng số tiền cần thanh toán
        return amountPaid.compareTo(totalAmount) < 0;
    }


    /**
     * tính giá giảm cho hóa đơn theo giá gốc tổng tiền hàng
     *
     * @param originalAmount giá gốc tổng tiền hàng
     * @param voucher        voucher áp dụng
     * @return giá trị giảm cho đơn hàng sau khi áp dụng voucher
     */
    private BigDecimal calculateDiscountAmount(BigDecimal originalAmount, Voucher voucher) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        // Kiểm tra nếu voucher là giảm giá tiền mặt (giảm giá cố định)
        if (voucher.getDiscountType() == DiscountType.CASH.getValue()) {
            // gán giá trị cố định cung cấp trong voucher
            discountAmount = new BigDecimal(voucher.getDiscountValue());
            if (discountAmount.compareTo(new BigDecimal(voucher.getMaxDiscount())) > 0) {
                discountAmount = new BigDecimal(voucher.getMaxDiscount());
            }
        } else if (voucher.getDiscountType() == DiscountType.PERCENTAGE.getValue()) {
            discountAmount = originalAmount.multiply(new BigDecimal(voucher.getDiscountValue()));
            discountAmount = discountAmount.divide(new BigDecimal(100), 0, RoundingMode.HALF_UP);

            // Kiểm tra nếu số tiền giảm giá vượt quá giới hạn tối đa cho phép
            if (discountAmount.compareTo(new BigDecimal(voucher.getMaxDiscount())) > 0) {
                // Nếu vượt quá giới hạn, gán lại discountAmount bằng giá trị tối đa
                discountAmount = new BigDecimal(voucher.getMaxDiscount());
            }
        }

        return discountAmount;
    }

    /**
     * kiểm tra voucher áp dụng cho hóa đơn
     *
     * @param originalAmount
     * @param discountAmountRequest
     * @param voucherId
     * @return trả ra voucher áp dụng cho đơn hàng
     */
    private Voucher validateVoucherForOrder(BigDecimal originalAmount, BigDecimal discountAmountRequest, Integer voucherId) {
        Voucher voucher = voucherService.getById(voucherId);

        // kiểm tra voucher đã hết hạn chưa
        if (voucher.getEndDate().before(new Date())) {
            throw new InvalidDataException("Voucher đã hết hạn sử dụng!");
        }

        // kiểm tra voucher đã hết số lần sử chưa
        if (voucher.getUsageLimit() > 0) {
            throw new InvalidDataException("Voucher đã hết số lần sử dụng sử dụng!");
        }

        // kiểm tra điều kiện voucher có áp dụng cho hóa đơn này được không
        if (originalAmount.compareTo(voucher.getMinOrderValue()) < 0) {
            throw new InvalidDataException("Tổng giá trị đơn hàng chưa đủ để áp dụng voucher.");
        }

        // tính giá trị giảm giá cho hóa đơn
        BigDecimal discountAmount = this.calculateDiscountAmount(originalAmount, voucher);

        // kiểm tra giá trị giảm được trả về có chính xác không
        if (discountAmount.compareTo(discountAmountRequest) != 0) {
            throw new InvalidDataException("Giá trị giảm giá không chính xác.");
        }

        return voucher;
    }


    /**
     * Xử lý list sản phẩm trong hóa đơn online khi tạo
     *
     * @param orderItemsRequest
     */
    private List<OrderItem> handleOrderItemsOnline(List<CreateOrderItemOnlineRequest> orderItemsRequest) {
        List<OrderItem> orderItems = new ArrayList<>();

        // Lọc danh sách các orderItem có productVariantId hợp lệ
        List<CreateOrderItemOnlineRequest> orderItemsRequestTemp = orderItemsRequest.stream()
                .filter(itemRequest -> itemRequest.getProductVariantId() != null)
                .toList();

        if (orderItemsRequestTemp.isEmpty()) {
            throw new InvalidDataException("Không có sản phẩm nào trong giỏ.");
        }

        // Tập hợp các productVariantId
        Set<Long> listProductVariantId = orderItemsRequestTemp.stream()
                .map(CreateOrderItemOnlineRequest::getProductVariantId)
                .collect(Collectors.toSet());

        // Kiểm tra sự tồn tại của productVariant
        List<ProductVariant> productVariants = productVariantRepository.findAllById(listProductVariantId);
        if (productVariants.isEmpty()) {
            throw new InvalidDataException("Không có sản phẩm nào trong giỏ tồn tại.");
        }

        // chuyển đổi danh sách productVariant thỏa mãn sang Map
        Map<Long, ProductVariant> productVariantMap = productVariants.stream()
                .collect(Collectors.toMap(ProductVariant::getId, productVariant -> productVariant));

        // xử lý những orderItemRequest thỏa mãn
        for (CreateOrderItemOnlineRequest itemRequest : orderItemsRequestTemp) {
            ProductVariant productVariant = productVariantMap.get(itemRequest.getProductVariantId());

            // bỏ qua những sản phẩm không tồn tại
            if (productVariant == null) {
                continue;
            }

            // lưu lại những order item thỏa mãn
            OrderItem orderItem = new OrderItem();
            orderItem.setProductVariant(productVariant);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSalePrice(itemRequest.getSalePrice());
            orderItem.setDiscountedPrice(itemRequest.getDiscountedPrice());
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
