-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 01, 2023 at 02:09 PM
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
-- Table structure for table `milk_tea`
--

CREATE TABLE `milk_tea` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
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

INSERT INTO `milk_tea` (`order_id`, `customer_id`, `item_name`, `quantity`, `size`, `add_ons`, `sugar_level`, `ask_me`, `size_price`, `addons_price`, `final_price`) VALUES
(1, 0, 'Classic Milktea', 2, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 118),
(2, 0, 'Classic Milktea', 2, 'Small', 'Cream Cheese', 'Low', 1, 39, 20, 118);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `milk_tea`
--
ALTER TABLE `milk_tea`
  ADD PRIMARY KEY (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `milk_tea`
--
ALTER TABLE `milk_tea`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
