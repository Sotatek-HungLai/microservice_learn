package com.example.ms_market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetadataPage {
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer totalPages;
    private Long totalElements;

}
