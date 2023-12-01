package com.example.ms_backend.services;

import com.example.ms_backend.dtos.UpdateInTransactionDTO;
import com.example.ms_backend.dtos.UpdateProductAmountDTO;
import com.example.ms_backend.dtos.UpdateUserBalanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final UserService userService;
    private final ProductService productService;
    public void updateInTransaction(UpdateInTransactionDTO updateInTransactionDTO){
        Double total = updateInTransactionDTO.amount() * updateInTransactionDTO.price();
        UpdateUserBalanceDTO updateBuyerBalanceDTO = new UpdateUserBalanceDTO(updateInTransactionDTO.sellerId(), -total);
        userService.updateUserBalance(updateBuyerBalanceDTO);
        UpdateUserBalanceDTO updateSellerBalanceDTO = new UpdateUserBalanceDTO(updateInTransactionDTO.sellerId(), total);
        userService.updateUserBalance(updateSellerBalanceDTO);
        UpdateProductAmountDTO updateProductAmountOfBuyer = new UpdateProductAmountDTO(updateInTransactionDTO.productId(), updateInTransactionDTO.buyerId(), updateInTransactionDTO.amount());
        productService.updateProductAmount(updateProductAmountOfBuyer);
    }
}
