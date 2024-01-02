-- 删除现有表结构及其数据（如果存在）
DROP TABLE IF EXISTS Messages;
DROP TABLE IF EXISTS Posts;
DROP TABLE IF EXISTS Users;

-- 创建用户表
CREATE TABLE IF NOT EXISTS Users (
    userID BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    avatarPath VARCHAR(255); -- 存储用户头像的URL或文件路径
-- 其他个人信息的字段定义
    -- ...
    );

-- 创建帖子表
CREATE TABLE IF NOT EXISTS Posts (
    postID INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    dateTime DATETIME NOT NULL,
    userID INT,
    imagePaths TEXT, -- 用于存储多个图片路径，使用逗号分隔
    FOREIGN KEY (userID) REFERENCES Users(userID)
    -- 其他与帖子相关的字段定义
    -- ...
    );

-- 创建站内消息表
CREATE TABLE IF NOT EXISTS Messages (
    messageID INT AUTO_INCREMENT PRIMARY KEY,
    senderID INT,
    recipientID INT,
    content TEXT,
    dateTime DATETIME NOT NULL,
    FOREIGN KEY (senderID) REFERENCES Users(userID),
    FOREIGN KEY (recipientID) REFERENCES Users(userID)
    -- 其他与消息相关的字段定义
    -- ...
    );
