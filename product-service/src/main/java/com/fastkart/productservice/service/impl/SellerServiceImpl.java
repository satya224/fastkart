package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.CategoryRepository;
import com.fastkart.productservice.repository.ProductRepository;
import com.fastkart.productservice.service.SellerService;
import com.fastkart.productservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fastkart.productservice.utils.Mapper.productEntityToSellerProductDetailsDto;
import static com.fastkart.productservice.utils.Mapper.productPostDtoToProduct;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final UserServiceImpl userService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductPostDto productDto, Integer sellerId) {
        Category category = getCategory(productDto.getCategoryId());
        User seller = userService.getSeller(sellerId);
        Product product = productPostDtoToProduct(productDto, category, seller);
        return productRepository.save(product);
    }

    private Category getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new FastKartException(
                        "Category not found",
                        404,
                        "Category with id " + categoryId + " not found"));
    }


    @Override
    public SellerProductDetailsDto getProduct(Integer productId, Integer sellerId) {
        User seller = userService.getSeller(sellerId);
        Product product = productRepository.findByIdWithBids(productId, seller)
                .orElseThrow(() -> new FastKartException(
                        "Product not found",
                        404,
                        "Product with id " + productId));
        return productEntityToSellerProductDetailsDto(product);
    }


    @Override
    public List<ProductListDto> getProducts(Integer sellerId) {
        User seller = userService.getSeller(sellerId);
        List<Product> productList = productRepository.findBySellerWithListedDate(seller);
        return productList.stream().map(Mapper::productToProductDto).toList();
    }

    @Override
    public String addProducts(Integer sellerId, MultipartFile file) throws IOException {
        List<Product> productList = new ArrayList<>();
        User seller = userService.getSeller(sellerId);

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String name = row.getCell(0).getStringCellValue();
            String description = row.getCell(1).getStringCellValue();
            double price = row.getCell(2).getNumericCellValue();
            int categoryId = (int) row.getCell(3).getNumericCellValue();

            ProductPostDto productPostDto = new ProductPostDto();
            productPostDto.setName(name);
            productPostDto.setDescription(description);
            productPostDto.setMinimumBidAmount(price);
            productPostDto.setCategoryId(categoryId);

            Category category = getCategory(productPostDto.getCategoryId());

            Product product = productPostDtoToProduct(productPostDto, category, seller);
            productList.add(product);
        }

        productRepository.saveAll(productList);
        return "Products added successfully";

    }


}
