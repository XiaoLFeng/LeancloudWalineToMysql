-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主机： 172.17.0.3
-- 生成日期： 2023-12-22 17:37:06
-- 服务器版本： 8.2.0
-- PHP 版本： 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `waline`
--

-- --------------------------------------------------------

--
-- 表的结构 `wl_comment`
--

CREATE TABLE `wl_comment` (
  `id` int UNSIGNED NOT NULL,
  `user_id` int DEFAULT NULL,
  `comment` text,
  `insertedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(100) DEFAULT '',
  `link` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `rid` int DEFAULT NULL,
  `sticky` tinyint(1) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT '',
  `like` int DEFAULT NULL,
  `ua` text,
  `url` varchar(255) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `object_id` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 表的结构 `wl_counter`
--

CREATE TABLE `wl_counter` (
  `id` int UNSIGNED NOT NULL,
  `time` int DEFAULT NULL,
  `reaction0` int DEFAULT NULL,
  `reaction1` int DEFAULT NULL,
  `reaction2` int DEFAULT NULL,
  `reaction3` int DEFAULT NULL,
  `reaction4` int DEFAULT NULL,
  `reaction5` int DEFAULT NULL,
  `reaction6` int DEFAULT NULL,
  `reaction7` int DEFAULT NULL,
  `reaction8` int DEFAULT NULL,
  `url` varchar(255) NOT NULL DEFAULT '',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- 表的结构 `wl_users`
--

CREATE TABLE `wl_users` (
  `id` int UNSIGNED NOT NULL,
  `display_name` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(50) NOT NULL DEFAULT '',
  `label` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `github` varchar(255) DEFAULT NULL,
  `twitter` varchar(255) DEFAULT NULL,
  `facebook` varchar(255) DEFAULT NULL,
  `google` varchar(255) DEFAULT NULL,
  `weibo` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `2fa` varchar(32) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转储表的索引
--

--
-- 表的索引 `wl_comment`
--
ALTER TABLE `wl_comment`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wl_counter`
--
ALTER TABLE `wl_counter`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wl_users`
--
ALTER TABLE `wl_users`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `wl_comment`
--
ALTER TABLE `wl_comment`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wl_counter`
--
ALTER TABLE `wl_counter`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wl_users`
--
ALTER TABLE `wl_users`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
