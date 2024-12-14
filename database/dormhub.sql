-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2024 at 10:45 PM
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
(1, 1),
(2, 2),
(3, 3);

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
  `favicon` varchar(255) NOT NULL,
  `nama_website` varchar(255) DEFAULT NULL,
  `nama_gedung` varchar(255) DEFAULT NULL,
  `logo` varchar(255) NOT NULL,
  `lantai` int(11) NOT NULL,
  `kamar` int(11) NOT NULL,
  `kasur` int(11) NOT NULL,
  `footer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konfigurasi`
--

INSERT INTO `konfigurasi` (`id`, `favicon`, `nama_website`, `nama_gedung`, `logo`, `lantai`, `kamar`, `kasur`, `footer`) VALUES
(1, 'favicon.ico', 'DormHub', 'Gedung Maxwell', 'logo.png', 4, 14, 4, 'Copyright © 2024 DormHub. All Rights Reserved');

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
(1, 1, 1, 'paket', 'bukti_paket1.jpg', 'menunggu', '2024-12-15 04:43:33', '2024-12-15 04:43:37'),
(2, 2, 2, 'makanan', 'bukti_makanan1.jpg', 'diterima', '2024-12-15 04:43:39', '2024-12-15 04:43:46'),
(3, 3, 3, 'paket', 'bukti_paket2.jpg', 'menunggu', '2024-12-15 04:43:52', '2024-12-15 04:43:54'),
(4, 1, 4, 'makanan', 'bukti_makanan2.jpg', 'diterima', '2024-12-15 04:43:57', '2024-12-15 04:43:59'),
(5, 2, 5, 'paket', 'bukti_paket3.jpg', 'menunggu', '2024-12-15 04:44:02', '2024-12-15 04:44:04');

-- --------------------------------------------------------

--
-- Table structure for table `laporan_umum`
--

