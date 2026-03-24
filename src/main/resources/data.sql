-- 初始化管理员用户（如果不存在）
INSERT INTO users (username, password, email, phone, nickname, role, account_status)
SELECT 'admin', '12345678', 'admin@example.com', '13800138001', '系统管理员', 1, 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- 初始化测试用户（如果不存在）
INSERT INTO users (username, password, email, phone, nickname, role)
SELECT 'test1', '12345678', 'test1@example.com', '13800138002', '测试用户1', 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'test1');

INSERT INTO users (username, password, email, phone, nickname, role)
SELECT 'test2', '12345678', 'test2@example.com', '13800138003', '测试用户2', 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'test2');



-- 插入第一条博客
INSERT INTO blog (title, content, summary, author_id, author_name)
SELECT
    '我的第一篇博客 - 使用Markdown',
    '# 欢迎来到我的博客\n\n这是我的第一篇博客文章，使用**Markdown**格式编写。\n\n## 主要特点\n- 支持Markdown语法\n- 支持代码块\n\n```java\nSystem.out.println(\"Hello Blog!\");\n```\n\n## 后续计划\n我会在这里分享技术文章和学习心得。',
    '这是我的第一篇博客文章',
    1,
    '张三'
 WHERE NOT EXISTS (SELECT 1 FROM blog WHERE title = '我的第一篇博客 - 使用Markdown');
