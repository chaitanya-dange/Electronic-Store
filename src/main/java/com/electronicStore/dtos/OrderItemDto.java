package com.electronicStore.dtos;

import com.electronicStore.entities.Order;
import com.electronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private  int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
    //private Order order; // no need for this
}
