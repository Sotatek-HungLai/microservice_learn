package com.example.ms_market.services;

import com.example.ms_market.dtos.*;
import com.example.ms_market.entities.OrderEntity;
import com.example.ms_market.exceptions.ForbiddenException;
import com.example.ms_market.exceptions.BadRequestException;
import com.example.ms_market.exceptions.NotFoundException;
import com.example.ms_market.repositories.OrderRepository;
import com.example.ms_market.utils.OrderConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.ms_market.dtos.MetadataPage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final BackendService backendService;

    private final OrderConverter orderConverter;

    public void addOrderToMarket(AddOrderDTO addOrderDTO, String authorizationHeader) {
        Long currentUserId = userService.getUserIdFromContext();

        Optional<OrderEntity> optionalOrderEntity = orderRepository.findByProductIdAndUserId(addOrderDTO.productId(),currentUserId);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity foundOrder = optionalOrderEntity.get();
            UpdateProductAmountDTO updateProductAmountDTO = new UpdateProductAmountDTO(foundOrder.getProductId(), foundOrder.getUserId(), foundOrder.getAmount());
            backendService.changeProductAmount(updateProductAmountDTO);
            foundOrder.setAmount(addOrderDTO.amount() + foundOrder.getAmount());
            foundOrder.setPrice(addOrderDTO.price());
            orderRepository.save(foundOrder);
        } else {
            ProductDTO productDTO = productService.getProduct(addOrderDTO.productId(), authorizationHeader);

            log.info("productDTO : " + productDTO);

            Optional<ProductUserDTO> productDTOOptional = productDTO.getProductUsers().stream().filter(productUserDTO -> productUserDTO.getUserId().equals(currentUserId)).findFirst();
            if (productDTOOptional.isEmpty()){
                throw new ForbiddenException("You are not the owner of this product");
            }
            ProductUserDTO productUserDTO = productDTOOptional.get();
            if(productUserDTO.getAmount() < addOrderDTO.amount()){
                throw new BadRequestException("Not enough product");
            }
            UpdateProductAmountDTO updateProductAmountDTO = new UpdateProductAmountDTO(addOrderDTO.productId(),currentUserId, -addOrderDTO.amount());
            backendService.changeProductAmount(updateProductAmountDTO);

            OrderEntity newOrderEntity = OrderEntity.builder()
                    .productId(productDTO.getId())
                    .amount(addOrderDTO.amount())
                    .price(addOrderDTO.price())
                    .userId(currentUserId)
                    .build();

            orderRepository.save(newOrderEntity);
        }
    }


    public void deleteOrder(Long id) {
        OrderEntity foundOrderEntity = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (foundOrderEntity == null) {
            throw new NotFoundException("Order not found");
        }
        log.info("foundOrderEntity : " + foundOrderEntity);
        if (foundOrderEntity.getUserId().equals(userService.getUserIdFromContext())) {
            UpdateProductAmountDTO updateProductAmountDTO = new UpdateProductAmountDTO(foundOrderEntity.getProductId(), foundOrderEntity.getUserId(), foundOrderEntity.getAmount());
            backendService.changeProductAmount(updateProductAmountDTO);
            orderRepository.deleteById(id);
        } else {
            throw new ForbiddenException("You are not the owner of this product");
        }

    }

    public GetOrdersResponseDTO getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Long id = userService.getUserIdFromContext();
        Page<OrderEntity> orderEntityPage = orderRepository.findAllByUserId(id,pageable);
        List<OrderDTO> orderEntityList = orderEntityPage.getContent().stream().map(orderConverter::convertToDto).toList();

        return new GetOrdersResponseDTO(orderEntityList,
                new MetadataPage(
                        orderEntityPage.hasNext(),
                        orderEntityPage.hasPrevious(),
                        orderEntityPage.getTotalPages(),
                        orderEntityPage.getTotalElements()
                )
        );
    }

    public void buyProduct(BuyProductDTO buyProductDTO,String authorizationHeader) {
        OrderEntity orderEntity = orderRepository.findByProductIdAndUserId(buyProductDTO.productId(),buyProductDTO.sellerId()).orElseThrow(() -> new NotFoundException(String.format("Order for product id %d not found", buyProductDTO.productId())));
        if (orderEntity.getUserId().equals(userService.getUserIdFromContext())) {
            throw new ForbiddenException("You are the owner of this product");
        }

        if (orderEntity.getAmount() < buyProductDTO.amount()) {
            throw new BadRequestException("Not enough product");
        }

        UpdateInTransactionDTO updateInTractionDTO = new UpdateInTransactionDTO(buyProductDTO.productId(), orderEntity.getUserId(), userService.getUserIdFromContext(), buyProductDTO.amount(),orderEntity.getPrice());

        backendService.updateInTransaction(updateInTractionDTO  );

        orderEntity.setAmount(orderEntity.getAmount() - buyProductDTO.amount());
        orderRepository.save(orderEntity);
    }
}
