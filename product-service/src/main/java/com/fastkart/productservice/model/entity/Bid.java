package com.fastkart.productservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fastkart.productservice.model.embeddable.BidId;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
public class Bid {
    @EmbeddedId
    private BidId bidId;
    private Double bidAmount;
    @ManyToOne
    @MapsId("bidderId")
    @JoinColumn(name = "bidderId")
    private User bidder;
    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;
    @CreationTimestamp
    private Instant bidTime;

}
