package com.example.ms_backend.repositories;

import com.example.ms_backend.entities.products.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {

    @Query("SELECT pu FROM ProductUser pu WHERE pu.product.id = :productId and pu.userId = :userId")
    Optional<ProductUser> findProductUserByProductIdAndUserId(Long productId, Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE ProductUser pu SET pu.amount = pu.amount + :amount where pu.product.id = :productId and pu.userId = :userId")
    Integer updateAmount(Long productId, Long userId, Long amount);

}
