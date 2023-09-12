package com.fastkart.productservice.repository;

import com.fastkart.productservice.model.entity.Product;
import com.fastkart.productservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.bids b WHERE p.productId = :productId ORDER BY b.bidTime DESC")
    Optional<Product> findByIdWithBids(Integer productId);
    List<Product> findBySeller(User seller);
}
