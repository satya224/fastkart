package com.fastkart.productservice.service;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.entity.Bid;

import java.util.List;

public interface BuyerService {
    List<ProductListDto> getProducts();

    BuyerProductDetailsDto getProduct(Integer productId);

    Bid bid(Integer buyerId, Integer productId, Double bidAmount);
}
