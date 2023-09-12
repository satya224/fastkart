package com.fastkart.productservice.utils;

import com.fastkart.commonlibrary.exception.FastKartException;
import org.springframework.http.HttpStatus;

public class Validate {
    private Validate() {
    }

    public static void validateSeller(String role) {
        if (!role.equals("SELLER")) {
            throw new FastKartException("Only seller can access this resource", HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access");
        }
    }

    public static void validateBuyer(String role) {
        if (!role.equals("BUYER")) {
            throw new FastKartException("Only buyer can access this resource", HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access");
        }
    }
}
