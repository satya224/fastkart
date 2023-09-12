package com.fastkart.productservice.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ProductListDto {
    private Integer id;
    private String name;
    private String category;
    private Instant listedDate;
    private Double maximumBidAmount;
}
