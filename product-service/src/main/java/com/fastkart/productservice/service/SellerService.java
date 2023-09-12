package com.fastkart.productservice.service;

import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.entity.Product;

import java.util.List;

public interface SellerService {
    Product addProduct(ProductPostDto product, Integer sellerId);

    SellerProductDetailsDto getProduct(Integer productId);

    List<ProductListDto> getProducts(Integer sellerId);
}
