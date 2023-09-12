package com.fastkart.productservice.model.dto.seller;

import lombok.Data;

import java.util.List;

@Data
public class SellerProductDetailsDto {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private Double minimumBidAmountBySeller;
    private Double minimumBidAmountByBuyer;
    private Double maximumBidAmountByBuyer;
    private List<BidderDto> bidders;
}
