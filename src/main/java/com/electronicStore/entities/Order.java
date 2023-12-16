package com.electronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private  String orderId;
    //can keep enum as well
    private String orderStatus;

    //UNPAID ,PAID
    //can keep enum OR boolean as well
    private String paymentStatus;
    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingPhoneNumber;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem>userItems = new ArrayList<>();


}
