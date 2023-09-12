package com.fastkart.productservice.controller;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.service.BuyerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fastkart.productservice.utils.Validate.validateBuyer;

@RestController
@RequestMapping("fastkart/buyer")
@RequiredArgsConstructor
@Slf4j
public class BuyerController {

    private final BuyerService buyerService;

    @GetMapping("product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListDto> getProducts(@RequestHeader("role") String role) {
        log.info("Inside getProducts method of BuyerController");
        validateBuyer(role);
        return buyerService.getProducts();
    }

    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public BuyerProductDetailsDto getProduct(@PathVariable Integer productId,
                                             @RequestHeader("role") String role) {
        log.info("Inside getProduct method of BuyerController");
        validateBuyer(role);
        return buyerService.getProduct(productId);
    }

    @PostMapping("product/{productId}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public Bid bid(@RequestHeader("userId") Integer buyerId,
                   @RequestHeader("role") String role,
                   @PathVariable Integer productId,
                   @RequestParam Double bidAmount) {
        log.info("Inside bid method of BuyerController");
        validateBuyer(role);
        return buyerService.bid(buyerId, productId, bidAmount);
    }


}
