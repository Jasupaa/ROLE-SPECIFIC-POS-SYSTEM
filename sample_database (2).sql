-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 13, 2023 at 05:11 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sample_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `coffee`
--

CREATE TABLE `coffee` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(255) NOT NULL,
  `sugar_level` int(11) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `ID` int(11) NOT NULL,
  `Name` varchar(225) NOT NULL,
  `Contact` varchar(225) NOT NULL,
  `Role` varchar(225) NOT NULL,
  `Code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`ID`, `Name`, `Contact`, `Role`, `Code`) VALUES
(2, 'Jasper', '09', 'cashier', 123456),
(3, 'Jas', '09', 'kitchen', 456789),
(4, 'Per', '09', 'admin', 789123);

-- --------------------------------------------------------

--
-- Table structure for table `extras`
--

CREATE TABLE `extras` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `frappe`
--

CREATE TABLE `frappe` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(255) NOT NULL,
  `sugar_level` varchar(255) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `size_price` int(11) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `frappe`
--

INSERT INTO `frappe` (`order_id`, `customer_id`, `date_time`, `order_type`, `item_name`, `quantity`, `size`, `sugar_level`, `ask_me`, `size_price`, `final_price`) VALUES
(1, 0, '2023-11-09 23:13:19', NULL, 'HazelNut Almonds', 2, 'Small', 'Low', 1, 39, 78),
(2, 0, '2023-11-09 23:28:51', NULL, 'HazelNut Almonds', 2, 'Small', 'Low', 1, 39, 78),
(3, 0, '2023-11-12 08:34:07', NULL, 'Oreo', 2, 'Medium', 'Medium', 1, 69, 138);

-- --------------------------------------------------------

--
-- Table structure for table `fruit_drink`
--

CREATE TABLE `fruit_drink` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `date_time` datetime DEFAULT NULL,
  `order_type` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(255) NOT NULL,
  `fruit_flavor` varchar(255) NOT NULL,
  `sinkers` varchar(255) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `size_price` int(11) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fruit_drink`
--

INSERT INTO `fruit_drink` (`order_id`, `customer_id`, `item_name`, `date_time`, `order_type`, `quantity`, `size`, `fruit_flavor`, `sinkers`, `ask_me`, `size_price`, `final_price`) VALUES
(46, 0, 'Fruit Tea', NULL, NULL, 6, 'Small', 'Strawberry', 'Fruit Jelly', 1, 39, 234),
(47, 0, 'Quadraple Triple', NULL, NULL, 3, 'Medium', 'Lychee', 'Nata De Coco', 1, 69, 207),
(48, 3, 'Fruit Tea', NULL, NULL, 3, 'Medium', 'Lychee', 'Nata De Coco', 1, 69, 207),
(49, 4, 'Fruit Tea', NULL, NULL, 5, 'Small', 'Lychee', 'Nata De Coco', 1, 39, 195);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `order_id` int(11) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  `EmpID` int(11) DEFAULT NULL,
  `EmpName` varchar(255) DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Cash` double DEFAULT NULL,
  `Change_` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `milk_tea`
--

CREATE TABLE `milk_tea` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(255) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` varchar(255) NOT NULL,
  `add_ons` varchar(255) NOT NULL,
  `sugar_level` varchar(255) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `size_price` int(11) DEFAULT NULL,
  `addons_price` int(11) DEFAULT NULL,
  `final_price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `milk_tea`
--

INSERT INTO `milk_tea` (`order_id`, `customer_id`, `date_time`, `order_type`, `item_name`, `quantity`, `size`, `add_ons`, `sugar_level`, `ask_me`, `size_price`, `addons_price`, `final_price`) VALUES
(43, 0, '2023-11-13 23:22:20', NULL, 'Classic Milktea', 5, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 295),
(44, 0, '2023-11-13 23:22:32', NULL, 'Classic Milktea', 4, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 236),
(45, 0, '2023-11-13 23:26:15', NULL, 'Classic Milktea', 4, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 236),
(46, 0, '2023-11-13 23:26:24', NULL, 'Classic Milktea', 5, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 295),
(47, 0, '2023-11-13 23:28:17', NULL, 'Classic Milktea', 4, 'Medium', 'Cream Cheese', 'Medium', 1, 69, 20, 356),
(48, 0, '2023-11-13 23:31:38', NULL, 'Classic Milktea', 2, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 118),
(49, 0, '2023-11-13 23:34:07', NULL, 'Classic Milktea', 3, 'Small', 'Pearl', 'Medium', 1, 39, 0, 117),
(50, 0, '2023-11-13 23:38:26', NULL, 'Classic Milktea', 4, 'Small', 'Pearl', 'Low', 1, 39, 0, 156),
(51, 0, '2023-11-13 23:42:39', NULL, 'Classic Milktea', 4, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 236),
(52, 1, '2023-11-13 23:51:38', NULL, 'Classic Milktea', 3, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 177),
(53, 1, '2023-11-13 23:52:25', NULL, 'Taro Milktea', 3, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 177),
(54, 2, '2023-11-13 23:52:43', NULL, 'Taro Milktea', 4, 'Small', 'Pearl', 'Low', 1, 39, 0, 156);

-- --------------------------------------------------------

--
-- Table structure for table `products_stock`
--

CREATE TABLE `products_stock` (
  `PID` int(11) NOT NULL,
  `Name` varchar(225) NOT NULL,
  `Category` varchar(225) NOT NULL,
  `Quantity` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rice_meal`
--

CREATE TABLE `rice_meal` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `snacks`
--

CREATE TABLE `snacks` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `order_type` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `ask_me` tinyint(1) NOT NULL,
  `final_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `frappe`
--
ALTER TABLE `frappe`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `fruit_drink`
--
ALTER TABLE `fruit_drink`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`customer_id`),
  ADD KEY `EmpID` (`EmpID`);

--
-- Indexes for table `milk_tea`
--
ALTER TABLE `milk_tea`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `products_stock`
--
ALTER TABLE `products_stock`
  ADD PRIMARY KEY (`PID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `frappe`
--
ALTER TABLE `frappe`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `fruit_drink`
--
ALTER TABLE `fruit_drink`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `milk_tea`
--
ALTER TABLE `milk_tea`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `products_stock`
--
ALTER TABLE `products_stock`
  MODIFY `PID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`EmpID`) REFERENCES `employees` (`ID`),
  ADD CONSTRAINT `invoice_ibfk_2` FOREIGN KEY (`EmpID`) REFERENCES `employees` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
