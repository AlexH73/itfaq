package com.example.itfaq.converter;

import com.example.itfaq.model.Category;
import com.example.itfaq.repository.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<Long, Category> {
    private final CategoryRepository categoryRepository;

    public CategoryConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category convert(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
