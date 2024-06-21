package com.sparta.shoppingmall.product.service;

import com.sparta.shoppingmall.product.dto.ProductRequest;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.entity.Product;
import com.sparta.shoppingmall.product.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class ProductService {
    private ProductRepository productRepository;

    /**
     * 상품등록
     */
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product=Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                        .build();

        productRepository.save(product);
        return ProductResponse.builder()
                .build();
    }


    /**
     * 상품조회(전체) get api/products
     */

    @Transactional(readOnly = true)
    public ProductResponse getProducts(Pageable pageable/*, Long userId*/) {
        //user 체크
        //User user = getUser(userId);


        return ProductResponse.builder()
                //cartId(cart.getId())
                //.cartProducts(cartProducts)
                .build();
    }



    /**
     * 상품조회(단일)get api/products/{productId}
     */


    /**
     * 상품수정 api/products/{productId}
     */


    /**
     * 상품삭제 delete  api/products
     */

}

