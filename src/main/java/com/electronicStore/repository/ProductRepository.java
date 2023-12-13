package com.electronicStore.repository;

import com.electronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findByTitleContaining(Pageable pageable ,String subTitle);

    Page<Product> findByLiveTrue(Pageable pageable);

}
