package com.sparta.shoppingmall.product.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.product.dto.ProductRequest;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.sparta.shoppingmall.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    /**
     * 상품등록
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> createProduct(
            @Valid @RequestBody ProductRequest productRequest,
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "상품 추가 실패");
        }
        try{
            ProductResponse response = productService.addProduct(productRequest/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "상품 등록 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 상품조회(전체) get api/products
     */
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getProducts(
            @PageableDefault(
                    size = 5,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try{
            ProductResponse response = productService.getProducts(pageable/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "상품 목록 조회 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
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
