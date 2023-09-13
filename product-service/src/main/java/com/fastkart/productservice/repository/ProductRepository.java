package com.fastkart.productservice.repository;

import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.bids b WHERE p.productId = :productId ORDER BY b.bidTime DESC")
    Optional<Product> findByIdWithBids(Integer productId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.bids b WHERE p.productId = :productId and p.seller = :seller ORDER BY b.bidTime DESC")
    Optional<Product> findByIdWithBids(Integer productId, User seller);

    @Query("SELECT p FROM Product p WHERE p.seller = :seller ORDER BY p.listedDate DESC")
    List<Product> findBySellerWithListedDate(User seller);
}