CREATE TABLE `laporan_umum` (
  `id` int(11) NOT NULL,
  `helpdesk_id` int(11) NOT NULL,
  `mahasiswa_id` int(11) NOT NULL,
  `jenis` varchar(255) DEFAULT NULL,
  `alasan` varchar(255) NOT NULL,
  `bukti_foto` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `laporan_umum`
--

INSERT INTO `laporan_umum` (`id`, `helpdesk_id`, `mahasiswa_id`, `jenis`, `alasan`, `bukti_foto`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 19, 'izin', 'Menghadiri seminar', 'izin1.jpg', 'menunggu', '2024-12-15 04:44:10', '2024-12-15 04:44:13'),
(2, 2, 19, 'keluhan', 'AC rusak', 'keluhan1.jpg', 'setuju', '2024-12-15 04:44:19', '2024-12-15 04:44:29'),
(3, 3, 19, 'izin', 'Kunjungan keluarga', 'izin2.jpg', 'tolak', '2024-12-15 04:44:32', '2024-12-15 04:44:35'),
(4, 1, 4, 'keluhan', 'Lampu mati', 'keluhan2.jpg', 'menunggu', '2024-12-15 04:44:38', '2024-12-15 04:44:40'),
(5, 2, 5, 'izin', 'Liburan keluarga', 'izin3.jpg', 'setuju', '2024-12-15 04:44:43', '2024-12-15 04:44:47');

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
  `no_kasur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `user_id`, `jurusan_id`, `no_kamar`, `no_kasur`) VALUES
(1, 9, 1, 101, 1),
(2, 10, 2, 102, 2),
(3, 11, 3, 103, 3),
(4, 12, 5, 104, 4),
(5, 13, 6, 105, 1),
(6, 14, 7, 106, 2),
(7, 15, 8, 107, 3),
(8, 16, 9, 108, 4),
(9, 17, 10, 109, 1),
(10, 18, 11, 110, 2),
(11, 19, 4, 111, 1),
(12, 20, 12, 112, 2),
(13, 21, 13, 113, 3),
(14, 22, 13, 114, 4),
(15, 43, 12, 114, 2),
(16, 44, 10, 114, 3),
(17, 45, 9, 114, 4),
(18, 46, 9, 201, 1),
(19, 47, 1, 201, 2);

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
(1, 11),
(2, 12),
(3, 13),
(4, 14);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `foto_profil` varchar(255) NOT NULL,
  `nomor_hp` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `level_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama_lengkap`, `email`, `password`, `foto_profil`, `nomor_hp`, `jenis_kelamin`, `level_id`, `created_at`, `updated_at`) VALUES
(1, 'Andi Nugraha', 'andi.nugraha@dormhub.web.id', '$2a$10$BEsiccIubK6HFPLEHSe30eojugmpnf0ZoBKDxaaU9tWMpdNe28Q1S', 'default.png', '08121234567', 'Laki-Laki', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, 'Rina Ayu', 'rina.ayu@dormhub.web.id', 'password123', 'default.png', '08121238976', 'Perempuan', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(3, 'Joko Santoso', 'joko.santoso@dormhub.web.id', 'password123', 'default.png', '08121234578', 'Laki-Laki', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(4, 'Rudi Wijaya', 'rudi.wijaya@dormhub.web.id', 'password123', 'default.png', '08127891234', 'Laki-Laki', 4, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(5, 'Fajar Sidiq', 'fajar.sidiq@dormhub.web.id', 'password123', 'default.png', '08123456789', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(6, 'Gita Maharani', 'gita.maharani@dormhub.web.id', 'password123', 'default.png', '08123412345', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(7, 'Rangga Saputra', 'rangga.saputra@dormhub.web.id', 'password123', 'default.png', '08129876432', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(8, 'Sari Anggraini', 'sari.anggraini@dormhub.web.id', 'password123', 'default.png', '08123456721', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(9, 'Ani Kartika', 'ani.kartika@dormhub.web.id', 'password123', 'default.png', '08123456788', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(10, 'Budi Santoso', 'budi.santoso@dormhub.web.id', 'password123', 'default.png', '08123456789', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(11, 'Citra Laras', 'citra.laras@dormhub.web.id', 'password123', 'default.png', '08123456787', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(12, 'Doni Pratama', 'doni.pratama@dormhub.web.id', 'password123', 'default.png', '08123456786', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(13, 'Eka Wulandari', 'eka.wulandari@dormhub.web.id', 'password123', 'default.png', '08123456785', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(14, 'Faisal Hakim', 'faisal.hakim@dormhub.web.id', 'password123', 'default.png', '08123456784', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(15, 'Gina Melati', 'gina.melati@dormhub.web.id', 'password123', 'default.png', '08123456783', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(16, 'Hari Wijaya', 'hari.wijaya@dormhub.web.id', 'password123', 'default.png', '08123456782', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(17, 'Ika Rahma', 'ika.rahma@dormhub.web.id', 'password123', 'default.png', '08123456781', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(18, 'Joko Susanto', 'joko.susanto@dormhub.web.id', 'password123', 'default.png', '08123456780', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(19, 'Andi Nugraha', 'andi.nugraha@dormhub.web.id', 'password123', 'default.png', '08121234567', 'Laki-Laki', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(20, 'Rina Ayu', 'rina.ayu@dormhub.web.id', 'password123', 'default.png', '08121238976', 'Perempuan', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(21, 'Joko Santoso', 'joko.santoso@dormhub.web.id', 'password123', 'default.png', '08121234578', 'Laki-Laki', 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(22, 'Rudi Wijaya', 'rudi.wijaya@dormhub.web.id', 'password123', 'default.png', '08127891234', 'Laki-Laki', 4, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(23, 'Fajar Sidiq', 'fajar.sidiq@dormhub.web.id', 'password123', 'default.png', '08123456789', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(24, 'Gita Maharani', 'gita.maharani@dormhub.web.id', 'password123', 'default.png', '08123412345', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(25, 'Rangga Saputra', 'rangga.saputra@dormhub.web.id', 'password123', 'default.png', '08129876432', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(26, 'Sari Anggraini', 'sari.anggraini@dormhub.web.id', 'password123', 'default.png', '08123456721', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(27, 'Ani Kartika', 'ani.kartika@dormhub.web.id', 'password123', 'default.png', '08123456788', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(28, 'Budi Santoso', 'budi.santoso@dormhub.web.id', 'password123', 'default.png', '08123456789', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(29, 'Citra Laras', 'citra.laras@dormhub.web.id', 'password123', 'default.png', '08123456787', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(30, 'Doni Pratama', 'doni.pratama@dormhub.web.id', 'password123', 'default.png', '08123456786', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(31, 'Eka Wulandari', 'eka.wulandari@dormhub.web.id', 'password123', 'default.png', '08123456785', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(32, 'Faisal Hakim', 'faisal.hakim@dormhub.web.id', 'password123', 'default.png', '08123456784', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(33, 'Gina Melati', 'gina.melati@dormhub.web.id', 'password123', 'default.png', '08123456783', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(34, 'Hari Wijaya', 'hari.wijaya@dormhub.web.id', 'password123', 'default.png', '08123456782', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(35, 'Ika Rahma', 'ika.rahma@dormhub.web.id', 'password123', 'default.png', '08123456781', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(36, 'Joko Susanto', 'joko.susanto@dormhub.web.id', 'password123', 'default.png', '08123456780', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(37, 'Nurul Huda', 'nurul.huda@dormhub.web.id', 'password123', 'default.png', '08123456712', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(38, 'Ahmad Fauzi', 'ahmad.fauzi@dormhub.web.id', 'password123', 'default.png', '08123456713', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(39, 'Dewi Sartika', 'dewi.sartika@dormhub.web.id', 'password123', 'default.png', '08123456714', 'Perempuan', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(40, 'Bagus Rahmat', 'bagus.rahmat@dormhub.web.id', 'password123', 'default.png', '08123456715', 'Laki-Laki', 2, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(41, 'wefwef', 'msyahrulma@gmail.com', '$2a$10$lozm80m0B1i3ZuXigd31ROtbAGVy7z/UVJc8N/xf2NbHGujSHCbZu', 'default.png', '567567', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(42, 'df df', 'tester@gmail.com', '$2a$10$Lo1twMs9tPpbjiyJXKHZ4OA3mhRWVZp50nLMjHX1y3aFCfbNEcf7W', 'default.png', '5453535', 'Laki-Laki', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(43, 'trgtrghrtb', 'admin@gmail.com', '$2a$10$Zq64SaL0mIlFJaynZd8jx.WORvSZZ8Z0eznZlJecXS51rIRR4cRbG', 'default.png', '55454', 'Perempuan', 1, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(44, 'cedsfefefe', 'kontits@gmail.com', '$2a$10$AuDA3..xJG/FFn33VUQM.eA4w7lrrFCk3p3EkRxCrJBLO7r96YXdi', 'default.png', '214325454354', 'Laki-Laki', 1, '2024-12-04 14:00:51', '2024-12-04 14:00:51'),
(45, 'wefwef', 'msy4554ahrulma@gmail.com', '$2a$10$uhFEUvh4lV/F15xZWYRqOu0eTqQDBe4rggxY5RycTxcePjdB0qRdq', 'default.png', '344334', 'Laki-Laki', 1, '2024-12-04 14:02:57', '2024-12-04 14:02:57'),
(46, 'wefwef', 'admin123@gmail.com', '$2a$10$BEsiccIubK6HFPLEHSe30eojugmpnf0ZoBKDxaaU9tWMpdNe28Q1S', 'default.png', '08874568743', 'Laki-Laki', 1, '2024-12-05 00:16:25', '2024-12-05 00:16:25'),
(47, 'tester123', 'tester123@gmail.com', '$2a$10$JxIYtJa8fzO9TTuh10IVz.OaJXQB7TeUI/QNo/oOKaFXUDYWJtnlW', 'default.png', '087477575675', 'Laki-Laki', 1, '2024-12-15 03:43:31', '2024-12-15 03:43:31');

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
  ADD KEY `mahasiswa_id` (`mahasiswa_id`),
  ADD KEY `helpdesk_id` (`helpdesk_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `jurusan`
--
ALTER TABLE `jurusan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `konfigurasi`
--
ALTER TABLE `konfigurasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `laporan_barang`
--
ALTER TABLE `laporan_barang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `laporan_umum`
--
ALTER TABLE `laporan_umum`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `senior_residence`
--
ALTER TABLE `senior_residence`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

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
  ADD CONSTRAINT `laporan_umum_ibfk_1` FOREIGN KEY (`mahasiswa_id`) REFERENCES `mahasiswa` (`id`),
  ADD CONSTRAINT `laporan_umum_ibfk_2` FOREIGN KEY (`helpdesk_id`) REFERENCES `helpdesk` (`id`);

--
-- Constraints for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
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
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
