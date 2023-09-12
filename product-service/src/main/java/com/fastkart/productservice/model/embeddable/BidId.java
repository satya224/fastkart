package com.fastkart.productservice.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class BidId implements Serializable {
    private Integer productId;
    private Integer bidderId;
}
