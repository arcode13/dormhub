-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 01, 2025 at 05:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dormhub`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `user_id`) VALUES
(1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `helpdesk`
--

CREATE TABLE `helpdesk` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `helpdesk`
--

INSERT INTO `helpdesk` (`id`, `user_id`) VALUES
(1, 3),
(2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `jurusan`
--

CREATE TABLE `jurusan` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jurusan`
--

INSERT INTO `jurusan` (`id`, `nama`) VALUES
(1, 'Informatika'),
(2, 'Teknik Elektro'),
(3, 'Teknik Telekomunikasi'),
(4, 'Sistem Informasi'),
(5, 'Teknik Industri'),
(6, 'Teknik Komputer'),
(7, 'Desain Komunikasi Visual'),
(8, 'Manajemen Bisnis Telekomunikasi Informatika (MBTI)'),
(9, 'Teknik Informatika (International)'),
(10, 'Akuntansi'),
(11, 'Ilmu Komunikasi'),
(12, 'Administrasi Bisnis'),
(13, 'Teknik Fisika');

-- --------------------------------------------------------

--
-- Table structure for table `konfigurasi`
--

CREATE TABLE `konfigurasi` (
  `id` int(11) NOT NULL,
  `k_key` varchar(255) NOT NULL,
  `k_value` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konfigurasi`
--

INSERT INTO `konfigurasi` (`id`, `k_key`, `k_value`) VALUES
(1, 'web-favicon', 'favicon.ico'),
(2, 'web-nama-website', 'DormHub'),
(3, 'web-nama-gedung', 'A'),
(4, 'web-logo', 'logo.png'),
(5, 'web-lantai', '4'),
(6, 'web-kamar', '14'),
(7, 'web-kasur', '4'),
(8, 'web-mulai-tgl-co', '2024-12-19'),
(9, 'web-selesai-tgl-co', '2024-12-29'),
(10, 'web-footer', 'Copyright © 2024 DormHub. All Rights Reserved');

-- --------------------------------------------------------

--
-- Table structure for table `laporan_barang`
--

CREATE TABLE `laporan_barang` (
  `id` int(11) NOT NULL,
  `helpdesk_id` int(11) NOT NULL,
  `mahasiswa_id` int(11) NOT NULL,
  `jenis` varchar(10) NOT NULL,
  `bukti_foto` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `laporan_barang`
--

INSERT INTO `laporan_barang` (`id`, `helpdesk_id`, `mahasiswa_id`, `jenis`, `bukti_foto`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'Makanan', '1735510006225_DormHub.drawio.png', 'Sudah Diambil', '2024-12-30 05:06:46', '2024-12-30 05:06:46'),
(2, 1, 2, 'Makanan', '1735511210269_DormHub.drawio.png', 'menunggu', '2024-12-30 05:26:50', '2024-12-30 05:26:50'),
(3, 1, 1, 'Paket', '1735511295230_DormHub.drawio.png', 'Diterima', '2024-12-30 05:28:15', '2025-01-01 06:04:15');

-- --------------------------------------------------------

--
-- Table structure for table `laporan_umum`
--

CREATE TABLE `laporan_umum` (
  `id` int(11) NOT NULL,
  `mahasiswa_id` int(11) NOT NULL,
  `jenis` varchar(255) DEFAULT NULL,
  `alasan` varchar(255) NOT NULL,
  `bukti_foto` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `laporan_umum`
--

INSERT INTO `laporan_umum` (`id`, `mahasiswa_id`, `jenis`, `alasan`, `bukti_foto`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 'Izin', 'rgergeg', NULL, 'menunggu', '2025-01-01 03:28:56', '2025-01-01 22:23:34'),
(2, 1, 'Izin', 'qwqewqe', '1735682777114_DormHub.drawio.png', 'diterima', '2025-01-01 05:06:17', '2025-01-01 05:06:31');

-- --------------------------------------------------------

--
-- Table structure for table `level`
--

CREATE TABLE `level` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `level`
--

INSERT INTO `level` (`id`, `nama`) VALUES
(1, 'Mahasiswa'),
(2, 'Senior Residence'),
(3, 'Help Desk'),
(4, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `jurusan_id` int(11) NOT NULL,
  `no_kamar` int(11) NOT NULL,
  `no_kasur` int(11) NOT NULL,
  `is_checkin` int(1) NOT NULL,
  `is_checkout` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `user_id`, `jurusan_id`, `no_kamar`, `no_kasur`, `is_checkin`, `is_checkout`) VALUES
(1, 1, 1, 101, 1, 1, 0),
(2, 2, 2, 101, 2, 1, 0),
(3, 6, 12, 101, 3, 0, 0),
(4, 7, 12, 101, 4, 0, 0),
(5, 8, 12, 102, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `senior_residence`
--

CREATE TABLE `senior_residence` (
  `id` int(11) NOT NULL,
  `mahasiswa_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `senior_residence`
--

INSERT INTO `senior_residence` (`id`, `mahasiswa_id`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `foto_profil` varchar(255) DEFAULT NULL,
  `nomor_hp` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `level_id` int(11) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama_lengkap`, `email`, `password`, `foto_profil`, `nomor_hp`, `jenis_kelamin`, `level_id`, `token`, `created_at`, `updated_at`) VALUES
(1, 'Andi Nugraha', 'mahasiswa@dormhub.my.id', '$2a$10$DD.hS28XoSzjgrisO6RAceGdhPPWqEyug/7ZEVJBGtrOaumLydr1C', '1734978527748_DormHub.drawio.png', '08121234567', 'Laki-Laki', 1, '8262a610-dcaf-45f8-82d0-91800e155099', '2024-12-19 00:40:18', '2024-12-24 01:28:47'),
(2, 'Rina Ayu', 'seniorresidence@dormhub.my.id', '$2a$10$JxIYtJa8fzO9TTuh10IVz.OaJXQB7TeUI/QNo/oOKaFXUDYWJtnlW', NULL, '08121238976', 'Perempuan', 2, NULL, '2024-12-19 01:37:54', '2025-01-01 17:32:01'),
(3, 'Joko Santoso', 'helpdesk1@dormhub.my.id', '$2a$10$JxIYtJa8fzO9TTuh10IVz.OaJXQB7TeUI/QNo/oOKaFXUDYWJtnlW', '1735514127084_DormHub.drawio.png', '08121234578', 'Laki-Laki', 3, NULL, '2024-12-19 01:37:57', '2024-12-30 06:17:10'),
(4, 'Rudi Wijaya', 'helpdesk2@dormhub.my.id', '$2a$10$JxIYtJa8fzO9TTuh10IVz.OaJXQB7TeUI/QNo/oOKaFXUDYWJtnlW', 'default.png', '08127891234', 'Laki-Laki', 3, NULL, '2024-12-19 01:37:59', '2024-12-19 14:30:57'),
(5, 'Fajar Sidiq', 'admin@dormhub.my.id', '$2a$10$JxIYtJa8fzO9TTuh10IVz.OaJXQB7TeUI/QNo/oOKaFXUDYWJtnlW', 'default.png', '08123456789', 'Laki-Laki', 4, NULL, '2024-12-19 01:38:02', '2024-12-19 14:31:00'),
(6, 'rgegeg', 'mahasiswra@dormhub.my.id', '$2a$10$l/jrRZlxgnnWUG.t4XNsxuhSIeHaklK8mz85iJgcn4ELDRRYEIODO', 'default.png', '08567567655', 'Laki-Laki', 1, NULL, '2025-01-01 06:13:46', '2025-01-01 06:13:46'),
(7, 'erherh', 'mahasiswregregha@dormhub.my.id', '$2a$10$TuE4MfbO4yy3uQ3ebvmbD.fG2ZQ9yYcUvQdI4O4BNBTKu9xCvUIm2', 'default.png', '081212345633', 'Laki-Laki', 1, NULL, '2025-01-01 06:16:03', '2025-01-01 06:16:03'),
(8, 'regheh', 'erherhre@dormhub.my.id', '$2a$10$NgbhYFAdvvITtwO3GZCIX.LvxuWC9xn55EMkCV/rwAjkRLL4ITuXe', NULL, '0812123345435', 'Laki-Laki', 1, NULL, '2025-01-01 06:17:10', '2025-01-01 06:17:10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `helpdesk`
--
ALTER TABLE `helpdesk`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `jurusan`
--
ALTER TABLE `jurusan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `konfigurasi`
--
ALTER TABLE `konfigurasi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `laporan_barang`
--
ALTER TABLE `laporan_barang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `laporan_barang_ibfk_1` (`mahasiswa_id`),
  ADD KEY `helpdesk_id` (`helpdesk_id`);

--
-- Indexes for table `laporan_umum`
--
ALTER TABLE `laporan_umum`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mahasiswa_id` (`mahasiswa_id`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `jurusan_id` (`jurusan_id`);

--
-- Indexes for table `senior_residence`
--
ALTER TABLE `senior_residence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mahasiswa_id` (`mahasiswa_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`level_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `helpdesk`
--
ALTER TABLE `helpdesk`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `jurusan`
--
ALTER TABLE `jurusan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `konfigurasi`
--
ALTER TABLE `konfigurasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `laporan_barang`
--
ALTER TABLE `laporan_barang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `laporan_umum`
--
ALTER TABLE `laporan_umum`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `senior_residence`
--
ALTER TABLE `senior_residence`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `helpdesk`
--
ALTER TABLE `helpdesk`
  ADD CONSTRAINT `helpdesk_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `laporan_barang`
--
ALTER TABLE `laporan_barang`
  ADD CONSTRAINT `laporan_barang_ibfk_1` FOREIGN KEY (`mahasiswa_id`) REFERENCES `mahasiswa` (`id`),
  ADD CONSTRAINT `laporan_barang_ibfk_2` FOREIGN KEY (`helpdesk_id`) REFERENCES `helpdesk` (`id`);

--
-- Constraints for table `laporan_umum`
--
ALTER TABLE `laporan_umum`
  ADD CONSTRAINT `laporan_umum_ibfk_1` FOREIGN KEY (`mahasiswa_id`) REFERENCES `mahasiswa` (`id`);

--
-- Constraints for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD CONSTRAINT `FK5pqlohrl6plp7qhiug2a8hayg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKm9k60l7iarq8iqykalsoutt8g` FOREIGN KEY (`jurusan_id`) REFERENCES `jurusan` (`id`),
  ADD CONSTRAINT `mahasiswa_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `mahasiswa_ibfk_2` FOREIGN KEY (`jurusan_id`) REFERENCES `jurusan` (`id`);

--
-- Constraints for table `senior_residence`
--
ALTER TABLE `senior_residence`
  ADD CONSTRAINT `senior_residence_ibfk_1` FOREIGN KEY (`mahasiswa_id`) REFERENCES `mahasiswa` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK3rxjahel52808330txlcj7vy7` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`),
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
