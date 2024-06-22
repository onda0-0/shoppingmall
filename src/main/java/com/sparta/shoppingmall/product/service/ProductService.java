package com.sparta.shoppingmall.product.service;

import com.sparta.shoppingmall.product.dto.ProductRequest;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.dto.ProductUpdateRequest;
import com.sparta.shoppingmall.product.entity.Product;
import com.sparta.shoppingmall.product.entity.ProductStatus;
import com.sparta.shoppingmall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품등록
     */
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()

//                .user(user)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .status(ProductStatus.ONSALE)
                .build();

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())

                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .status(product.getStatus())
                .build();
    }


    /**
     * 상품조회(전체) get api/products
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(Pageable pageable/*, Long userId*/) {
        //user 체크
        //User user = getUser(userId);

        List<Product> products = productRepository.findAllByStatus(ProductStatus.ONSALE);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getId())
                    //.user(product.getUser())
                    .name(product.getName())
                    .price(product.getPrice())
                    .status(product.getStatus())
                    .build();
            productResponses.add(productResponse);
        }

        return productResponses;
    }


    /**
     * 상품조회(단일)get api/products/{productId}
     */
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("Product with id " + productId + " not found")
        );
        return ProductResponse.builder()
                .id(product.getId())
                //.user(product.getUser())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

    /**
     * 상품수정 api/products/{productId}
     */
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("Product with id " + productId + " not found")
        );

        product.update(
                productUpdateRequest.getName(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getStatus()
        );

//        Product updatedProduct = product.builder()
//                .name(productUpdateRequest.getName())
//                .price(productUpdateRequest.getPrice())
//                .status(productUpdateRequest.getStatus())
//                .build();
//        productRepository.save(updatedProduct);

        return ProductResponse.builder()
                .id(product.getId())
                //.user(product.getUser())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }


    /**
     * 상품삭제 delete  api/products
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NullPointerException("Product with id " + productId + " not found"));
        productRepository.delete(product);
    }

}