package com.example.itfaq.controller.admin;

import com.example.itfaq.model.Question;
import com.example.itfaq.repository.QuestionRepository;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        // createdAt проставится автоматически через @PrePersist
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
    public String editQuestion(
            @PathVariable Long id,
            @ModelAttribute("question") Question formQuestion,
            BindingResult bindingResult,
            @RequestParam(value = "editReason", required = false) String editReason,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("users", userRepository.findAll());
            return "admin/question-form";
        }
        // Найдём старую версию, чтобы не потерять createdAt!
        Optional<Question> oldOpt = questionRepository.findById(id);
        if (oldOpt.isPresent()) {
            Question old = oldOpt.get();
            // Обновляем только нужные поля
            old.setLanguage(formQuestion.getLanguage());
            old.setLevel(formQuestion.getLevel());
            old.setTopic(formQuestion.getTopic());
            old.setQuestionText(formQuestion.getQuestionText());
            old.setShortAnswer(formQuestion.getShortAnswer());
            old.setDetailedAnswer(formQuestion.getDetailedAnswer());
            old.setCategory(formQuestion.getCategory());
            old.setAuthor(formQuestion.getAuthor());
            old.setUpdatedAt(LocalDateTime.now());
            old.setEditReason(editReason);
            questionRepository.save(old);
        }
        return "redirect:/admin/questions";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/admin/questions";
    }
}