package com.example.ms_market.controllers;


import com.example.ms_market.dtos.AddOrderDTO;
import com.example.ms_market.dtos.OrderDTO;
import com.example.ms_market.dtos.BuyProductDTO;
import com.example.ms_market.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_MEMBER')")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrderToMarket(@RequestBody AddOrderDTO addOrderDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.addOrderToMarket(addOrderDTO, authorizationHeader);
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Long id,HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.deleteOrder(id,authorizationHeader);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDTO>> getOrderList() {
        return ResponseEntity.ok().body(orderService.getOrders());
    }

    @PostMapping("/buy")
    @ResponseStatus(HttpStatus.OK)
    public void buyProduct(@RequestBody BuyProductDTO buyProductDTO,HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        orderService.buyProduct(buyProductDTO,authorizationHeader);
    }
}
