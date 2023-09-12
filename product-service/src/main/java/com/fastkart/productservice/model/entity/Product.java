package com.fastkart.productservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer productId;
    private String productName;
    private String productDescription;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private Double minimumBidAmountBySeller;
    @CreationTimestamp
    private Instant listedDate;
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<Bid> bids;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User seller;
    private Double maximumBidAmountByBuyer;
    private Double minimumBidAmountByBuyer;
}
