package com.electronicStore.controller;

import com.electronicStore.dtos.AddItemsToCartRequest;
import com.electronicStore.dtos.ApiResponseMessage;
import com.electronicStore.dtos.CartDto;
import com.electronicStore.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "CartController" ,description = "API's for Cart Module")
public class CartController {

    @Autowired
    private CartService cartService;

    // add item from cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(
            @PathVariable String userId,
            @RequestBody AddItemsToCartRequest request
            ){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return  ResponseEntity.ok(cartDto);

    }

    // remove item from cart
    @DeleteMapping ("/{userId}/items/{cartItem}")
    public ResponseEntity<ApiResponseMessage> deleteItemToCart(
            @PathVariable String userId,
            @PathVariable int cartItem
    ){
       cartService.removeItemFromCart(userId,cartItem);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Item deleted successfully").success(true).build();
        return  ResponseEntity.ok(responseMessage);

    }

    // clear cart
    @DeleteMapping ("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Cart cleared successfully").success(true).build();
        return  ResponseEntity.ok(responseMessage);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartOfUser(@PathVariable String userId){
        CartDto cartDto = cartService.getCartByUserId(userId);
        return  ResponseEntity.ok(cartDto);

    }


}
