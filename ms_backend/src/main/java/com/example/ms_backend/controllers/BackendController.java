package com.example.ms_backend.controllers;

import com.example.ms_backend.dtos.UpdateInTransactionDTO;
import com.example.ms_backend.dtos.UpdateProductAmountDTO;
import com.example.ms_backend.dtos.UpdateUserBalanceDTO;
import com.example.ms_backend.services.BackendService;
import com.example.ms_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ms_backend.services.ProductService;
@RestController
@RequestMapping("/backend")
@RequiredArgsConstructor
public class BackendController {

    private final BackendService backendService;
    private final ProductService productService;
    @PostMapping("/transaction")
    public void udpateInTransaction(@RequestBody UpdateInTransactionDTO updateInTransactionDTO){
        backendService.updateInTransaction(updateInTransactionDTO);
    }
    @PostMapping("/product-amount")
    public void updateProductAmount(@RequestBody UpdateProductAmountDTO updateProductAmountDTO){
        productService.updateProductAmount(updateProductAmountDTO);
    }
}
