package com.example.itfaq.converter;

import com.example.itfaq.model.User;
import com.example.itfaq.repository.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<Long, User> {
    private final UserRepository userRepository;

    public UserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User convert(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}