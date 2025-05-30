package com.example.itfaq.repository;

import com.example.itfaq.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryIdAndLanguage(Long categoryId, String language);
}