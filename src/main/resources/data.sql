INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO users (id, username, password, email, enabled) VALUES
(1, 'admin', '$2a$10$eB2lRZpB1I6kYpWkHxVd/OQqQnqY2aBqkXjDkC1p1ay2cB1l3Tx8e', 'admin@example.com', true);
-- пароль: admin

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);