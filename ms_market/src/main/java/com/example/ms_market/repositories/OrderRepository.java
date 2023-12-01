package com.example.ms_market.repositories;

import com.example.ms_market.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long>, JpaRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.productId = :productId AND o.userId = :userId")
    Optional<OrderEntity> findByProductIdAndUserId(Long productId,Long userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.userId = ?1")
    Page<OrderEntity> findAllByUserId(Long id, Pageable pageable);
}
