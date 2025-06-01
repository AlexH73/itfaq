package com.example.itfaq.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String language;
    private String level;
    private String topic;
    private String questionText;
    private String shortAnswer;
    private String detailedAnswer;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private User author;

    public Question() {} // ОБЯЗАТЕЛЕН для JPA/Spring

    public Question(Long id, String language, String level, String topic, String questionText,
                    String shortAnswer, String detailedAnswer, LocalDateTime createdAt,
                    Category category, User author) {
        this.id = id;
        this.language = language;
        this.level = level;
        this.topic = topic;
        this.questionText = questionText;
        this.shortAnswer = shortAnswer;
        this.detailedAnswer = detailedAnswer;
        this.createdAt = createdAt;
        this.category = category;
        this.author = author;
    }
}