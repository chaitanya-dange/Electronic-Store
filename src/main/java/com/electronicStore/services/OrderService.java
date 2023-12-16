package com.electronicStore.services;

import com.electronicStore.dtos.CreateOrderRequest;
import com.electronicStore.dtos.OrderDto;
import com.electronicStore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {
    //create
    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    // remove
    void removeOrder(String orderId);

    //get order of user
    List<OrderDto> getOrdersForUser(String userId);

    // get orders
    PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    // other methods.
}
