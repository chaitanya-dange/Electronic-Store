package com.electronicStore.repository;

import com.electronicStore.entities.Cart;
import com.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
   Optional<Cart>  findByUser(User user);
}
