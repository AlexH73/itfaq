# ITFAQ Portal

## Описание

**ITFAQ Portal** — это административная панель для управления IT-вопросами и ответами в формате базы знаний или внутреннего FAQ для сотрудников. В качестве визуального редактора используется CKEditor, а шаблоны реализованы с помощью Thymeleaf.

## Основные возможности

- Управление списком IT-вопросов и ответов
- Визуальное редактирование подробных ответов через CKEditor
- Поиск и фильтрация по вопросам
- Добавление, редактирование и удаление записей
- Защищённый доступ к административной части

## Технологии

- **Backend:** Java 17+, Spring Boot, Spring Data JPA
- **Frontend:** Thymeleaf, CKEditor 4, HTML5, CSS3
- **База данных:** H2/PostgreSQL/MySQL (на выбор)
- **Система сборки:** Maven/Gradle

## Быстрый старт

1. **Клонируй репозиторий:**
    ```bash
    git clone https://github.com/yourusername/itfaq-portal.git
    cd itfaq-portal
    ```

2. **Собери и запусти проект:**
    ```bash
    ./mvnw spring-boot:run
    ```
    или
    ```bash
    ./gradlew bootRun
    ```

3. **Открой в браузере:**
    ```
    http://localhost:8080/admin
    ```

4. **Данные для входа по умолчанию:**
    - Логин: `admin`
    - Пароль: `admin`

## Локальное подключение CKEditor

CKEditor 4.22.1 подключён локально и не требует внешнего CDN.

## Пример использования CKEditor в шаблоне

```html
<textarea id="detailedAnswer" th:field="*{detailedAnswer}" rows="6" required></textarea>
<script src="/ckeditor/ckeditor.js"></script>
<script>
  CKEDITOR.replace('detailedAnswer', { height: 300 });
</script>
```

## Безопасность

- CKEditor используется только во внутренней (админской) части.
- Для вывода HTML-ответов используется конструкция `th:utext`.
- Рекомендуется ограничить доступ к админке авторизацией.

## Лицензия

Проект распространяется под MIT License.

## Контакты

Автор: [AlexH73](https://github.com/AlexH73)  
Почта: ewebotah@gmail.com

---

> Есть вопросы или предложения — создавайте Issue или Pull Request!