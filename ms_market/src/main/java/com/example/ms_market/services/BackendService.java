package com.example.ms_market.services;

import com.example.ms_market.dtos.UpdateInTransactionDTO;
import com.example.ms_market.dtos.UpdateProductAmountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final RestTemplate restTemplate;
    @Value("${backend-service.url}")
    private String BACKEND_SERVICE_URL;
    @Value("${backend-service.key}")
    private String BACKEND_KEY;

    public void updateInTransaction(UpdateInTransactionDTO changeInTractionDTO) {
        String CHANGE_PRODUCT_AMOUNT_URL = BACKEND_SERVICE_URL + "/transaction";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BACKEND_KEY);
        HttpEntity<UpdateInTransactionDTO> entity = new HttpEntity<>(changeInTractionDTO, headers);
        restTemplate.exchange(CHANGE_PRODUCT_AMOUNT_URL, HttpMethod.POST, entity, Void.class);
    }

    public void changeProductAmount(UpdateProductAmountDTO updateProductAmountDTO) {
        String CHANGE_USER_BALANCE_URL = BACKEND_SERVICE_URL + "/product-amount";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BACKEND_KEY);
        HttpEntity<UpdateProductAmountDTO> entity = new HttpEntity<>(updateProductAmountDTO, headers);
        restTemplate.exchange(CHANGE_USER_BALANCE_URL, HttpMethod.POST, entity, Void.class);
    }
}
