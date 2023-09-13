package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.BidderDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.embeddable.BidId;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.CategoryRepository;
import com.fastkart.productservice.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fastkart.productservice.utils.Mapper.productPostDtoToProduct;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SellerServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        // Mock the categoryRepository to return a category
        Category category = new Category();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        // Mock the userService to return a seller
        int sellerId = 1;
        User seller = new User();
        when(userService.getSeller(sellerId)).thenReturn(seller);

        // Mock the productRepository to save the product
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Create a test ProductPostDto
        ProductPostDto productDto = new ProductPostDto();
        productDto.setCategoryId(1); // Set the category ID as needed

        // Call the service method
        Product result = sellerService.addProduct(productDto, sellerId);

        // Verify that the repository methods were called and the result is not null
        verify(categoryRepository).findById(1);
        verify(userService).getSeller(sellerId);
        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
    }

    @Test
    public void testAddProductWithInvalidCategory() {
        // Mock the categoryRepository to return an empty result (category not found)
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Create a test ProductPostDto
        ProductPostDto productDto = new ProductPostDto();
        productDto.setCategoryId(1); // Set the category ID as needed

        // Call the service method and expect an exception
        assertThrows(FastKartException.class, () -> sellerService.addProduct(productDto, 1));
    }

    @Test
    public void testGetProduct() {
        SellerProductDetailsDto expected = new SellerProductDetailsDto();
        expected.setId(1);
        expected.setName("Test Product");
        expected.setDescription("Description of the test product");
        expected.setCategory("Test Category");
        expected.setMinimumBidAmountBySeller(10.0);
        expected.setMinimumBidAmountByBuyer(15.0);
        expected.setMaximumBidAmountByBuyer(20.0);
        expected.setBidders(List.of(new BidderDto("test_buyer", 10.0)));

        int sellerId = 1;
        User seller = new User();
        when(userService.getSeller(sellerId)).thenReturn(seller);

        int productId = 1;
        Product product = createProduct();
        when(productRepository.findByIdWithBids(productId, seller)).thenReturn(Optional.of(product));

        // Call the service method
        SellerProductDetailsDto result = sellerService.getProduct(productId, 1);

        // Verify that the repository method was called and the result is not null
        verify(productRepository).findByIdWithBids(productId, seller);
        assertEquals(result, expected);
    }

    @Test
    public void testGetProductThrowException() {

        int productId = 1;
        Product product = createProduct();
        when(productRepository.findByIdWithBids(productId)).thenReturn(Optional.empty());

        assertThrows(FastKartException.class, () -> sellerService.getProduct(productId, 1));
    }

    @Test
    public void testGetProducts() {
        // Mock the userService to return a seller
        int sellerId = 1;
        User seller = new User();
        when(userService.getSeller(sellerId)).thenReturn(seller);

        // Mock the productRepository to return a list of products
        List<Product> productList = List.of(createProduct());
        when(productRepository.findBySellerWithListedDate(seller)).thenReturn(productList);

        // Call the service method
        List<ProductListDto> result = sellerService.getProducts(sellerId);

        // Verify that the repository methods were called and the result is not empty
        verify(userService).getSeller(sellerId);
        verify(productRepository).findBySellerWithListedDate(seller);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testAddProducts() throws IOException {
        // Mock sellerId
        Integer sellerId = 1;

        FileInputStream excelFile = new FileInputStream("src/test/resources/test-file.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "test-file.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", excelFile);


        // Mock User and Category
        User seller = new User();
        Category category = new Category();
        category.setCategoryId(1);

        when(userService.getSeller(sellerId)).thenReturn(seller);
        when(categoryRepository.findById(anyInt())).thenReturn(java.util.Optional.of(category));

        Sheet sheet = mock(Sheet.class);

        // Mock the behavior of the rows in the sheet
        int numberOfRows = 6;
        List<Product> productList = new ArrayList<>();

        for (int i = 1; i <= numberOfRows; i++) {
            Row row = mock(Row.class);

            when(sheet.getRow(i)).thenReturn(row);
            when(row.getCell(0)).thenReturn(mock(Cell.class));
            when(row.getCell(1)).thenReturn(mock(Cell.class));
            when(row.getCell(2)).thenReturn(mock(Cell.class));
            when(row.getCell(3)).thenReturn(mock(Cell.class));

            when(row.getCell(0).getStringCellValue()).thenReturn("product" + (i+10));
            when(row.getCell(1).getStringCellValue()).thenReturn("description" + (i+10));
            when(row.getCell(2).getNumericCellValue()).thenReturn(1000.00 * i);
            when(row.getCell(3).getNumericCellValue()).thenReturn((double) i);

            ProductPostDto productDto = new ProductPostDto();
            productDto.setName("product" + (i+10));
            productDto.setDescription("description" + (i+10));
            productDto.setMinimumBidAmount(1000.0 * i);
            productDto.setCategoryId(i);

            Product product = productPostDtoToProduct(productDto, category, seller);
            productList.add(product);
        }

        // Mock the behavior of the productRepository
        when(productRepository.saveAll(anyList())).thenReturn(productList);

        // Call the addProducts method
        sellerService.addProducts(sellerId, file);

        // Verify that the productRepository's saveAll method was called with the correct list
        verify(productRepository, times(1)).saveAll(productList);
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


