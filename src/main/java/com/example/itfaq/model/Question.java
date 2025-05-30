package com.example.itfaq.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language; // "RU", "DE"
    private String level;    // "easy", "medium", "hard"
    private String topic;
    private String question;
    private String shortAnswer;
    private String detailedAnswer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
