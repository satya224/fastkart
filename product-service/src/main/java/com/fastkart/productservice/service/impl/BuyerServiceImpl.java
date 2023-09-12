package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.dto.ProductListDto;
import com.fastkart.productservice.model.dto.buyer.BuyerProductDetailsDto;
import com.fastkart.productservice.model.embeddable.BidId;
import com.fastkart.productservice.model.entity.Bid;
import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.BidRepository;
import com.fastkart.productservice.repository.ProductRepository;
import com.fastkart.productservice.service.BuyerService;
import com.fastkart.productservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fastkart.productservice.utils.Mapper.productEntityToBuyerProductDetailsDto;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;
    private final UserServiceImpl userService;

    @Override
    public List<ProductListDto> getProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(Mapper::productToProductDto).toList();
    }

    @Override
    public BuyerProductDetailsDto getProduct(Integer productId) {
        Product product = getProductFromDb(productId);
        return productEntityToBuyerProductDetailsDto(product);
    }

    @Override
    public Bid bid(Integer buyerId, Integer productId, Double bidAmount) {
        Product product = getProductFromDb(productId);
        if (bidAmount < product.getMinimumBidAmountBySeller()) {
            throw new FastKartException(
                    "Bid amount too low",
                    400,
                    "Bid amount too low, minimum bid amount for product with id " + productId + " is " + product.getMinimumBidAmountBySeller());
        }

        if (product.getMaximumBidAmountByBuyer() == null)
            product.setMaximumBidAmountByBuyer(bidAmount);
        else if (bidAmount > product.getMaximumBidAmountByBuyer())
            product.setMaximumBidAmountByBuyer(bidAmount);


        if (product.getMinimumBidAmountByBuyer() == null)
            product.setMinimumBidAmountByBuyer(bidAmount);
        else if (bidAmount < product.getMinimumBidAmountByBuyer())
            product.setMinimumBidAmountByBuyer(bidAmount);

        User buyer = userService.getBuyer(buyerId);
        BidId bidId = new BidId();
        bidId.setBidderId(buyerId);
        bidId.setProductId(productId);

        if (bidRepository.existsByBidId(bidId)) {
            throw new FastKartException(
                    "Bid already exists",
                    400,
                    "Bid already exists for product with id " + productId + " and buyer with id " + buyerId);
        }
        Bid bid = new Bid();
        bid.setBidId(bidId);
        bid.setProduct(product);
        bid.setBidder(buyer);
        bid.setBidAmount(bidAmount);
        return bidRepository.save(bid);

    }

    private Product getProductFromDb(Integer productId) {
        return productRepository.findByIdWithBids(productId)
                .orElseThrow(() -> new FastKartException(
                        "Product not found",
                        404,
                        "Product with id " + productId + " not found"));
    }
}
