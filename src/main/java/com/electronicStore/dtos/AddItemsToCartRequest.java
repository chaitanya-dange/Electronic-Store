package com.electronicStore.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddItemsToCartRequest {
    private  String productId;
    private int  quantity;
}
