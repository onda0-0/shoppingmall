package com.sparta.shoppingmall.product.repository;

import com.sparta.shoppingmall.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
