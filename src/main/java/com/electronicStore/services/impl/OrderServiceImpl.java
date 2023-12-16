package com.electronicStore.services.impl;

import com.electronicStore.dtos.CreateOrderRequest;
import com.electronicStore.dtos.OrderDto;
import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.entities.*;
import com.electronicStore.exceptions.BadApiRequest;
import com.electronicStore.exceptions.ResourceNotFoundException;
import com.electronicStore.helpers.Helper;
import com.electronicStore.repository.CartRepository;
import com.electronicStore.repository.OrderRepository;
import com.electronicStore.repository.UserRepository;
import com.electronicStore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        String userId=createOrderRequest.getUserId();
        String cartId=createOrderRequest.getCartId();
        //fetch User
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItem> items = cart.getItems();
        if(items.size()<1){
            throw new BadApiRequest("atleast one item should be there.");
        }
        Order order = Order.builder()
                .billingAddress(createOrderRequest.getBillingAddress())
                .billingName(createOrderRequest.getBillingName())
                .billingPhoneNumber(createOrderRequest.getBillingPhoneNumber())
                .orderStatus(createOrderRequest.getOrderStatus())
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //orderItem not set
        AtomicInteger orderTotalAmount = new AtomicInteger();

        List<OrderItem> collectedOrders = items.stream().map(cartItem -> {
            // convert cartItem to orderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderTotalAmount.set(orderTotalAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(collectedOrders);
        order.setOrderAmount(orderTotalAmount.get());

        cart.getItems().clear();

        cartRepository.save(cart);
        Order saveOrder = orderRepository.save(order);

        return modelMapper.map(saveOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersForUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        List<Order> ordersList = orderRepository.findByUser(user);

        List<OrderDto> collect = ordersList.stream().map(item -> modelMapper.map(item, OrderDto.class)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> allOrders = orderRepository.findAll(pageable);

        return Helper.getPageableResponse(allOrders,OrderDto.class);
    }
}
