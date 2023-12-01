package com.example.ms_market.controllers;


import com.example.ms_market.dtos.AddOrderDTO;
import com.example.ms_market.dtos.GetOrdersResponseDTO;
import com.example.ms_market.dtos.OrderDTO;
import com.example.ms_market.dtos.BuyProductDTO;
import com.example.ms_market.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_MEMBER')")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "addOrderToMarket")
    public void addOrderToMarket(@RequestBody AddOrderDTO addOrderDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.addOrderToMarket(addOrderDTO, authorizationHeader);
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "deleteOrder")
    public void deleteOrder(@PathVariable("id") Long id,HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.deleteOrder(id,authorizationHeader);
    }

    @GetMapping("/order")
    @Operation(operationId = "getOrderList")
    public ResponseEntity<GetOrdersResponseDTO> getOrderList(@PathParam("page") Integer page, @PathParam("size") Integer size) {
        return ResponseEntity.ok().body(orderService.getOrders(page,size));
    }

    @PostMapping("/buy")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "buyProduct")
    public void buyProduct(@RequestBody BuyProductDTO buyProductDTO,HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.buyProduct(buyProductDTO,authorizationHeader);
    }
}
