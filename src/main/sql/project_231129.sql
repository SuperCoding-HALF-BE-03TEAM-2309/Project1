CREATE TABLE `user` (
  `email` varchar2 PRIMARY KEY COMMENT '이메일 (로그인계정)',
  `password` varchar2 COMMENT '비밀번호',
  `nickname` varchar2 COMMENT '닉네임',
  `created_at` timestamp COMMENT '가입일자'
);

CREATE TABLE `post` (
  `post_id` integer PRIMARY KEY AUTO_INCREMENT,
  `title` varchar2,
  `content` text,
  `email` varchar2,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `comment` (
  `comment_id` integer PRIMARY KEY AUTO_INCREMENT,
  `post_id` integer,
  `email` varchar2,
  `content` text,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `like` (
  `like_id` integer PRIMARY KEY AUTO_INCREMENT,
  `type` integer,
  `email` varchar2,
  `post_id` integer,
  `comment_id` integer
);

ALTER TABLE `post` ADD FOREIGN KEY (`email`) REFERENCES `user` (`email`);

ALTER TABLE `comment` ADD FOREIGN KEY (`email`) REFERENCES `user` (`email`);

ALTER TABLE `comment` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`);

ALTER TABLE `like` ADD FOREIGN KEY (`email`) REFERENCES `user` (`email`);

ALTER TABLE `like` ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`);

ALTER TABLE `like` ADD FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`);
