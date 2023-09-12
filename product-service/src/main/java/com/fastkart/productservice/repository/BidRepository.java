package com.fastkart.productservice.repository;

import com.fastkart.productservice.model.embeddable.BidId;
import com.fastkart.productservice.model.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    boolean existsByBidId(BidId bidId);
}
