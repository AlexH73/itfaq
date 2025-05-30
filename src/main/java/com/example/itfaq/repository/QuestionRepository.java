package com.example.itfaq.repository;

import com.example.itfaq.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryIdAndLanguage(Long categoryId, String language);
    List<Question> findByCategoryId(Long categoryId);
    List<Question> findByLanguage(String language);
    List<Question> findByLevel(String level);
    List<Question> findByTopic(String topic);
    List<Question> findByTopicContainingIgnoreCase(String topic);
    List<Question> findByQuestionContainingIgnoreCase(String search);
    List<Question> findByQuestionContainingIgnoreCaseOrDetailedAnswerContainingIgnoreCase(String search1, String search2);
    List<Question> findByCategoryIdAndLevelAndLanguageAndTopic(Long categoryId, String level, String language, String topic);
}