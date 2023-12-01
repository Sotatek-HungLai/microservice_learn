package com.example.ms_product_service.repositories;

import com.example.ms_product_service.entities.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE ProductUser p SET p.amount = p.amount + :amount WHERE p.product.id = :productId and p.userId = :userId")
    Integer updateAmount(Long userId, Long productId, Long amount);

    @Query("SELECT p from ProductUser p where p.userId = :userId and p.product.id = :productId")
    Optional<ProductUser> findByUserIdAndProductId(Long userId, Long productId);
}
