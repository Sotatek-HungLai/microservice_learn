package com.example.ms_backend.repositories;

import com.example.ms_backend.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.balance = u.balance + :balance WHERE u.id = :id")
    Integer updateUserBalance(Long id, Double balance);
}
