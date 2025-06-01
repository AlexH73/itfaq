package com.example.itfaq.controller;

import com.example.itfaq.model.Category;
import com.example.itfaq.model.Question;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionRepository questionRepo;
    private final CategoryRepository categoryRepo;

    public QuestionController(QuestionRepository questionRepo, CategoryRepository categoryRepo) {
        this.questionRepo = questionRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public List<Question> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String search
    ) {
        // Поиск по тексту вопроса или подробному ответу
        if (search != null && !search.isEmpty()) {
            return questionRepo.findByQuestionTextContainingIgnoreCaseOrDetailedAnswerContainingIgnoreCase(search, search);
        }
        // Поиск по части темы (например, topic=Spring найдёт "Spring Boot")
        if (topic != null && !topic.isEmpty()) {
            return questionRepo.findByTopicContainingIgnoreCase(topic);
        }
        // Комбинированный фильтр (пример: всё сразу)
        if (categoryId != null && level != null && language != null && topic != null) {
            return questionRepo.findByCategoryIdAndLevelAndLanguageAndTopic(categoryId, level, language, topic);
        }
        // По отдельным полям
        if (categoryId != null) return questionRepo.findByCategoryId(categoryId);
        if (language != null) return questionRepo.findByLanguage(language);
        if (level != null) return questionRepo.findByLevel(level);

        return questionRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable Long id) {
        return questionRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Question> create(@RequestBody Question question) {
        // Проверка, что категория существует
        if (question.getCategory() != null && question.getCategory().getId() != null) {
            Category cat = categoryRepo.findById(question.getCategory().getId()).orElse(null);
            if (cat == null) return ResponseEntity.badRequest().build();
            question.setCategory(cat);
        }
        return ResponseEntity.ok(questionRepo.save(question));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> update(@PathVariable Long id, @RequestBody Question updated) {
        return questionRepo.findById(id)
                .map(q -> {
                    q.setLanguage(updated.getLanguage());
                    q.setLevel(updated.getLevel());
                    q.setTopic(updated.getTopic());
                    q.setQuestionText(updated.getQuestionText());
                    q.setShortAnswer(updated.getShortAnswer());
                    q.setDetailedAnswer(updated.getDetailedAnswer());
                    // Категория
                    if (updated.getCategory() != null && updated.getCategory().getId() != null) {
                        Category cat = categoryRepo.findById(updated.getCategory().getId()).orElse(null);
                        q.setCategory(cat);
                    }
                    return ResponseEntity.ok(questionRepo.save(q));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}