package com.example.itfaq.controller;

import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    private final CategoryRepository categoryRepo;
    private final QuestionRepository questionRepo;

    public PageController(CategoryRepository categoryRepo, QuestionRepository questionRepo) {
        this.categoryRepo = categoryRepo;
        this.questionRepo = questionRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "index";
    }

    // Категория по id
    @GetMapping("/category/{id}")
    public String categoryById(@PathVariable Long id, Model model) {
        var category = categoryRepo.findById(id).orElse(null);
        if (category == null) return "redirect:/";
        model.addAttribute("category", category);
        model.addAttribute("questions", questionRepo.findByCategoryId(id));
        return "category";
    }

    // Категория по имени
    @GetMapping("/category")
    public String categoryByName(@RequestParam String name, Model model) {
        var category = categoryRepo.findByName(name).orElse(null);
        if (category == null) return "redirect:/";
        model.addAttribute("category", category);
        model.addAttribute("questions", questionRepo.findByCategoryId(category.getId()));
        return "category";
    }

    @GetMapping("/question/{id}")
    public String questionById(@PathVariable Long id, Model model) {
        var question = questionRepo.findById(id).orElse(null);
        if (question == null) return "redirect:/";
        model.addAttribute("question", question);
        return "question";
    }
}