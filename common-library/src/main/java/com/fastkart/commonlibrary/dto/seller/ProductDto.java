package com.fastkart.commonlibrary.dto.seller;

import lombok.Data;

import java.time.Instant;

@Data
public class ProductDto {
    private String name;
    private String category;
    private Instant ListedDate;
    private Double maximumBidAmount;
}
