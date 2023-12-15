package com.electronicStore.services;

import com.electronicStore.dtos.AddItemsToCartRequest;
import com.electronicStore.dtos.CartDto;

public interface CartService {

    // add item to cart
    //case1-> if cart is not available for particualar then create it
    //case2 -> cart is available then add items
    CartDto addItemToCart(String userId, AddItemsToCartRequest request);

    // remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //clear cart
    void clearCart(String userId);
}
