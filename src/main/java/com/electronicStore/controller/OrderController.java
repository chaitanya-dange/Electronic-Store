package com.electronicStore.controller;

import com.electronicStore.dtos.ApiResponseMessage;
import com.electronicStore.dtos.CreateOrderRequest;
import com.electronicStore.dtos.OrderDto;
import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDto> createOrderForUser(@RequestBody CreateOrderRequest createOrderRequest){
        OrderDto order = orderService.createOrder(createOrderRequest);
        return  ResponseEntity.ok(order);

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrderOfUser(@PathVariable("orderId") String orderId){
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Order deleted successfully").status(HttpStatus.OK).success(true).build();
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/users/{userId}")
    public  ResponseEntity<List<OrderDto>> getAllOrderOfUser(@PathVariable(name = "userId") String userId){
        List<OrderDto> ordersForUserList = orderService.getOrdersForUser(userId);
        return ResponseEntity.ok(ordersForUserList);
    }

    @GetMapping()
    public ResponseEntity<PageableResponse<OrderDto>> getAllUsersOrders(
            @RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam( value = "sortBy",defaultValue ="orderStatus",required = false) String sortBy,
            @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir
    ){
        PageableResponse<OrderDto> allOrders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allOrders);
    }



}
