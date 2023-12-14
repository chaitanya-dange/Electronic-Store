package com.electronicStore.dtos;

import com.electronicStore.entities.Cart;
import com.electronicStore.entities.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemDto {
    private  int cartItemId;

    private ProductDto product;
    private  int quantity;
    private  int totalPrice;
    //mapping to cart

//    private CartDto cart;
}
