package com.electronicStore.services.impl;

import com.electronicStore.dtos.AddItemsToCartRequest;
import com.electronicStore.dtos.CartDto;
import com.electronicStore.entities.Cart;
import com.electronicStore.entities.CartItem;
import com.electronicStore.entities.Product;
import com.electronicStore.entities.User;
import com.electronicStore.exceptions.ResourceNotFoundException;
import com.electronicStore.repository.CartRepository;
import com.electronicStore.repository.ProductRepository;
import com.electronicStore.repository.UserRepository;
import com.electronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl  implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CartDto addItemToCart(String userId, AddItemsToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();
        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("the product is not available "));
        // fetch the user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User doesn't found"));
        Cart cart=null;

        try{
          cart=  cartRepository.findByUser(user).get();

        }catch (NoSuchElementException ex){
            cart= new Cart();
            cart.setCreatedAt(new Date());
            cart.setCartId(UUID.randomUUID().toString());
            cart.setUser(user);
        }
        // perform cart operation
        // if cartItem is already present then updated its fields

        AtomicBoolean updated= new AtomicBoolean(false);
        List<CartItem> updatedItems = cart.getItems();

        updatedItems.stream().map(item -> {
            if(item.getProduct().getProductId().equals(productId)){
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getPrice());
                updated.set(true);

            }

                return  item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        //creating cartItems
        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .cart(cart)
                    .totalPrice(quantity * product.getPrice())
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }




        Cart savedCart = cartRepository.save(cart);

        return modelMapper.map(savedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
