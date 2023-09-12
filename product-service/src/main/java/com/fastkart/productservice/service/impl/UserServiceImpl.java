package com.fastkart.productservice.service.impl;

import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.UserRepository;
import com.fastkart.productservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getSeller(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new FastKartException("Seller not found", HttpStatus.NOT_FOUND.value(), "Seller with id " + userId + "not found"));
    }

    @Override
    public User getBuyer(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new FastKartException("Buyer not found", HttpStatus.NOT_FOUND.value(), "Buyer with id " + userId + "not found"));
    }
}
