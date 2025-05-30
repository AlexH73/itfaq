package com.example.itfaq.controller;

import com.example.itfaq.model.News;
import com.example.itfaq.model.User;
import com.example.itfaq.repository.NewsRepository;
import com.example.itfaq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/news")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String listNews(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        return "admin/news-list";
    }

    @GetMapping("/create")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("users", userRepository.findAll());
        return "admin/news-form";
    }

    @PostMapping("/create")
    public String saveNews(@ModelAttribute("news") News news,
                           @RequestParam(value = "authorId", required = false) Long authorId,
                           Principal principal) {
        if (authorId != null) {
            User author = userRepository.findById(authorId).orElse(null);
            news.setAuthor(author);
        } else if (news.getAuthor() == null) {
            // Автоматически подставить текущего пользователя
            User currentUser = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));
            news.setAuthor(currentUser);
        }
        news.setCreatedAt(LocalDateTime.now());
        news.setUpdatedAt(LocalDateTime.now());
        newsRepository.save(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String editNewsForm(@PathVariable Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow();
        model.addAttribute("news", news);
        model.addAttribute("users", userRepository.findAll());
        return "admin/news-form";
    }

    @PostMapping("/edit/{id}")
    public String updateNews(@PathVariable Long id,
                             @ModelAttribute("news") News news,
                             @RequestParam(value = "authorId", required = false) Long authorId,
                             Principal principal) {
        news.setId(id);
        if (authorId != null) {
            User author = userRepository.findById(authorId).orElse(null);
            news.setAuthor(author);
        } else if (news.getAuthor() == null) {
            User currentUser = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));
            news.setAuthor(currentUser);
        }
        news.setUpdatedAt(LocalDateTime.now());
        newsRepository.save(news);
        return "redirect:/admin/news";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsRepository.deleteById(id);
        return "redirect:/admin/news";
    }
}