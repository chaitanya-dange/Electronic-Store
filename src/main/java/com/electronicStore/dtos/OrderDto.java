package com.electronicStore.dtos;

import com.electronicStore.entities.OrderItem;
import com.electronicStore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private  String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhoneNumber;
    private String billingName;
    private Date orderDate=new Date() ;
    private Date deliveredDate;
    private UserDto user;
    private List<OrderItemDto> userItems = new ArrayList<>();

}
