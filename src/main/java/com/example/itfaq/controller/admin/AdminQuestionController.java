package com.example.itfaq.controller.admin;

import com.example.itfaq.model.Question;
import com.example.itfaq.repository.QuestionRepository;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuestionController {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String listQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "admin/question-list";
    }

    @GetMapping("/create")
    public String createQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin/question-form";
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute("question") Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/question-form";
        }
        questionRepository.save(question);
        return "redirect:/admin/questions";
    }

    @GetMapping("/{id}")
    public String viewQuestion(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        question.ifPresent(q -> model.addAttribute("question", q));
        return question.isPresent() ? "admin/question-view" : "redirect:/admin/questions";
    }

    @GetMapping("/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("users", userRepository.findAll());
            return "admin/question-form";
        }
        return "redirect:/admin/questions";
    }

    @PostMapping("/edit/{id}")
    public String editQuestion(@PathVariable Long id, @ModelAttribute("question") Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/question-form";
        }
        question.setId(id);
        questionRepository.save(question);
        return "redirect:/admin/questions";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/admin/questions";
    }
}