package com.fastkart.commonlibrary.dto.seller;

import lombok.Data;

@Data
public class SellerProductDto {
    private String productName;
    private String productDescription;
    private Double minimumBidAmount;
    private Integer categoryId;
}
