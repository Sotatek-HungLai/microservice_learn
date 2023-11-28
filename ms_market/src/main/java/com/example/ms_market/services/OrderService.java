package com.example.ms_market.services;

import com.example.ms_market.dtos.AddOrderDTO;
import com.example.ms_market.dtos.OrderDTO;
import com.example.ms_market.dtos.ProductDTO;
import com.example.ms_market.entities.OrderEntity;
import com.example.ms_market.dtos.BuyProductDTO;
import com.example.ms_market.exceptions.ForbiddenException;
import com.example.ms_market.exceptions.BadRequestException;
import com.example.ms_market.exceptions.NotFoundException;
import com.example.ms_market.repositories.OrderRepository;
import com.example.ms_market.utils.OrderConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final OrderConverter orderConverter;

    public void addOrderToMarket(AddOrderDTO addOrderDTO, String authorizationHeader) {
        Long currentUserId = userService.getUserIdFromContext();

        Optional<OrderEntity> optionalOrderEntity = orderRepository.findByProductId(addOrderDTO.productId());
        if (optionalOrderEntity.isPresent()) {
            OrderEntity foundOrder = optionalOrderEntity.get();
            if (!foundOrder.getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not the owner of this product");
            }
            productService.changeProductAmount(addOrderDTO.productId(), -addOrderDTO.amount(), authorizationHeader);
            foundOrder.setAmount(addOrderDTO.amount() + addOrderDTO.amount());
            foundOrder.setPrice(addOrderDTO.price() + addOrderDTO.price());
            orderRepository.save(foundOrder);
        } else {
            ProductDTO productDTO = productService.getProduct(addOrderDTO.productId(), authorizationHeader);

            if (productDTO == null) {
                throw new NotFoundException("Product not found");
            }

            log.info("productDTO : " + productDTO);

            if (!productDTO.getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not the owner of this product");
            }

            if(productDTO.getAmount() < addOrderDTO.amount()){
                throw new BadRequestException("Not enough product");
            }
            productService.changeProductAmount(addOrderDTO.productId(), -addOrderDTO.amount(), authorizationHeader);
            OrderEntity newOrderEntity = OrderEntity.builder()
                    .productId(productDTO.getId())
                    .amount(addOrderDTO.amount())
                    .price(addOrderDTO.price())
                    .userId(currentUserId)
                    .build();

            orderRepository.save(newOrderEntity);
        }
    }


    public void deleteOrder(Long id,String authorizationHeader) {
        OrderEntity foundOrderEntity = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (foundOrderEntity == null) {
            throw new NotFoundException("Order not found");
        }
        log.info("foundOrderEntity : " + foundOrderEntity);
        if (foundOrderEntity.getUserId().equals(userService.getUserIdFromContext())) {
            Long foundProductId = foundOrderEntity.getProductId();
            Long foundAmount = foundOrderEntity.getAmount();
            productService.changeProductAmount(foundProductId, foundAmount, authorizationHeader);
            orderRepository.deleteById(id);
        } else {
            throw new ForbiddenException("You are not the owner of this product");
        }

    }

    public List<OrderDTO> getOrders() {
        Long id = userService.getUserIdFromContext();
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserId(id);
        return orderEntityList.stream().map(orderConverter::convertToDto).toList();
    }

    public void buyProduct(BuyProductDTO buyProductDTO,String authorizationHeader) {
        OrderEntity orderEntity = orderRepository.findByProductId(buyProductDTO.productId()).orElseThrow(() -> new NotFoundException(String.format("Order for product id %d not found", buyProductDTO.productId())));
        if (orderEntity.getUserId().equals(userService.getUserIdFromContext())) {
            throw new ForbiddenException("You are the owner of this product");
        }

        if (orderEntity.getAmount() < buyProductDTO.amount()) {
            throw new BadRequestException("Not enough product");
        }
        Double allPrice = orderEntity.getPrice() * buyProductDTO.amount();
        userService.updateUserBalance(orderEntity.getUserId(), allPrice,authorizationHeader);


        orderEntity.setAmount(orderEntity.getAmount() - buyProductDTO.amount());
        orderRepository.save(orderEntity);
    }
}
