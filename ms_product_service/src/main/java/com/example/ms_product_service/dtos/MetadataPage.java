package com.example.ms_product_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MetadataPage {
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;
    private Long totalElements;
}
