-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-05-2024 a las 09:17:53
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pakag`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bezeroa`
--

CREATE TABLE `bezeroa` (
  `bezeroa_nan` char(9) NOT NULL,
  `bezeroa_izena` varchar(45) NOT NULL,
  `bezeroa_abizena` varchar(45) NOT NULL,
  `bezeroa_telefonoa` char(9) NOT NULL,
  `bezeroa_helbidea` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `bezeroa`
--

INSERT INTO `bezeroa` (`bezeroa_nan`, `bezeroa_izena`, `bezeroa_abizena`, `bezeroa_telefonoa`, `bezeroa_helbidea`) VALUES
('12345678S', 'Ander', 'Salamanca', '666666666', 'Durango, Andra Mari Kalea, 4'),
('12345678Y', 'Jurgi', 'Leunda', '666554433', 'Oñati, San Juan Kalea, 9'),
('43214321A', 'Andoni', 'Azpirotz', '603603603', 'Azpeitia, Enparan Kalea, 11'),
('43219876E', 'Ester', 'Tembleque', '605324321', 'Arrasate, Ferrerias Kalea, 8'),
('65719876M', 'Maria', 'Cabezon', '789076421', 'Galdakao, Aperribai Kalea, 10'),
('87654321J', 'Jokin', 'Peti', '666888666', 'Tolosa, Euskal Herria Kalea, 3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historiala`
--

CREATE TABLE `historiala` (
  `ID` int(11) NOT NULL,
  `langilea_nan` char(9) NOT NULL,
  `bezeroa_nan` char(9) NOT NULL,
  `entrega_data` date NOT NULL,
  `oharra` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `historiala`
--

INSERT INTO `historiala` (`ID`, `langilea_nan`, `bezeroa_nan`, `entrega_data`, `oharra`) VALUES
(44, '87654321S', '43214321A', '2024-05-28', 'Paketea bizilagunari eman diogu'),
(50, '87654321S', '43219876E', '2024-05-28', ''),
(51, '87654321S', '43214321A', '2024-05-28', 'Ondo entregatu da.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `langilea`
--

CREATE TABLE `langilea` (
  `langilea_nan` char(9) NOT NULL,
  `langilea_izena` varchar(45) NOT NULL,
  `langilea_abizena` varchar(45) NOT NULL,
  `langilea_telefonoa` varchar(45) NOT NULL,
  `erabiltzailea` varchar(45) NOT NULL,
  `pasahitza` varchar(45) NOT NULL,
  `mota` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `langilea`
--

INSERT INTO `langilea` (`langilea_nan`, `langilea_izena`, `langilea_abizena`, `langilea_telefonoa`, `erabiltzailea`, `pasahitza`, `mota`) VALUES
('22334455K', 'Jurgi', 'Leunda', '678901234', 'jurgileunda', 'pvlbtnse', 'kudeatzailea'),
('56745678T', 'Hodei', 'Urresti', '644514268', 'hodeiurresti', 'pvlbtnse', 'langilea'),
('87654321S', 'Julen', 'Garcia', '688675645', 'julengarcia', 'pvlbtnse', 'langilea');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paketea`
--

CREATE TABLE `paketea` (
  `ID` int(11) NOT NULL,
  `entrega_data` date NOT NULL,
  `langilea_nan` char(9) NOT NULL,
  `bezeroa_nan` char(9) NOT NULL,
  `zein_entregatu` tinyint(1) NOT NULL DEFAULT 0,
  `entregatuta` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `paketea`
--

INSERT INTO `paketea` (`ID`, `entrega_data`, `langilea_nan`, `bezeroa_nan`, `zein_entregatu`, `entregatuta`) VALUES
(41, '2024-06-03', '87654321S', '43219876E', 0, 0),
(42, '2024-06-13', '87654321S', '65719876M', 0, 0),
(43, '2024-06-02', '87654321S', '87654321J', 0, 0),
(45, '2024-05-30', '87654321S', '12345678Y', 0, 0),
(46, '2024-06-04', '87654321S', '12345678S', 0, 0),
(47, '2024-05-30', '87654321S', '87654321J', 0, 0),
(48, '2024-06-03', '87654321S', '65719876M', 0, 0),
(49, '2024-05-31', '87654321S', '43214321A', 0, 0);

--
-- Disparadores `paketea`
--
DELIMITER $$
CREATE TRIGGER `paketea_entregatu` AFTER UPDATE ON `paketea` FOR EACH ROW BEGIN
    IF OLD.entregatuta = 0 AND NEW.entregatuta = 1 THEN
        INSERT INTO historiala (ID, langilea_nan, bezeroa_nan, entrega_data)
        VALUES (OLD.ID, OLD.langilea_nan, OLD.bezeroa_nan, CURDATE());
    END IF;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bezeroa`
--
ALTER TABLE `bezeroa`
  ADD PRIMARY KEY (`bezeroa_nan`);

--
-- Indices de la tabla `historiala`
--
ALTER TABLE `historiala`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `nan_langilea` (`langilea_nan`),
  ADD KEY `bezeroa_nan` (`bezeroa_nan`);

--
-- Indices de la tabla `langilea`
--
ALTER TABLE `langilea`
  ADD PRIMARY KEY (`langilea_nan`);

--
-- Indices de la tabla `paketea`
--
ALTER TABLE `paketea`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `nan_langilea` (`langilea_nan`),
  ADD KEY `bezeroa_nan` (`bezeroa_nan`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `paketea`
--
ALTER TABLE `paketea`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `historiala`
--
ALTER TABLE `historiala`
  ADD CONSTRAINT `historiala_ibfk_1` FOREIGN KEY (`langilea_nan`) REFERENCES `langilea` (`langilea_nan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `historiala_ibfk_2` FOREIGN KEY (`bezeroa_nan`) REFERENCES `bezeroa` (`bezeroa_nan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `paketea`
--
ALTER TABLE `paketea`
  ADD CONSTRAINT `paketea_ibfk_1` FOREIGN KEY (`langilea_nan`) REFERENCES `langilea` (`langilea_nan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `paketea_ibfk_2` FOREIGN KEY (`bezeroa_nan`) REFERENCES `bezeroa` (`bezeroa_nan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
