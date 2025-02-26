-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Temps de generació: 26-02-2025 a les 08:54:13
-- Versió del servidor: 10.4.32-MariaDB
-- Versió de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `agency_db`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `flight`
--

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `flight_name` varchar(255) DEFAULT NULL,
  `city_from` varchar(255) DEFAULT NULL,
  `city_destination` varchar(255) DEFAULT NULL,
  `type_of_seat` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `date_from` date DEFAULT NULL,
  `booked` bit(1) NOT NULL,
  `available` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `flight`
--

INSERT INTO `flight` (`id`, `code`, `flight_name`, `city_from`, `city_destination`, `type_of_seat`, `price`, `date_from`, `booked`, `available`) VALUES
(1, 'BAMI-1235', 'Iberia', 'Barcelona', 'Miami', 'Economy', 650, '2025-02-28', b'0', b'1'),
(2, 'MIMA1420', 'Iberia', 'Miami', 'Madrid', 'Business\r\n', 2400, '2025-03-04', b'0', b'1'),
(3, NULL, 'Iberia', 'Bogota', 'Buenos Aires', 'Economy', 300, '2025-03-14', b'0', b'1'),
(4, 'BOIG-6567', 'Iberia', 'Bogota', 'Buenos Aires', 'Economy', 300, '2025-03-14', b'0', b'1'),
(5, 'BOIG-6568', 'Iberia', 'Buenos Aires', 'Bogota', 'Economy', 300, '2025-03-18', b'0', b'1'),
(6, 'string', 'string', 'string', 'string', 'bla', 0, '2025-03-20', b'0', b'1'),
(7, 'MAVA-5983', 'Iberia', 'Madrid', 'Valencia', 'Economy', 100, '2025-03-26', b'1', b'1'),
(8, 'VAMA-5111', 'Iberia', 'Valencia', 'Madrid', 'Economy', 100, '2025-03-20', b'0', b'1'),
(9, 'SEVA-2639', 'Iberia', 'Sevilla', 'Valencia', 'Business', 200, '2025-03-28', b'0', b'1');

-- --------------------------------------------------------

--
-- Estructura de la taula `flight_booking`
--

