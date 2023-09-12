package com.fastkart.productservice.controller;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fastkart/buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @GetMapping("products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListDto> getProducts() {
        return buyerService.getProducts();
    }

    @GetMapping("products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public BuyerProductDetailsDto getProduct(@PathVariable Integer productId) {
        return buyerService.getProduct(productId);
    }

    @PostMapping("products/{productId}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public Bid bid(@RequestHeader("userId") Integer buyerId, @PathVariable Integer productId, @RequestParam Double bidAmount) {
        return buyerService.bid(buyerId, productId, bidAmount);
    }


}
