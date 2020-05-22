package com.mycompany.online_shop_demo_backend.controllers;

import com.mycompany.online_shop_demo_backend.domain.Order;
import com.mycompany.online_shop_demo_backend.dto.request.OrderRequest;
import com.mycompany.online_shop_demo_backend.dto.response.OrderResponse;
import com.mycompany.online_shop_demo_backend.service.db.OrderDbService;
import com.mycompany.online_shop_demo_backend.service.db.UserDbService;
import com.mycompany.online_shop_demo_backend.service.security.SecurityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderDbService orderDbService;

    @ApiOperation("Creates an order")
    @PostMapping(path = "/api/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        Order order = OrderRequest.toDomainObject(request);
        return OrderResponse.toDto(orderDbService.save(order));
    }

    @ApiOperation("Gets orders for an authenticated user")
    @GetMapping(path = "/api/users/{id}/orders",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderResponse> getUserOrders(@PathVariable("id") long id) {
        return orderDbService.getOrdersByUserId(id)
                .stream()
                .map(OrderResponse::toDto)
                .collect(toList());
    }
}