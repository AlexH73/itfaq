package com.example.itfaq.controller;

import com.example.itfaq.model.Question;
import com.example.itfaq.model.User;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.QuestionRepository;
import com.example.itfaq.repository.LikeRepository;
import com.example.itfaq.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    private final CategoryRepository categoryRepo;
    private final QuestionRepository questionRepo;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public PageController(CategoryRepository categoryRepo, QuestionRepository questionRepo, LikeRepository likeRepository, UserRepository userRepository) {
        this.categoryRepo = categoryRepo;
        this.questionRepo = questionRepo;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
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
    public String questionById(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        var question = questionRepo.findById(id).orElse(null);
        if (question == null) return "redirect:/";
        model.addAttribute("question", question);

        // Подсчёт лайков и likedByCurrentUser
        long likeCount = likeRepository.countByObjectTypeAndObjectId("question", id);
        question.setLikeCount(likeCount);

        boolean likedByCurrentUser = false;
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user != null) {
                likedByCurrentUser = likeRepository.findByUserAndObjectTypeAndObjectId(user, "question", id).isPresent();
            }
        }
        model.addAttribute("likedByCurrentUser", likedByCurrentUser);

        return "question";
    }
}