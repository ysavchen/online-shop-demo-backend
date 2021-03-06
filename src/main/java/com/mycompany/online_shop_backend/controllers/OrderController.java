package com.mycompany.online_shop_backend.controllers;

import com.mycompany.online_shop_backend.dto.request.OrderRequest;
import com.mycompany.online_shop_backend.dto.response.OrderResponse;
import com.mycompany.online_shop_backend.service.OrderService;
import com.mycompany.online_shop_backend.service.security.SecurityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    @ApiOperation("Creates an order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful execution"),
            @ApiResponse(code = 401, message = "Invalid authentication")
    })
    @PostMapping(
            path = "/v1/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.save(request);
    }

    @ApiOperation("Gets orders for an authenticated user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful execution"),
            @ApiResponse(code = 401, message = "Invalid authentication")
    })
    @GetMapping(
            path = "/v1/users/{id}/orders",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<OrderResponse> getUserOrders(HttpServletRequest request) {
        String email = securityService.getUsernameFromRequest(request);
        return orderService.getOrdersByEmail(email);
    }
}
