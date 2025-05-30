package com.example.itfaq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "targetType", "targetId"})
})
public class Like {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String targetType; // "NEWS" или "COMMENT"
    private Long targetId;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters, setters
}