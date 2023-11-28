package com.example.ms_user_service.repositories;

import com.example.ms_user_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.balance = u.balance + :balance WHERE u.id = :id")
    void udpateBalance(@Param("id") Long id, @Param("balance") Double balance);
}
