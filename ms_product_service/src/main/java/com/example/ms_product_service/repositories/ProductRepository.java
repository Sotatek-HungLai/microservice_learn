package com.example.ms_product_service.repositories;

import com.example.ms_product_service.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long>, JpaRepository<ProductEntity, Long>, PagingAndSortingRepository<ProductEntity,Long> {
    Page<ProductEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String keyword,Pageable pageable);
}
