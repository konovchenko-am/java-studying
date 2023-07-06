-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Май 15 2023 г., 21:32
-- Версия сервера: 8.0.30
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `lesson32`
--

-- --------------------------------------------------------

--
-- Структура таблицы `books`
--

CREATE TABLE `books` (
  `id` bigint NOT NULL,
  `author` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `descriptyion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` int NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `books`
--

INSERT INTO `books` (`id`, `author`, `descriptyion`, `price`, `title`) VALUES
(1, 'Б. Страуструп', 'Полное руководство', 8000, 'Программирование на С++'),
(2, 'C. Иванов', 'Для начинающих', 1200, 'Python 3'),
(52, 'А.С. Пушкин', 'Художественная литература', 450, 'Капитанская дочка'),
(53, 'С. Лем', 'Публицистика', 760, 'Молох'),
(54, 'Л.Н. Толстой', 'Художественная литература', 3500, 'Война и мир'),
(102, 'Н. Носов', 'Детская литература', 690, 'Фантазёры'),
(152, 'Варлам Шаламов', 'Художественная литература', 340, 'Инженер Киселев');

-- --------------------------------------------------------

--
-- Структура таблицы `books_seq`
--

CREATE TABLE `books_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `books_seq`
--

INSERT INTO `books_seq` (`next_val`) VALUES
(251);

-- --------------------------------------------------------

--
-- Структура таблицы `cart`
--

CREATE TABLE `cart` (
  `id` bigint NOT NULL,
  `count` int NOT NULL,
  `price` int NOT NULL,
  `product_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `cart`
--

INSERT INTO `cart` (`id`, `count`, `price`, `product_id`) VALUES
(307, 3, 1200, 2),
(308, 1, 450, 52),
(310, 1, 3500, 54),
(402, 2, 690, 102),
(452, 2, 8000, 1),
(453, 2, 760, 53),
(454, 1, 340, 152);

-- --------------------------------------------------------

--
-- Структура таблицы `cart_seq`
--

CREATE TABLE `cart_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `cart_seq`
--

INSERT INTO `cart_seq` (`next_val`) VALUES
(551);

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `login` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `login`, `name`, `password`) VALUES
(2, 'mulder', 'Fox Mulder', '1345'),
(3, 'scully', 'Dana Scully', '13456'),
(104, 'admin', 'Василий Иванович', '134'),
(105, 'admin', 'Васька', '134'),
(106, 'admin', 'Васька Шустрый', '134');

-- --------------------------------------------------------

--
-- Структура таблицы `users_seq`
--

CREATE TABLE `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `users_seq`
--

INSERT INTO `users_seq` (`next_val`) VALUES
(201);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ey2t23ju6wbpsypqcd6rnm0go` (`product_id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `FK8ftfr18s5590sv1v1y2rxd9ky` FOREIGN KEY (`product_id`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