CREATE TABLE `flight_booking` (
  `id` bigint(20) NOT NULL,
  `flight_date` date DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `flight_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `flight_date`, `seat_type`, `flight_id`) VALUES
(2, '2025-03-18', 'Economy', 5),
(3, '2025-03-26', 'Economy', 7);

-- --------------------------------------------------------

--
-- Estructura de la taula `flight_booking_passengers`
--

CREATE TABLE `flight_booking_passengers` (
  `flight_booking_id` bigint(20) NOT NULL,
  `passengers_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de la taula `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `type_room` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `booked` bit(1) NOT NULL,
  `available` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `hotel`
--

INSERT INTO `hotel` (`id`, `code`, `hotel_name`, `city`, `type_room`, `price`, `date_from`, `date_to`, `booked`, `available`) VALUES
(1, 'AR-0001', 'Atlantis Resort', 'Miami', 'Single', 600, '2025-02-28', '2025-03-01', b'1', b'1'),
(2, 'AR-0003', 'Atlantis Resort', 'Miami', 'Triple', 800, '2025-02-28', '2025-03-03', b'1', b'1'),
(3, 'RC-0001', 'Ritz-Carlton', 'Buenos Aires', 'Single', 500, '2025-03-02', '2025-03-03', b'0', b'1'),
(4, 'RC-0002', 'Ritz-Carlton 2', 'Medellin', 'Doble', 720, '2025-04-05', '2025-04-06', b'0', b'1'),
(5, 'RC-0003', 'Grand Hyatt', 'Madrid', 'Doble', 579, '2025-03-02', '2025-04-02', b'0', b'1'),
(6, 'string', 'Grand Hyatt 2', 'Buenos Aires', 'Single', 390, '2025-04-03', '2025-04-23', b'0', b'1'),
(7, 'HL-0001', 'Hilton', 'Barcelona', 'Single', 390, '2025-03-30', '2025-04-01', b'0', b'1'),
(8, 'HL-0002', 'Hilton', 'Barcelona', 'Doble', 400, '2025-03-30', '2025-04-07', b'0', b'0');

-- --------------------------------------------------------

--
-- Estructura de la taula `hotel_booking`
--

CREATE TABLE `hotel_booking` (
  `id` bigint(20) NOT NULL,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `number_of_nights` int(11) DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `hotel_booking`
--

INSERT INTO `hotel_booking` (`id`, `check_in_date`, `check_out_date`, `number_of_nights`, `hotel_id`) VALUES
(22, '2025-03-02', '2025-03-03', 1, 3),
(23, '2025-04-05', '2025-04-06', 1, 4),
(24, '2025-04-05', '2025-04-06', 1, 4),
(25, '2025-04-05', '2025-04-06', 1, 4),
(26, '2025-01-28', '2025-03-01', 32, 1),
(27, '2025-03-01', '2025-03-02', 1, 2);

-- --------------------------------------------------------

--
-- Estructura de la taula `hotel_booking_hosts`
--

CREATE TABLE `hotel_booking_hosts` (
  `hotel_booking_id` bigint(20) NOT NULL,
  `hosts_id` bigint(20) NOT NULL,
  `bookings_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de la taula `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `complete_name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `reserve_id` bigint(20) DEFAULT NULL,
  `hotel_booking_id` bigint(20) DEFAULT NULL,
  `flight_booking_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Bolcament de dades per a la taula `user`
--

INSERT INTO `user` (`id`, `complete_name`, `contact`, `booking_id`, `reserve_id`, `hotel_booking_id`, `flight_booking_id`) VALUES
(1, 'Maite Albenca', '785940383', 22, NULL, NULL, NULL),
(2, 'Carol Rodriguez', '987654321', 23, NULL, NULL, NULL),
(3, 'MArc Rodriguez', '987654321', 23, NULL, NULL, NULL),
(4, 'Carol Rodriguez', '987654321', 24, NULL, NULL, NULL),
(5, 'MArc Rodriguez', '987654321', 24, NULL, NULL, NULL),
(6, 'Carol Rodriguez', '987654321', 25, NULL, NULL, NULL),
(7, 'Marc Rodriguez', '987654321', 25, NULL, NULL, NULL),
(8, 'Lucia Blanco', '90876543', 26, NULL, NULL, NULL),
(9, 'Lucia Blanco', '90876543', 27, NULL, NULL, NULL),
(10, 'Maite Albenca', '688997733', NULL, 2, NULL, NULL),
(11, 'Maite Albenca', '688997733', NULL, 3, NULL, NULL);

--
-- Índexs per a les taules bolcades
--

--
-- Índexs per a la taula `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`);

--
-- Índexs per a la taula `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3uiklmjy1d7ba6rrjp6iq08kq` (`flight_id`);

--
-- Índexs per a la taula `flight_booking_passengers`
--
ALTER TABLE `flight_booking_passengers`
  ADD KEY `FKc0ubsbo02bgc7fi69aylr1r5q` (`passengers_id`),
  ADD KEY `FKpr0o7okgjaxlk8f64nx31n1vc` (`flight_booking_id`);

--
-- Índexs per a la taula `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Índexs per a la taula `hotel_booking`
--
ALTER TABLE `hotel_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjlo84i0jvx7jw2ot8dkj84xtd` (`hotel_id`);

--
-- Índexs per a la taula `hotel_booking_hosts`
--
ALTER TABLE `hotel_booking_hosts`
  ADD KEY `FKdti3rubhvx03rvhulms253m5g` (`hosts_id`),
  ADD KEY `FKkchhs7expttdahtmhbi50ahja` (`hotel_booking_id`),
  ADD KEY `FK4nyogkg6b7n9fro4mt5jyuoqo` (`bookings_id`);

--
-- Índexs per a la taula `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtqpwlnvafistfh48savsrdbtf` (`booking_id`),
  ADD KEY `FKkschdd1m7wn6jjxlm51d7u4r1` (`reserve_id`),
  ADD KEY `FKcmp44dq5e0kj0al257uwv2v21` (`hotel_booking_id`),
  ADD KEY `FKrdbh7b54x52ur5dn0a25hobhe` (`flight_booking_id`);

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `flight`
--
ALTER TABLE `flight`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la taula `flight_booking`
--
ALTER TABLE `flight_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la taula `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la taula `hotel_booking`
--
ALTER TABLE `hotel_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT per la taula `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restriccions per a les taules bolcades
--

--
-- Restriccions per a la taula `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK3uiklmjy1d7ba6rrjp6iq08kq` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`);

--
-- Restriccions per a la taula `flight_booking_passengers`
--
ALTER TABLE `flight_booking_passengers`
  ADD CONSTRAINT `FKc0ubsbo02bgc7fi69aylr1r5q` FOREIGN KEY (`passengers_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKpr0o7okgjaxlk8f64nx31n1vc` FOREIGN KEY (`flight_booking_id`) REFERENCES `flight_booking` (`id`);

--
-- Restriccions per a la taula `hotel_booking`
--
ALTER TABLE `hotel_booking`
  ADD CONSTRAINT `FKjlo84i0jvx7jw2ot8dkj84xtd` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Restriccions per a la taula `hotel_booking_hosts`
--
ALTER TABLE `hotel_booking_hosts`
  ADD CONSTRAINT `FK4nyogkg6b7n9fro4mt5jyuoqo` FOREIGN KEY (`bookings_id`) REFERENCES `hotel_booking` (`id`),
  ADD CONSTRAINT `FKdti3rubhvx03rvhulms253m5g` FOREIGN KEY (`hosts_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKkchhs7expttdahtmhbi50ahja` FOREIGN KEY (`hotel_booking_id`) REFERENCES `hotel_booking` (`id`);

--
-- Restriccions per a la taula `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKcmp44dq5e0kj0al257uwv2v21` FOREIGN KEY (`hotel_booking_id`) REFERENCES `hotel_booking` (`id`),
  ADD CONSTRAINT `FKkschdd1m7wn6jjxlm51d7u4r1` FOREIGN KEY (`reserve_id`) REFERENCES `flight_booking` (`id`),
  ADD CONSTRAINT `FKrdbh7b54x52ur5dn0a25hobhe` FOREIGN KEY (`flight_booking_id`) REFERENCES `flight_booking` (`id`),
  ADD CONSTRAINT `FKtqpwlnvafistfh48savsrdbtf` FOREIGN KEY (`booking_id`) REFERENCES `hotel_booking` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
