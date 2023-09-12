package com.fastkart.productservice.service;

import com.fastkart.productservice.model.entity.User;

public interface UserService {
    User getSeller(Integer sellerId);

    User getBuyer(Integer userId);
}
