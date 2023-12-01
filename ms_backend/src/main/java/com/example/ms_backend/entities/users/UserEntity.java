package com.example.ms_backend.entities.users;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String email;

    private String address;

    private String phone;
    @Getter
    private String password;
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "float8 default 0 check(balance >= 0)")
    private Double balance;
}