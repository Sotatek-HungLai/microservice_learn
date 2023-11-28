package com.example.ms_market.entities;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "int8 default 0 check(amount >= 0)")
    private Long amount;

    private Double price;

    private Long productId;

    private Long userId;

}
