package com.electronicStore.dtos;

import com.electronicStore.entities.CartItem;
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

public class CartDto {

    private  String cartId;
    private Date createdAt;
    private UserDto user;

    //mapping cartItem list
    private List<CartItemDto> items= new ArrayList<>();
}
