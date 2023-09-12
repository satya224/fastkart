package com.fastkart.productservice.model.dto.buyer;

import com.fastkart.productservice.model.dto.seller.BidderDto;
import lombok.Data;

import java.util.List;

@Data
public class BuyerProductDetailsDto {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String sellerName;
    private Double minimumBidAmountBySeller;
    private List<BidderDto> bidders;
}
