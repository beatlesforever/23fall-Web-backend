-- 插入第一个用户
INSERT INTO Users (username, password, email)
VALUES ('user1', 'password1', 'user1@example.com');

INSERT INTO Users (username, password, email)
VALUES ('user2', 'password2', 'user1@example.com');

INSERT INTO Posts (title, content, dateTime, userID)
VALUES ('First Post Title', 'Content of the first post', NOW(), 1),
       ('Second Post Title', 'Content of the second post', NOW(), 1);

-- Insert posts for user2
INSERT INTO Posts (title, content, dateTime, userID)
VALUES ('Third Post Title', 'Content of the third post', NOW(), 2),
       ('Fourth Post Title', 'Content of the fourth post', NOW(), 2);