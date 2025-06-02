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

    @Column(columnDefinition = "text")
    private String shortAnswer;

    @Column(columnDefinition = "text")
    private String detailedAnswer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String editReason;

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

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}