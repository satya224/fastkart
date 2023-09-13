package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.dto.seller.BidderDto;
import com.fastkart.productservice.model.embeddable.BidId;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.BidRepository;
import com.fastkart.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuyerServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private BuyerServiceImpl buyerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProducts() {
        // Mock the productRepository to return a list of products
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productRepository.findAll()).thenReturn(productList);

        // Call the service method
        List<ProductListDto> result = buyerService.getProducts();

        // Verify that the repository method was called and the result is not empty
        verify(productRepository).findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetProduct() {
        BuyerProductDetailsDto expected = new BuyerProductDetailsDto();
        expected.setId(1);
        expected.setName("Test Product");
        expected.setDescription("Description of the test product");
        expected.setCategory("Test Category");
        expected.setSellerName("test_seller");
        expected.setMinimumBidAmountBySeller(10.0);
        expected.setBidders(List.of(new BidderDto("test_buyer", 10.0)));


        // Mock the productRepository to return a product with a specific ID
        int productId = 1;
        Product product = createProduct();
        when(productRepository.findByIdWithBids(productId)).thenReturn(Optional.of(product));

        // Call the service method
        BuyerProductDetailsDto result = buyerService.getProduct(productId);

        // Verify that the repository method was called and the result matches
        verify(productRepository).findByIdWithBids(productId);
        assertEquals(result, expected);
    }

    @Test
    public void testBid() {
        // Mock the productRepository to return a product with specific bid constraints
        int productId = 1;
        Product product = createProduct();
        when(productRepository.findByIdWithBids(productId)).thenReturn(Optional.of(product));

        // Mock the bidRepository to return false for existsByBidId (bid does not exist)
        when(bidRepository.existsByBidId(any())).thenReturn(false);

        // Mock the userService to return a buyer
        int buyerId = 2;
        when(userService.getBuyer(buyerId)).thenReturn(new User()); // Mocking a valid buyer

        when(bidRepository.save(any())).thenReturn(product.getBids().get(0));

        // Call the service method
        Bid result = buyerService.bid(buyerId, productId, 60.0);


        // Verify that the repository methods were called and the result is not null
        verify(productRepository).findByIdWithBids(productId);
        verify(bidRepository).existsByBidId(any());
        verify(userService).getBuyer(buyerId);
        assertNotNull(result);
    }

    @Test
    public void testBidWithInvalidAmount() {
        // Mock the productRepository to return a product with a specific minimum bid amount
        int productId = 1;
        Product product = new Product();
        product.setMinimumBidAmountBySeller(50.0); // Mocking a minimum bid amount set by the seller
        when(productRepository.findByIdWithBids(productId)).thenReturn(Optional.of(product));

        // Call the service method with a bid amount that's too low
        assertThrows(FastKartException.class, () -> buyerService.bid(1, productId, 40.0));
    }

    @Test
    public void testBidWithExistingBid() {
        // Mock the bidRepository to return true for existsByBidId (bid already exists)
        when(bidRepository.existsByBidId(any())).thenReturn(true);
        when(productRepository.findByIdWithBids(any())).thenReturn(Optional.of(createProduct()));

        // Call the service method with an existing bid
        assertThrows(FastKartException.class, () -> buyerService.bid(1, 2, 60.0));
    }

    @Test
    public void testProductNotFound() {
        // Mock the bidRepository to return true for existsByBidId (bid already exists)
        when(bidRepository.existsByBidId(any())).thenReturn(true);

        // Call the service method with an existing bid
        assertThrows(FastKartException.class, () -> buyerService.bid(1, 2, 60.0));
    }

    private Product createProduct() {


        User seller = new User();
        seller.setUserId(1);
        seller.setUsername("test_seller");

        User buyer = new User();
        buyer.setUserId(2);
        buyer.setUsername("test_buyer");

        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Test Category");

        Product product = new Product();

        Bid bid = new Bid();
        bid.setBidId(new BidId(1, 1));
        bid.setBidAmount(10.0);
        bid.setBidder(buyer);
        bid.setProduct(product);


        product.setProductId(1);
        product.setProductName("Test Product");
        product.setProductDescription("Description of the test product");
        product.setCategory(category);
        product.setMinimumBidAmountBySeller(10.0);
        product.setListedDate(Instant.now());
        product.setBids(List.of(bid));
        product.setSeller(seller);
        product.setMaximumBidAmountByBuyer(20.0);
        product.setMinimumBidAmountByBuyer(15.0);
        return product;
    }
}
