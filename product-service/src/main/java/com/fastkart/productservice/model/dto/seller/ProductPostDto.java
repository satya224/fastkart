package com.fastkart.productservice.model.dto.seller;

import lombok.Data;

@Data
public class ProductPostDto {
    private String name;
    private String description;
    private Double minimumBidAmount;
    private Integer categoryId;
}
