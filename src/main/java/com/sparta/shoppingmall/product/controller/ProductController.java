package com.sparta.shoppingmall.product.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.product.dto.ProductRequest;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.dto.ProductUpdateRequest;
import com.sparta.shoppingmall.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

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
            ProductResponse response = productService.createProduct(productRequest/*, userDetails.getUser().getId()*/);
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
            List<ProductResponse> response = productService.getProducts(pageable);
            return getResponseEntity(response, "상품 목록 조회 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }


    /**
     * 상품조회(단일)get api/products/{productId}
     */
    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<?>> getProduct(
            @PathVariable Long productId
    ){
        try{
            ProductResponse response = productService.getProduct(productId);
            return getResponseEntity(response, "상품 조회 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }


    /**
     * 상품수정 api/products/{productId}
     */
    @PutMapping("/{productId}")
    public ResponseEntity<CommonResponse<?>> updateProduct(
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest, @PathVariable Long productId
    ){
        try{
            ProductResponse response = productService.updateProduct(productId, productUpdateRequest);
            return getResponseEntity(response, "상품 수정 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 상품삭제 delete  api/products
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponse<?>> deleteProduct(
            @PathVariable Long productId
    ){
        try{
            productService.deleteProduct(productId);
            return getResponseEntity(null, "상품 삭제 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }
    }

