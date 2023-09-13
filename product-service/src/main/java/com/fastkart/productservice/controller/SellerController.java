package com.fastkart.productservice.controller;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.fastkart.productservice.utils.Validate.validateSeller;

@RestController
@RequestMapping("fastkart/seller")
@RequiredArgsConstructor
@Slf4j
public class SellerController {
    private final SellerService sellerService;

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductPostDto product,
                              @RequestHeader("userId") Integer sellerId,
                              @RequestHeader("role") String role) {
        log.info("Inside addProduct method of SellerController");
        validateSeller(role);
        return sellerService.addProduct(product, sellerId);
    }

    @PostMapping(value = "products", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public String addProducts(@RequestParam("file") MultipartFile file,
                              @RequestHeader("userId") Integer sellerId,
                              @RequestHeader("role") String role) throws IOException {
        log.info("Inside addProducts method of SellerController");
        validateSeller(role);
        return sellerService.addProducts(sellerId, file);
    }


    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public SellerProductDetailsDto getProduct(@PathVariable Integer productId,
                                              @RequestHeader("userId") Integer sellerId,
                                              @RequestHeader("role") String role) {
        log.info("Inside getProduct method of SellerController");
        validateSeller(role);
        return sellerService.getProduct(productId, sellerId);
    }

    @GetMapping("product")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListDto> getProducts(@RequestHeader("userId") Integer sellerId,
                                            @RequestHeader("role") String role) {
        log.info("Inside getProducts method of SellerController");
        validateSeller(role);
        return sellerService.getProducts(sellerId);
    }
}
