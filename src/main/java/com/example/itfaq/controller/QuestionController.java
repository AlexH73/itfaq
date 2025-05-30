package com.example.itfaq.controller;

import com.example.itfaq.model.Category;
import com.example.itfaq.model.Question;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final CategoryRepository categoryRepo;
    private final QuestionRepository questionRepo;

    public QuestionController(CategoryRepository categoryRepo, QuestionRepository questionRepo) {
        this.categoryRepo = categoryRepo;
        this.questionRepo = questionRepo;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/questions")
    public List<Question> getQuestions(
            @RequestParam Long categoryId,
            @RequestParam String language
    ) {
        return questionRepo.findByCategoryIdAndLanguage(categoryId, language);
    }
}