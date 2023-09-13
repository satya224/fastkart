package com.fastkart.productservice.model.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidderDto {
    private String name;
    private Double amount;
}
