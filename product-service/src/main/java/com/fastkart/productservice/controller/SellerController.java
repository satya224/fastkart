package com.fastkart.productservice.controller;

import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fastkart/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductPostDto product, @RequestHeader("userId") Integer sellerId) {
        return sellerService.addProduct(product, sellerId);
    }

    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public SellerProductDetailsDto getProduct(@PathVariable Integer productId) {
        return sellerService.getProduct(productId);
    }

    @GetMapping("product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListDto> getProducts(@RequestHeader("userId")Integer sellerId) {
        return sellerService.getProducts(sellerId);
    }
}
