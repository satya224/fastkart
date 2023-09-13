package com.fastkart.productservice.controller;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SellerControllerTest {

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        when(sellerService.addProduct(any(), eq(1))).thenReturn(product);

        Product result = sellerController.addProduct(new ProductPostDto(), 1, "SELLER");

        verify(sellerService).addProduct(any(), eq(1));
        assertEquals(product, result);
    }

    @Test
    public void testGetProduct() {
        SellerProductDetailsDto productDetailsDto = new SellerProductDetailsDto();
        when(sellerService.getProduct(1, 1)).thenReturn(productDetailsDto);

        SellerProductDetailsDto result = sellerController.getProduct(1, 1, "SELLER");

        verify(sellerService).getProduct(1, 1);
        assertEquals(productDetailsDto, result);
    }

    @Test
    public void testGetProducts() {
        List<ProductListDto> productList = Arrays.asList(new ProductListDto(), new ProductListDto());
        when(sellerService.getProducts(1)).thenReturn(productList);

        List<ProductListDto> result = sellerController.getProducts(1, "SELLER");

        verify(sellerService).getProducts(1);
        assertEquals(productList, result);
    }

    @Test
    public void testUnauthorizedRequest() {
        try {
            sellerController.addProduct(new ProductPostDto(), 1, "INVALID_ROLE");
        } catch (FastKartException e) {
            assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getErrorCode());
        }
    }
}
