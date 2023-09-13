package com.fastkart.productservice.controller;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.service.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuyerControllerTest {

    @Mock
    private BuyerService buyerService;

    @InjectMocks
    private BuyerController buyerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProducts() {
        // Mock the buyerService to return a list of products
        List<ProductListDto> productList = Arrays.asList(new ProductListDto(), new ProductListDto());
        when(buyerService.getProducts()).thenReturn(productList);

        // Call the controller method
        List<ProductListDto> result = buyerController.getProducts("BUYER");

        // Verify that the service method was called and the result matches
        verify(buyerService).getProducts();
        assertEquals(productList, result);
    }

    @Test
    public void testGetProduct() {
        // Mock the buyerService to return a product details DTO
        BuyerProductDetailsDto productDetailsDto = new BuyerProductDetailsDto();

        when(buyerService.getProduct(1)).thenReturn(productDetailsDto);

        // Call the controller method
        BuyerProductDetailsDto result = buyerController.getProduct(1, "BUYER");

        // Verify that the service method was called and the result matches
        verify(buyerService).getProduct(1);
        assertEquals(productDetailsDto, result);
    }

    @Test
    public void testBid() {
        // Mock the buyerService to return a Bid entity
        Bid bid = new Bid();
        when(buyerService.bid(1, 2, 100.0)).thenReturn(bid);

        // Call the controller method
        Bid result = buyerController.bid(1, "BUYER", 2, 100.0);

        // Verify that the service method was called and the result matches
        verify(buyerService).bid(1, 2, 100.0);
        assertEquals(bid, result);
    }

    @Test
    public void testGetProductsUnauthorized() {
        // Simulate an unauthorized request by providing an invalid role
        try {
            buyerController.getProducts("INVALID_ROLE");
        } catch (FastKartException e) {
            // Verify that a ResponseStatusException with HttpStatus.UNAUTHORIZED is thrown
            assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getErrorCode());
        }
    }

    // Similar tests for other controller methods and error cases...
}
