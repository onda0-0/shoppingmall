package com.sparta.shoppingmall.domain.order.service;

import com.sparta.shoppingmall.common.exception.customexception.OrderNotFoundException;
import com.sparta.shoppingmall.domain.order.dto.OrderGroupRequest;
import com.sparta.shoppingmall.domain.order.dto.OrderGroupResponse;
import com.sparta.shoppingmall.domain.order.dto.OrdersResponse;
import com.sparta.shoppingmall.domain.order.entity.OrderGroup;
import com.sparta.shoppingmall.domain.order.entity.OrderStatus;
import com.sparta.shoppingmall.domain.order.entity.Orders;
import com.sparta.shoppingmall.domain.order.repository.OrderGroupRepository;
import com.sparta.shoppingmall.domain.product.entity.Product;
import com.sparta.shoppingmall.domain.product.entity.ProductStatus;
import com.sparta.shoppingmall.domain.product.service.ProductService;
import com.sparta.shoppingmall.domain.user.entity.User;
import com.sparta.shoppingmall.domain.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderGroupService {

    private final ProductService productService;
    private final OrderGroupRepository orderGroupRepository;

    /**
     * 주문하기 (OrderGroup생성 -> Order생성)
     */
    @Transactional
    public OrderGroupResponse createOrder(OrderGroupRequest request, User user) {// 여기문제
        //주문한 상품들 불러오기
        List<Long> productIdList = request.getProductIdList();
        List<Product> productList = new ArrayList<>();

        for(Long productId : productIdList){
            Product product = productService.findByProductId(productId);
            //상품이 판매중 상태일 때만 주문
            if(ProductStatus.ONSALE.equals(product.getStatus()) || ProductStatus.RECOMMEND.equals(product.getStatus())){
                productList.add(product);
            }
        }

        //주문시 OrderGroup 생성
        OrderGroup orderGroup = OrderGroup.createOrderGroup(request, OrderStatus.ORDERED, user, productList);

        List<OrdersResponse> ordersResponses = new ArrayList<>();
        //Orders 생성
        for(Product product : productList){
            Orders orders = Orders.createOrders(product.getName(), product.getPrice());
            orderGroup.addOrder(orders);
            ordersResponses.add(new OrdersResponse(orders));

            // 상품 상태 판매중으로 변경
            product.updateStatus(ProductStatus.INPROGRESS);
        }

        orderGroupRepository.save(orderGroup);

        return new OrderGroupResponse(orderGroup, ordersResponses);
    }

    /**
     * 사용자의 모든 주문내역 조회하기
     */
    @Transactional(readOnly = true)
    public List<OrderGroupResponse> getOrderGroups(User user) {
        List<OrderGroup> orderGroups = orderGroupRepository.findByUserId(user.getId()).orElseThrow(
                () -> new OrderNotFoundException("해당 사용자의 주문 내역이 존재하지 않습니다.")
        );

        List<OrderGroupResponse> list = new ArrayList<>();
        List<OrdersResponse> ordersResponseList = new ArrayList<>();
        for(OrderGroup orderGroup : orderGroups) {
            List<Orders> orders = orderGroup.getOrders();
            for(Orders order : orders) {
                ordersResponseList.add(new OrdersResponse(order));
                list.add(new OrderGroupResponse(orderGroup, ordersResponseList));
            }
        }

        return list;
    }

    /**
     * 사용자의 주문내역 상세 조회
     */
    @Transactional(readOnly = true)
    public OrderGroupResponse getOrderGroup(Long orderGroupId, User user){
        OrderGroup orderGroup = findByOrderGroupId(orderGroupId);

        if (!UserType.ADMIN.equals(user.getUserType())) {
            orderGroup.verifyOrderGroupUser(user.getId());
        }
        List<Orders> orders = orderGroup.getOrders();
        List<OrdersResponse> OrdersResponses = new ArrayList<>();
        for(Orders order : orders) {
            new OrdersResponse(order);
        }

        return new OrderGroupResponse(orderGroup, OrdersResponses);
    }

    /**
     * 주문 취소하기
     */
    @Transactional
    public Long cancelOrder(Long orderGroupId, User user) {
        OrderGroup orderGroup = findByOrderGroupId(orderGroupId);

        if (!UserType.ADMIN.equals(user.getUserType())) {
            orderGroup.verifyOrderGroupUser(user.getId());
        }
        orderGroup.updateStatus(OrderStatus.CANCELED);

        //상품들 상태 ONSALE로 변경 및 연관관계 제거
        List<Product> products = orderGroup.getProducts();
        for (Product product : products) {
            product.updateStatus(ProductStatus.ONSALE);
            orderGroup.removeProduct(product);
        }

        return orderGroup.getId();
    }

    /**
     * 상세 주문내역 조회
     */
    public OrderGroup findByOrderGroupId (Long orderGroupId) {
        return orderGroupRepository.findById(orderGroupId).orElseThrow(
                () -> new OrderNotFoundException("해당 주문내역은 존재하지 않습니다.")
        );
    }
}
