package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.CategoryRepository;
import com.fastkart.productservice.repository.ProductRepository;
import com.fastkart.productservice.service.SellerService;
import com.fastkart.productservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fastkart.productservice.utils.Mapper.*;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserServiceImpl userService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductPostDto productDto, Integer sellerId) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new FastKartException(
                        "Category not found",
                        404,
                        "Category with id " + productDto.getCategoryId() + " not found"));
        User seller = userService.getSeller(sellerId);
        Product product = productPostDtoToProduct(productDto, category, seller);
        return productRepository.save(product);
    }


    @Override
    public SellerProductDetailsDto getProduct(Integer productId) {
        Product product = productRepository.findByIdWithBids(productId)
                .orElseThrow(() -> new FastKartException(
                        "Product not found",
                        404,
                        "Product with id " + productId));
        return productEntityToSellerProductDetailsDto(product);
    }





    @Override
    public List<ProductListDto> getProducts(Integer sellerId) {
        User seller = userService.getSeller(sellerId);
        List<Product> productList = productRepository.findBySeller(seller);
        return productList.stream().map(Mapper::productToProductDto).toList();
    }


}
