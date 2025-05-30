package com.example.itfaq.config;

import com.example.itfaq.model.Category;
import com.example.itfaq.model.Question;
import com.example.itfaq.repository.CategoryRepository;
import com.example.itfaq.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepo;
    private final QuestionRepository questionRepo;

    public DataLoader(CategoryRepository categoryRepo, QuestionRepository questionRepo) {
        this.categoryRepo = categoryRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public void run(String... args) {
        // Проверка, чтобы не добавлять дубли
        if (categoryRepo.count() == 0) {
            Category java = new Category();
            java.setName("Java");
            Category spring = new Category();
            spring.setName("Spring");
            categoryRepo.save(java);
            categoryRepo.save(spring);

            Question q1 = new Question();
            q1.setLanguage("RU");
            q1.setLevel("easy");
            q1.setTopic("JVM");
            q1.setQuestion("Что такое JVM?");
            q1.setShortAnswer("Виртуальная машина Java");
            q1.setDetailedAnswer("JVM — это виртуальная машина, исполняющая байт-код Java.");
            q1.setCategory(java);

            Question q2 = new Question();
            q2.setLanguage("RU");
            q2.setLevel("medium");
            q2.setTopic("Spring Boot");
            q2.setQuestion("Что делает Spring Boot?");
            q2.setShortAnswer("Упрощает создание приложений Spring");
            q2.setDetailedAnswer("Spring Boot — это инструмент для быстрого создания production-ready приложений на Spring.");
            q2.setCategory(spring);

            questionRepo.save(q1);
            questionRepo.save(q2);
        }
    }
}