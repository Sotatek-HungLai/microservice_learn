package com.example.ms_product_service.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "products")
@ToString
public class ProductEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    private String imagePath;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductUser> productUsers;
}
