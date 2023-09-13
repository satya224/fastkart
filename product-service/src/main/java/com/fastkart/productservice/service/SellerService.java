package com.fastkart.productservice.service;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SellerService {
    Product addProduct(ProductPostDto product, Integer sellerId);

    SellerProductDetailsDto getProduct(Integer productId, Integer sellerId);

    List<ProductListDto> getProducts(Integer sellerId);

    void addProducts(Integer sellerId, MultipartFile file) throws IOException;
}
