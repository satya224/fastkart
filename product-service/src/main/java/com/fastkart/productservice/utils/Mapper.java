package com.fastkart.productservice.utils;

import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.dto.seller.BidderDto;
import com.fastkart.productservice.model.dto.seller.ProductPostDto;
import com.fastkart.productservice.model.dto.seller.SellerProductDetailsDto;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;

public class Mapper {
    private Mapper() {
    }

    public static Product productPostDtoToProduct(ProductPostDto productDto, Category category, User user) {
        Product productEntity = new Product();
        productEntity.setProductName(productDto.getName());
        productEntity.setProductDescription(productDto.getDescription());
        productEntity.setMinimumBidAmountBySeller(productDto.getMinimumBidAmount());
        productEntity.setCategory(category);
        productEntity.setSeller(user);
        return productEntity;
    }

    public static SellerProductDetailsDto productEntityToSellerProductDetailsDto(Product product) {
        SellerProductDetailsDto sellerProductDetailsDto = new SellerProductDetailsDto();
        sellerProductDetailsDto.setId(product.getProductId());
        sellerProductDetailsDto.setName(product.getProductName());
        sellerProductDetailsDto.setDescription(product.getProductDescription());
        sellerProductDetailsDto.setCategory(product.getCategory().getCategoryName());
        sellerProductDetailsDto.setMinimumBidAmountBySeller(product.getMinimumBidAmountBySeller());
        sellerProductDetailsDto.setMinimumBidAmountByBuyer(product.getMinimumBidAmountByBuyer());
        sellerProductDetailsDto.setMaximumBidAmountByBuyer(product.getMaximumBidAmountByBuyer());
        sellerProductDetailsDto.setBidders(product.getBids().stream().map(Mapper::bidToBidderDto).toList());
        return sellerProductDetailsDto;
    }

    public static BuyerProductDetailsDto productEntityToBuyerProductDetailsDto(Product product) {
        BuyerProductDetailsDto buyerProductDetailsDto = new BuyerProductDetailsDto();
        buyerProductDetailsDto.setId(product.getProductId());
        buyerProductDetailsDto.setName(product.getProductName());
        buyerProductDetailsDto.setDescription(product.getProductDescription());
        buyerProductDetailsDto.setCategory(product.getCategory().getCategoryName());
        buyerProductDetailsDto.setSellerName(product.getSeller().getUsername());
        buyerProductDetailsDto.setMinimumBidAmountBySeller(product.getMinimumBidAmountBySeller());
        buyerProductDetailsDto.setBidders(product.getBids().stream().map(Mapper::bidToBidderDto).toList());
        return buyerProductDetailsDto;
    }


    public static BidderDto bidToBidderDto(Bid bid) {
        return new BidderDto(bid.getBidder().getUsername(), bid.getBidAmount());
    }

    public static ProductListDto productToProductDto(Product product) {
        ProductListDto productListDto = new ProductListDto();
        productListDto.setId(product.getProductId());
        productListDto.setCategory(product.getCategory().getCategoryName());
        productListDto.setName(product.getProductName());
        productListDto.setListedDate(product.getListedDate());
        productListDto.setMaximumBidAmount(product.getMaximumBidAmountByBuyer());
        return productListDto;
    }
}
