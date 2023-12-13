package com.electronicStore.repository;

import com.electronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepository extends JpaRepository<Category,String> {

}
