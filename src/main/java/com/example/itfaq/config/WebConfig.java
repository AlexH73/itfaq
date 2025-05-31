package com.example.itfaq.config;

import com.example.itfaq.converter.CategoryConverter;
import com.example.itfaq.converter.UserConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CategoryConverter categoryConverter;
    private final UserConverter userConverter;

    public WebConfig(CategoryConverter categoryConverter, UserConverter userConverter) {
        this.categoryConverter = categoryConverter;
        this.userConverter = userConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(categoryConverter);
        registry.addConverter(userConverter);
    }
}