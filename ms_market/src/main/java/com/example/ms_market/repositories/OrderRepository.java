package com.example.ms_market.repositories;

import com.example.ms_market.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long>, JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.productId = ?1")
    Optional<OrderEntity> findByProductId(Long productId);

    @Query("SELECT o FROM OrderEntity o WHERE o.userId = ?1")
    List<OrderEntity> findAllByUserId(Long id);
}
