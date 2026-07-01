/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.8.6-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: u637179302_ojol
-- ------------------------------------------------------
-- Server version	11.8.6-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` varchar(500) NOT NULL,
  `level` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES
(1,'admin','Administrator','baea67c9f6af459d89642d4538d49bdf489bc42e','admin@gmail.com','1174bc8131f768603e1ba2aebb67a828.png',1,1),
(6,'Ekapay','Admin','40bd001563085fc35165329ea1ff5c5ecbdbbeef','Ekapay@gmail.com','9af37654c4b01dc0ff974f913fe41a24.jpg',8,1),
(7,'demo','demo','40bd001563085fc35165329ea1ff5c5ecbdbbeef','demo@gmail.com','b599cbec7473dd4e10e7e380e0f3dffd.PNG',1,1),
(8,'Chandra86','Chendra','cff1744b6809cbf10bef19c7e805a9a15965567b','admin@pertapa.sch.id','575edda702c8e0da241846f43cc20517.jpg',1,1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `admin_level`
--

DROP TABLE IF EXISTS `admin_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(30) NOT NULL,
  `akses` varchar(1) NOT NULL,
  `menu` text NOT NULL,
  `keterangan` text NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci AVG_ROW_LENGTH=99;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_level`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `admin_level` DISABLE KEYS */;
INSERT INTO `admin_level` VALUES
(1,'Super Administrator','','','Level user all akses',1),
(6,'administrator','','','admin pusat',NULL),
(7,'Admin Driver','','','Konfirmasi',NULL),
(8,'Ekapay','','','Admin',NULL),
(9,'siapa','','','daftar',NULL),
(10,'demo','','','beberapa menu di hide',NULL);
/*!40000 ALTER TABLE `admin_level` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `app_settings`
--

DROP TABLE IF EXISTS `app_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_email` varchar(500) NOT NULL,
  `app_contact` varchar(500) NOT NULL,
  `app_website` varchar(500) NOT NULL,
  `app_description` text NOT NULL,
  `app_privacy_policy` text NOT NULL,
  `app_aboutus` text NOT NULL,
  `email_subject` varchar(500) NOT NULL,
  `email_subject_confirm` varchar(500) NOT NULL,
  `email_text1` text NOT NULL,
  `email_text2` text NOT NULL,
  `email_text3` text NOT NULL,
  `email_text4` text NOT NULL,
  `app_logo` varchar(500) NOT NULL,
  `smtp_host` varchar(500) NOT NULL,
  `smtp_port` varchar(500) NOT NULL,
  `smtp_username` varchar(500) NOT NULL,
  `smtp_password` varchar(500) NOT NULL,
  `smtp_from` varchar(500) NOT NULL,
  `smtp_secure` varchar(250) NOT NULL,
  `app_name` varchar(500) NOT NULL,
  `app_address` text NOT NULL,
  `app_linkgoogle` varchar(500) NOT NULL,
  `app_currency` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `app_currency_text` varchar(10) NOT NULL,
  `map_key` varchar(250) NOT NULL,
  `fcm_key` varchar(250) NOT NULL,
  `stripe_secret_key` varchar(500) NOT NULL,
  `stripe_published_key` varchar(500) NOT NULL,
  `stripe_status` varchar(5) NOT NULL,
  `stripe_active` varchar(20) NOT NULL,
  `paypal_key` varchar(500) NOT NULL,
  `paypal_mode` varchar(20) NOT NULL,
  `paypal_active` varchar(20) NOT NULL,
  `midtrans_url` varchar(250) NOT NULL,
  `midtrans_key` varchar(250) NOT NULL,
  `midtrans_aktif` varchar(250) NOT NULL DEFAULT '1',
  `mobilepulsa_url` varchar(250) NOT NULL,
  `mobilepulsa_user` varchar(250) NOT NULL,
  `mobilepulsa_pass` varchar(250) NOT NULL,
  `mobilepulsa_aktif` varchar(1) NOT NULL DEFAULT '1',
  `main_background` varchar(255) DEFAULT NULL,
  `saldo_background` varchar(255) DEFAULT NULL,
  `versi_cs` varchar(255) DEFAULT NULL,
  `versi_driver` varchar(255) DEFAULT NULL,
  `versi_mitra` varchar(255) DEFAULT NULL,
  `force_update_user` tinyint(1) NOT NULL,
  `force_update_driver` tinyint(1) NOT NULL,
  `force_update_mitra` tinyint(1) NOT NULL,
  `maintenance` varchar(1) DEFAULT '0',
  `bannerid` varchar(250) NOT NULL,
  `bannerunit` varchar(250) NOT NULL,
  `banneraktif` int(1) NOT NULL DEFAULT 0,
  `isotp` int(11) NOT NULL,
  `minimum_transfer` int(11) NOT NULL,
  `minimum_wallet` int(11) NOT NULL,
  `fee_rain` int(11) NOT NULL,
  `fee_rain_status` tinyint(1) NOT NULL,
  `fee_add_time` int(11) NOT NULL,
  `fee_time_on` varchar(20) NOT NULL,
  `fee_time_off` varchar(20) NOT NULL,
  `fee_time_status` tinyint(1) NOT NULL,
  `sender_wasap` varchar(20) NOT NULL,
  `key_api_wasap` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_settings`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `app_settings` DISABLE KEYS */;
INSERT INTO `app_settings` VALUES
(1,'cs@ogotindonesiateknologi.com','08385925875','https://app.ogotindonesiateknologi.com/','Ogot','<p>Kebijakan Privasi berikut ini menjelaskan bagaimana kami mengumpulkan, menggunakan, memindahkan, mengungkapkan dan melindungi informasi pribadi Anda yang dapat diidentifikasi yang diperoleh melalui Aplikasi kami (sebagaimana dijelaskan di bawah). Mohon Anda membaca Kebijakan Privasi ini dengan seksama untuk memastikan bahwa Anda memahami bagaimana ketentuan Kebijakan Privasi ini kami berlakukan. Kebijakan Privasi ini disertakan sebagai bagian dari Ketentuan Penggunaan kami. Kebijakan Privasi ini mencakup hal-hal sebagai berikut:</p>\r\n<div class=\"content\">\r\n<ol>\r\n<li>definisi</li>\r\n<li>Informasi yang kami kumpulkan</li>\r\n<li>Penggunaan informasi yang kami kumpulkan</li>\r\n<li>Keterangan informasi yang kami kumpulkan</li>\r\n<li>Penahanan informasi yang kami kumpulkan</li>\r\n<li>Keamanan</li>\r\n<li>Perubahan atas Kebijakan Privasi ini</li>\r\n<li>Lain-lain</li>\r\n<li>Pengakuan dan persetujuan</li>\r\n<li>Berhenti menerima email</li>\r\n<li>Cara untuk menghubungi kami</li>\r\n</ol>\r\n</div>\r\n<p><strong>Penggunaan Anda atas aplikasi dan layanan kami tunduk pada Ketentuan Penggunaan dan Kebijakan Privasi ini dan indikasi persetujuan Anda terhadap Ketentuan Penggunaan dan Kebijakan Privasi tersebut.</strong></p>\r\n<div class=\"content\">\r\n<ol>\r\n<li class=\"strong\">\r\n<h4 class=\"normal-font\">definisi</h4>\r\n</li>\r\n</ol>\r\n</div>','<div>Aplikasi Ojek Online yang selalu berusaha memenuhi kebutuhan transportasi sekaligus mempermudah Kita untuk Berbelanja Kebutuhan Sehari-hari.</div>\r\n<br>KPK JAYA Memiliki Misi untuk Selalu Hadir & Mendukung Tumbuhnya UKMK & Ekonomi Masyarakat.','Setel Ulang Kata Sandi','Pendaftaran diterima','<div style=\"text-align: justify;\"><span style=\"font-size: 0.875rem; font-weight: initial;\">Kami telah menerima permintaan Anda untuk mengatur ulang kata sandi. Konfirmasikan melalui tombol di bawah ini:</span></div>','<div style=\"text-align: justify;\"><span style=\"font-size: 0.875rem; font-weight: initial;\">Abaikan email ini jika Anda tidak pernah meminta untuk mengatur ulang kata sandi Anda. Untuk pertanyaan, silahkan hubungi</span></div>','<div style=\"text-align: justify;\"><span style=\"font-size: 0.875rem; font-weight: initial;\">Terima kasih telah mendaftarkan driver, kami telah menerima, silakan klik tombol di bawah ini untuk mengatur ulang kata sandi Anda:</span></div>','<span style=\"text-align: justify;\">Abaikan email ini jika Anda tidak pernah meminta untuk mengatur ulang kata sandi Anda. Untuk pertanyaan, silahkan hubungi</span>','lol.jpg','mail.murahati.my.id','465','cs@murahati.my.id','Kacinong95','cs@murahati.my.id','ssl','OGOT','<span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\">Jl. </span></span></span></span></span></span></span></span></span></span></span></span></span></span></span><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\"><span style=\"vertical-align: inherit;\">Niaga RT 03</span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span>','https://play.google.com/','Rp','USD','AIza_REDACTED_API_KEY','FCM_SERVER_KEY_REDACTED','sk_test_REDACTED','pk_test_REDACTED','1','0','Ab95j_J-CIrQ-Fbg6dAv2ee9d1dD3OQLmAqTp_ZJZybEp1OCmqRBaoLBEaAA0cTL_dIjxvGVFWMPGljb','1','0','https://admin.ogotindonesiateknologi.com//payment/checkout.php/','Mid-client-2qOwIMAx3XetGiWj','1','https://api.mobilepulsa.net/','082279029409','768624feb9ceff2cjr6x','1',NULL,NULL,'1','1','1',0,0,0,'0','ca-app-pub-8843558554957670~48281873222','ca-app-pub-8843558554957670/33208379977',0,0,1000,1000,2,0,3,'00:00','23:59',0,'6282335382016','oy2jPXNFMmzltnYsLFSHQ5xdRj4HNY');
/*!40000 ALTER TABLE `app_settings` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `kota` varchar(200) NOT NULL,
  `promo` varchar(200) NOT NULL,
  `rate1` varchar(200) NOT NULL,
  `rate2` varchar(200) NOT NULL,
  `rate3` varchar(200) NOT NULL,
  `status` varchar(200) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES
(23,'Berau','0','2','3','4','1'),
(24,'surakarta','0','1','2','3','1'),
(25,'Kabupaten Semarang','0','1','2','3','1'),
(26,'karanganyar','0','1','2','3','1'),
(29,'lhokseumawe','1000','1','2','3','1'),
(30,'Semarang','1000','2','3','4','1'),
(31,'Ungaran','0','2','3','4','1'),
(33,'Kotasemarang','1000','1','1','1','1'),
(34,'Ketapang','0','2','3','5','1'),
(36,'Sulawesi','10','3','4','5','1'),
(37,'Pekanbaru','2','3','4','5','1'),
(39,'Karawang','0','0','0','0','1'),
(40,'manado','0','1','2','3','1'),
(41,'Indramayu','10000','3','4','5','1');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `berita`
--

DROP TABLE IF EXISTS `berita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `berita` (
  `id_berita` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_kategori` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `foto_berita` varchar(255) DEFAULT NULL,
  `created_berita` timestamp NULL DEFAULT current_timestamp(),
  `status_berita` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_berita`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `berita`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `berita` DISABLE KEYS */;
INSERT INTO `berita` VALUES
(2,'5','Yuk Menabung','Menabung tanpa ada potongan dalam setiap bulan, non biaya admin.<br />Kalimat-kalimat di atas adalah contoh kalimat homonim. Jika diperhatikan kelima kata tersebut memiliki bunyi serta susunan huruf yang sama, tapi memiliki arti yang berbeda. Baik kata hak, bulan, rapat, bisa, maupun selang memiliki makna y<img class=\"example1\" src=\"https://pulsabillionaireindonesia.com/assets/logo.jpeg\" alt=\"yuu\" width=\"120\" height=\"120\" />ang berbeda-beda.vvv','778649d7f9ddd3750f7431b22f82af90.jpeg','2021-12-23 20:21:37','1'),
(4,'5','Berlibur Ke Pantai','tersebut memiliki bunyi serta susunan huruf yang sama, tapi memiliki arti yang berbeda. Baik kata hak, bulan, rapat, bisa, maupun selang memiliki makna yang berbeda-beda.','6af049d5375d9e69b85c602e449a0a89.png','2023-06-22 14:58:17','1'),
(5,'5','Pakaian Distro','Baju Berkualita tinggi Yang sangat cocok untuk Style masa kini, ayo tunggu apa lagi buruan beli di toko kami.','588b30dcc8de3a945b89b476287781c2.png','2023-06-22 15:02:40','1');
/*!40000 ALTER TABLE `berita` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `berkas_driver`
--

DROP TABLE IF EXISTS `berkas_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `berkas_driver` (
  `id_berkas` int(11) NOT NULL AUTO_INCREMENT,
  `id_driver` varchar(250) NOT NULL,
  `foto_ktp` varchar(250) NOT NULL,
  `foto_sim` varchar(250) NOT NULL,
  `id_sim` varchar(250) NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_berkas`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `berkas_driver`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `berkas_driver` DISABLE KEYS */;
INSERT INTO `berkas_driver` VALUES
(1,'D1735003893','f7e1282a0d910c1c181e540ff96d9cd5.png','8e00acd68ab8e3c2073a84fbd74df563.png','7242424','2024-12-24 01:56:09'),
(2,'M1735004384','6611f4f7e142c22868c57e53e8bdfe3f.png','','','2024-12-24 01:57:07'),
(3,'D1735025353','1735025543-25678.jpg','1735025543-16344.jpg','848454545','2024-12-24 07:32:23'),
(4,'D1735092793','1735093699-39130.jpg','1735093699-69627.jpg','15320609000097','2024-12-25 02:28:19'),
(5,'D1752155139','5a178abfac7680316bfe6ce91977efdb.jpg','6e537a227f4defd0d3bd3849375e84d0.jpg','5271818188111','2025-07-10 13:45:39'),
(6,'M1752947352','1752947352-35021.jpg','','','2025-07-19 17:49:12'),
(7,'D1752949043','98f8af4418715f7574cf348a02032017.jpeg','noimage.jpg','6543678999','2025-07-19 18:17:23'),
(8,'D1752995881','278c3c1497fdf62d21bc6d4ece14f54b.jpg','86c9b93b1b1cdb520cf2176d37fc8b47.jpeg','432111876','2025-07-20 07:18:01'),
(9,'D1752996190','e766dd2bdc26a0826ca0584f0b63844a.jpg','9ef2bcf8d226486dddee2ae841bee82e.jpg','6543678999','2025-07-20 07:23:10'),
(10,'D1752996584','1752996837-70341.jpg','1752996837-19225.jpg','524638','2025-07-20 07:33:57'),
(11,'M1753302166','1753302166-19431.jpg','','','2025-07-23 20:22:46'),
(12,'M1754040626','1754040626-41866.jpg','','','2025-08-01 09:30:26'),
(13,'D1754050053','1754050895-92241.jpg','1754050896-68163.jpg','12345','2025-08-01 12:21:36'),
(14,'M1755594528','1755594528-90772.jpg','','','2025-08-19 09:08:48'),
(15,'M1757916707','8a0a9caabba072f5bc94f56702cdac4c.jpg','','','2025-09-15 06:11:47'),
(16,'M1757918481','5b317decedae2fa6e089c54113fb8a3a.jpg','','','2025-09-15 06:41:21'),
(17,'M1759568052','1759568052-89803.jpg','','','2025-10-04 08:54:12'),
(18,'D1775205550','58991c505094dfb77b178d7052dcee39.jpg','edd28d87d14c6eb25b43da1d6fa36b6f.jpg','866876787678977','2026-04-03 08:39:10'),
(19,'D1775207413','1775211076-29351.jpg','1775211076-88839.jpg','123456789','2026-04-03 10:11:16');
/*!40000 ALTER TABLE `berkas_driver` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `category_item`
--

DROP TABLE IF EXISTS `category_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_item` (
  `id_kategori_item` int(11) NOT NULL AUTO_INCREMENT,
  `nama_kategori_item` varchar(250) NOT NULL,
  `foto_kategori_item` varchar(250) NOT NULL,
  `id_merchant` varchar(250) NOT NULL,
  `created_cat_item` timestamp NOT NULL DEFAULT current_timestamp(),
  `all_category` varchar(50) NOT NULL,
  `status_kategori` varchar(5) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_kategori_item`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_item`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `category_item` DISABLE KEYS */;
INSERT INTO `category_item` VALUES
(2,'ayam geprek','1736491342-38800.jpg','1','2025-01-10 06:42:22','0','1'),
(3,'mnm','1752947082-87659.jpg','1','2025-07-19 17:44:42','0','1'),
(4,'makn','1752947672-22665.jpg','2','2025-07-19 17:54:32','0','1'),
(5,'mnum','1752953676-14638.jpg','2','2025-07-19 19:34:36','0','1'),
(7,'Nasi Goreng Laptop ','1754042176-19140.jpg','4','2025-08-01 09:56:16','0','1'),
(8,'makanan','','6','2025-09-15 06:12:27','0','1'),
(9,'minuman','','6','2025-09-15 06:12:45','0','1'),
(10,'minuman','1757917694-66690.jpg','1','2025-09-15 06:28:14','0','1'),
(11,'minuman','','7','2025-09-15 06:41:36','0','1'),
(12,'makanan','','5','2025-12-14 03:01:34','0','1'),
(13,'nimuman','','5','2025-12-14 03:02:00','0','1');
/*!40000 ALTER TABLE `category_item` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `category_merchant`
--

DROP TABLE IF EXISTS `category_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_merchant` (
  `id_kategori_merchant` int(11) NOT NULL AUTO_INCREMENT,
  `nama_kategori` varchar(250) NOT NULL,
  `foto_kategori` varchar(250) NOT NULL,
  `id_fitur` varchar(200) NOT NULL,
  `status_kategori` varchar(100) NOT NULL,
  PRIMARY KEY (`id_kategori_merchant`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_merchant`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `category_merchant` DISABLE KEYS */;
INSERT INTO `category_merchant` VALUES
(137,'Makanan','723fd842c9461bec2be9a7ab0568a1e2.png','5','1'),
(138,'Minuman','6d21e432d692d557a2734d05a28ae7e1.png','8','1'),
(139,'All Produk','e67772053971467f7a7ef34b47a62414.png','6','1'),
(140,'Minuman','a969a1e9d8b3248e0d38d6951e29cf5a.jpg','5','0'),
(141,'bakso','f39cb50af180b0bf8616d047d0ce2eee.jpg','5','0');
/*!40000 ALTER TABLE `category_merchant` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `config_driver`
--

DROP TABLE IF EXISTS `config_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_driver` (
  `id_driver` varchar(200) NOT NULL,
  `latitude` varchar(30) NOT NULL DEFAULT '0',
  `longitude` varchar(30) NOT NULL DEFAULT '0',
  `bearing` varchar(250) NOT NULL DEFAULT '0',
  `uang_belanja` int(11) NOT NULL DEFAULT 1,
  `update_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `target` int(2) NOT NULL DEFAULT 0,
  `isreset` int(1) NOT NULL DEFAULT 0,
  `nextday` date NOT NULL,
  `status` varchar(1) NOT NULL DEFAULT '5',
  PRIMARY KEY (`id_driver`) USING BTREE,
  KEY `latitude` (`latitude`) USING BTREE,
  KEY `longitude` (`longitude`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_driver`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `config_driver` DISABLE KEYS */;
INSERT INTO `config_driver` VALUES
('D1735003893','-6.1590965','106.8006346','0.0',1,'2026-06-14 09:48:40',0,0,'0000-00-00','1'),
('D1735025353','-8.0781499','111.7041034','0.16904654',1,'2025-07-31 18:07:28',0,0,'0000-00-00','1'),
('D1735092793','0','0','0',1,'2025-07-19 18:18:55',0,0,'0000-00-00','5'),
('D1752155139','0','0','0',1,'2025-07-10 13:45:39',0,0,'0000-00-00','5'),
('D1752949043','0','0','0',1,'2025-07-19 18:17:23',0,0,'0000-00-00','5'),
('D1752995881','0','0','0',1,'2025-07-20 07:18:01',0,0,'0000-00-00','5'),
('D1752996190','0','0','0',1,'2025-07-20 07:23:10',0,0,'0000-00-00','5'),
('D1752996584','-8.1990866','111.1096681','119.493355',1,'2025-07-20 16:36:19',0,0,'0000-00-00','4'),
('D1754050053','0.5699173','101.4553384','206.28987',1,'2025-11-10 12:31:05',0,0,'0000-00-00','4'),
('D1775205550','0','0','0',1,'2026-04-03 08:39:10',0,0,'0000-00-00','5'),
('D1775207413','-8.367935','114.14145','0.0',1,'2026-04-14 23:34:14',0,0,'0000-00-00','1');
/*!40000 ALTER TABLE `config_driver` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `config_user`
--

DROP TABLE IF EXISTS `config_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_user` (
  `id_user` varchar(200) NOT NULL,
  `latitude` varchar(30) NOT NULL DEFAULT '0',
  `longitude` varchar(30) NOT NULL DEFAULT '0',
  `bearing` varchar(250) NOT NULL DEFAULT '0',
  `update_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_user`) USING BTREE,
  KEY `latitude` (`latitude`) USING BTREE,
  KEY `longitude` (`longitude`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_user`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `config_user` DISABLE KEYS */;
INSERT INTO `config_user` VALUES
('P1735004105','0','0','0','2024-12-24 01:35:05'),
('P1735023118','0','0','0','2024-12-24 06:51:58'),
('P1735092903','0','0','0','2024-12-25 02:15:03'),
('P1736502310','0','0','0','2025-01-10 09:45:10'),
('P1736502326','0','0','0','2025-01-10 09:45:26'),
('P1736502329','0','0','0','2025-01-10 09:45:29'),
('P1736502330','0','0','0','2025-01-10 09:45:30'),
('P1736502337','0','0','0','2025-01-10 09:45:37'),
('P1737201561','0','0','0','2025-01-18 11:59:21'),
('P1737201569','0','0','0','2025-01-18 11:59:29'),
('P1737201583','0','0','0','2025-01-18 11:59:43'),
('P1737201584','0','0','0','2025-01-18 11:59:44'),
('P1737201597','0','0','0','2025-01-18 11:59:57'),
('P1737201599','0','0','0','2025-01-18 11:59:59'),
('P1741630877','0','0','0','2025-03-10 18:21:17'),
('P1741630879','0','0','0','2025-03-10 18:21:19'),
('P1741630882','0','0','0','2025-03-10 18:21:22'),
('P1741630883','0','0','0','2025-03-10 18:21:23'),
('P1741630884','0','0','0','2025-03-10 18:21:24'),
('P1742031893','0','0','0','2025-03-15 09:44:53'),
('P1742031914','0','0','0','2025-03-15 09:45:14'),
('P1742031922','0','0','0','2025-03-15 09:45:22'),
('P1742031939','0','0','0','2025-03-15 09:45:39'),
('P1742031944','0','0','0','2025-03-15 09:45:44'),
('P1742031947','0','0','0','2025-03-15 09:45:47'),
('P1742031948','0','0','0','2025-03-15 09:45:48'),
('P1742031949','0','0','0','2025-03-15 09:45:49'),
('P1742031974','0','0','0','2025-03-15 09:46:14'),
('P1742031978','0','0','0','2025-03-15 09:46:18'),
('P1742031979','0','0','0','2025-03-15 09:46:19'),
('P1752155264','0','0','0','2025-07-10 13:47:44'),
('P1752155282','0','0','0','2025-07-10 13:48:02'),
('P1752155306','0','0','0','2025-07-10 13:48:26'),
('P1752155310','0','0','0','2025-07-10 13:48:30'),
('P1752155311','0','0','0','2025-07-10 13:48:31'),
('P1752155312','0','0','0','2025-07-10 13:48:32'),
('P1752155313','0','0','0','2025-07-10 13:48:33'),
('P1752155314','0','0','0','2025-07-10 13:48:34'),
('P1752155315','0','0','0','2025-07-10 13:48:35'),
('P1752155316','0','0','0','2025-07-10 13:48:36'),
('P1752155317','0','0','0','2025-07-10 13:48:37'),
('P1752155320','0','0','0','2025-07-10 13:48:40'),
('P1752155322','0','0','0','2025-07-10 13:48:42'),
('P1752155323','0','0','0','2025-07-10 13:48:43'),
('P1752155324','0','0','0','2025-07-10 13:48:44'),
('P1752155328','0','0','0','2025-07-10 13:48:48'),
('P1752155867','0','0','0','2025-07-10 13:57:47'),
('P1752155869','0','0','0','2025-07-10 13:57:49'),
('P1752155877','0','0','0','2025-07-10 13:57:57'),
('P1752155886','0','0','0','2025-07-10 13:58:06'),
('P1752155894','0','0','0','2025-07-10 13:58:14'),
('P1752155936','0','0','0','2025-07-10 13:58:56'),
('P1752155941','0','0','0','2025-07-10 13:59:01'),
('P1752155942','0','0','0','2025-07-10 13:59:02'),
('P1752155943','0','0','0','2025-07-10 13:59:03'),
('P1752499890','0','0','0','2025-07-14 13:31:30'),
('P1752499893','0','0','0','2025-07-14 13:31:33'),
('P1752499979','0','0','0','2025-07-14 13:32:59'),
('P1752499985','0','0','0','2025-07-14 13:33:05'),
('P1752499991','0','0','0','2025-07-14 13:33:11'),
('P1752499994','0','0','0','2025-07-14 13:33:14'),
('P1752946763','0','0','0','2025-07-19 17:39:23'),
('P1752946771','0','0','0','2025-07-19 17:39:31'),
('P1752946775','0','0','0','2025-07-19 17:39:35'),
('P1752978008','0','0','0','2025-07-20 02:20:08'),
('P1752978031','0','0','0','2025-07-20 02:20:31'),
('P1752981378','0','0','0','2025-07-20 03:16:18'),
('P1753005353','0','0','0','2025-07-20 09:55:53'),
('P1753005413','0','0','0','2025-07-20 09:56:53'),
('P1753005509','0','0','0','2025-07-20 09:58:29'),
('P1753028290','0','0','0','2025-07-20 16:18:10'),
('P1753033513','0','0','0','2025-07-20 17:45:13'),
('P1753603485','0','0','0','2025-07-27 08:04:45'),
('P1754050466','0','0','0','2025-08-01 12:14:26'),
('P1757646121','0','0','0','2025-09-12 03:02:01'),
('P1759559503','0','0','0','2025-10-04 06:31:43'),
('P1759904007','0','0','0','2025-10-08 06:13:27'),
('P1760420146','0','0','0','2025-10-14 05:35:46'),
('P1761976856','0','0','0','2025-11-01 06:00:56'),
('P1762075498','0','0','0','2025-11-02 09:24:58'),
('P1765585531','0','0','0','2025-12-13 00:25:31'),
('P1775197589','0','0','0','2026-04-03 06:26:29'),
('P1776169472','0','0','0','2026-04-14 12:24:32'),
('P1780559025','0','0','0','2026-06-04 07:43:45');
/*!40000 ALTER TABLE `config_user` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `data_menu_web`
--

DROP TABLE IF EXISTS `data_menu_web`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_menu_web` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_kode` varchar(15) NOT NULL,
  `menu_parent` varchar(10) NOT NULL,
  `menu_label` varchar(100) NOT NULL,
  `menu_url` varchar(100) NOT NULL,
  `menu_icon` varchar(50) NOT NULL DEFAULT 'fa fa-angle-right',
  `menu_group` tinyint(1) NOT NULL,
  `menu_status` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `menu_kode` (`menu_kode`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci AVG_ROW_LENGTH=73 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_menu_web`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `data_menu_web` DISABLE KEYS */;
INSERT INTO `data_menu_web` VALUES
(56,'mn01','mn01','Transaksi','transaction','ion ion-md-calculator',1,1),
(57,'mn02','mn02','Finansial','','ion ion-md-wallet',1,1),
(58,'mn0201','mn02','Saldo','wallet','fa fa-angle-right',1,1),
(59,'mn0202','mn02','Manual Topup','wallet/tambahtopup','fa fa-angle-right',1,1),
(60,'mn0203','mn02','Manual Penarikan','wallet/tambahwithdraw','fa fa-angle-right',1,1),
(61,'mn03','mn03','Driver','','ion ion-md-car',1,1),
(62,'mn0301','mn03','Driver','driver','fa fa-angle-right',1,1),
(63,'mn0302','mn03','Registrasi','newregistration','fa fa-angle-right',1,1),
(64,'mn0303','mn03','Map Driver','driver/tracking_driver','fa fa-angle-right',1,1),
(65,'mn04','mn04','Customer','users','ion ion-md-people',1,1),
(66,'mn05','mn05','Mitra','','ion ion-md-cart',1,1),
(67,'mn0501','mn05','Kategori','categorymerchant','fa fa-angle-right',1,1),
(68,'mn0502','mn05','Daftar Mitra','mitra','fa fa-angle-right',1,1),
(69,'mn0503','mn05','Registrasi','mitra/newregmitra','fa fa-angle-right',1,1),
(70,'mn06','mn06','PPOB','','ion ion-md-phone-portrait',1,1),
(71,'mn0601','mn06','Operator','ppoboperator','fa fa-angle-right',1,1),
(72,'mn07','mn07','Layanan','','ion ion-md-outlet',2,1),
(73,'mn0701','mn07','Layanan','services','fa fa-angle-right',2,1),
(74,'mn0702','mn07','Tipe Job','partnerjob','',2,1),
(75,'mn08','mn08','Promo','promocode','ion ion-md-pricetag',2,1),
(76,'mn09','mn09','Spanduk','promoslider','ion ion-md-images',2,1),
(77,'mn10','mn10','Berita','news','ion ion-md-document',2,1),
(78,'mn11','mn11','Kirim Email','sendemail','ion ion-md-send',2,1),
(79,'mn12','mn12','Kirim Notifikasi','appnotification','ion ion-md-notifications',2,1),
(80,'mn13','mn13','Poin','poin','ion ion-md-gift',2,1),
(81,'mn14','mn14','Setup User Admin','','ion ion-md-contact',3,1),
(82,'mn1401','mn14','Role Admin','group','fa fa-angle-right',3,1),
(83,'mn1402','mn14','Data Admin','admin','fa fa-angle-right',3,1),
(84,'mn15','mn15','Pengaturan Aplikasi','appsettings','ion ion-md-build',3,1),
(85,'mn16','mn16','Metode Pembayaran','','ion ion-md-card',1,1),
(86,'mn0602','mn06','Digi Kategori','digi/kategori','fa fa-angle-right',1,1),
(87,'mn0603','mn06','Digi Operator','digi/operator','fa fa-angle-right',1,1),
(88,'mn0604','mn06','Digi Produk','digi/produk','fa fa-angle-right',1,1),
(89,'mn0605','mn06','Digi Transaksi','digi','fa fa-angle-right',1,1),
(90,'mn17','mn17','Donasi','donasi','ion ion-md-heart',2,1),
(91,'mn1601','mn16','Metode','payments/method','ion ion-md-card',1,1),
(92,'mn1602','mn16','Transaksi','payments','ion ion-md-card',1,1),
(93,'mn1603','mn16','Setting','payments/setting','ion ion-md-card',1,1);
/*!40000 ALTER TABLE `data_menu_web` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_kategori`
--

DROP TABLE IF EXISTS `digi_kategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_kategori` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) NOT NULL,
  `tipe` tinyint(1) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `inq` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_kategori`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_kategori` DISABLE KEYS */;
INSERT INTO `digi_kategori` VALUES
(1,'Pulsa',1,'https://admin.ogotindonesiateknologi.com/images/ppob/3b5b8822e4ead6b97e30205943f37eff.png',0,1),
(2,'Paket Data',1,'https://admin.ogotindonesiateknologi.com/images/ppob/4015b0d987bffd1b4b144118398bbac2.png',0,1),
(3,'Token',1,'https://admin.ogotindonesiateknologi.com/images/ppob/677cc2326bf5df6e4544e36d892286f2.png',1,1),
(4,'PLN',1,'https://admin.ogotindonesiateknologi.com/images/ppob/554a339f7e9309dca207b2a114ab61a4.png',1,1),
(5,'PDAM',1,'https://admin.ogotindonesiateknologi.com/images/ppob/a66405229fdbb2c90b8840a52d5035d0.png',1,1),
(6,'Internet',1,'https://admin.ogotindonesiateknologi.com/images/ppob/ec6b174e422406493d671950694c8c79.png',1,1),
(7,'HP Pascabayar',1,'https://admin.ogotindonesiateknologi.com/images/ppob/e2bf5d171c354654c0772d24fd9030f7.png',1,1),
(8,'BPJS',1,'https://admin.ogotindonesiateknologi.com/images/ppob/2ab21952d7075f61a08490ae8bfeb682.png',1,1),
(9,'TV Kabel',1,'https://admin.ogotindonesiateknologi.com/images/ppob/235be41bd571154588fee7390560b9f2.png',1,1);
/*!40000 ALTER TABLE `digi_kategori` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_operator`
--

DROP TABLE IF EXISTS `digi_operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kategori` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_operator`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_operator` DISABLE KEYS */;
INSERT INTO `digi_operator` VALUES
(2,1,'Telkomsel',NULL,1),
(3,1,'Indosat',NULL,1),
(4,1,'XL',NULL,1),
(5,2,'Telkomsel Data',NULL,1),
(6,3,'Token PLN',NULL,1),
(7,4,'PLN Pasca',NULL,1),
(8,5,'PDAM PASCA',NULL,1);
/*!40000 ALTER TABLE `digi_operator` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_prefix`
--

DROP TABLE IF EXISTS `digi_prefix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_prefix` (
  `id` int(11) NOT NULL,
  `id_operator` int(11) NOT NULL,
  `prefix` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_prefix`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_prefix` DISABLE KEYS */;
/*!40000 ALTER TABLE `digi_prefix` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_produk`
--

DROP TABLE IF EXISTS `digi_produk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_produk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` int(11) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `deskripsi` varchar(255) NOT NULL,
  `hpp` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `admin` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_produk`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_produk` DISABLE KEYS */;
INSERT INTO `digi_produk` VALUES
(1,2,'TO1','Telkomsel 1.000','Pulsa Telkomsel Rp 1.000',1580,1700,0,1,'0000-00-00 00:00:00'),
(2,2,'T02','Telkomsel 5.000','Pulsa Telkomsel Rp 5.000',5265,6000,0,1,'0000-00-00 00:00:00'),
(3,2,'TO3','Telkomsel 10.000','Pulsa Telkomsel Rp 10.000',10200,11000,0,1,'0000-00-00 00:00:00'),
(4,2,'T04','Telkomsel 20.000','Pulsa Telkomsel Rp 20.000',19930,21000,0,1,'0000-00-00 00:00:00'),
(5,2,'P025','Telkomsel 25.000','Pulsa Telkomsel Rp 25.000',24835,26000,0,1,'0000-00-00 00:00:00'),
(6,2,'T050','Telkomsel 50.000','Pulsa Telkomsel Rp 50.000',49675,51000,0,1,'0000-00-00 00:00:00'),
(7,2,'T0100','Telkomsel 100.000','Pulsa Telkomsel Rp 100.000',96719,100000,0,1,'0000-00-00 00:00:00'),
(8,6,'pr002ln','PLN 20.000','PLN 20.000',20080,25000,0,1,'0000-00-00 00:00:00'),
(10,7,'PLN','PLN','Tagihan PLN',1000,1000,0,1,'0000-00-00 00:00:00'),
(11,6,'PR003LN','PLN 50.000','PLN 50.000',50075,52000,0,1,'0000-00-00 00:00:00'),
(12,2,'PRAOO1LN','PLN 100.000','PLN 100.000',100005,101000,0,1,'0000-00-00 00:00:00'),
(13,2,'PRA002LN','PLN 200.000','PLN 200.000',200030,201000,0,1,'0000-00-00 00:00:00'),
(14,8,'BKT05','PDAM Kota Bontang','PDAM Kota Bontang',0,0,0,1,'0000-00-00 00:00:00'),
(15,8,'PKS06','PDAM Kota Samarinda','PDAM Kota Samarinda',0,0,0,1,'0000-00-00 00:00:00'),
(16,8,'TBKT01','PDAM Tirta Tuah Benua Kutai Timur','PDAM Tirta Tuah Benua Kutai Timur',0,0,0,1,'0000-00-00 00:00:00'),
(17,2,'HP001PC','Halo Postpaid','Halo Postpaid',0,0,0,1,'0000-00-00 00:00:00'),
(18,9,'PAYIND','SPEEDY dan INDIHOME','SPEEDY dan  INDIHOME',0,0,0,1,'0000-00-00 00:00:00'),
(19,10,'BP01K','Bpjs Kesehatan','Bpjs Kesehatan',0,0,0,1,'0000-00-00 00:00:00'),
(20,11,'TV001INDO','INDOVISION','INDOVISION',0,0,0,1,'0000-00-00 00:00:00'),
(21,11,'TV002TEL','TELKOMVISION','TELKOMVISION',0,0,0,1,'0000-00-00 00:00:00'),
(22,5,'TEDAT002','Telkomsel Data 1 GB','Telkomsel Data 1 GB + 5 GB Videomax / 30 Hari',24960,26000,0,1,'0000-00-00 00:00:00'),
(23,5,'TEDAT003','Telkomsel Data 3 GB','Telkomsel Data 3 GB + 12 GB Videomax / 30 Hari',48750,50000,0,1,'0000-00-00 00:00:00'),
(24,5,'TEDAT004','Telkomsel Data 7 GB','Telkomsel Data 7 GB + 28 GB Videomax / 30 Hari',100100,101000,0,1,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `digi_produk` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_setting`
--

DROP TABLE IF EXISTS `digi_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `key_production` varchar(255) NOT NULL,
  `key_development` varchar(255) NOT NULL,
  `is_demo` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_setting`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_setting` DISABLE KEYS */;
INSERT INTO `digi_setting` VALUES
(1,'radalugwKm4g','8d31f3e2-94bb-5a05-bcfe-810de18dffaa','dev-0669a810-6fba-11ed-8323-23cd8394af2f',1);
/*!40000 ALTER TABLE `digi_setting` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `digi_transaction`
--

DROP TABLE IF EXISTS `digi_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `digi_transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice` varchar(20) NOT NULL,
  `id_user` varchar(20) NOT NULL,
  `kode_produk` varchar(50) NOT NULL,
  `harga` varchar(11) NOT NULL,
  `admin` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `nomor_tagihan` varchar(50) NOT NULL,
  `nama_pelanggan` varchar(255) DEFAULT NULL,
  `detail` text DEFAULT NULL,
  `trx_reff` varchar(255) DEFAULT NULL,
  `trx_rc` varchar(10) DEFAULT NULL,
  `trx_message` varchar(255) DEFAULT NULL,
  `trx_status` varchar(255) DEFAULT NULL,
  `trx_sn` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `digi_transaction`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `digi_transaction` DISABLE KEYS */;
INSERT INTO `digi_transaction` VALUES
(29,'POB222012070614','P1665481281','T02','6000',0,6000,'082335382016','','',NULL,'00','Transaksi Sukses','Sukses','02896200001929182632',1,'2022-12-06 20:07:17'),
(30,'POB222012190612','P1665481281','T02','6000',0,6000,'082335382016','','',NULL,'00','Transaksi Sukses','Sukses','02896400001929405824',1,'2022-12-06 20:19:14');
/*!40000 ALTER TABLE `digi_transaction` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `donasi`
--

DROP TABLE IF EXISTS `donasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `donasi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama_lembaga` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `deskripsi` varchar(255) NOT NULL,
  `gambar` varchar(255) NOT NULL,
  `tanggal_awal` date NOT NULL,
  `tanggal_akhir` date NOT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donasi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `donasi` DISABLE KEYS */;
INSERT INTO `donasi` VALUES
(1,'PANTI TES TES','Jl.alhirat No 5','082335382016','Panti Asuhan','&lt;div&gt;Para Anak Anak di panti Asuhan Membutuhkan Iyuran tangan kita&lt;/div&gt;','699a92ea202fcb29694f5be4591541f6.jpg','2022-07-04','2022-07-31',2,'2022-07-04 08:44:44'),
(2,'PESANTREN TES TES','Jl. Rahmat Allah swt. No 132','082335382016','Pembangunan Pesantren','Pesantren Merupakan Sekolah yang memfokuskan diri ke pendidikan Agama islam','f86e46e14438ab6a31464fb37b64d9af.jpeg','2022-07-04','2022-07-04',2,'2022-07-04 16:33:59'),
(3,'Mesjid Al aqsa','Palestina','082335382016','Pembangunan Mesjid','Ayo Sedekah','19c7fcc52e792c7e4fe087a6c13ad04b.jpg','2022-07-05','2023-07-05',9,'2022-07-04 17:07:12'),
(4,'Mesjid Demo','Jl.ayu','082335382016','Pembangunan','Ini merupakan tes','0416858c15d31e0f6664a1ff2b648f39.jpg','2022-07-14','2023-07-04',9,'2022-07-14 08:07:28'),
(5,'Pembangunan Sekolah','Sura karta','82335382016','Pembangunan sekolah','&lt;span xss=removed&gt;&lt;span xss=removed&gt;Ini merupakan demo sedekah&lt;/span&gt;&lt;/span&gt;','c237195a15d9873b3b0f569ab820274b.jpg','2022-08-02','2022-08-31',2,'2022-08-02 04:54:20'),
(6,'Gemini GYM','Jln. Jepang kimochy','082297455144','Gemini gym butuh sumbangan kasiang','Konten','994d60141b371fd8cd6eaa3a30269521.jpg','2022-08-07','2022-08-07',9,'2022-08-07 09:48:24'),
(7,'Blaster','Jln. Jepang kimochy','082297455144','Kantong kering','Konten','noimage.jpg','2022-08-26','2022-08-26',9,'2022-08-26 03:01:23'),
(8,'tesss','fsfsfs','082335382016','Pembangunan Pesantren','Konten bsdgerg','a573237d6bc2a13d2bf9971fe7424a19.png','2022-08-31','2025-09-30',9,'2022-08-30 19:07:52'),
(9,'Contoh donasi','Jl. Rahmat Allah swt. No 132','082335382016','Panti Asuhan','Konten gagag','c1470bfec5c9368b1177a4f8914d1386.jpg','2022-10-30','2033-10-30',2,'2022-10-30 15:23:55'),
(10,'Panti Asuhan','Jl.demo tes coba','082335382016','Salurkan Bantuan Anda','&lt;span xss=removed&gt;&lt;span xss=removed&gt;Sedekah&lt;/span&gt;&lt;/span&gt;','9e6d5a29d991386a00f1ee1c3b834cca.jpg','2023-06-09','2021-12-27',2,'2023-06-09 06:17:58'),
(11,'Panti Asuhan','Jl.alhirat No 5','082335382016','Salurkan Bantuan Anda','&lt;div xss=removed&gt;Sangat membutuhkan uluran tangan anda&lt;/div&gt;','2cb49b6d3bf83d415c4042a0dc16281c.jpeg','2023-06-16','2026-09-16',9,'2023-06-16 02:47:28'),
(12,'Sedekah Lebaran Idul adha','Jl.alhirat No 5','082335382016','Meringankan beli baju lebaran','Ayo Berbagi','da01e5e177a15698f6d9ebdb899ef90c.jpg','2023-06-16','2025-10-16',1,'2023-06-16 03:00:25'),
(13,'Rumah Kurban','Jl.alhirat No 5','082335382016','Sedekah Qurban Lebaran','Ulurkan Bantuan','c86f344d6be6f7993b67ce0260bf775f.jpg','2023-06-22','2025-07-22',1,'2023-06-22 13:45:23');
/*!40000 ALTER TABLE `donasi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `donasi_transaksi`
--

DROP TABLE IF EXISTS `donasi_transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `donasi_transaksi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice` varchar(30) NOT NULL,
  `id_donasi` int(11) NOT NULL,
  `id_user` varchar(30) NOT NULL,
  `nama_pengirim` varchar(255) NOT NULL,
  `masuk` int(11) NOT NULL,
  `keluar` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donasi_transaksi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `donasi_transaksi` DISABLE KEYS */;
INSERT INTO `donasi_transaksi` VALUES
(1,'DNS-251307441127',13,'P1735004105','demo',10000,0,1,'2025-07-11 13:44:27'),
(2,'DNS-250107332420',12,'P1735004105','test',12000,0,1,'2025-07-24 01:33:20'),
(3,'WD-1753295798',12,'0','withdraw',0,12000,1,'2025-07-23 18:36:38'),
(4,'DNS-252007332640',12,'P1735004105','demo',1000,0,1,'2025-07-26 20:33:40'),
(5,'DNS-252007342654',12,'P1735004105','demo',10000,0,1,'2025-07-26 20:34:54'),
(6,'DNS-251808161954',13,'P1735004105','demo',10000,0,1,'2025-08-19 18:16:54'),
(7,'DNS-262001530851',12,'P1735004105','demo',1000,0,1,'2026-01-08 20:53:51'),
(8,'DNS-262001540818',12,'P1735004105','demo',1500,0,1,'2026-01-08 20:54:18'),
(9,'DNS-261101000941',13,'P1735004105','demo',20000,0,1,'2026-01-09 11:00:41');
/*!40000 ALTER TABLE `donasi_transaksi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `donasi_withdraw`
--

DROP TABLE IF EXISTS `donasi_withdraw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `donasi_withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_donasi` int(11) NOT NULL,
  `invoice` varchar(20) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `foto` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `regalias` varchar(255) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donasi_withdraw`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `donasi_withdraw` DISABLE KEYS */;
INSERT INTO `donasi_withdraw` VALUES
(9,1,'WD-1657783520',500,'tes','de948484108465910fd4ab015d525b19.jpg',1,'Administrator','2022-07-14 07:25:20'),
(10,1,'WD-1657786144',500,'terundi','8359af5d187dcccd38983ee908d36f2c.jpg',1,'Administrator','2022-07-14 08:09:04'),
(11,1,'WD-1659013985',500,'Terimakasih','noimage.jpg',1,'Administrator','2022-07-28 13:13:05'),
(12,2,'WD-1659014022',250,'Manual topup','fd318d18b8da4a7ab23d4801a2dcdbfd.png',1,'Administrator','2022-07-28 13:13:42'),
(13,9,'WD-1667143637',1000,'Penyaluran','12d11faab440a3431b23bc95a1b5402a.jpg',1,'Administrator','2022-10-30 15:27:17'),
(14,12,'WD-1753295798',12000,'Test','5eeddeb0183827ba8ba399db46b8cacb.jpg',1,'demo','2025-07-23 18:36:38');
/*!40000 ALTER TABLE `donasi_withdraw` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `id` varchar(200) NOT NULL,
  `nama_driver` varchar(20) NOT NULL,
  `no_ktp` varchar(16) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `tempat_lahir` varchar(20) DEFAULT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `countrycode` varchar(20) NOT NULL,
  `phone` varchar(250) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `foto` varchar(50) NOT NULL,
  `rating` double NOT NULL DEFAULT 0,
  `job` int(11) NOT NULL,
  `gender` varchar(250) DEFAULT '2',
  `alamat_driver` text NOT NULL,
  `kendaraan` int(11) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `reg_id` varchar(250) NOT NULL DEFAULT '1234',
  `status` char(1) DEFAULT '2',
  `point` varchar(250) NOT NULL DEFAULT '0',
  `Kota` varchar(250) NOT NULL DEFAULT 'Indramayu',
  `aktivitas` varchar(250) NOT NULL DEFAULT 'Offline',
  `islogin` int(1) NOT NULL DEFAULT 0,
  `pin` int(4) NOT NULL DEFAULT 1234,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `no_telepon` (`no_telepon`) USING BTREE,
  UNIQUE KEY `no_ktp` (`no_ktp`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES
('D1735003893','demo1','757545544','1980-12-24',NULL,'6282335382016','+62','82335382016','ogottolis@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','c7e19d8cd2385a75babb09b9ef471c62.png',3.8857142857143,2,'Male','jl.pahlawan',112,'2024-12-24 01:31:33','2026-06-04 16:33:52','e1yB-5rOQ5G0wFpJvmFeJp:APA91bE5w5QrkPeEkQyeshGa4Xc34iN_6Cps0g2do7DGQzdL2i74g53IqtP3_stS79brgi6_qtlh18eb57VwMnTj9I6e8KEEtrgsc9h_K5UaMY7KnTM5WYU','1','12','Indramayu','Offline',1,1234),
('D1735025353','ego','8788787','1996-12-24',NULL,'6282292609816','+62','82292609816','egosandijumran@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','1735025543-83405.jpg',4.5,2,'Male','disini',113,'2024-12-24 07:29:13','2025-07-28 04:16:46','fqcNJIfpS8OEcJ-tlRvv48:APA91bEwTa9nuqOalRvHa0yd7jFeyOF6jX9rrEaxzi9qm78gsZc-V0XrA5GD0MuoXdL0VmOOUCjoS8qSgNQjtNoxijYG_Eqi6KgZ3YLUezj3yQKQ87m2P3o','1','26','Indramayu','Offline',1,1234),
('D1735092793','Aksa','3509101209060001','2006-09-12',NULL,'6285710216344','+62','85710216344','diginusaberkah@gmail.com','cbdbe4936ce8be63184d9f2e13fc249234371b9a','1735093699-20080.jpg',0,2,'Male','jl bedadung no.40 Rambipuji',114,'2024-12-25 02:13:13','2025-07-19 18:18:59','','1','0','Indramayu','Offline',0,1234),
('D1735093935','aksa',NULL,'2006-09-12',NULL,'6285142484916','+62','85142484916','laksmana.rambipuji12@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','',0,0,'Male','jl bedadung no.40 Rambipuji ',1,'2024-12-25 02:32:15','2025-09-03 01:11:38','12345','1','0','Indramayu','Offline',0,1234),
('D1735095016','arfan123',NULL,'2024-12-25',NULL,'62085198144756','+62','085198144756','vanpelarisiput@gmail.com','0a5a795c27d958e44ba5b28082c1e17ebcd8c42f','',0,0,'Male','jl.bendadung no.40 rambipuji-jember',1,'2024-12-25 02:50:16','2024-12-25 02:50:16','12345','0','0','Indramayu','Offline',0,1234),
('D1736509751','Fiki Muhamad',NULL,'1988-09-30',NULL,'6285340021007','+62','85340021007','vickyfxcm@gmail.com','c15970692e8219bca6d87f72f9657a15179992e3','',0,0,'Female','Telaga Biru Kab Gorontalo',1,'2025-01-10 11:49:11','2025-01-10 11:49:11','12345','0','0','Indramayu','Offline',0,1234),
('D1752948297','testing',NULL,'1997-07-20',NULL,'620987654321','+62','0987654321','testing@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','',0,2,'Female','sty\nndhd',1,'2025-07-19 18:04:57','2025-07-19 18:21:01','12345','0','0','Indramayu','Offline',0,1234),
('D1752996584','geo','3524807675727664','1981-07-20',NULL,'6282345678032','+62','82345678032','viewtrade03@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','1752996837-65645.jpg',0,2,'Male','pct',119,'2025-07-20 07:29:44','2025-07-20 12:55:40','','1','0','Indramayu','Offline',0,1234),
('D1754050053','Ojek Endru ','12345','1986-03-04',NULL,'6285324667722','+62','85324667722','endru.phoenix@gmail.com','8cb2237d0679ca88db6464eac60da96345513964','1754050895-1520.jpg',0,2,'Male','Rumbai ',120,'2025-08-01 12:07:33','2025-08-01 12:25:39','cXtWc6xbRMKC4wrIbl8Elg:APA91bHTSnJ4jqFROAcVHrMbqf55OyAg2cxbskLnCLhP8Tc7ndwXpTq196UZewu_wOgdbhEm0Hd3SYVP1WpgBdJcq5F95cn47odIZXHhIlbOZcDFNKn5dHQ','1','0','Indramayu','Offline',1,1234),
('D1755618301','ahmad',NULL,'2002-08-19',NULL,'6281384742009','+62','81384742009','annajayalaris@gmail.com','93bf09b3e351db948b018b4d6a6c6f3356464c91','',0,0,'Select Gender','',1,'2025-08-19 15:45:01','2025-08-19 15:45:01','12345','0','0','Indramayu','Offline',0,1234),
('D1755760577','wijaya',NULL,'2001-08-21',NULL,'62085316787777','+62','085316787777','cv.karyamuda.km@gmail.com','7c4a8d09ca3762af61e59520943dc26494f8941b','',0,0,'Male','balikpapan',1,'2025-08-21 07:16:17','2025-08-21 07:16:17','12345','0','0','Indramayu','Offline',0,1234),
('D1755760675','Wijaya aa',NULL,'2025-08-06',NULL,'6285316787777','+62','85316787777','cv.karyamuda.kmm@gmail.com','7c4a8d09ca3762af61e59520943dc26494f8941b','',0,0,'Male','balikpapan',1,'2025-08-21 07:17:55','2025-08-21 07:17:55','12345','0','0','Indramayu','Offline',0,1234),
('D1758861639','Denza voltrip',NULL,'1990-01-20',NULL,'62081327000021','+62','081327000021','agathanuary@gmail.com','b1fdcd2dbdc466c33a51e1abb22e026d32faeb87','',0,0,'Male','bukit tani',1,'2025-09-26 04:40:39','2025-09-26 04:40:39','12345','0','0','Indramayu','Offline',0,1234),
('D1759554584','Leon',NULL,'1977-04-12',NULL,'6282169168169','+62','82169168169','leon@gmail.com','be92910ae7bb6f58bb9b7c6f845bc30640119592','',0,0,'Male','tanggerang',1,'2025-10-04 05:09:44','2025-10-04 06:11:10','c9KqG1r9SJu5kyYq7HqL4V:APA91bETBDkPipmAFHtt-zDuzkgSfy5TUBlU9COuV1H56rixhrGm0V9Gh3EWfvJ4lU6pcmdH4PDftLvMtk-tcxARkuNzEcnWU4G57kyp5oFC8pIy-cifP1U','0','0','Indramayu','Offline',0,1234),
('D1767929299','herry',NULL,'1988-12-08',NULL,'6281381820076','+62','81381820076','herry081288@gmail.com','4d9012b4a77a9524d675dad27c3276ab5705e5e8','',0,0,'Select Gender','Jl. Batu III, RT.6/RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia',1,'2026-01-09 03:28:19','2026-04-03 06:21:41','12345','1','0','Indramayu','Offline',0,1234),
('D1775199912','yus',NULL,'2026-04-03',NULL,'62628385925875','62+62','8385925875','yusufw77@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','7aaafc708427715c95e0a87bd65960a6.jpg',0,2,'Male','banyuwangi',1,'2026-04-03 07:05:12','2026-04-03 08:45:17','daZwL9ivTZCL1zZB4ZAK5J:APA91bGi2Tg68nLXS9jSs6lqXEUMjbEhPnO5KAhU3k-k4vzBbcT2_aLFIisN9sH__l5weG55XW7GTYGt2fSXMJS4_EBN01gZp4ij-RXlBxSgHxDn52Gyff0','1','0','Indramayu','Offline',0,1234),
('D1775207413','yus','3656865685998898','2026-04-03',NULL,'628385925875','+62','8385925875','8385925875','fb8019319e4ce53e4443a7905c33d2a9c480a9a8','1775211076-64317.jpg',5,2,'Male','banyuwangi ',122,'2026-04-03 09:10:13','2026-04-14 22:40:23','daZwL9ivTZCL1zZB4ZAK5J:APA91bGi2Tg68nLXS9jSs6lqXEUMjbEhPnO5KAhU3k-k4vzBbcT2_aLFIisN9sH__l5weG55XW7GTYGt2fSXMJS4_EBN01gZp4ij-RXlBxSgHxDn52Gyff0','1','6','Indramayu','Offline',1,1234),
('D1780356375','agus',NULL,'2026-06-02',NULL,'62081327808876','+62','081327808876','reat@gmail.com','f95116b9b01bebabebfe0658c6d50cca3f8db0a4','',0,0,'Select Gender','jakatya',1,'2026-06-01 23:26:15','2026-06-01 23:26:15','12345','0','0','Indramayu','Offline',0,1234),
('D1780559663','rengs',NULL,'2026-06-19',NULL,'6281327808876','+62','81327808876','kkkk@gmail.com','59ecaac896f9715bcd76032bb8c1dd1d713e44c4','',0,0,'Male','kebumen',1,'2026-06-04 07:54:23','2026-06-04 07:54:23','12345','0','0','Indramayu','Offline',0,1234);
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `driver_job`
--

DROP TABLE IF EXISTS `driver_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver_job` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `driver_job` varchar(250) NOT NULL,
  `icon` varchar(20) NOT NULL DEFAULT '1',
  `status_job` varchar(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_job`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `driver_job` DISABLE KEYS */;
INSERT INTO `driver_job` VALUES
(1,'Car','2','1'),
(2,'Bike','1','1'),
(3,'Truck','3','1'),
(4,'Hatchback','3','1'),
(5,'SUV Car','2','1'),
(6,'Van Car','7','1'),
(7,'Kurir','4','1'),
(8,'Bicycle','8','1'),
(9,'Tuktuk','9','1'),
(17,'Jastip','1','1'),
(19,'Bentor','1','1');
/*!40000 ALTER TABLE `driver_job` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `driver_job_select`
--

DROP TABLE IF EXISTS `driver_job_select`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver_job_select` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_driver` varchar(30) NOT NULL,
  `id_job` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_job_select`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `driver_job_select` DISABLE KEYS */;
INSERT INTO `driver_job_select` VALUES
(20,'D1744799277',2),
(21,'D1752155139',2),
(28,'D1752948344',2),
(29,'D1752948344',7),
(30,'D1752949043',2),
(33,'D1752949442',2),
(34,'D1752949442',7),
(44,'D1735025353',1),
(45,'D1735025353',2),
(46,'D1735025353',7),
(47,'D1752948297',2),
(48,'D1752948297',7),
(49,'D1752995881',2),
(50,'D1752996190',2),
(51,'D1735092793',1),
(52,'D1735092793',2),
(53,'D1735092793',7),
(54,'D1735092793',8),
(55,'D1735092793',9),
(62,'D1752996584',1),
(63,'D1752996584',2),
(64,'D1752996584',7),
(65,'D1735003893',2),
(66,'D1735003893',7),
(67,'D1754050053',2),
(69,'D1775205550',1),
(73,'D1775207413',1),
(74,'D1775207413',2),
(75,'D1775207413',4),
(76,'D1775207413',5),
(77,'D1775207413',6),
(78,'D1775207413',7),
(79,'D1775207413',8),
(80,'D1775199912',1),
(81,'D1775199912',2),
(82,'D1775199912',3),
(83,'D1775199912',4),
(84,'D1775199912',5),
(85,'D1775199912',6),
(86,'D1775199912',7),
(87,'D1775199912',8);
/*!40000 ALTER TABLE `driver_job_select` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `fitur`
--

DROP TABLE IF EXISTS `fitur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitur` (
  `id_fitur` int(11) NOT NULL,
  `fitur` varchar(20) NOT NULL,
  `biaya` int(11) NOT NULL,
  `biaya_minimum` int(11) NOT NULL,
  `jarak_minimum` varchar(100) NOT NULL,
  `maks_distance` varchar(250) NOT NULL,
  `wallet_minimum` varchar(100) NOT NULL,
  `komisi_tipe` tinyint(1) NOT NULL DEFAULT 1,
  `komisi` varchar(200) DEFAULT '0',
  `komisi_mitra_tipe` tinyint(1) NOT NULL,
  `komisi_mitra` int(11) NOT NULL,
  `keterangan_biaya` varchar(50) NOT NULL,
  `fee_rain` int(11) NOT NULL,
  `fee_rain_status` tinyint(1) NOT NULL,
  `fee_add_time` int(11) NOT NULL,
  `fee_time_on` varchar(20) NOT NULL,
  `fee_time_off` varchar(20) NOT NULL,
  `fee_time_status` int(11) NOT NULL,
  `driver_job` int(11) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `icon` varchar(500) NOT NULL,
  `home` varchar(1) NOT NULL,
  `background` varchar(255) DEFAULT NULL,
  `active` varchar(1) NOT NULL,
  `is_pending` tinyint(1) NOT NULL,
  `time_pending` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitur`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `fitur` DISABLE KEYS */;
INSERT INTO `fitur` VALUES
(6,'OgotShop',1300,6400,'10','20','1000',1,'10',1,5,'KM',0,0,0,'','',0,7,'Laper ? Yuk Pesan Sekarang','aa04b8e65a12a8cec1592e959e55a843.png','4','#ffbd20','1',0,0),
(2,'OgotCar',12000,50000,'5','200','50000',1,'10',1,0,'KM',0,0,1000,'10:00','17:00',1,1,'Maksimal 6 Orang','c11096c9da303be8f0b8659dfcdafa3f.png','1','#ffbd20','1',0,0),
(1,'OgotJek',2500,7000,'15','100','1000',1,'0',1,0,'KM',500,1,500,'10:00','17:00',1,2,'Maksimal 1 Orang','64f75d140d6617695cfd92035e90712b.png','1','#e6b000','1',0,0),
(8,'OgotPromo',2750,12000,'5','40','10000',1,'10',1,0,'KM',0,0,0,'','',0,7,'Belanja Kini Jadi Lebih Mudah.','f750ffde404efe3735e2736a6688602c.png','6','#ffbd20','1',0,0),
(10,'OgotSampah',2750,12000,'5','40','10000',1,'10',1,0,'Hr',0,0,0,'','',0,2,'Belanja Kini Jadi Lebih Mudah.','14e99e58b095f6ca215af6012a019847.png','3','#ffbd20','1',0,0),
(4,'Travel',5000,3000,'50','20','1000',1,'10',1,0,'KM',0,0,0,'','',0,7,'Rental diluar biaya bahan bakar','e69094c730ac6dfa88461f7646295ab9.png','2','#ffbd20','1',0,0),
(5,'OgotFood',4000,5000,'15','15','5000',1,'2000',1,10,'KM',0,0,0,'','',0,7,'Laper, Pesan makan yuk','00abee41fdb8612b18638c65b981e307.png','4','#ffbd20','1',0,0),
(13,'OgotDigital',2750,12000,'5','40','10000',1,'10',1,0,'KM',0,0,0,'','',0,7,'Antar Pesanan Kini Jadi Lebih Mudah.','877da06c4780e9f0bee62977ec48d36a.png','5','#ffbd20','1',0,0),
(0,'Bentor',10000,3000,'1','100','10000',1,'10%',1,10,'KM',0,0,0,'','',0,19,'Bentor ku','1b86885b3730887767c855bbb9ca9844.png','1','#030507','1',1,0),
(0,'kapal wisata',650000,650000,'1','100','1000000',1,'80',1,20,'Dy',0,0,0,'','',0,9,'kapal ku','594be709da12439034748d0157a1579b.png','1','#e4e0ff','1',1,0);
/*!40000 ALTER TABLE `fitur` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `forgot_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idkey` int(11) NOT NULL,
  `userid` varchar(200) NOT NULL,
  `token` varchar(500) NOT NULL,
  `created` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forgot_password`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `forgot_password` DISABLE KEYS */;
INSERT INTO `forgot_password` VALUES
(1,3,'M1735004384','2e76a5f72edfce8e00360307966a26e03cf9513d','2024-12-24 01:40:29'),
(2,2,'D1735003893','815c4e9907e659b54d343fd2100b3817d70e4a0c','2024-12-24 01:44:58'),
(4,2,'D1735025353','158396d4d0104d62a475b7d0a6c470a019920554','2024-12-24 07:33:37'),
(7,2,'D1735092793','d05b1493dd78452eac426df0012589ab9357a8c9','2025-07-10 13:52:45'),
(8,3,'M1752947352','47467d5d3add8d838089de6a6d3907a441eec5c3','2025-07-19 17:52:29'),
(12,2,'D1752996584','7e5daeaaee5649a803e6c9e264331443978b2f1a','2025-07-20 07:36:05'),
(13,3,'M1753302166','f799ee99d7748653ca122dce425fd9dae117db37','2025-07-23 20:24:29'),
(14,3,'M1754040626','74d64e5eda5cc7072bd266a596d4591ede6f1c0e','2025-08-01 09:53:14'),
(15,3,'M1754040626','bd6ddfbd093fe9930b8640d8936487a18187d750','2025-08-01 09:53:15'),
(16,2,'D1754050053','656de73b3674ddbbba96b84e6cc4300f661e9db5','2025-08-01 12:25:39'),
(17,3,'M1755594528','edb84cd5f9e16bf4d81a57b66c7ec8965cd396ed','2025-08-19 09:10:19'),
(18,2,'D1735093935','491c4a397ec94bd30414d358bb4b996f51340e19','2025-09-03 01:11:38'),
(19,3,'M1757916707','4e65e1b9ef3895ce7216bc04efa143fffcd79740','2025-09-15 06:11:58'),
(20,2,'D1767929299','7c50c5cb5dfd88efda3ee303605386efb743311d','2026-04-03 06:21:41'),
(21,2,'D1775199912','92c334a54ed822850e8a76823a5c4645dea737e3','2026-04-03 07:16:17'),
(22,2,'D1775199912','3075c6eaf87192a19f20640e6da0aa824cc480a8','2026-04-03 07:16:20'),
(23,2,'D1775199912','d0545a2ad7dd4fc2b28efcb6a49a24c8dcadf132','2026-04-03 07:16:22'),
(24,2,'D1775199912','1d0b04eec834d4750642e5c6d0c3421562bc4fc8','2026-04-03 07:16:22'),
(25,2,'D1775199912','18412a9707e1d9bad356766d2a1dc558f5285c31','2026-04-03 07:16:22'),
(26,2,'D1775199912','1114b86607b6dccead8563f2c57ca4bd4e52a968','2026-04-03 07:16:22'),
(27,2,'D1775199912','efdbba17c1215e70a936df9e76986b04fe3bfa77','2026-04-03 07:16:23'),
(28,2,'D1775199912','89d928beb5f0d3b28b44fbd7d3735e0625a9bd50','2026-04-03 07:16:23'),
(29,2,'D1775199912','3a7961bd77327da64bdf13341ee220b9b0c953f4','2026-04-03 07:16:24'),
(30,2,'D1775199912','80af354c089ace5e92cb2e872968d631be8546b7','2026-04-03 07:16:24'),
(31,2,'D1775199912','855b9ae70a4b0c1eac4802121f93a161083bb819','2026-04-03 07:16:25'),
(32,2,'D1775207413','d32e809f947b3c56baf48eebf7eacd03fa43b070','2026-04-10 06:52:52');
/*!40000 ALTER TABLE `forgot_password` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `history_transaksi`
--

DROP TABLE IF EXISTS `history_transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_transaksi` (
  `nomor` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_transaksi` int(11) NOT NULL,
  `id_driver` varchar(200) NOT NULL,
  `waktu` datetime NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL,
  `catatan` varchar(100) DEFAULT NULL,
  `proses` int(1) NOT NULL DEFAULT 0,
  `gender` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`nomor`) USING BTREE,
  UNIQUE KEY `nomor` (`nomor`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=317 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_transaksi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `history_transaksi` DISABLE KEYS */;
INSERT INTO `history_transaksi` VALUES
(1,1,'D0','2024-12-24 03:16:36',0,NULL,0,1),
(2,2,'D0','2024-12-24 03:20:28',0,NULL,0,1),
(3,3,'D0','2024-12-24 03:20:54',0,NULL,0,1),
(4,4,'D1735003893','2024-12-24 03:26:02',4,NULL,0,1),
(5,5,'D1735003893','2024-12-24 03:30:03',4,NULL,0,1),
(7,7,'D1735025353','2024-12-24 07:35:03',4,NULL,0,1),
(8,8,'D0','2024-12-24 07:36:13',1,NULL,0,1),
(9,9,'D0','2024-12-24 07:36:49',1,NULL,0,1),
(10,10,'D0','2024-12-24 07:36:56',0,NULL,0,1),
(12,12,'D1735025353','2025-01-10 06:43:32',4,NULL,0,1),
(14,14,'D0','2025-01-10 11:37:05',5,NULL,0,1),
(15,15,'D0','2025-01-10 11:51:38',5,NULL,0,1),
(16,16,'D0','2025-01-10 11:53:36',5,NULL,0,1),
(17,17,'D0','2025-01-10 11:55:19',5,NULL,0,1),
(18,18,'D0','2025-01-10 11:57:33',0,NULL,0,1),
(19,19,'D0','2025-01-10 11:58:23',0,NULL,0,1),
(20,20,'D0','2025-01-18 12:10:56',0,NULL,0,1),
(21,21,'D0','2025-01-18 12:14:14',0,NULL,0,1),
(22,22,'D0','2025-01-18 12:37:08',1,NULL,0,1),
(23,23,'D0','2025-02-26 02:09:20',5,NULL,0,1),
(24,24,'D0','2025-02-26 07:22:02',1,NULL,0,1),
(26,26,'D0','2025-02-27 01:11:33',0,NULL,0,1),
(27,27,'D0','2025-02-27 01:13:01',0,NULL,0,1),
(28,28,'D0','2025-02-27 01:14:11',1,NULL,0,1),
(29,29,'D0','2025-03-05 03:28:52',0,NULL,0,1),
(30,30,'D0','2025-03-05 03:30:20',0,NULL,0,1),
(31,31,'D0','2025-03-05 03:32:11',1,NULL,0,1),
(32,32,'D1735003893','2025-03-05 03:38:55',4,NULL,0,1),
(33,33,'D0','2025-03-06 07:03:46',0,NULL,0,1),
(34,34,'D1735003893','2025-03-06 07:04:56',4,NULL,0,1),
(35,35,'D0','2025-03-06 13:32:43',1,NULL,0,1),
(36,36,'D0','2025-03-06 14:17:06',0,NULL,0,1),
(37,37,'D0','2025-03-06 14:18:01',0,NULL,0,1),
(38,38,'D0','2025-03-06 14:20:24',0,NULL,0,1),
(39,39,'D0','2025-03-06 14:22:20',0,NULL,0,1),
(40,40,'D0','2025-03-06 14:24:02',0,NULL,0,1),
(41,41,'D0','2025-03-06 14:24:05',0,NULL,0,1),
(42,42,'D0','2025-03-06 14:24:56',0,NULL,0,1),
(43,43,'D0','2025-03-06 14:24:59',0,NULL,0,1),
(44,44,'D0','2025-03-06 14:26:10',0,NULL,0,1),
(45,45,'D0','2025-03-06 14:26:47',0,NULL,0,1),
(46,46,'D0','2025-03-06 14:27:19',0,NULL,0,1),
(47,47,'D0','2025-03-06 14:28:15',0,NULL,0,1),
(48,48,'D0','2025-03-06 14:28:39',0,NULL,0,1),
(49,49,'D0','2025-03-06 14:29:37',0,NULL,0,1),
(50,50,'D0','2025-03-06 14:29:39',0,NULL,0,1),
(51,51,'D0','2025-03-06 14:30:34',0,NULL,0,1),
(52,52,'D0','2025-03-06 14:30:40',0,NULL,0,1),
(53,53,'D0','2025-03-06 14:32:06',0,NULL,0,1),
(54,54,'D0','2025-03-06 14:32:08',0,NULL,0,1),
(55,55,'D0','2025-03-06 14:32:15',0,NULL,0,1),
(56,56,'D0','2025-03-06 14:32:59',0,NULL,0,1),
(57,57,'D0','2025-03-06 14:33:17',0,NULL,0,1),
(58,58,'D0','2025-03-06 14:33:55',0,NULL,0,1),
(59,59,'D0','2025-03-06 14:34:04',0,NULL,0,1),
(60,60,'D0','2025-03-06 14:34:31',0,NULL,0,1),
(61,61,'D0','2025-03-06 14:35:26',0,NULL,0,1),
(62,62,'D0','2025-03-06 14:36:01',0,NULL,0,1),
(63,63,'D0','2025-03-06 14:36:17',0,NULL,0,1),
(64,64,'D0','2025-03-06 14:37:07',0,NULL,0,1),
(65,65,'D0','2025-03-06 14:38:33',0,NULL,0,1),
(66,66,'D0','2025-03-06 14:39:05',0,NULL,0,1),
(67,67,'D0','2025-03-06 14:39:17',0,NULL,0,1),
(68,68,'D0','2025-03-06 14:40:01',0,NULL,0,1),
(69,69,'D0','2025-03-06 14:47:58',0,NULL,0,1),
(70,70,'D1735003893','2025-03-06 14:48:46',4,NULL,0,1),
(71,71,'D0','2025-03-06 14:49:21',0,NULL,0,1),
(72,72,'D0','2025-03-06 14:52:20',0,NULL,0,1),
(73,73,'D0','2025-03-06 14:52:45',0,NULL,0,1),
(74,74,'D0','2025-03-06 14:53:32',0,NULL,0,1),
(75,75,'D0','2025-03-06 14:53:32',0,NULL,0,1),
(76,76,'D0','2025-03-06 14:54:42',0,NULL,0,1),
(77,77,'D0','2025-03-06 14:55:39',0,NULL,0,1),
(78,78,'D0','2025-03-06 14:55:41',0,NULL,0,1),
(79,79,'D0','2025-03-06 14:57:21',0,NULL,0,1),
(80,80,'D0','2025-03-06 14:58:18',0,NULL,0,1),
(81,81,'D0','2025-03-06 15:00:10',0,NULL,0,1),
(82,82,'D0','2025-03-06 15:01:50',0,NULL,0,1),
(83,83,'D0','2025-03-06 15:02:55',0,NULL,0,1),
(84,84,'D0','2025-03-06 15:04:06',0,NULL,0,1),
(85,85,'D0','2025-03-06 15:05:14',0,NULL,0,1),
(86,86,'D1735003893','2025-03-06 15:06:16',4,NULL,0,1),
(87,87,'D0','2025-03-06 15:08:24',0,NULL,0,1),
(88,88,'D0','2025-03-06 15:09:17',0,NULL,0,1),
(89,89,'D1735003893','2025-03-06 15:10:28',4,NULL,0,1),
(90,90,'D0','2025-03-06 15:12:00',0,NULL,0,1),
(91,91,'D0','2025-03-06 15:12:57',0,NULL,0,1),
(93,93,'D0','2025-03-06 15:14:45',0,NULL,0,1),
(94,94,'D0','2025-03-06 15:16:12',1,NULL,0,1),
(95,95,'D0','2025-03-06 15:16:34',0,NULL,0,1),
(96,96,'D0','2025-03-06 15:16:59',0,NULL,0,1),
(97,97,'D0','2025-03-06 15:18:23',0,NULL,0,1),
(98,98,'D0','2025-03-06 15:18:52',0,NULL,0,1),
(99,99,'D0','2025-03-06 15:19:27',0,NULL,0,1),
(100,100,'D0','2025-03-06 15:19:52',0,NULL,0,1),
(101,101,'D0','2025-03-06 15:20:54',0,NULL,0,1),
(102,102,'D0','2025-03-06 15:21:45',0,NULL,0,1),
(103,103,'D1735003893','2025-03-06 15:23:24',4,NULL,0,1),
(104,104,'D1735003893','2025-03-06 15:24:42',4,NULL,0,1),
(105,105,'D0','2025-03-06 21:05:08',0,NULL,0,1),
(106,106,'D1735003893','2025-03-07 03:15:23',4,NULL,0,1),
(107,107,'D1735003893','2025-03-07 04:28:28',4,NULL,0,1),
(108,108,'D0','2025-03-10 18:26:23',0,NULL,0,1),
(109,109,'D0','2025-03-15 10:20:08',0,NULL,0,1),
(110,110,'D0','2025-03-17 06:51:04',0,NULL,0,1),
(111,111,'D0','2025-03-25 15:21:27',0,NULL,0,1),
(112,112,'D1735003893','2025-03-25 15:22:28',4,NULL,0,1),
(113,113,'D0','2025-04-16 11:12:43',1,NULL,0,1),
(114,114,'D0','2025-04-16 11:13:13',0,NULL,0,1),
(115,115,'D1735003893','2025-04-16 11:16:14',4,NULL,0,1),
(116,116,'D0','2025-04-30 11:45:58',0,NULL,0,1),
(117,117,'D0','2025-04-30 11:47:36',0,NULL,0,1),
(120,120,'D0','2025-05-07 23:55:33',1,NULL,0,1),
(121,121,'D0','2025-05-07 23:55:47',1,NULL,0,1),
(123,123,'D0','2025-05-08 00:00:22',0,NULL,0,1),
(124,124,'D0','2025-05-08 00:01:29',0,NULL,0,1),
(125,125,'D0','2025-05-08 00:05:42',0,NULL,0,1),
(126,126,'D0','2025-05-20 22:26:06',0,NULL,0,1),
(127,127,'D0','2025-07-11 02:36:05',5,NULL,0,1),
(128,128,'D0','2025-07-14 22:31:24',0,NULL,0,1),
(129,129,'D0','2025-07-15 09:42:28',0,NULL,0,1),
(130,130,'D0','2025-07-15 22:54:47',5,NULL,0,1),
(131,131,'D0','2025-07-19 16:01:56',5,NULL,0,1),
(132,132,'D0','2025-07-19 16:03:13',5,NULL,0,1),
(133,133,'D1735003893','2025-07-19 16:04:49',4,NULL,0,1),
(134,134,'D0','2025-07-19 16:11:34',5,NULL,0,1),
(135,135,'D0','2025-07-19 16:33:20',5,NULL,0,1),
(136,136,'D0','2025-07-19 16:39:54',5,NULL,0,1),
(137,137,'D0','2025-07-19 16:53:33',0,NULL,0,1),
(138,138,'D0','2025-07-19 16:55:00',5,NULL,0,1),
(139,139,'D0','2025-07-19 16:57:36',5,NULL,0,1),
(140,140,'D0','2025-07-19 16:59:27',0,NULL,0,1),
(141,141,'D0','2025-07-19 17:03:54',0,NULL,0,1),
(142,142,'D0','2025-07-19 17:05:03',0,NULL,0,1),
(143,143,'D0','2025-07-19 17:06:56',0,NULL,0,1),
(144,144,'D0','2025-07-19 17:08:14',0,NULL,0,1),
(145,145,'D0','2025-07-19 17:09:43',0,NULL,0,1),
(146,146,'D0','2025-07-19 17:12:49',0,NULL,0,1),
(147,147,'D0','2025-07-19 17:14:27',0,NULL,0,1),
(148,148,'D0','2025-07-19 17:16:35',0,NULL,0,1),
(149,149,'D0','2025-07-19 17:20:09',0,NULL,0,1),
(150,150,'D0','2025-07-19 17:22:16',0,NULL,0,1),
(151,151,'D0','2025-07-19 17:24:12',0,NULL,0,1),
(152,152,'D0','2025-07-19 17:30:07',0,NULL,0,1),
(153,153,'D0','2025-07-19 17:31:48',0,NULL,0,1),
(154,154,'D0','2025-07-19 17:34:26',0,NULL,0,1),
(155,155,'D0','2025-07-19 17:58:15',0,NULL,0,1),
(156,156,'D0','2025-07-19 17:59:30',0,NULL,0,1),
(157,157,'D0','2025-07-19 18:00:46',0,NULL,0,1),
(158,158,'D0','2025-07-19 18:34:02',0,NULL,0,1),
(159,159,'D0','2025-07-19 19:32:44',0,NULL,0,1),
(160,160,'D0','2025-07-19 19:37:36',0,NULL,0,1),
(161,161,'D0','2025-07-19 19:39:31',0,NULL,0,1),
(162,162,'D0','2025-07-19 19:42:10',0,NULL,0,1),
(163,163,'D0','2025-07-19 19:43:36',0,NULL,0,1),
(164,164,'D0','2025-07-19 19:45:24',0,NULL,0,1),
(165,165,'D0','2025-07-19 19:47:18',0,NULL,0,1),
(166,166,'D0','2025-07-19 19:48:16',0,NULL,0,1),
(167,167,'D0','2025-07-19 19:51:25',0,NULL,0,1),
(168,168,'D0','2025-07-19 19:52:22',0,NULL,0,1),
(169,169,'D0','2025-07-20 02:13:24',0,NULL,0,1),
(170,170,'D0','2025-07-20 02:22:44',0,NULL,0,1),
(171,171,'D0','2025-07-20 02:23:50',0,NULL,0,1),
(172,172,'D0','2025-07-20 02:26:23',0,NULL,0,1),
(173,173,'D0','2025-07-20 02:28:46',0,NULL,0,1),
(174,174,'D0','2025-07-20 02:29:26',0,NULL,0,1),
(175,175,'D0','2025-07-20 02:31:02',1,NULL,0,1),
(176,176,'D0','2025-07-20 02:32:33',0,NULL,0,1),
(177,177,'D0','2025-07-20 02:35:59',0,NULL,0,1),
(178,178,'D0','2025-07-20 02:41:52',0,NULL,0,1),
(179,179,'D0','2025-07-20 02:44:30',0,NULL,0,1),
(180,180,'D0','2025-07-20 02:45:29',0,NULL,0,1),
(181,181,'D0','2025-07-20 02:46:14',0,NULL,0,1),
(182,182,'D0','2025-07-20 02:47:31',0,NULL,0,1),
(183,183,'D0','2025-07-20 02:49:06',0,NULL,0,1),
(184,184,'D0','2025-07-20 02:50:26',0,NULL,0,1),
(185,185,'D0','2025-07-20 03:20:11',0,NULL,0,1),
(186,186,'D0','2025-07-20 03:21:29',0,NULL,0,1),
(187,187,'D0','2025-07-20 03:24:07',0,NULL,0,1),
(188,188,'D0','2025-07-20 03:24:58',0,NULL,0,1),
(189,189,'D0','2025-07-20 03:25:49',0,NULL,0,1),
(190,190,'D0','2025-07-20 03:27:56',0,NULL,0,1),
(191,191,'D0','2025-07-20 03:32:21',0,NULL,0,1),
(192,192,'D0','2025-07-20 03:38:12',0,NULL,0,1),
(193,193,'D0','2025-07-20 05:14:27',0,NULL,0,1),
(194,194,'D0','2025-07-20 05:42:24',0,NULL,0,1),
(195,195,'D0','2025-07-20 05:43:26',0,NULL,0,1),
(196,196,'D0','2025-07-20 05:44:34',0,NULL,0,1),
(197,197,'D0','2025-07-20 05:45:19',0,NULL,0,1),
(198,198,'D0','2025-07-20 05:47:32',0,NULL,0,1),
(199,199,'D0','2025-07-20 05:48:25',0,NULL,0,1),
(200,200,'D0','2025-07-20 05:50:42',0,NULL,0,1),
(201,201,'D0','2025-07-20 05:51:33',0,NULL,0,1),
(202,202,'D0','2025-07-20 05:52:14',0,NULL,0,1),
(203,203,'D0','2025-07-20 05:54:20',1,NULL,0,1),
(204,204,'D0','2025-07-20 05:54:21',0,NULL,0,1),
(205,205,'D0','2025-07-20 05:55:03',0,NULL,0,1),
(206,206,'D0','2025-07-20 05:55:58',0,NULL,0,1),
(207,207,'D0','2025-07-20 07:37:55',0,NULL,0,1),
(208,208,'D0','2025-07-20 07:38:41',0,NULL,0,1),
(209,209,'D0','2025-07-20 08:59:05',0,NULL,0,1),
(210,210,'D1735025353','2025-07-20 16:47:03',4,NULL,0,1),
(211,211,'D0','2025-07-20 16:48:27',0,NULL,0,1),
(212,212,'D1735025353','2025-07-20 16:51:07',4,NULL,0,1),
(213,213,'D1735003893','2025-07-20 17:10:42',4,NULL,0,1),
(214,214,'D1735003893','2025-07-20 17:20:57',4,NULL,0,1),
(215,215,'D1735003893','2025-07-20 17:26:50',4,NULL,0,1),
(216,216,'D1735003893','2025-07-20 17:32:43',4,NULL,0,1),
(217,217,'D0','2025-07-20 17:37:26',0,NULL,0,1),
(218,218,'D1735003893','2025-07-20 17:38:15',4,NULL,0,1),
(219,219,'D0','2025-07-21 10:06:37',0,NULL,0,1),
(220,220,'D0','2025-07-24 05:52:42',0,NULL,0,1),
(221,221,'D1735003893','2025-07-24 05:57:34',4,NULL,0,1),
(222,222,'D0','2025-07-24 05:58:01',0,NULL,0,1),
(223,223,'D1735003893','2025-07-24 06:32:45',5,NULL,0,1),
(224,224,'D0','2025-07-24 14:41:02',1,NULL,0,1),
(225,225,'D0','2025-07-24 14:41:31',0,NULL,0,1),
(226,226,'D0','2025-07-24 14:43:36',0,NULL,0,1),
(227,227,'D1735025353','2025-07-24 14:44:26',5,NULL,0,1),
(228,228,'D0','2025-07-24 14:44:51',0,NULL,0,1),
(229,229,'D1735003893','2025-07-24 14:46:39',5,NULL,0,1),
(230,230,'D0','2025-07-24 14:49:02',0,NULL,0,1),
(231,231,'D1735025353','2025-07-24 15:19:26',4,NULL,0,1),
(232,232,'D1735025353','2025-07-24 15:47:19',4,NULL,0,1),
(233,233,'D1735003893','2025-07-24 15:52:14',4,NULL,0,1),
(234,234,'D1735003893','2025-07-24 16:00:11',4,NULL,0,1),
(235,235,'D1735025353','2025-07-24 16:03:41',4,NULL,0,1),
(236,236,'D0','2025-07-25 08:36:46',0,NULL,0,1),
(237,237,'D0','2025-07-25 08:38:21',0,NULL,0,1),
(238,238,'D0','2025-07-27 05:53:58',0,NULL,0,1),
(239,239,'D0','2025-07-27 05:58:22',0,NULL,0,1),
(240,240,'D0','2025-07-27 05:59:55',0,NULL,0,1),
(241,241,'D0','2025-07-27 06:17:10',0,NULL,0,1),
(242,242,'D0','2025-07-27 06:19:06',0,NULL,0,1),
(243,243,'D1735025353','2025-07-27 09:09:52',4,NULL,0,1),
(244,244,'D0','2025-07-28 03:55:34',0,NULL,0,1),
(245,245,'D0','2025-07-28 03:56:35',0,NULL,0,1),
(246,246,'D0','2025-07-28 03:57:32',0,NULL,0,1),
(247,247,'D0','2025-07-28 04:01:24',0,NULL,0,1),
(248,248,'D1735025353','2025-07-28 04:02:53',4,NULL,0,1),
(249,249,'D1735025353','2025-07-28 04:10:38',5,NULL,0,1),
(250,250,'D0','2025-07-28 04:11:51',0,NULL,0,1),
(251,251,'D1735025353','2025-07-28 04:13:44',4,NULL,0,1),
(252,252,'D0','2025-07-28 05:28:22',0,NULL,0,1),
(253,253,'D0','2025-07-28 05:29:20',0,NULL,0,1),
(255,255,'D0','2025-08-21 07:22:23',1,NULL,0,1),
(256,256,'D0','2025-08-21 07:22:49',0,NULL,0,1),
(257,257,'D1735003893','2025-09-12 12:30:47',4,NULL,0,1),
(258,258,'D0','2025-09-12 12:30:48',0,NULL,0,1),
(259,259,'D0','2025-09-12 12:31:27',1,NULL,0,1),
(260,260,'D0','2025-09-12 13:30:13',0,NULL,0,1),
(261,261,'D1735003893','2025-09-12 13:31:32',4,NULL,0,1),
(262,262,'D0','2025-09-12 13:38:10',0,NULL,0,1),
(263,263,'D0','2025-09-12 13:38:44',0,NULL,0,1),
(264,264,'D0','2025-09-12 13:40:05',0,NULL,0,1),
(265,265,'D0','2025-09-12 13:41:02',0,NULL,0,1),
(266,266,'D1735003893','2025-09-12 13:42:18',5,NULL,0,1),
(267,267,'D1735003893','2025-09-12 13:47:19',4,NULL,0,1),
(268,268,'D1735003893','2025-09-12 13:58:49',4,NULL,0,1),
(269,269,'D1735003893','2025-09-13 07:08:11',4,NULL,0,1),
(270,270,'D0','2025-09-13 07:15:06',0,NULL,0,1),
(271,271,'D1735003893','2025-09-13 07:17:24',4,NULL,0,1),
(272,272,'D0','2025-10-24 01:55:48',0,NULL,0,1),
(273,273,'D0','2025-10-24 02:14:46',0,NULL,0,1),
(274,274,'D0','2025-11-04 15:16:45',0,NULL,0,1),
(275,275,'D0','2025-12-04 16:57:00',0,NULL,0,1),
(276,276,'D0','2025-12-14 02:45:54',0,NULL,0,1),
(277,277,'D0','2025-12-14 10:53:14',0,NULL,0,1),
(278,278,'D0','2026-01-08 13:55:51',0,NULL,0,1),
(279,279,'D1735003893','2026-01-09 03:04:10',4,NULL,0,1),
(280,280,'D1735003893','2026-01-09 03:12:30',5,NULL,0,1),
(281,281,'D1735003893','2026-01-09 03:36:55',4,NULL,0,1),
(282,282,'D1735003893','2026-01-09 03:41:09',4,NULL,0,1),
(283,283,'D0','2026-01-09 03:53:45',1,NULL,0,1),
(284,285,'D0','2026-01-09 03:55:48',0,NULL,0,1),
(285,286,'D0','2026-01-09 03:56:36',1,NULL,0,1),
(286,287,'D0','2026-01-09 03:57:05',0,NULL,0,1),
(287,288,'D1735003893','2026-01-09 03:57:31',4,NULL,0,1),
(288,289,'D1735003893','2026-01-09 04:08:33',4,NULL,0,1),
(289,291,'D0','2026-01-11 13:55:39',0,NULL,0,1),
(290,292,'D0','2026-02-14 15:41:01',0,NULL,0,1),
(291,293,'D0','2026-03-18 19:04:54',0,NULL,0,1),
(292,294,'D0','2026-03-25 13:10:42',0,NULL,0,1),
(293,295,'D0','2026-04-03 06:47:54',0,NULL,0,1),
(294,296,'D0','2026-04-03 06:49:01',0,NULL,0,1),
(295,297,'D0','2026-04-10 06:06:16',1,NULL,0,1),
(296,298,'D0','2026-04-10 06:06:27',0,NULL,0,1),
(297,299,'D0','2026-04-10 06:31:25',1,NULL,0,1),
(298,300,'D0','2026-04-10 06:31:39',0,NULL,0,1),
(299,301,'D0','2026-04-10 06:40:24',0,NULL,0,1),
(300,302,'D0','2026-04-10 06:45:07',1,NULL,0,1),
(301,303,'D0','2026-04-10 06:45:18',0,NULL,0,1),
(302,304,'D1775207413','2026-04-10 06:55:59',5,NULL,0,1),
(303,305,'D0','2026-04-10 06:56:13',1,NULL,0,1),
(304,306,'D0','2026-04-10 06:56:39',0,NULL,0,1),
(305,307,'D1775207413','2026-04-14 12:27:24',4,NULL,0,1),
(306,308,'D0','2026-04-14 12:29:58',0,NULL,0,1),
(307,309,'D0','2026-04-14 12:31:11',0,NULL,0,1),
(308,310,'D0','2026-04-14 12:34:43',0,NULL,0,1),
(309,311,'D1775207413','2026-04-14 22:39:12',4,NULL,0,1),
(310,312,'D0','2026-04-14 22:41:35',0,NULL,0,1),
(311,313,'D0','2026-04-14 22:45:48',0,NULL,0,1),
(312,314,'D1735003893','2026-06-02 00:01:40',4,NULL,0,1),
(313,315,'D0','2026-06-02 00:05:48',1,NULL,0,1),
(314,316,'D1735003893','2026-06-02 00:12:38',4,NULL,0,1),
(315,317,'D1735003893','2026-06-03 14:44:05',4,NULL,0,1),
(316,319,'D0','2026-06-03 14:44:06',0,NULL,0,1);
/*!40000 ALTER TABLE `history_transaksi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `inbok`
--

DROP TABLE IF EXISTS `inbok`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbok` (
  `no` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id` varchar(255) NOT NULL,
  `idpesan` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `konten` text DEFAULT NULL,
  `date` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbok`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `inbok` DISABLE KEYS */;
INSERT INTO `inbok` VALUES
(13,'P1701034124','467359','tes','halo','2024/01/20'),
(14,'P1701034124','194892','ddd','dededed','2024/01/20'),
(15,'P1701034124','152834','ddddsdsd','qwfwqfwqfwqf','2024/01/20');
/*!40000 ALTER TABLE `inbok` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id_item` int(11) NOT NULL AUTO_INCREMENT,
  `id_merchant` varchar(100) NOT NULL,
  `nama_item` varchar(250) NOT NULL,
  `harga_item` int(11) NOT NULL,
  `harga_promo` int(11) NOT NULL,
  `kategori_item` varchar(200) NOT NULL,
  `deskripsi_item` text NOT NULL,
  `foto_item` varchar(250) NOT NULL,
  `created_item` timestamp NOT NULL DEFAULT current_timestamp(),
  `status_item` varchar(10) NOT NULL,
  `status_promo` varchar(10) NOT NULL,
  PRIMARY KEY (`id_item`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES
(1,'1','nasi',10000,0,'2','cek','1736491373-53756.jpg','2025-01-10 06:42:53','1','0'),
(2,'1','tyf',2000,0,'2','fffgghhh','1752947050-71165.jpg','2025-07-19 17:44:10','1','0'),
(3,'1','hagsg',1234567,0,'3','bdndj','1752947116-61144.jpg','2025-07-19 17:45:16','1','0'),
(4,'2','ter',111111,0,'4','fvg hhjj','1752947697-85345.jpg','2025-07-19 17:54:57','1','0'),
(5,'2','teh',3000,0,'5','tes','1752953711-30415.jpg','2025-07-19 19:35:11','1','0'),
(7,'2','klm',10000,9000,'4','hdjdjkdjjdjkd','1753542602-61254.jpg','2025-07-26 15:10:02','1','1'),
(8,'4','ayan bakar',250000,0,'7','bsbsgs','1755590057-1292.jpg','2025-08-19 07:54:17','1','0'),
(9,'6','Soto Ayam',15000,0,'8','nasi + kuah soto','bdd848b4e4a9d44f5e07b97ed8690522.jpg','2025-09-15 06:13:29','1','0'),
(10,'6','es teh',5000,0,'9','es teh','663442ee98e8afdcd3ed862c35088b19.jpg','2025-09-15 06:14:02','1','0'),
(11,'1','es teh',10000,0,'10','es teh','1757917812-11412.jpg','2025-09-15 06:30:12','1','0'),
(12,'7','es teh',10000,0,'11','es teh','7f4e96bb8c3b0444d9d6c5eb907ddc69.jpg','2025-09-15 06:42:19','1','0'),
(13,'5','nagor',15000,0,'12','','dab5297fc206a6fbb91c924c430f7300.jpeg','2025-12-14 03:04:07','1','0'),
(14,'5','es teh',5000,3000,'13','','5e349eb515138c44a223d8951257e925.jpeg','2025-12-14 03:05:09','1','1');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `kategori_news`
--

DROP TABLE IF EXISTS `kategori_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategori_news` (
  `id_kategori_news` int(11) NOT NULL AUTO_INCREMENT,
  `kategori` varchar(250) NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_kategori_news`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategori_news`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `kategori_news` DISABLE KEYS */;
INSERT INTO `kategori_news` VALUES
(1,'Covid-19','2021-08-14 07:40:19'),
(2,'Olahraga','2021-08-14 07:43:25'),
(3,'Seputar Indramayu','2021-12-23 20:15:34'),
(4,'Bank sampah kutim','2022-06-10 01:25:28'),
(5,'Promo','2022-08-18 20:20:02');
/*!40000 ALTER TABLE `kategori_news` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `kendaraan`
--

DROP TABLE IF EXISTS `kendaraan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kendaraan` (
  `id_k` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_driver` varchar(255) NOT NULL,
  `merek` varchar(20) NOT NULL,
  `tipe` varchar(20) NOT NULL,
  `jenis` char(1) NOT NULL,
  `nomor_kendaraan` varchar(200) NOT NULL,
  `warna` varchar(200) NOT NULL,
  `no_stnk` varchar(255) NOT NULL,
  `foto_stnk` varchar(255) NOT NULL,
  PRIMARY KEY (`id_k`) USING BTREE,
  UNIQUE KEY `id` (`id_k`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kendaraan`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `kendaraan` DISABLE KEYS */;
INSERT INTO `kendaraan` VALUES
(81,'D1686308522','Honda','Mio m3','2','DN121','Hijau','8289892','1686312771-22359.jpg'),
(85,'D1688601576','Honda','metik','2','DN 7354','Mera','355353','1688602149-9592.jpg'),
(87,'D1704720762','jsjsjs','metik','','dn28282','merah','84545544','1704720855-84121.jpg'),
(88,'','ryryr','ryry','','ryry','ryry','',''),
(89,'D1704724392','mkjj','vggg','','ccc','ccff','87855','1704724478-89320.jpg'),
(90,'D1704724392','mkjj','vggg','','ccc','ccffc','878555','1704725073-21771.jpg'),
(91,'D1704725312','usysys','hsysys','','ysysys','ysysts','12121212','1704725424-26023.jpg'),
(92,'D1704726234','rfff','ffff','','fffff','fffr','2588','1704726304-64551.jpg'),
(93,'D1704727266','ysy6s','tzts','','rsrrs','tststs','5755757','1704727403-88740.jpg'),
(94,'D1704728014','gGags','gagags','','ata5a','fafafa','8484854','1704728079-38150.jpg'),
(95,'D1704728014','gGags','gagags','','ata5a','fafafa','8484854','1704728123-97919.jpg'),
(96,'D1704728014','gGags','gagags','','ata5a','fafafa','8484854','1704728173-54273.jpg'),
(97,'D1704791080','bshj','gagga','','gagagga',' VvV','878878877','1704791230-8886.jpg'),
(98,'D1705009289','gffg','dddr','','dddd','ssss','44445555','1705009382-93501.jpg'),
(104,'D1705126060','Ymaj','matic','','r123','hitam','123456','1705127862-43135.jpg'),
(105,'D1705128153','dddfr','ffff','','ffff','dddddd','8778555','1705128224-32736.jpg'),
(106,'D1718020084','honda','metik','','DN7272','mera','84845454','1718020312-62938.jpg'),
(107,'D1730293266','bshaha','tatata','','fafara','adfafa','5454554','1730293507-9874.jpg'),
(108,'D1730790213','Ymaha','Matic','','R123','HITAM','3456123','1730790764-4412.jpg'),
(109,'D1730863862','ghgg','ghhh','','gjhy','hh','66666','1730864164-41900.jpg'),
(110,'D1732029317','mehs','gagata','','faffa','affata','5754546454','1732029469-20577.jpg'),
(111,'D1734184557','Mio','Matic','2','m2234ck','putih','16496637966466','1734184721-20197.jpg'),
(112,'D1735003893','honda','metik','','DD5252662','merah','64545454','1735003979-71385.jpg'),
(113,'D1735025353','honda','meruk','','dd11333','mera','646464','1735025543-90707.jpg'),
(114,'D1735092793','beat','cth: matic','1','p 6141 lb','gray','09053299','1735093699-20988.jpg'),
(115,'','Grabb','Motor','','62718111','Hitam','',''),
(116,'','ymh','mio','','ad 1111 de','hitm','',''),
(117,'','ymh','mio','','ad 1111 de','hitm','',''),
(118,'','ymh','mio','','ad 1111 de','hitm','',''),
(119,'D1752996584','honda','matic','','ad 3456 lk','hitam','2580494675','1752996837-53391.jpg'),
(120,'D1754050053','vario','','','bm123bm','hitam','12345','1754050896-31676.jpg'),
(121,'','Honda','Suv','','5445446754675','Putih','',''),
(122,'D1775207413','honda','matik','','P 5757 sk','hijau','58555455','1775211076-34681.jpg');
/*!40000 ALTER TABLE `kendaraan` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `kode_menu_web`
--

DROP TABLE IF EXISTS `kode_menu_web`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kode_menu_web` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_group` varchar(2) NOT NULL,
  `menu_kode` varchar(10) NOT NULL,
  `menu_set` tinyint(1) NOT NULL DEFAULT 1,
  `menu_reg_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `menu_reg_ip` varchar(20) NOT NULL,
  `menu_reg_alias` varchar(30) NOT NULL,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=942 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci AVG_ROW_LENGTH=73 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kode_menu_web`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `kode_menu_web` DISABLE KEYS */;
INSERT INTO `kode_menu_web` VALUES
(602,'6','mn01',1,'2022-04-10 22:24:12','',''),
(603,'6','mn02',1,'2022-04-10 22:24:12','',''),
(604,'6','mn0201',1,'2022-04-10 22:24:12','',''),
(605,'6','mn0202',1,'2022-04-10 22:24:12','',''),
(606,'6','mn0203',1,'2022-04-10 22:24:12','',''),
(607,'6','mn03',1,'2022-04-10 22:24:12','',''),
(608,'6','mn0301',1,'2022-04-10 22:24:12','',''),
(609,'6','mn0302',1,'2022-04-10 22:24:12','',''),
(610,'6','mn0303',1,'2022-04-10 22:24:12','',''),
(611,'6','mn04',1,'2022-04-10 22:24:12','',''),
(612,'6','mn05',1,'2022-04-10 22:24:12','',''),
(613,'6','mn0501',1,'2022-04-10 22:24:12','',''),
(614,'6','mn0502',1,'2022-04-10 22:24:12','',''),
(615,'6','mn0503',1,'2022-04-10 22:24:12','',''),
(616,'6','mn06',1,'2022-04-10 22:24:12','',''),
(617,'6','mn0601',1,'2022-04-10 22:24:12','',''),
(618,'6','mn07',1,'2022-04-10 22:24:12','',''),
(619,'6','mn0701',1,'2022-04-10 22:24:12','',''),
(620,'6','mn0702',1,'2022-04-10 22:24:12','',''),
(621,'6','mn08',1,'2022-04-10 22:24:12','',''),
(622,'6','mn09',1,'2022-04-10 22:24:12','',''),
(623,'6','mn10',1,'2022-04-10 22:24:12','',''),
(624,'6','mn11',1,'2022-04-10 22:24:12','',''),
(625,'6','mn12',1,'2022-04-10 22:24:12','',''),
(626,'6','mn13',1,'2022-04-10 22:24:12','',''),
(627,'6','mn15',1,'2022-04-10 22:24:12','',''),
(738,'7','mn01',1,'2022-04-24 18:50:58','',''),
(739,'7','mn02',1,'2022-04-24 18:50:58','',''),
(740,'7','mn0201',1,'2022-04-24 18:50:58','',''),
(741,'7','mn0202',1,'2022-04-24 18:50:58','',''),
(742,'7','mn0203',1,'2022-04-24 18:50:58','',''),
(743,'7','mn03',1,'2022-04-24 18:50:58','',''),
(744,'7','mn0301',1,'2022-04-24 18:50:58','',''),
(745,'7','mn0302',1,'2022-04-24 18:50:58','',''),
(746,'7','mn0303',1,'2022-04-24 18:50:58','',''),
(747,'7','mn04',1,'2022-04-24 18:50:58','',''),
(748,'7','mn05',1,'2022-04-24 18:50:58','',''),
(749,'7','mn0501',1,'2022-04-24 18:50:58','',''),
(750,'7','mn0502',1,'2022-04-24 18:50:58','',''),
(751,'7','mn0503',1,'2022-04-24 18:50:58','',''),
(752,'7','mn06',1,'2022-04-24 18:50:58','',''),
(753,'7','mn0601',1,'2022-04-24 18:50:58','',''),
(754,'7','mn07',1,'2022-04-24 18:50:58','',''),
(755,'7','mn0701',1,'2022-04-24 18:50:58','',''),
(756,'7','mn0702',1,'2022-04-24 18:50:58','',''),
(757,'7','mn08',1,'2022-04-24 18:50:58','',''),
(758,'7','mn09',1,'2022-04-24 18:50:58','',''),
(759,'7','mn10',1,'2022-04-24 18:50:58','',''),
(760,'7','mn11',1,'2022-04-24 18:50:58','',''),
(761,'7','mn12',1,'2022-04-24 18:50:58','',''),
(762,'7','mn13',1,'2022-04-24 18:50:58','',''),
(763,'7','mn14',1,'2022-04-24 18:50:58','',''),
(764,'7','mn1401',1,'2022-04-24 18:50:58','',''),
(765,'7','mn1402',1,'2022-04-24 18:50:58','',''),
(766,'7','mn15',1,'2022-04-24 18:50:58','',''),
(797,'1','mn01',1,'2022-07-04 08:26:11','',''),
(798,'1','mn02',1,'2022-07-04 08:26:11','',''),
(799,'1','mn0201',1,'2022-07-04 08:26:11','',''),
(800,'1','mn0202',1,'2022-07-04 08:26:11','',''),
(801,'1','mn0203',1,'2022-07-04 08:26:11','',''),
(802,'1','mn03',1,'2022-07-04 08:26:11','',''),
(803,'1','mn0301',1,'2022-07-04 08:26:11','',''),
(804,'1','mn0302',1,'2022-07-04 08:26:11','',''),
(805,'1','mn0303',1,'2022-07-04 08:26:11','',''),
(806,'1','mn04',1,'2022-07-04 08:26:11','',''),
(807,'1','mn05',1,'2022-07-04 08:26:11','',''),
(808,'1','mn0501',1,'2022-07-04 08:26:11','',''),
(809,'1','mn0502',1,'2022-07-04 08:26:11','',''),
(810,'1','mn0503',1,'2022-07-04 08:26:11','',''),
(811,'1','mn06',1,'2022-07-04 08:26:11','',''),
(812,'1','mn0601',1,'2022-07-04 08:26:11','',''),
(813,'1','mn07',1,'2022-07-04 08:26:11','',''),
(814,'1','mn0701',1,'2022-07-04 08:26:11','',''),
(815,'1','mn0702',1,'2022-07-04 08:26:11','',''),
(816,'1','mn08',1,'2022-07-04 08:26:11','',''),
(817,'1','mn09',1,'2022-07-04 08:26:11','',''),
(818,'1','mn10',1,'2022-07-04 08:26:11','',''),
(819,'1','mn11',1,'2022-07-04 08:26:11','',''),
(820,'1','mn12',1,'2022-07-04 08:26:11','',''),
(821,'1','mn13',1,'2022-07-04 08:26:11','',''),
(822,'1','mn14',1,'2022-07-04 08:26:11','',''),
(823,'1','mn1401',1,'2022-07-04 08:26:11','',''),
(824,'1','mn1402',1,'2022-07-04 08:26:11','',''),
(825,'1','mn15',1,'2022-07-04 08:26:11','',''),
(826,'1','mn16',1,'2022-07-04 08:26:11','',''),
(827,'1','mn17',1,'2022-07-04 08:26:11','',''),
(828,'8','on',1,'2022-08-18 22:14:47','',''),
(829,'8','mn0201',1,'2022-08-18 22:14:47','',''),
(830,'8','mn0202',1,'2022-08-18 22:14:47','',''),
(831,'8','mn0203',1,'2022-08-18 22:14:47','',''),
(832,'8','on',1,'2022-08-18 22:14:47','',''),
(833,'8','on',1,'2022-08-18 22:14:47','',''),
(834,'8','on',1,'2022-08-18 22:14:47','',''),
(835,'8','on',1,'2022-08-18 22:14:47','',''),
(836,'8','on',1,'2022-08-18 22:14:47','',''),
(837,'8','mn1402',1,'2022-08-18 22:14:47','',''),
(838,'9','on',1,'2022-10-30 15:39:18','',''),
(839,'9','mn0302',1,'2022-10-30 15:39:18','',''),
(908,'10','mn01',1,'2023-06-15 15:07:50','',''),
(909,'10','mn02',1,'2023-06-15 15:07:50','',''),
(910,'10','mn0201',1,'2023-06-15 15:07:50','',''),
(911,'10','mn0202',1,'2023-06-15 15:07:50','',''),
(912,'10','mn0203',1,'2023-06-15 15:07:50','',''),
(913,'10','mn03',1,'2023-06-15 15:07:50','',''),
(914,'10','mn0301',1,'2023-06-15 15:07:50','',''),
(915,'10','mn0302',1,'2023-06-15 15:07:50','',''),
(916,'10','mn0303',1,'2023-06-15 15:07:50','',''),
(917,'10','mn04',1,'2023-06-15 15:07:50','',''),
(918,'10','mn05',1,'2023-06-15 15:07:50','',''),
(919,'10','mn0501',1,'2023-06-15 15:07:50','',''),
(920,'10','mn0502',1,'2023-06-15 15:07:50','',''),
(921,'10','mn0503',1,'2023-06-15 15:07:50','',''),
(922,'10','mn06',1,'2023-06-15 15:07:50','',''),
(923,'10','mn0601',1,'2023-06-15 15:07:50','',''),
(924,'10','mn0602',1,'2023-06-15 15:07:50','',''),
(925,'10','mn0603',1,'2023-06-15 15:07:50','',''),
(926,'10','mn0604',1,'2023-06-15 15:07:50','',''),
(927,'10','mn0605',1,'2023-06-15 15:07:50','',''),
(928,'10','mn08',1,'2023-06-15 15:07:50','',''),
(929,'10','mn09',1,'2023-06-15 15:07:50','',''),
(930,'10','mn10',1,'2023-06-15 15:07:50','',''),
(931,'10','mn11',1,'2023-06-15 15:07:50','',''),
(932,'10','mn12',1,'2023-06-15 15:07:50','',''),
(933,'10','mn13',1,'2023-06-15 15:07:50','',''),
(934,'10','mn14',1,'2023-06-15 15:07:50','',''),
(935,'10','mn1401',1,'2023-06-15 15:07:50','',''),
(936,'10','mn1402',1,'2023-06-15 15:07:50','',''),
(937,'10','mn16',1,'2023-06-15 15:07:50','',''),
(938,'10','mn1601',1,'2023-06-15 15:07:50','',''),
(939,'10','mn1602',1,'2023-06-15 15:07:50','',''),
(940,'10','mn1603',1,'2023-06-15 15:07:50','',''),
(941,'10','mn17',1,'2023-06-15 15:07:50','','');
/*!40000 ALTER TABLE `kode_menu_web` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `kode_otp`
--

DROP TABLE IF EXISTS `kode_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kode_otp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regtime` datetime NOT NULL,
  `id_user` varchar(20) NOT NULL,
  `kode` varchar(10) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kode_otp`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `kode_otp` DISABLE KEYS */;
INSERT INTO `kode_otp` VALUES
(1,'2024-12-24 08:31:34','D1735003893','087741',9),
(2,'2024-12-24 08:33:16','D1735003893','875968',9),
(3,'2024-12-24 08:35:05','P1735004105','855656',9),
(4,'2024-12-24 08:39:44','M1735004384','297730',9),
(5,'2024-12-24 08:40:47','M1735004384','265809',9),
(6,'2024-12-24 10:10:56','P1735004105','859821',9),
(7,'2024-12-24 10:15:42','D1735003893','681635',9),
(8,'2024-12-24 13:51:58','P1735023118','827857',9),
(9,'2024-12-24 14:29:14','D1735025353','300164',9),
(10,'2024-12-24 14:32:32','D1735025353','907904',9),
(11,'2024-12-25 09:08:44','D1735003893','683404',9),
(12,'2024-12-25 09:09:27','D1735003893','335250',9),
(13,'2024-12-25 09:11:30','D1735003893','917778',9),
(14,'2024-12-25 09:13:14','D1735092793','257421',9),
(15,'2024-12-25 09:15:03','P1735092903','319297',9),
(16,'2024-12-25 09:28:26','D1735092793','305284',9),
(17,'2024-12-25 09:32:19','D1735093935','449654',0),
(18,'2024-12-25 09:35:44','D1735003893','223895',9),
(19,'2024-12-25 09:48:48','D1735003893','729898',9),
(20,'2024-12-25 09:50:01','D1735003893','321903',9),
(21,'2024-12-25 09:50:15','D1735092793','520703',9),
(22,'2024-12-25 09:50:16','D1735095016','611940',9),
(23,'2024-12-25 09:53:38','D1735003893','872254',9),
(24,'2024-12-25 09:53:59','D1735095016','838799',0),
(25,'2025-01-10 18:49:12','D1736509751','778401',0),
(26,'2025-04-16 17:27:57','D1744799277','202284',0),
(27,'2025-07-10 20:47:07','D1735092793','427885',9),
(28,'2025-07-10 20:48:56','D1735092793','431975',9),
(29,'2025-07-10 20:51:57','D1735092793','146483',9),
(30,'2025-07-10 20:53:43','D1735092793','110623',9),
(31,'2025-07-10 20:56:44','D1735092793','779067',0),
(32,'2025-07-10 22:48:56','D1735003893','100449',9),
(33,'2025-07-16 09:12:12','P1735004105','134704',9),
(34,'2025-07-20 01:04:57','D1752948297','994959',0),
(35,'2025-07-20 01:05:45','D1752948344','340956',0),
(36,'2025-07-20 01:24:02','D1752949442','518317',0),
(37,'2025-07-20 14:29:44','D1752996584','225877',0),
(38,'2025-07-21 00:45:13','P1753033513','297256',9),
(39,'2025-07-21 17:05:17','P1753033513','622744',0),
(40,'2025-07-27 15:04:45','P1753603485','693879',0),
(41,'2025-08-01 19:07:33','D1754050053','879018',9),
(42,'2025-08-01 19:10:41','D1754050053','691592',0),
(43,'2025-08-01 19:14:26','P1754050466','661551',9),
(44,'2025-08-01 19:32:20','P1754050466','124600',0),
(45,'2025-08-19 22:45:02','D1755618301','533797',9),
(46,'2025-08-20 00:06:56','D1755618301','678431',0),
(47,'2025-08-21 14:16:17','D1755760577','518723',0),
(48,'2025-08-21 14:17:55','D1755760675','575161',0),
(49,'2025-09-12 10:02:01','P1757646121','145603',0),
(50,'2025-09-26 11:40:40','D1758861639','197570',0),
(51,'2025-10-04 12:09:45','D1759554584','159666',9),
(52,'2025-10-04 12:13:03','D1759554584','135996',9),
(53,'2025-10-04 13:06:43','D1759554584','753410',9),
(54,'2025-10-04 13:09:55','D1759554584','544539',9),
(55,'2025-10-04 13:31:44','P1759559503','510740',9),
(56,'2025-10-04 14:47:21','P1759559503','764666',9),
(57,'2025-10-04 15:54:13','M1759568052','022403',9),
(58,'2025-10-08 13:13:28','P1759904007','244371',0),
(59,'2025-10-14 12:35:47','P1760420146','416271',9),
(60,'2025-10-14 13:40:46','P1760420146','632006',0),
(61,'2025-10-14 13:41:07','P1735004105','177471',9),
(62,'2025-10-15 10:02:07','P1735004105','962695',9),
(63,'2025-10-15 14:24:29','P1735004105','003052',9),
(64,'2025-10-15 14:28:54','P1735004105','988356',9),
(65,'2025-10-15 14:32:01','P1735004105','259113',9),
(66,'2025-11-01 13:00:57','P1761976856','533508',9),
(67,'2025-11-02 16:24:58','P1762075498','805864',9),
(68,'2025-11-04 15:32:00','P1735004105','924014',9),
(69,'2025-11-04 22:09:09','D1735003893','020722',0),
(70,'2025-11-04 22:13:08','P1735004105','140974',9),
(71,'2025-12-13 07:25:32','P1765585531','249628',9),
(72,'2025-12-13 08:40:00','P1765585531','831773',0),
(73,'2025-12-13 08:45:08','P1735004105','632290',9),
(74,'2025-12-13 08:45:59','P1735004105','787007',0),
(75,'2026-01-09 10:28:19','D1767929299','445513',0),
(76,'2026-04-03 13:26:29','P1775197589','699655',0),
(77,'2026-04-03 14:05:12','D1775199912','333694',0),
(78,'2026-04-03 16:10:13','D1775207413','368019',9),
(79,'2026-04-03 17:02:49','D1775207413','349616',0),
(80,'2026-04-14 19:24:32','P1776169472','442481',0),
(81,'2026-06-02 06:26:15','D1780356375','625858',0),
(82,'2026-06-04 14:43:46','P1780559025','511787',0),
(83,'2026-06-04 14:54:24','D1780559663','090946',0);
/*!40000 ALTER TABLE `kode_otp` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `kodepromo`
--

DROP TABLE IF EXISTS `kodepromo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kodepromo` (
  `id_promo` int(11) NOT NULL AUTO_INCREMENT,
  `nama_promo` varchar(250) NOT NULL,
  `kode_promo` varchar(250) NOT NULL,
  `nominal_promo` varchar(500) NOT NULL,
  `minimal` varchar(250) NOT NULL DEFAULT '0',
  `type_promo` varchar(250) NOT NULL,
  `expired` varchar(250) NOT NULL,
  `fitur` varchar(250) NOT NULL,
  `id_user` varchar(255) DEFAULT NULL,
  `image_promo` varchar(500) NOT NULL,
  `status` varchar(250) NOT NULL,
  PRIMARY KEY (`id_promo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kodepromo`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `kodepromo` DISABLE KEYS */;
INSERT INTO `kodepromo` VALUES
(25,'Test','12345','50','7000','persen','2025-07-31','1',NULL,'c3afb3e0ab5de5790531d039ee7115b7.jpg','1');
/*!40000 ALTER TABLE `kodepromo` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `list_bank`
--

DROP TABLE IF EXISTS `list_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `list_bank` (
  `id_bank` int(11) NOT NULL AUTO_INCREMENT,
  `nama_bank` varchar(250) NOT NULL,
  `image_bank` varchar(250) NOT NULL,
  `rekening_bank` varchar(250) NOT NULL,
  `status_bank` varchar(20) NOT NULL,
  PRIMARY KEY (`id_bank`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_bank`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `list_bank` DISABLE KEYS */;
INSERT INTO `list_bank` VALUES
(3,'PT. TOPJEK TEKNO INDONESIA','b3460001c9edacd34cee8369a9308153.jpg','105701001022564','1');
/*!40000 ALTER TABLE `list_bank` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `log_api`
--

DROP TABLE IF EXISTS `log_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipe` tinyint(1) NOT NULL,
  `request` text NOT NULL,
  `response` text NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_api`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `log_api` DISABLE KEYS */;
INSERT INTO `log_api` VALUES
(251,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO1\",\"customer_no\":\"082335382016\",\"ref_id\":\"POB241201141919\",\"sign\":\"dadd2c6676cea586d6dee94963e92b74\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB241201141919\\\",\\\"customer_no\\\":\\\"082335382016\\\",\\\"buyer_sku_code\\\":\\\"TO1\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2024-01-19 12:14:20'),
(252,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO3\",\"customer_no\":\"027722\",\"ref_id\":\"POB240801282141\",\"sign\":\"c019e93f6661913ed6aa0f46c19a2200\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB240801282141\\\",\\\"customer_no\\\":\\\"027722\\\",\\\"buyer_sku_code\\\":\\\"TO3\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2024-01-21 08:28:41'),
(253,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO1\",\"customer_no\":\"082434455\",\"ref_id\":\"POB241101172123\",\"sign\":\"07a689daffbea1fd4b31434e66ad2285\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB241101172123\\\",\\\"customer_no\\\":\\\"082434455\\\",\\\"buyer_sku_code\\\":\\\"TO1\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2024-01-21 11:17:23'),
(254,1,'{\"commands\":\"pln-subscribe\",\"customer_no\":\"14335056439\"}','{\"body\":\"{\\\"data\\\":{\\\"customer_no\\\":\\\"14335056439\\\",\\\"meter_no\\\":\\\"14335056439\\\",\\\"subscriber_id\\\":\\\"522601582952\\\",\\\"name\\\":\\\"SUWARJO\\\",\\\"segment_power\\\":\\\"R1  \\/000000450\\\"}}\",\"http_code\":200}','2024-07-08 23:19:23'),
(255,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO1\",\"customer_no\":\"082335382016\",\"ref_id\":\"POB240911192120\",\"sign\":\"00a2823fd559b0af5af101adc9769b4b\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB240911192120\\\",\\\"customer_no\\\":\\\"082335382016\\\",\\\"buyer_sku_code\\\":\\\"TO1\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2024-11-21 09:19:20'),
(256,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"T02\",\"customer_no\":\"082299400044\",\"ref_id\":\"POB241612391606\",\"sign\":\"603fb31a9e016f070f5f365952976851\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB241612391606\\\",\\\"customer_no\\\":\\\"082299400044\\\",\\\"buyer_sku_code\\\":\\\"T02\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2024-12-16 16:39:06'),
(257,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"T04\",\"customer_no\":\"082246456887\",\"ref_id\":\"POB250902142652\",\"sign\":\"fefa4269097b29b8c2c889fb6035e5ce\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB250902142652\\\",\\\"customer_no\\\":\\\"082246456887\\\",\\\"buyer_sku_code\\\":\\\"T04\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2025-02-26 09:14:52'),
(258,1,'{\"commands\":\"pln-subscribe\",\"customer_no\":\"1234567789\"}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"\\\",\\\"customer_no\\\":\\\"1234567789\\\",\\\"buyer_sku_code\\\":\\\"\\\",\\\"message\\\":\\\"Invalid Payload\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"40\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2025-04-16 22:29:40'),
(259,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"P025\",\"customer_no\":\"082246456887\",\"ref_id\":\"POB250705030805\",\"sign\":\"8460f8c5dcb3a6872bbb6f7add91e7fb\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB250705030805\\\",\\\"customer_no\\\":\\\"082246456887\\\",\\\"buyer_sku_code\\\":\\\"P025\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.204\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2025-05-08 07:03:05'),
(260,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO3\",\"customer_no\":\"081280304787\",\"ref_id\":\"POB261001470908\",\"sign\":\"57fc5b40d4681e8ddb742552785b6ae4\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB261001470908\\\",\\\"customer_no\\\":\\\"081280304787\\\",\\\"buyer_sku_code\\\":\\\"TO3\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2026-01-09 10:47:10'),
(261,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO3\",\"customer_no\":\"081280304787\",\"ref_id\":\"POB261001470930\",\"sign\":\"dae1e5d3717a19b470d75ca5582df327\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB261001470930\\\",\\\"customer_no\\\":\\\"081280304787\\\",\\\"buyer_sku_code\\\":\\\"TO3\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2026-01-09 10:47:30'),
(262,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"P025\",\"customer_no\":\"081280304787\",\"ref_id\":\"POB261101150909\",\"sign\":\"d95a9d48c6d006f47ee767eefae274ed\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB261101150909\\\",\\\"customer_no\\\":\\\"081280304787\\\",\\\"buyer_sku_code\\\":\\\"P025\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2026-01-09 11:15:09'),
(263,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO1\",\"customer_no\":\"081327808876\",\"ref_id\":\"POB261506520324\",\"sign\":\"05dd80fd6c87573a15569b7d743a23ca\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB261506520324\\\",\\\"customer_no\\\":\\\"081327808876\\\",\\\"buyer_sku_code\\\":\\\"TO1\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2026-06-03 15:52:29'),
(264,1,'{\"username\":\"radalugwKm4g\",\"buyer_sku_code\":\"TO1\",\"customer_no\":\"081327808876\",\"ref_id\":\"POB261506520330\",\"sign\":\"9a2e590e7f3d71b74de1509a264d0c3c\",\"testing\":true}','{\"body\":\"{\\\"data\\\":{\\\"ref_id\\\":\\\"POB261506520330\\\",\\\"customer_no\\\":\\\"081327808876\\\",\\\"buyer_sku_code\\\":\\\"TO1\\\",\\\"message\\\":\\\"IP Anda tidak kami kenali: 153.92.13.229\\\",\\\"status\\\":\\\"Gagal\\\",\\\"rc\\\":\\\"45\\\",\\\"sn\\\":\\\"\\\"}}\",\"http_code\":400}','2026-06-03 15:52:30');
/*!40000 ALTER TABLE `log_api` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `log_callback`
--

DROP TABLE IF EXISTS `log_callback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_callback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` text NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_callback`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `log_callback` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_callback` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `lokasi_pelanggan`
--

DROP TABLE IF EXISTS `lokasi_pelanggan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `lokasi_pelanggan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pelanggan` varchar(250) NOT NULL,
  `nama` varchar(250) NOT NULL,
  `latitude` varchar(250) NOT NULL,
  `longitude` varchar(250) NOT NULL,
  `alamat` varchar(250) NOT NULL,
  `utama` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lokasi_pelanggan`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `lokasi_pelanggan` DISABLE KEYS */;
INSERT INTO `lokasi_pelanggan` VALUES
(1,'P1735004105','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','1.333797378965197','120.97888514399529','8XMH+8QQ, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi, Indonesia',0),
(2,'P1735023118','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','1.3317892831056568','120.98108187317847','8XJJ+VC6, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia',0),
(3,'P1735023118','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','1.334422164256551','120.97745217382908','8XMG+QX Salumpaga, Toli-Toli Regency, Central Sulawesi, Indonesia',0),
(4,'P1735023118','8XJH+84 Salumpaga, Toli-Toli Regency, Central Sulawesi, Indonesia','1.338019034000877','120.98017528653145','8XQJ+34V, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi, Indonesia',0),
(5,'P1735023118','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','1.3326882506082556','120.98000463098289','8XMH+8QQ, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi, Indonesia',0),
(6,'P1735004105','J2J4+68W, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo 96137, Indonesia','0.5377284100157519','123.06200433522463','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0),
(7,'P1735004105','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','0.5377284100157519','123.06200433522463','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0),
(8,'P1735004105','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','0.6269984343881857','122.97988817095757','Jl. Baso Bobihoe No.308, Kayubulan, Kec. Limboto, Kabupaten Gorontalo, Gorontalo 96214, Indonesia',0),
(9,'P1735004105','J293+M7M, Jl. Kyai Hi. Saleh Kadir, Hutuo, Kec. Telaga Biru, Kabupaten Gorontalo, Gorontalo 96214, Indonesia','0.6318207552113554','122.57566183805466','JHJG+P7F, Molombulahe, Paguyaman, Boalemo Regency, Gorontalo, Indonesia',0),
(10,'P1735004105','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','0.5377284100157519','123.06200433522463','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0),
(11,'P1735004105','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','0.6218445405733976','123.0093690007925','J2C5+PPQ, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia',0),
(12,'P1735004105','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','0.5377284100157519','123.06200433522463','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0),
(13,'P1735004105','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','-7.202209956593579','109.2878458276391','Q7XQ+86J, Kandanggotong Lor, Gombong, Kec. Belik, Kabupaten Pemalang, Jawa Tengah 52356, Indonesia',0),
(14,'P1735004105','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','-7.204212721936142','109.28415175527334','Q7WM+8M Gombong, Pemalang Regency, Central Java, Indonesia',0),
(15,'P1735004105','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','-7.211509884408593','109.27301287651062','Q7QF+959, Jl. Nur Supeno, Soyi, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia',0),
(16,'P1735004105','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.568324611906064','111.41600701957941','CCJ8+W75, Sambirejo, Pelem, Kec. Karangrejo, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(17,'P1735004105','C96R+WFH, Babadan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.5719146996736075','111.39649093151093','C9HW+HFQ, Jenangsari, Jungke, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(18,'P1735004105','C96V+XMM, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.5684532334153385','111.39772709459066','C9JX+J3 Jungke, Magetan Regency, East Java, Indonesia',0),
(19,'P1735004105','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.5614222003438645','111.40376105904579','CCQ3+PGG, Padasbolong, Patihan, Kec. Karangrejo, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(20,'P1735004105','P8QJ+3G, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.287058055991276','114.33117192238569','P87J+5FF, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia',0),
(21,'P1735004105','P993+V5H, Jalan Raya, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia\n','-8.262142269964098','114.32522915303707','P8QG+43X, Dusun Babakan, Kedayunan, Kabat, Banyuwangi Regency, East Java 68461, Indonesia',0),
(22,'P1735004105','P982+RXW, Jalan Raya, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.293253910872663','114.33538533747196','P84M+JP9, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(23,'P1735004105','P972+Q4F, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.28910809021477','114.34518311172724','P86V+GQX, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(24,'P1735004105','P869+F7F, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.322051492123215','114.31545082479715','Jalan No.Km 5, Krajan, Watukebo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',0),
(25,'P1735004105','P87J+5FF, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia','-8.305587269645248','114.33238763362169','M8VJ+HJ7, Krajan, Karangbendo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',0),
(26,'P1735004105','P86F+JW6, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.287537471004615','114.31565567851067','P878+287, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(27,'P1735004105','P878+75J, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.276919535840413','114.31736256927252','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(28,'P1735004105','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.276836258086824','114.31746549904346','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(29,'P1735004105','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.274646478168515','114.31921999901533','P8F9+XHX, Jl. Raya Jember, Krajan, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(30,'P1735004105','P87J+Q33, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.277288147518282','114.31716408580542','Jl. Raya Jember No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(31,'P1735004105','P8CJ+M5 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.261100428266541','114.3316674605012','P8QJ+CMH, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(32,'P1735004105','P89M+74V, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.281398592257423','114.31436285376549','P897+CP Labanasem, Banyuwangi Regency, East Java, Indonesia',0),
(33,'P1735004105','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.269550879546784','114.32229716330767','P8JC+3VH, RT.02/RW.05, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(34,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.248270619151455','114.32231292128563','Q82C+9R, Dusun Kertosari, Pendarungan, Kabat, Banyuwangi Regency, East Java, Indonesia',0),
(35,'P1735004105','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.262194361976771','114.33104082942009','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(36,'P1735004105','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.277244352089463','114.3174909800291','Jl. Raya Jember No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(37,'P1735004105','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.20318663571707','114.34311512857676','Q8WV+GVW, Krajan Satu, Boyolangu, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur 68424, Indonesia',0),
(38,'P1735004105','P8CM+564, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.279628209825338','114.31593596935272','P8C8+483, RT.02/RW.04, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(39,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.236256003084032','114.35853246599436','Q975+G94, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0),
(40,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.23597030767017','114.3584007024765','Jl. Adi Sucipto No.143, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0),
(41,'P1735004105','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.265021255789394','114.3216510862112','P8MC+PHX, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(42,'P1735004105','P88H+R3 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.273187613488505','114.3117218837142','Jl. Karang Rejo No.1, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(43,'P1735004105','P87H+VM7, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.279245001935239','114.31611701846123','P8C8+6CQ, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(44,'P1735004105','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.210357092491215','114.38546989113092','Jl. Ikan Cucut No.28, Kampungmandar, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68419, Indonesia',0),
(45,'P1735004105','P85J+WJ3, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.264894510514669','114.32159945368767','P8MC+PHX, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(46,'P1735004105','P8QJ+4F8, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.235785816677778','114.35823038220406','Jl. Adi Sucipto No.118, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0),
(47,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.205435879377966','114.36332322657108','Jl. Villa Bukit Mas Perum Griya Permata Husada No.i-7, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia',0),
(48,'P1735004105','P87Q+5VC, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia','-8.256971854649382','114.33733128011227','Jl. Raya Jember No.98, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(49,'P1735004105','P85J+WJ3, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.288223249111757','114.31798048317432','P869+P4X, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(50,'P1735004105','P88H+52 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.29631514138927','114.30717520415783','P834+FWC, Jl. Raya Jember, Kawang, Labanasem, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(51,'P1735004105','P89M+F4P, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.27690128772858','114.31746382266283','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(52,'P1735004105','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.286341752470936','114.31546691805124','P878+C5F, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(53,'P1741272299','P88H+F3 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.28002966531019','114.31562382727861','P898+X75, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(54,'P1741272299','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.285014314847496','114.32454854249954','P87F+XR Pakistaji, Banyuwangi Regency, East Java, Indonesia',0),
(55,'P1735004105','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.283198826798184','114.3170490860939','P888+J97, Jl. Macan Putih, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(56,'P1741272299','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.28679860762275','114.32102311402559','P87C+3H, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(57,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.231203709754093','114.38985329121351','Q99Q+GW Kertosari, Banyuwangi Regency, East Java, Indonesia',0),
(58,'P1735004105','P8QJ+4F8, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.211471408611802','114.38519228249788','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0),
(59,'P1741272299','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.28987614527621','114.31748628616333','P859+R4R, RT.001/RW.012, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 48461, Indonesia',0),
(60,'P1735004105','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.207280262952823','114.36290379613638','Q9V7+45F, Jl. Letkol Istiqlah, Lingkungan Mojoroto R, Mojopanggung, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(61,'P1735004105','P8QJ+FF9, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.211499946744157','114.38510075211525','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0),
(62,'P1741272299','P85J+75 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.28821196878489','114.31810017675161','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(63,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.219204171916429','114.35278415679932','Q9J3+75W, Lingkungan Cuking Rw., Mojopanggung, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur 68425, Indonesia',0),
(64,'P1741272299','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.274778528878173','114.32246278971434','P8GF+G3F, Krajan, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(65,'P1741272299','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.288225239757638','114.31820847094059','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(66,'P1741272299','P89M+74V, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.282845814260428','114.32306192815304','P88F+V6 Pakistaji, Banyuwangi Regency, East Java, Indonesia',0),
(67,'P1741272299','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.288193389422267','114.31806329637766','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(68,'P1741272299','P89M+F4P, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.29261989775706','114.31640636175871','P848+XH Pakistaji, Banyuwangi Regency, East Java, Indonesia',0),
(69,'P1741272299','P86H+4MH, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.289366541739838','114.31222479790449','Jl. Raya Jember No.28, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(70,'P1741272299','P89M+53 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.294850716977301','114.30190064013004','P833+Q22, Kawang, Labanasem, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(71,'P1741272299','P8CV+93 Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.268136787824938','114.30631823837757','P8J4+PG Kabat, Banyuwangi Regency, East Java, Indonesia',0),
(72,'P1741272299','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia\n    ','-8.256343423761','114.35187555849552','P9V2+FQ Dadapan, Banyuwangi Regency, East Java, Indonesia',0),
(73,'P1741272299','P8MX+7GF, Dukuh segoling, Dusun Secawan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.250046119428148','114.33719582855701','P8XP+XV Dadapan, Banyuwangi Regency, East Java, Indonesia',0),
(74,'P1741272299','P9Q2+85 Pondoknongko, Banyuwangi Regency, East Java, Indonesia','-8.261727192598327','114.35047309845686','P9Q2+85 Pondoknongko, Banyuwangi Regency, East Java, Indonesia',0),
(75,'P1741272299','Dusun Watu Ulo,Rejosari, Glagah,Kabupaten Banyuwangi Lingk. Gunungsari, Rt/Rw.02/01, Jl. Banjarsari, Dusun Watu Ulo, Rejosari, Kec. Glagah, Kabupaten Banyuwangi, Jawa Timur 68432, Indonesia','-8.267197817502403','114.30368900299072','P8M3+4F Kabat, Banyuwangi Regency, East Java, Indonesia',0),
(76,'P1741272299','M8JQ+7QP, Patoman, Watukebo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia','-8.363471069248181','114.30807441473007','J8P5+R9M, Jl. K.H. Nawawi, Pekiwen, Kaligung, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',0),
(77,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.198271638951383','114.38237462192774','R92J+WV, Lateng, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68413, Indonesia',0),
(78,'P1735004105','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.211021103158762','114.38540484756231','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0),
(79,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.21150591984137','114.38532203435898','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0),
(80,'P1741272299','M8WP+H4P, Krajan, Badean, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur, Indonesia','-8.331896917873992','114.29611075669527','M79W+6C Gladag, Banyuwangi Regency, East Java, Indonesia',0),
(81,'P1735004105','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.211443202316168','114.38502732664347','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0),
(82,'P1741272299','P88M+JQ Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.314386432242006','114.31527346372604','M8P8+64 Karangbendo, Banyuwangi Regency, East Java, Indonesia',0),
(83,'P1741272299','P83V+8G3, Donosuko, Badean, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.338323613306423','114.3190872296691','M869+MJ Gintangan, Banyuwangi Regency, East Java, Indonesia',0),
(84,'P1741272299','P82M+Q5 Badean, Banyuwangi Regency, East Java, Indonesia','-8.347769234031292','114.31712083518505','M828+VR Gintangan, Banyuwangi Regency, East Java, Indonesia',0),
(85,'P1741272299','P88P+4J Pakistaji, Banyuwangi Regency, East Java, Indonesia','-8.309373292538291','114.36016257852316','M9R6+73 Badean, Banyuwangi Regency, East Java, Indonesia',0),
(86,'P1741272299','P8PP+7F7, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','-8.260521773600546','114.31108351796865','P8Q6+PFV, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0),
(87,'P1735004105','J743+J4H, Sukomukti, Kebaman, Kec. Srono, Kabupaten Banyuwangi, Jawa Timur 68471, Indonesia','-8.202352374799425','114.36665989458561','Jl. Moh. Husni Thamrin No.110, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia',0),
(88,'P1741272299','Jl. Bromo No.10, RT.02/RW.01, Singotrunan, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia','-8.2124433631509','114.36556790024042','Q9Q8+C6F, Singonegaran, Banyuwangi Sub-District, Banyuwangi Regency, East Java 68415, Indonesia',0),
(89,'P1741272299','Jl. Basuki Rahmat No.43, RT.1, Singotrunan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia','-8.173862343499605','114.34350941330194','R8GV+FC Kelir, Banyuwangi Regency, East Java, Indonesia',0),
(90,'P1735004105','V467+32R, Parit Lalang, Rangkui, Pangkal Pinang City, Bangka Belitung Islands 33684, Indonesia','-2.1474609829043327','106.11140862107277','V426+WHQ, Jl. Basuki Rahmat, Bukitintan, Kec. Girimaya, Kota Pangkal Pinang, Kepulauan Bangka Belitung 33684, Indonesia',0),
(91,'P1735004105','2C5M+M5, Lubuk Semut, Karimun, Karimun Regency, Riau Islands, Indonesia','1.009307304314539','103.43289267271757','2C5M+M5, Lubuk Semut, Karimun, Karimun Regency, Riau Islands, Indonesia',0),
(92,'P1735004105','2C3M+WPX, Jalan, Lubuk Semut, Kec. Karimun, Karimun, Kepulauan Riau 29663, Indonesia','1.0084025343219198','103.42640742659569','Jl. Kapling Gg. Nusa Indah No.12, Kapling, Kec. Tebing, Kabupaten Karimun, Kepulauan Riau 29663, Indonesia',0),
(93,'P1735004105','J2J4+68W, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo 96137, Indonesia','0.5377284100157519','123.06200433522463','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0),
(94,'P1735004105','J2J4+68W, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo 96137, Indonesia','0.5982622195035374','123.03246583789587','Jl. Ahmad A. Wahab No.17, Pantungo, Kec. Telaga Biru, Kabupaten Gorontalo, Gorontalo 96211, Indonesia',0),
(95,'P1735004105','Kp. Tlumpak rt0701, Sambiroto, Kec. Tembalang, Kota Semarang, Jawa Tengah 50274, Indonesia','-6.996953393289655','110.45421481132507','Jl. Sendangsari Utara XV No.27, RW.5, Kalicari, Kec. Pedurungan, Kota Semarang, Jawa Tengah 50198, Indonesia',0),
(96,'P1735004105','XFC2+Q55, Sambiroto, Tembalang, Semarang City, Central Java 50274, Indonesia','-6.951531153576045','110.44828478246927','2CXX+98 Tambakrejo, Semarang City, Central Java, Indonesia',0),
(97,'P1735004105','M7FG+9GX, Tafuna, Western District 96799, American Samoa','-14.291539223560367','-170.68444099277258','P858+96 Faga\'alu, Eastern District, American Samoa',0),
(98,'P1735004105','M7GG+2VH, Tafuna, Western District 96799, American Samoa','-14.34458719069352','-170.7549150288105','M64V+JXH, \'Ili\'ili, Western District 96799, American Samoa',0),
(99,'P1735004105','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.567283341272608','111.41408521682024','CCM7+6JJ, Jl. Turi, RT.06/RW.01, Sambirejo, Pelem, Kec. Karangrejo, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(100,'P1735004105','C96W+VX4, Pule, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.564944212183449','111.40946242958307','CCM5+VHR, Sambirejo, Geplak, Karas, Magetan Regency, East Java 63395, Indonesia',0),
(101,'P1735004105','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.593991320102095','111.41282659024','CC46+RXQ, Jl. Cemp., Tanjungsepreh II, Tanjungsepreh, Kec. Maospati, Kabupaten Magetan, Jawa Timur 63392, Indonesia',0),
(102,'P1735004105','C97W+PV2, Gg. Madinah 5, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.604750548968002','111.44185546785593','9CWR+JVJ, Bakung, Maospati, Kec. Maospati, Kabupaten Magetan, Jawa Timur 63392, Indonesia',0),
(103,'P1735004105','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.6093718665336665','111.38908199965954','99RQ+7J Kembangan, Magetan Regency, East Java, Indonesia',0),
(104,'P1735004105','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.570008653280798','111.4242048561573','Jl. Raya Maospati - Ngawi No.25, Glodog, Karangrejo, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(105,'P1735004105','M7GG+JW7, Tafuna, Western District 96799, American Samoa','-14.35235553000142','-170.73704112321138','J7X7+45X, Vaitogi, Western District, American Samoa',0),
(106,'P1735004105','Jl. Raya Singkil No.1, Pesalakan, Adiwerna, Kec. Adiwerna, Kabupaten Tegal, Jawa Tengah 52194, Indonesia\n','-7.088846609560501','109.04829204082489','W26X+F87, Jembayat, Kec. Margasari, Kabupaten Tegal, Jawa Tengah 52463, Indonesia',0),
(107,'P1735004105','PRWX+5X2, Gunungsari Ulu, Balikpapan Tengah, Balikpapan City, East Kalimantan 76122, Indonesia','-1.2377840723655986','116.85800291597842','QV75+492, Jl. Tiga Dalam Gg. Sudimapan, Gn. Samarinda, Kec. Balikpapan Utara, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0),
(108,'P1735004105','95X3+J4 Handil Bakti, Samarinda City, East Kalimantan, Indonesia','-0.5698202120125471','117.13560417294501','C4JP+36 Simpang Pasir, Samarinda City, East Kalimantan, Indonesia',0),
(109,'P1735004105','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','-7.5846592364200935','111.4042230695486','CC83+6H9, Doro, Temenggungan, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0),
(110,'P1735004105','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.186372323332357','111.10204875469208','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(111,'P1735004105','R434+FFF, Jl. Gatot Subroto, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.189175864257384','111.11369524151087','Jl. Tentara Pelajar No.172, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(112,'P1735004105','Terminal Bus Pacitan, Kios No. 29, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.196160736876614','111.11722636967897','R438+GVC, Pager, Arjowinangun, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0),
(113,'P1735004105','Q3QX+9JH, Temon, Kembang, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.195672914806758','111.1001168936491','R432+M43, Jl. Jend. A. Yani, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(114,'P1735004105','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.204148323337149','111.1162493750453','Jl. Ki Ageng Buwono Keling No.59, Ngemplak, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(115,'P1735004105','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.19532181528696','111.09209608286619','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(116,'P1735004105','R449+6PP, Gg. Masjid, Menadi, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','-8.182488218400794','111.11624401062727','Jl. Tentara Pelajar No.48, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(117,'P1735004105','R445+V73, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.186550531954433','111.10170107334852','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(118,'P1735004105','R423+C7P, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.19229697058002','111.10273271799088','Jl. Imam Bonjol No.8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(119,'P1735004105','R443+F92, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','-8.187316131216962','111.11373614519835','R467+PMX, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(120,'P1735004105','Jl. Basuki Rahmat No.1d, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','-8.180558098859953','111.11750934273005','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(121,'P1735004105','Jl. Veteran No.38, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.186396549090132','111.11503902822733','Jl. Tentara Pelajar No.46a, tegalrejo, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(122,'P1735004105','R453+2C8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.189088917617156','111.11427795141935','Jl. Tentara Pelajar No.127, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(123,'P1735004105','Jl. Basuki Rahmat No.36b, Purwoharjo, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.182843975315052','111.11630536615849','R488+VF5, RT.2/RW.01, Dsn. Tegalrejo, Nanggungan, Pacitan, Pacitan Regency, East Java 63518, Indonesia',0),
(124,'P1735004105','R433+PFJ, Jl. Jend. A. Yani, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.185427185769198','111.10294260084629','R473+V3R, Jl. Sultan Hasanudin, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0),
(125,'P1735004105','R455+8JG, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.195098146811254','111.10088970512152','R432+W96, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(126,'P1735004105','Jl. Hos Cokroaminoto No.17, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.200284302795119','111.08687818050385','Jl. Dewi Sartika No.49, Barean, Sidoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0),
(127,'P1735004105','R444+CFR, Jl. Dr. Sutomo No.37, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.193650272019845','111.09844721853733','R34X+HCX, Krajan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(128,'P1735004105','R433+6M4, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.193151827542318','111.11054364591837','Jl. Dr. Sutomo No.10, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(129,'P1735004105','R445+V73, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.188965798487239','111.11374318599701','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0),
(130,'P1735004105','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.194327916573073','111.10512759536505','Jl. R.A. Kartini No.5, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(131,'P1735004105','Jl. Jend. Sudirman No.132, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.189156948386886','111.10091216862202','Jl. Moh. Yamin. Sh No.33, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0),
(132,'P1735004105','R442+JPC, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.20342888299039','111.09731063246727','Drainase Blumbang - SPBU, JL. Jend Gatot Subroto, 9-A, Pacitan, 63511, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(133,'P1735004105','R456+PWG, Jl. Sunan Gn. Jati, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.197563139032459','111.10923908650875','R425+WMF, Tanjung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(134,'P1735004105','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n    ','-8.192548516414092','111.10847130417824','R445+QCX, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(135,'P1735004105','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','-8.193713987913373','111.11003033816814','Jl. Dr. Sutomo No.12, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(136,'P1735004105','Jl. Veteran Gg. 8 No.2, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.194867841157398','111.09205015003681','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(137,'P1735004105','JL. Tentara Pelajar, No. 164 Rt. 004 Rw. 001, Tanjungsari, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.194990958461576','111.11910626292229','R449+2J8, Menadi, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0),
(138,'P1735004105','Jl. Sunan Gn. Jati No.4, RT.4/RW.RW, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.188669449589842','111.1179431900382','R469+QC2, Jl. Sunan Kalijogo, Tawang, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(139,'P1735004105','JL. Tentara Pelajar, Rt. 03 Rw. 01, Nanggungan, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.172890294729182','111.11996792256832','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(140,'P1735004105','R466+MHV, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.172118362198606','111.11971277743578','R4G9+VJ6, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(141,'P1735004105','Jl. Brigjend Katamso No.90 C, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.195073921583054','111.09206892549992','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(142,'P1735004105','Jl. Tentara Pelajar No.127, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.203331984098773','111.10510546714067','Q4W4+F4P, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(143,'P1735004105','Jl. Veteran No.21, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.212629524196007','111.09554372727871','Q3PW+W62, Plelen, Sidoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0),
(144,'P1735004105','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','-8.175216371297434','111.11159607768059','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(145,'P1735004105','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','-8.190978841261826','111.11266024410725','R456+PWG, Jl. Sunan Gn. Jati, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(146,'P1735004105','Jl. Ponorogo - Pacitan No.123, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.172238167818248','111.12029649317265','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(147,'P1735004105','R476+CM3, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.176743629487873','111.12429298460484','R4FF+8P Purworejo, Pacitan Regency, East Java, Indonesia',0),
(148,'P1735004105','Jl. Brigjend Katamso No.24, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.203613720879078','111.09739277511835','Jl. Gatot Subroto No.81, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(149,'P1735004105','R466+8G8, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.192865769244609','111.11523114144802','R448+MCH, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(150,'P1735004105','Jl. Tentara Pelajar No.137, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.182287109407618','111.11610252410173','Jl. Tentara Pelajar No.12, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(151,'P1735004105','Jl. Veteran No.60, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.212654080191882','111.11777320504189','Q4P9+V3W, Krajan, Kembang, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(152,'P1735004105','Jl. Brigjend Katamso No.90c, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.222599133589716','111.13399587571621','Q4GM+HFR, Area Pegunungan, Sukoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(153,'P1735004105','R467+597, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.189963029159077','111.09085690230131','R36R+38F, Jl. Dr. GS Sam J Ratulangi, RT.02/RW.01, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(154,'P1735004105','R466+GHV, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.180938415451209','111.11835356801748','R499+R97, Krajan, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(155,'P1735004105','Jl. Raden Saleh No.21b, Purwoharjo, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.211465767352825','111.1153545230627','Q4Q8+66H, krajan, Krajan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(156,'P1735004105','Jl. DI. Panjaitan No.3a, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.186705178478059','111.10172387212515','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(157,'P1735004105','Desa Arjowinangun, R446+GJ5, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.201044234226725','111.09737433493137','JL. MT. Haryono, No. 45, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0),
(158,'P1735004105','R445+XX5, RT.04/RW.03, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.185984711007514','111.11786741763353','R479+895, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(159,'P1735004105','R455+XG8, Jl. Tj. Rejo, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.18472961440179','111.11637577414513','Jl. Nasional III No.69, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(160,'P1735004105','Jl. Gatot Subroto No.42, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.208771559279954','111.09336979687214','Q3RV+883, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(161,'P1735004105','R454+JJQ, Gantung, Pacitan, Pacitan Regency, East Java, Indonesia','-8.185937586880659','111.09967768192291','Jln. Rahman Hakim Rt.04 Rw.03 lingk. Pucangmulyo, Blimbing, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0),
(162,'P1735004105','R462+GC2, Jl. Moh. Yamin. Sh, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia','-8.208944780252729','111.09313040971756','Q3RV+883, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(163,'P1735004105','R423+99Q, Jl. Gatot Subroto, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.2077169671299','111.10497571527958','Q4R4+X25, Krajan Kidul, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0),
(164,'P1735004105','R432+P56, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.186251526381174','111.10187139362097','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(165,'P1735004105','R467+PMX, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.175304980309248','111.11959341913462','R4F9+PXW, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(166,'P1735004105','Jl. Moh. Yamin. Sh No.33, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia','-8.185528071346356','111.08955904841423','R37Q+HR2, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(167,'P1735004105','R477+F2C, Jl. Sunan Muria, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.178096651902992','111.11913777887821','Jl. Tentara Pelajar No.75, Ngetol, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(168,'P1735004105','R443+77F, Jl. Jaksa Agung Suprapto, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.180526239862008','111.11754957586527','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(169,'P1735004105','Jl. Tentara Pelajar No.46a, tegalrejo, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.175436068511598','111.11162658780813','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(170,'P1735004105','R473+7G8, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.19525013519568','111.10033515840769','R432+R3V, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0),
(171,'P1735004105','Jl. Ponorogo - Pacitan No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.18101607167391','111.11795995384455','R499+F34, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(172,'P1735023118','3RQ4+2GF, Kalangkangan, Galang, Toli-Toli Regency, Central Sulawesi 94561, Indonesia','1.0786992782325875','120.80375868827103','Jl. Hi.Moh.Saleh No.26, Sandana, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia',0),
(173,'P1735023118','Togaso, Kalangkangan, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia','1.0729506414655223','120.8013255894184','3RF2+2RF, Ogomoli, Galang, Toli-Toli Regency, Central Sulawesi 94561, Indonesia',0),
(174,'P1735023118','4R48+J73, Togaso, Kalangkangan, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia','1.080706890441487','120.80423008650541','3RJ3+8QX, Dusun subur, Sandana, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia',0),
(175,'P1735004105','Jl. Tentara Pelajar No.12, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.2082976900259','111.09862323850393','Q3RX+RCC, Jl. KH. Ahmad Dahlan, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0),
(176,'P1735004105','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.190121656636334','111.08769960701466','Jl. Janur Sari III No.45, Jambu, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(177,'P1735004105','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.20818552761271','111.09858501702547','Q3RX+RCC, Jl. KH. Ahmad Dahlan, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0),
(178,'P1753033513','R466+244, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.183889009413688','111.11611392349005','Jl. Tentara Pelajar No.92, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(179,'P1735004105','Blk. D No.12, Kubang Jaya, Kec. Siak Hulu, Kabupaten Kampar, Riau 28293, Indonesia','0.5045622563479798','101.4500941336155','GF32+R27, Jl. Nangka, Wonorejo, Kec. Marpoyan Damai, Kota Pekanbaru, Riau 28128, Indonesia',0),
(180,'P1735004105','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia','0.43347705131872727','101.39601711183786','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia',0),
(181,'P1735004105','Blk. D No.12, Kubang Jaya, Kec. Siak Hulu, Kabupaten Kampar, Riau 28293, Indonesia','0.5045622563479798','101.4500941336155','GF32+R27, Jl. Nangka, Wonorejo, Kec. Marpoyan Damai, Kota Pekanbaru, Riau 28128, Indonesia',0),
(182,'P1735004105','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','-8.207404371939523','111.1144844815135','Q4R7+XP8, Suruhan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(183,'P1735004105','R43H+GH8, Jl. Sultan Agung, RT.05/RW.3, Krajan, Mentoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.168587896793943','111.1218112707138','R4JC+QWJ, Krajan III, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(184,'P1735004105','R38Q+5M9, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.168108005966339','111.09448727220297','R3JW+Q4R, Sawahan, Sambong, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(185,'P1735004105','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','-8.162468747716412','111.12422123551369','Ngawen, Krajan II, Semanten, Pacitan, Pacitan Regency, East Java 63518, Indonesia',0),
(186,'P1735004105','Jl. Diponegoro No.23, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.172296909064595','111.12058516591787','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(187,'P1735004105','Jl. Jend. A. Yani No.60, Caruban, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','-8.208950421547506','111.1193436384201','Q4R9+CW4, Jembatan Jalan raya JLS, RT.02/RW.04, Krajan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0),
(188,'P1753033513','Q4P2+Q9Q, Jl. Kusudana, Temon, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.180730668387865','111.11780002713203','Rt3/1, Krajan, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(189,'P1753033513','Q3WX+82M, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.18552408902142','111.08960531651974','R37Q+HR2, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(190,'P1753033513','Q3RV+QQ7, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','-8.186668010220348','111.1020839586854','Jl. Brigjend Katamso No.88c, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0),
(191,'P1753033513','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.11722502787616','111.14559341222048','V4MW+36P, Jl. Patrem, Tj. II, Tremas, Kec. Arjosari, Kabupaten Pacitan, Jawa Timur 63581, Indonesia',0),
(192,'P1753033513','4FPH+XPV, Jl. Cinde Wilis Gg. 2, Ronowijayan, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63491, Indonesia','-7.911573118018784','111.5250813961029','3GQG+G3W, Ngledok, Mlarak, Kec. Mlarak, Kabupaten Ponorogo, Jawa Timur 63472, Indonesia',0),
(193,'P1753033513','Q4P3+696 Grindulu Bridge, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.153156097791765','111.12537458539009','R4WG+M7C, Dayakan, Bolosingo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(194,'P1753033513','Jl. Alun-Alun Timur No.7, Ponorogo, Mangkujayan, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63413, Indonesia','-7.653901916153649','111.33134175091982','Jl. Jend. Sudirman No.38, Dusun Kebonagung, Kebonagung, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63317, Indonesia',0),
(195,'P1753033513','Jl. Basuki Rahmat Barat No.10, Dusun Magetan, Magetan, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63361, Indonesia','-7.653159249492752','111.33405312895775','88WM+QH5, Tambran, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63318, Indonesia',0),
(196,'P1753033513','XX22+4MJ, Sidorejo, Guwotirto, Kec. Giriwoyo, Kabupaten Wonogiri, Jawa Tengah 57675, Indonesia','-8.02134436223895','110.90919591486454','XWH5+MCC, Jl. Ngampohan Platarejo, Ngompuan, Platarejo, Kec. Giriwoyo, Kabupaten Wonogiri, Jawa Tengah 57675, Indonesia',0),
(197,'P1753033513','WP95+934, Karanggayam, Karangsoko, Kec. Trenggalek, Kabupaten Trenggalek, Jawa Timur 66319, Indonesia','-8.054066545284096','111.70870408415794','Jl. Panglima Sudirman No.65, Sawahan, Ngantru, Kec. Trenggalek, Kabupaten Trenggalek, Jawa Timur 66311, Indonesia',0),
(198,'P1753033513','4G29+3H4, Jl. Mat Sarwan, Krajan, Jarak, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia','-7.873580198054478','111.60600531846285','4JG4+M96, Krajan, Sidoharjo, Kec. Pulung, Kabupaten Ponorogo, Jawa Timur 63481, Indonesia',0),
(199,'P1753033513','Jl. Jaksa Agung Suprapto No.8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','-8.176718075672316','111.07246365398169','R3FF+C2G, Kembang Kidul, Sedeng, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(200,'P1753033513','8GV7+RW2, Jl. Sendang, Kuncen, Kec. Taman, Kota Madiun, Jawa Timur 63135, Indonesia','-7.720179109631581','111.46308716386557','7FH7+C32, Jati, Rejosari, Kec. Kb. Sari, Kabupaten Madiun, Jawa Timur 63173, Indonesia',0),
(201,'P1753033513','Jl. Ponorogo - Wonogiri No.196, Wetan Dalem, Carat, Kec. Kauman, Kabupaten Ponorogo, Jawa Timur 63451, Indonesia','-7.95048299046082','111.41015343368053','2CX6+P2C, Jl. Sumoroto, Kedung, Bringinan, Kec. Jambon, Kabupaten Ponorogo, Jawa Timur 63461, Indonesia',0),
(202,'P1753033513','Jl. Gajah Mada No.11, Bangunsari, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63419, Indonesia','-7.8684483192710655','111.49853892624378','4FJX+CF3, Godang, Ronowijayan, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia',0),
(203,'P1753033513','4FFC+5X3, Jl. Kumbokarno, RT.2/RW.4, Pesantren, Surodikraman, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63419, Indonesia','-7.8474252864674625','111.49598579853773','Jl. Raden Wijaya No.28A, Kebon, Kadipaten, Kec. Babadan, Kabupaten Ponorogo, Jawa Timur 63491, Indonesia',0),
(204,'P1753033513','4F2R+GP Brahu, Ponorogo Regency, East Java, Indonesia','-7.796063549165681','111.64077512919903','6J3R+J65, Dukuh Nglingi, Ngebel, Kec. Ngebel, Kabupaten Ponorogo, Jawa Timur 63493, Indonesia',0),
(205,'P1753033513','Q4V2+Q4R, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','-8.189207058848881','111.11365668475628','Jl. Tentara Pelajar No.172, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0),
(206,'P1753033513','4FM7+G7W, Jl. Banda, Temengungan, Mangkujayan, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63413, Indonesia','-7.797162057803755','111.5126433223486','Jl. RA. Kartini RT. 06/ RW.02, Brahu, Mlilir, Kec. Dolopo, Kabupaten Madiun, Jawa Timur 63174, Indonesia',0),
(207,'P1735004105','Jl. Joko Tole 2 Gang Posyandu No.66, Sumber Rejo, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia','-1.2521881569083384','116.85445200651884','PVX3+4R6, Jl. Mayor Pol. Zainal Arifin, Gunungsari Ulu, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0),
(208,'P1735004105','Jl. Joko Tole 2 Gang Posyandu No.66, Sumber Rejo, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia','-1.2521881569083384','116.85445200651884','PVX3+4R6, Jl. Mayor Pol. Zainal Arifin, Gunungsari Ulu, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0),
(209,'P1735004105','XVX9+6VH, Jl. Perkantoran, Lalingato, Kec. Tirawuta, Kabupaten Kolaka Timur, Sulawesi Tenggara 93572, Indonesia','-4.003736301313354','121.85890451073647','XVW5+3R, Lalingato, Tirawuta, Kolaka Regency, South East Sulawesi 93572, Indonesia',0),
(210,'P1735004105','XVX9+6VH, Jl. Perkantoran, Lalingato, Kec. Tirawuta, Kabupaten Kolaka Timur, Sulawesi Tenggara 93572, Indonesia','-4.004715927872973','121.86044879257679','XVV6+Q2M, Lalingato, Tirawuta, East Kolaka Regency, South East Sulawesi 93572, Indonesia',0),
(211,'P1735004105','Jl. Karet Pedurenan No.27, RT.10/RW.7, Kuningan, Karet Kuningan, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12940, Indonesia\n','-6.1952614386829765','106.82040296494961','Jl. M.H. Thamrin No.1, Kb. Melati, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10310, Indonesia',0),
(212,'P1759559503','FF58+XXC, Bada, Dompu, Dompu Regency, West Nusa Tenggara, Indonesia','-8.533602846575107','118.4518925473094','FF82+JPQ, Jl. Lkr. Utara, Karijawa, Kec. Dompu, Kabupaten Dompu, Nusa Tenggara Bar., Indonesia',0),
(213,'P1741272299','Jl. Komp. Bogor Raya Permai Blok FE6 No.12, Curug, Kec. Bogor Bar., Kota Bogor, Jawa Barat 16113, Indonesia\n','-6.601429080758518','106.80690307170153','Jl. Raya Pajajaran No.40, RT.04/RW.05, Tugu Kujang, Kecamatan Bogor Tengah, Kota Bogor, Jawa Barat 16127, Indonesia',0),
(214,'P1735004105','Apartement GreenBay Pluit, Tower F, Pluit, Kecamatan Penjaringan, Jkt Utara, Daerah Khusus Ibukota Jakarta 14450, Indonesia\n','-6.178582938734253','106.79221294820309','Mall Taman Anggrek LT . G 9A, CHATIME MALL TAMAN ANGGREK, Letjen S. Parman St, RT.12/RW.1, South Tanjung Duren, Grogol petamburan, West Jakarta City, Jakarta 11470, Indonesia',0),
(215,'P1735004105','Jl. Paseban Tim. Gg. 6 No.C59D, RT.14/RW.4, Paseban, Kec. Senen, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10440, Indonesia','-6.2113231256427595','106.87015559524298','Jl. Pisangan Baru Tengah No.10, RT.2/RW.14, Pisangan Baru, Kec. Matraman, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13110, Indonesia',0),
(216,'P1735004105','RR99+X67, RT.2/RW.3, Gambir, Central Jakarta City, Jakarta 10110, Indonesia','-6.172518323514552','106.83419689536095','Kementerian Agama, Ps. Baru, Kecamatan Sawah Besar, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10710, Indonesia',0),
(217,'P1735004105','Jl. Taman Kb. Sirih No.2, RT.1/RW.8, Kp. Bali, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10250, Indonesia','-6.165923300666177','106.81295815855265','Jl. A.M Sangaji No.2, RT.15/RW.5, Petojo Utara, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10130, Indonesia',0),
(218,'P1735004105','Jl. Medan Merdeka Tim. No.1A 6, RT.6/RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','-6.181367558502993','106.81978337466717','Bank Indonesia Jl. MH Thamrin, Jl. Kebon Sirih, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia',0),
(219,'P1735004105','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n    ','-6.249116218195029','107.01356392353773','Q227+9C4, Margahayu, Bekasi Timur, Bekasi, West Java, Indonesia',0),
(220,'P1735004105','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n    ','-6.249116218195029','107.01356392353773','Q227+9C4, Margahayu, Bekasi Timur, Bekasi, West Java, Indonesia',0),
(221,'P1735004105','Jl. Bukit Duri Tanjakan No.13, RT.13/RW.8, Bukit Duri, Kec. Tebet, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12840, Indonesia','-6.214061243102374','106.88543681055307','9, RW.9, Pisangan Tim., Kec. Pulo Gadung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta, Indonesia',0),
(222,'P1735004105','PVP6+M4P, Jl. MT Haryono, Damai, Kec. Balikpapan Kota, Kota Balikpapan, Kalimantan Timur, Indonesia','-1.2481852424988447','116.86676334589718','Jl. Agung Tunggal No.30, Damai, Kecamatan Balikpapan Selatan, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0),
(223,'P1735004105','F9WC+97W, Simpang Baru, Tampan, Pekanbaru City, Riau 28291, Indonesia','0.5165207457315764','101.44387912005186','GC8V+JH2, Jadirejo, Sukajadi, Pekanbaru City, Riau 28156, Indonesia',0),
(224,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','-8.302866831065913','114.13980100303888','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(225,'P1775197589','3FV4+JWR, Majasem, Madusari, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia\n    ','-7.619857214093114','111.51570037007332','Jl. Ronggolawe No.16, Winongo, Kec. Manguharjo, Kota Madiun, Jawa Timur 63126, Indonesia',0),
(226,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.933610344099049','112.658557780087','Jl. Raden Intan No.1, Arjosari, Kec. Blimbing, Kota Malang, Jawa Timur 65126, Indonesia',0),
(227,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.96662047028214','112.6326322183013','2JMM+932, Samaan, Klojen, Malang City, East Java 65112, Indonesia',0),
(228,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.9775235346774265','112.6370457932353','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0),
(229,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.9775235346774265','112.6370457932353','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0),
(230,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.9775235346774265','112.6370457932353','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0),
(231,'P1775197589','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','-7.9775235346774265','112.6370457932353','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0),
(232,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n','-8.36533826348803','114.14672713726759','J4MW+VM9, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(233,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n','-8.302866831065913','114.13980100303888','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(234,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia','-8.37432017528941','114.14531093090773','Jl. Diponegoro No.245, Dusun Krajan II, Gambiran, Kec. Gambiran, Kabupaten Banyuwangi, Jawa Timur 68486, Indonesia',0),
(235,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','-8.36453585867452','114.15484551340342','J5P3+5WQ, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia',0),
(236,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','-8.302866831065913','114.13980100303888','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(237,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','-8.222878533891297','114.34079434722662','Banyuwangi City Train Station, Lingkungan Karang Ase, Bakungan, Kec. Glagah, Kabupaten Banyuwangi, Jawa Timur 68431, Indonesia',0),
(238,'P1735004105','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','-8.36533826348803','114.14672713726759','J4MW+VM9, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0),
(239,'P1735004105','Jl. Kalianyar X No.24, RT.1/RW.6, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n','-6.161697909585108','106.78464107215405','RQQM+8R9, RT.11/RW.3, Jelambar, Grogol petamburan, West Jakarta City, Jakarta 11460, Indonesia',0),
(240,'P1735004105','Jl. Kalianyar X No.24, RT.1/RW.6, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n','-6.160856227396631','106.79276581853628','RQQV+M43, RT.3/RW.8, Grogol, Grogol petamburan, West Jakarta City, Jakarta 11450, Indonesia',0);
/*!40000 ALTER TABLE `lokasi_pelanggan` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant` (
  `id_merchant` int(11) NOT NULL AUTO_INCREMENT,
  `id_fitur` varchar(100) NOT NULL,
  `nama_merchant` varchar(250) NOT NULL,
  `alamat_merchant` varchar(250) NOT NULL,
  `latitude_merchant` varchar(250) NOT NULL,
  `longitude_merchant` varchar(250) NOT NULL,
  `jam_buka` varchar(250) NOT NULL,
  `jam_tutup` varchar(250) NOT NULL,
  `category_merchant` varchar(100) NOT NULL,
  `foto_merchant` varchar(250) NOT NULL,
  `telepon_merchant` varchar(250) NOT NULL,
  `deskripsi_merchant` text NOT NULL,
  `phone_merchant` varchar(250) NOT NULL,
  `country_code_merchant` varchar(20) NOT NULL,
  `status_merchant` varchar(250) NOT NULL,
  `open_merchant` varchar(20) NOT NULL,
  `token_merchant` varchar(500) NOT NULL,
  PRIMARY KEY (`id_merchant`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;
INSERT INTO `merchant` VALUES
(1,'5','Pakdenya Iksan','no Jl. Kebon Sirih No.87 2, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','-6.1819605466232055','106.81804966181517','06:00','23:28','137','d03f7c8f06f8c09ff6ba31907430b16e.gif','6282335382016','','82335382016','+62','1','',''),
(2,'5','tetetwtetet','R3R7+G62, Jl. Pring Kuku-Pelem, Tempel Lor, Glinggangan, Kec. Pringkuku, Kabupaten Pacitan, Jawa Timur 63552, Indonesia','-8.15905900221492','111.0622038692236','08:10','21:10','137','1752947352-39064.jpg','621234567890','','1234567890','+62','1','',''),
(3,'6','test','Jalan Tumpak Rinjing, RT. 1/RW. 9, Dusun Mbulu, Desa, Bulu, Ngadirejan, Kec. Pringkuku, Kabupaten Pacitan, Jawa Timur 63552, Indonesia','-8.17143669740664','111.04795530438423','08:06','21:06','139','1753302166-47138.jpg','6212234567890','','12234567890','+62','2','',''),
(4,'5','Cafe Endru','rumbai','','','08:00','23:00','139','6bd3b56147114faf6ff3eb3f143d21e9.jpg','6285324667722','','85324667722','+62','2','',''),
(5,'5','cafe heru','Jl. Bundo Kandung No.S9, Labuh Baru Bar., Kec. Payung Sekaki, Kota Pekanbaru, Riau 28292, Indonesia','0.5028705183917571','101.41299348324537','01:08','23:08','137','1755594528-31858.jpg','6282124100984','','82124100984','+62','2','',''),
(6,'5','Cafe Abadi','asdfasdf','3°27&#039;38.5&quot;','121°38&#039;11.7&quot;','07:00','16:00','139','a3d290bef5ca617adb63092914528608.jpg','626563563','','6563563','+62','1','','bd5d35c2b358c2c97cff6127c01afc96dd9fe631'),
(7,'5','Warung Pakde Gokil','Koltim','-4.0030529','121.8664069','06:00','16:00','139','6509be9fe736440448c996da3d6a726e.jpg','6245634564','','45634564','+62','0','','db2251f76f3e81faecfae845e7f9ad48df4bccdb'),
(8,'5','jualan','St. Cisauk, Sampora, Kec. Cisauk, Kabupaten Tangerang, Banten 15345, Indonesia','-6.324533578826296','106.64149258285761','15:53','18:53','137','1759568052-66006.jpg','6282169168169','','82169168169','+62','0','','1759568052');
/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `midtrans`
--

DROP TABLE IF EXISTS `midtrans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `midtrans` (
  `no` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id` varchar(10) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `rek` varchar(255) DEFAULT NULL,
  `tipe` varchar(255) DEFAULT NULL,
  `jumlah` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `midtrans`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `midtrans` DISABLE KEYS */;
/*!40000 ALTER TABLE `midtrans` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `mitra`
--

DROP TABLE IF EXISTS `mitra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `mitra` (
  `id_mitra` varchar(200) NOT NULL,
  `nama_mitra` varchar(250) NOT NULL,
  `jenis_identitas_mitra` varchar(250) NOT NULL,
  `nomor_identitas_mitra` varchar(250) NOT NULL,
  `alamat_mitra` varchar(250) NOT NULL,
  `email_mitra` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `telepon_mitra` varchar(250) NOT NULL,
  `phone_mitra` varchar(250) NOT NULL,
  `country_code_mitra` varchar(250) NOT NULL,
  `id_merchant` varchar(250) NOT NULL,
  `partner` varchar(250) NOT NULL,
  `status_mitra` varchar(10) NOT NULL,
  `created_mitra` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_mitra`) USING BTREE,
  UNIQUE KEY `email_mitra` (`email_mitra`) USING BTREE,
  UNIQUE KEY `telepon_mitra` (`telepon_mitra`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mitra`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `mitra` DISABLE KEYS */;
INSERT INTO `mitra` VALUES
('M1735004384','demo','ktp','724242554','jl.mnana','ogottolis@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','6282335382016','82335382016','+62','1','0','1','2024-12-24 01:39:44'),
('M1754040626','Endru','85324667722','12345','Rumbai','endru.phoenix@gmail.com','8cb2237d0679ca88db6464eac60da96345513964','6285324667722','85324667722','+62','4','1','3','2025-08-01 09:30:26'),
('M1755594528','cafe heru','8668262817890028','8668262817890028','jl. sudirman','herupranata8@gmail.com','40bd001563085fc35165329ea1ff5c5ecbdbbeef','6282124100984','82124100984','+62','5','0','1','2025-08-19 09:08:48'),
('M1757916707','coba','KTP','331111111444556','jawa','asldkjflkasj5@email.com','bd5d35c2b358c2c97cff6127c01afc96dd9fe631','626563563','6563563','+62','6','0','1','2025-09-15 06:11:47'),
('M1757918481','Pakde','KTP','456456+456+4','Koltim','asdfasfd@mail.com','db2251f76f3e81faecfae845e7f9ad48df4bccdb','6245634564','45634564','+62','7','0','0','2025-09-15 06:41:21'),
('M1759568052','Leon','ktp','520501120411000','Dompu ntb','leonmuslih@gmail.com','bf7858cc03176263189fe9ff8998067440022385','6282169168169','82169168169','+62','8','0','0','2025-10-04 08:54:12');
/*!40000 ALTER TABLE `mitra` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES
(1,'Waspada Covid-19','Selalu Gunakan Masker Dan Jangan Lupa Pola Hidup Sehat.','image.jpg');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payment_jenis`
--

DROP TABLE IF EXISTS `payment_jenis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_jenis` (
  `id_jenis` int(11) NOT NULL AUTO_INCREMENT,
  `nama_jenis` varchar(50) NOT NULL,
  PRIMARY KEY (`id_jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_jenis`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payment_jenis` DISABLE KEYS */;
INSERT INTO `payment_jenis` VALUES
(1,'Ewallet'),
(2,'Virtual Account'),
(3,'Retail Outlet'),
(4,'Manual Transfer');
/*!40000 ALTER TABLE `payment_jenis` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payment_log_service`
--

DROP TABLE IF EXISTS `payment_log_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_log_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipe` tinyint(1) DEFAULT 0,
  `request` text DEFAULT NULL,
  `respon` text DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=armscii8 COLLATE=armscii8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_log_service`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payment_log_service` DISABLE KEYS */;
INSERT INTO `payment_log_service` VALUES
(66,2,'{\"reference_id\":\"TRX-241301451300\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-13 13:45:00'),
(67,2,'{\"reference_id\":\"TRX-240901471454\",\"currency\":\"IDR\",\"amount\":20000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_261b24c9-948e-428c-be71-dbc8049cbd07\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-240901471454\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":20000,\\\"capture_amount\\\":20000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240114111212800110166613192655472\\\\u0026timestamp=1705200474715\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=hsodnlgJ42XSQ0j4%2FvREI5eiRIcofN5CMVfmfLZJBQjEwBwuDYYGVcX7McvAD6FRkCQQZU6yxhQtN35dFCU9fnnZRvroIwyMOkrljOBD973yicAZN6uEh2x8APsT%2FAMrnkjEuA%2BtNnUkuMBBkXfGED%2BRP%2FGZVRWFCg0yJoRO0hdOCqxFggo4V4OsCCtbLDCMYnVotL8ldu2p8dzp0zQmTUuwlK5dmFO9a%2F5xEGcgRtNJ62OwFzmaoWelBu4mAJGe5OLL%2BADxu31ExeN4JSuyt89Bj5fJMQpelGw9LIasS4u%2FnqLl%2FLsBoOofIbc0RZYhvTO6fuR9Oz7owpe%2BrF98Kw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240114111212800110166613192655472\\\\u0026timestamp=1705200474715\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=hsodnlgJ42XSQ0j4%2FvREI5eiRIcofN5CMVfmfLZJBQjEwBwuDYYGVcX7McvAD6FRkCQQZU6yxhQtN35dFCU9fnnZRvroIwyMOkrljOBD973yicAZN6uEh2x8APsT%2FAMrnkjEuA%2BtNnUkuMBBkXfGED%2BRP%2FGZVRWFCg0yJoRO0hdOCqxFggo4V4OsCCtbLDCMYnVotL8ldu2p8dzp0zQmTUuwlK5dmFO9a%2F5xEGcgRtNJ62OwFzmaoWelBu4mAJGe5OLL%2BADxu31ExeN4JSuyt89Bj5fJMQpelGw9LIasS4u%2FnqLl%2FLsBoOofIbc0RZYhvTO6fuR9Oz7owpe%2BrF98Kw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-01-14T02:47:54.380488Z\\\",\\\"updated\\\":\\\"2024-01-14T02:47:54.380488Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-01-14 09:47:54'),
(68,2,'{\"reference_id\":\"TRX-240901591449\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_3524e357-806f-42aa-931c-d86d5ab85eff\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-240901591449\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":10000,\\\"capture_amount\\\":10000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240114111212800110166655292525804\\\\u0026timestamp=1705201190141\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=qCM6xq5VTMHhYw0CjnPgnvFpxNDDUbu006XuQlr5jz8sUV5TyWoYx%2FYbiUGgIqJPmB%2B5m8Qfz%2BKYkYZcWgwu%2B0rFZa%2Bv08W6NK5y4R1bZ5PuwC9iyVA5bYtS7W1b1tVu0lori1Et%2BgLKyxvrMKV7yAZrfeL%2Bg5lyXQUhDGf3ctDy9JAoTm1NcPxpsfb9Y0vXTTPdiNu%2B3KX2VmqL%2Bc04k%2BGdWqtIxmmNrhuilkAXIJ7jm%2B%2F2CF4uItZYDdEfheqIOL3AaHv47h6bUNPfLDRfPjq1c4Rz%2BoqkLny%2Fy5I2DpVzZD4kDbdv7VzkoVkifNqwQFdKpqXIcMNapDMGj4qtuQ%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240114111212800110166655292525804\\\\u0026timestamp=1705201190141\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=qCM6xq5VTMHhYw0CjnPgnvFpxNDDUbu006XuQlr5jz8sUV5TyWoYx%2FYbiUGgIqJPmB%2B5m8Qfz%2BKYkYZcWgwu%2B0rFZa%2Bv08W6NK5y4R1bZ5PuwC9iyVA5bYtS7W1b1tVu0lori1Et%2BgLKyxvrMKV7yAZrfeL%2Bg5lyXQUhDGf3ctDy9JAoTm1NcPxpsfb9Y0vXTTPdiNu%2B3KX2VmqL%2Bc04k%2BGdWqtIxmmNrhuilkAXIJ7jm%2B%2F2CF4uItZYDdEfheqIOL3AaHv47h6bUNPfLDRfPjq1c4Rz%2BoqkLny%2Fy5I2DpVzZD4kDbdv7VzkoVkifNqwQFdKpqXIcMNapDMGj4qtuQ%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-01-14T02:59:49.859983Z\\\",\\\"updated\\\":\\\"2024-01-14T02:59:49.859983Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-01-14 09:59:50'),
(69,2,'{\"reference_id\":\"TRX-240801471544\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_a2e514ce-e663-4075-b1a3-4dc752b6ed54\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-240801471544\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":10000,\\\"capture_amount\\\":10000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240115111212800110166114692938908\\\\u0026timestamp=1705283264790\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=iibvo8%2FLIHDcC8Hh555y0cFwK0sLHgKLBBEolan1dtWyqKzAzWnWoa%2FAdFZQ4ZGAkdG2MRWfBgrc8GHm1JLOY%2BPSW0Wn%2BqroWi8177PfgMQDPghF%2FhX2PyRpKW78Qm3UE%2FKlI0yQEn71jggWnH5eED03o1zImsGJKX5l9LgNFy1XpSRbEk522wmUe7XXporhroYZhuqOvCgjfAIRukVCH3bsqa1p%2B5m2YA%2BS1edoVLdTJ9UCFHN2nGfqZYJFEUtvVDU2pInkmSkeXrg6AAlNzkyvEGhc8VFuNHyQNj8nmuK9lB9TYIlo0bz9tdmKGQwV1EmQdjXo%2Fbc0B7BRGKGLMw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240115111212800110166114692938908\\\\u0026timestamp=1705283264790\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=iibvo8%2FLIHDcC8Hh555y0cFwK0sLHgKLBBEolan1dtWyqKzAzWnWoa%2FAdFZQ4ZGAkdG2MRWfBgrc8GHm1JLOY%2BPSW0Wn%2BqroWi8177PfgMQDPghF%2FhX2PyRpKW78Qm3UE%2FKlI0yQEn71jggWnH5eED03o1zImsGJKX5l9LgNFy1XpSRbEk522wmUe7XXporhroYZhuqOvCgjfAIRukVCH3bsqa1p%2B5m2YA%2BS1edoVLdTJ9UCFHN2nGfqZYJFEUtvVDU2pInkmSkeXrg6AAlNzkyvEGhc8VFuNHyQNj8nmuK9lB9TYIlo0bz9tdmKGQwV1EmQdjXo%2Fbc0B7BRGKGLMw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-01-15T01:47:44.611937Z\\\",\\\"updated\\\":\\\"2024-01-15T01:47:44.611937Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-01-15 08:47:44'),
(70,2,'{\"reference_id\":\"TRX-240901391544\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:39:44'),
(71,2,'{\"reference_id\":\"TRX-240901421559\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:42:59'),
(72,2,'{\"reference_id\":\"TRX-240901431529\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:43:29'),
(73,2,'{\"reference_id\":\"TRX-240901441529\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:44:30'),
(74,2,'{\"reference_id\":\"TRX-240901461542\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:46:42'),
(75,2,'{\"reference_id\":\"TRX-240901471527\",\"currency\":\"IDR\",\"amount\":11000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285647832003\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-01-15 09:47:27'),
(76,2,'{\"reference_id\":\"TRX-241201191612\",\"currency\":\"IDR\",\"amount\":2000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_36d6d85c-2c13-4dae-be9c-b9040443a59d\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-241201191612\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":2000,\\\"capture_amount\\\":2000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240116111212800110166068292977402\\\\u0026timestamp=1705382352836\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=QzMMiDktbGfLVm0X5tK0Mds88NqO0EmVG7x90N0D%2By61vgSOC4xhKqYj1jXXqT4jfg9IKPDdYGnsrPBATlbY6rU3ELkiE1yudGOgehKp66eWbu8uG4hCClRw5usfYphy7h5SXYbj5hFpLbB3zrI73bTZ%2BIcwYR8rebPDXB%2FJMD2Q7in5cLi9WqrbaavS07BgyKVc26qotLehkndB4WVFutlz4%2BCL3wcJ6gbxx45PG%2FOukrftE17Psn2l6Hf7Wp7s75BIHq9WbQRCdEipKw6sE2AGEJKS7sagqrEMkanhDMDolll7Fi52OnWb4LuD1LJw6NReIQYLshzBJ30g7egcMA%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240116111212800110166068292977402\\\\u0026timestamp=1705382352836\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=QzMMiDktbGfLVm0X5tK0Mds88NqO0EmVG7x90N0D%2By61vgSOC4xhKqYj1jXXqT4jfg9IKPDdYGnsrPBATlbY6rU3ELkiE1yudGOgehKp66eWbu8uG4hCClRw5usfYphy7h5SXYbj5hFpLbB3zrI73bTZ%2BIcwYR8rebPDXB%2FJMD2Q7in5cLi9WqrbaavS07BgyKVc26qotLehkndB4WVFutlz4%2BCL3wcJ6gbxx45PG%2FOukrftE17Psn2l6Hf7Wp7s75BIHq9WbQRCdEipKw6sE2AGEJKS7sagqrEMkanhDMDolll7Fi52OnWb4LuD1LJw6NReIQYLshzBJ30g7egcMA%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-01-16T05:19:12.509536Z\\\",\\\"updated\\\":\\\"2024-01-16T05:19:12.509536Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-01-16 12:19:12'),
(77,2,'{\"reference_id\":\"TRX-241003032316\",\"currency\":\"IDR\",\"amount\":50000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_6ae98bf8-f18e-45ec-9dad-bf2157193d0c\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-241003032316\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":50000,\\\"capture_amount\\\":50000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240323111212800110166086597400578\\\\u0026timestamp=1711162998389\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=V0DJ3o9HHwRZYmbocE8xY9XwK3BoeimBFKWfaejDf1j0vI3FIAyA7mtfFO5BbgsDFtZotdS9%2FLuNTbdIfP2rUo0Jpx6Q68sLQIz4FvIm%2Fx6CGifWl5jqRVog1MnDycxxoErxfvj2kp1wVQrJIiYg7bBpyI6i2vR5rVA0T4c7JQsqQ81SIFuecTEAwGEp5mIfH9TLElUkvMM7cPcOsSlsqYG5x89Xnh8EPvIXIZDILhsjEJptvSpG8roZRun2Tr76wUiJbdWtInMZo1Wlgn4ucHxVWTIFzp8pRoeWkUoR7hQF1j0yxMq1JRd%2B6nwRR1XauB7YMaAnhgKJo7nfNPc45A%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240323111212800110166086597400578\\\\u0026timestamp=1711162998389\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=V0DJ3o9HHwRZYmbocE8xY9XwK3BoeimBFKWfaejDf1j0vI3FIAyA7mtfFO5BbgsDFtZotdS9%2FLuNTbdIfP2rUo0Jpx6Q68sLQIz4FvIm%2Fx6CGifWl5jqRVog1MnDycxxoErxfvj2kp1wVQrJIiYg7bBpyI6i2vR5rVA0T4c7JQsqQ81SIFuecTEAwGEp5mIfH9TLElUkvMM7cPcOsSlsqYG5x89Xnh8EPvIXIZDILhsjEJptvSpG8roZRun2Tr76wUiJbdWtInMZo1Wlgn4ucHxVWTIFzp8pRoeWkUoR7hQF1j0yxMq1JRd%2B6nwRR1XauB7YMaAnhgKJo7nfNPc45A%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-03-23T03:03:18.097708Z\\\",\\\"updated\\\":\\\"2024-03-23T03:03:18.097708Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-03-23 10:03:18'),
(78,2,'{\"reference_id\":\"TRX-242305271634\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_545eae0d-2874-4486-8a60-6b827feb8870\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-242305271634\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":10000,\\\"capture_amount\\\":10000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240516111212800110166466201232572\\\\u0026timestamp=1715876855326\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=u1AY1Gz3jBMcMghJ2arRbrHIO9LoWyHICaGy5d1AuoT2SW%2BN%2FpMAnuwWFKmmgUf3JyzcDaBZAs88K6P7C9VnVxc2QT4Bs0BA7lAnFhkotOuZYh%2BstUJKWOTTdtbrFXxObRquik0yFtLOldkx34br6eGI%2FYUl1QW8czVAgdHaI67KUY%2FEwz6LrJzp1NA9rgjXrNkHfU8N2HBohW3v7oFTf%2Fg76oRgG2Dm%2Bpwu84RuDIqeV8MHv2D9%2BDvojbfTY5vBe%2FqVSeM9OkrArcR1eCu7aXmNjIKFzHNMajwHH9BMXRivDupfPh442uky%2FpPTZdPNUyvJLLvfJoJe%2BP5Va3Bmpw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240516111212800110166466201232572\\\\u0026timestamp=1715876855326\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=u1AY1Gz3jBMcMghJ2arRbrHIO9LoWyHICaGy5d1AuoT2SW%2BN%2FpMAnuwWFKmmgUf3JyzcDaBZAs88K6P7C9VnVxc2QT4Bs0BA7lAnFhkotOuZYh%2BstUJKWOTTdtbrFXxObRquik0yFtLOldkx34br6eGI%2FYUl1QW8czVAgdHaI67KUY%2FEwz6LrJzp1NA9rgjXrNkHfU8N2HBohW3v7oFTf%2Fg76oRgG2Dm%2Bpwu84RuDIqeV8MHv2D9%2BDvojbfTY5vBe%2FqVSeM9OkrArcR1eCu7aXmNjIKFzHNMajwHH9BMXRivDupfPh442uky%2FpPTZdPNUyvJLLvfJoJe%2BP5Va3Bmpw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-05-16T16:27:35.089314Z\\\",\\\"updated\\\":\\\"2024-05-16T16:27:35.089314Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-05-16 23:27:35'),
(79,2,'{\"reference_id\":\"TRX-241507390731\",\"currency\":\"IDR\",\"amount\":20000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_28b49749-3c78-48eb-a394-0607d039c0b5\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-241507390731\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":20000,\\\"capture_amount\\\":20000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240707111212800110166219305981939\\\\u0026timestamp=1720341572110\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=j%2BnjH42rV9M%2FNutLOjxOCsTkqUzCxCKqjRr8U8vF94G49R2njEsvPmz0ubjzAe650ocifE7uUCdFCJ2q3OL6lN9rSMyCMQn%2F%2FtpOVsKERvyB0hfoHAKbZAkvDF0LTUZMHVrfM5BRin8DzSP3crX24YFz2VPU0S%2FTyzhHRFKuEEN48bz6l6yvVT3d8lpASp3fLzjbVbGibMftjbDADsMrTpZaliooR9%2FWX3e45O%2FpBGgrYKYOKvuSvzCClAvZcFyZMc%2FmQUvpkEgPDGjDCDLRL3IrTONt%2BBpFlOPpBop2OvrHjtwNz0y9uMssuBhJ0GS9iVjxI7JuF0SROQjiH%2BQ4WA%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240707111212800110166219305981939\\\\u0026timestamp=1720341572110\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=j%2BnjH42rV9M%2FNutLOjxOCsTkqUzCxCKqjRr8U8vF94G49R2njEsvPmz0ubjzAe650ocifE7uUCdFCJ2q3OL6lN9rSMyCMQn%2F%2FtpOVsKERvyB0hfoHAKbZAkvDF0LTUZMHVrfM5BRin8DzSP3crX24YFz2VPU0S%2FTyzhHRFKuEEN48bz6l6yvVT3d8lpASp3fLzjbVbGibMftjbDADsMrTpZaliooR9%2FWX3e45O%2FpBGgrYKYOKvuSvzCClAvZcFyZMc%2FmQUvpkEgPDGjDCDLRL3IrTONt%2BBpFlOPpBop2OvrHjtwNz0y9uMssuBhJ0GS9iVjxI7JuF0SROQjiH%2BQ4WA%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-07-07T08:39:31.675215Z\\\",\\\"updated\\\":\\\"2024-07-07T08:39:31.675215Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-07-07 15:39:32'),
(80,2,'{\"reference_id\":\"TRX-242307080940\",\"currency\":\"IDR\",\"amount\":101000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6282256330920\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"error_code\\\":\\\"CHANNEL_NOT_ACTIVATED\\\",\\\"message\\\":\\\"Payment request failed because this specific payment channel has not been activated. Please activate the payment channel via your dashboard or our customer service.\\\"}\\n\",\"http_code\":403}','2024-07-09 23:08:40'),
(81,2,'{\"reference_id\":\"TRX-242307080950\",\"currency\":\"IDR\",\"amount\":100000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"id\\\":\\\"ewc_a7129920-9008-4ab0-a21e-a8c1372f44de\\\",\\\"business_id\\\":\\\"65936e946f900f05f01c7b51\\\",\\\"reference_id\\\":\\\"TRX-242307080950\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":100000,\\\"capture_amount\\\":100000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240709111212800110166242506131329\\\\u0026timestamp=1720541331081\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=dDmDT7MamBgjVtB1Q3GDN5vIWKlceUPOEWUKF%2FaRe6jMV4kz%2FSlCCq6fjpy6yb5GSs%2B1URb8MmyRNbZj4OO9iGfI7Mp5egrLLaQuJmZYP4MFAg%2Frh9GCL1Wnx%2BixB6XD2OJSipC1nz%2FYRTLt8NbrejZD%2FbrlaifBhc1DYz6mEpqwjcjzt1H5ylPTfS%2BJztfbU1%2B6EYDX7efUOqnokpRIrm6wBD3HNzQCeerHbyktls4936aRY8EVxbGyHNT%2FB9%2Fku38bvRzWQMZ%2FT1KB2vauBO6yYAzYf6rtd6NTYqTdYl7dwccfrpMuh4Szmq18hzHNd34fpLRUlYWajtnf%2FuNDuw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/m.dana.id\\/m\\/portal\\/cashier\\/checkout?bizNo=20240709111212800110166242506131329\\\\u0026timestamp=1720541331081\\\\u0026originSourcePlatform=IPG\\\\u0026mid=216620000035833566172\\\\u0026did=216650000811786748170\\\\u0026sid=216660000811755862170\\\\u0026sign=dDmDT7MamBgjVtB1Q3GDN5vIWKlceUPOEWUKF%2FaRe6jMV4kz%2FSlCCq6fjpy6yb5GSs%2B1URb8MmyRNbZj4OO9iGfI7Mp5egrLLaQuJmZYP4MFAg%2Frh9GCL1Wnx%2BixB6XD2OJSipC1nz%2FYRTLt8NbrejZD%2FbrlaifBhc1DYz6mEpqwjcjzt1H5ylPTfS%2BJztfbU1%2B6EYDX7efUOqnokpRIrm6wBD3HNzQCeerHbyktls4936aRY8EVxbGyHNT%2FB9%2Fku38bvRzWQMZ%2FT1KB2vauBO6yYAzYf6rtd6NTYqTdYl7dwccfrpMuh4Szmq18hzHNd34fpLRUlYWajtnf%2FuNDuw%3D%3D\\\\u0026forceToH5=false\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2024-07-09T16:08:50.69703Z\\\",\\\"updated\\\":\\\"2024-07-09T16:08:50.69703Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2024-07-09 23:08:51'),
(82,2,'{\"reference_id\":\"TRX-242112061455\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ewe\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-14 21:06:55'),
(83,2,'{\"reference_id\":\"TRX-242112071413\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-14 21:07:13'),
(84,2,'{\"reference_id\":\"TRX-242112481613\",\"currency\":\"IDR\",\"amount\":50000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-16 21:48:13'),
(85,2,'{\"reference_id\":\"TRX-242112481628\",\"currency\":\"IDR\",\"amount\":50000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ewe\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-16 21:48:29'),
(86,2,'{\"reference_id\":\"TRX-240112332155\",\"currency\":\"IDR\",\"amount\":50000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ewe\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-21 01:33:55'),
(87,2,'{\"reference_id\":\"TRX-240812312140\",\"currency\":\"IDR\",\"amount\":1001000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285184196376\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-21 08:31:40'),
(88,2,'{\"reference_id\":\"TRX-240812322131\",\"currency\":\"IDR\",\"amount\":1000000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ewe\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2024-12-21 08:32:31'),
(89,2,'{\"reference_id\":\"TRX-251801441002\",\"currency\":\"IDR\",\"amount\":1000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285216848464\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-01-10 18:44:02'),
(90,2,'{\"reference_id\":\"TRX-252001542340\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-01-23 20:54:40'),
(91,2,'{\"reference_id\":\"TRX-251601562444\",\"currency\":\"IDR\",\"amount\":1000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285216848464\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-01-24 16:56:44'),
(92,2,'{\"reference_id\":\"TRX-251403090608\",\"currency\":\"IDR\",\"amount\":100000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-03-06 14:09:08'),
(93,2,'{\"reference_id\":\"TRX-250103251114\",\"currency\":\"IDR\",\"amount\":21000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6281278737105\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-03-11 01:25:14'),
(94,2,'{\"reference_id\":\"TRX-252103011355\",\"currency\":\"IDR\",\"amount\":50000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-03-13 21:01:55'),
(95,2,'{\"reference_id\":\"TRX-251704371656\",\"currency\":\"IDR\",\"amount\":51000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+628882335382016\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-04-16 17:37:56'),
(96,2,'{\"reference_id\":\"TRX-252307231032\",\"currency\":\"IDR\",\"amount\":1000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6285710216344\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-07-10 23:23:32'),
(97,2,'{\"reference_id\":\"TRX-251407431541\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:10','{\"body\":\"{\\\"message\\\": \\\"Your account is not allowed to access this API endpoint\\/resource. This is usually because of local regulations or business\\/industry restrictions. If you believe this restriction is incorrect, kindly contact our Customer Success team with Request-ID\\\", \\\"error_code\\\":\\\"REQUEST_FORBIDDEN_ERROR\\\"}\",\"http_code\":403}','2025-07-15 14:43:41'),
(98,2,'{\"reference_id\":\"TRX-251309301545\",\"currency\":\"IDR\",\"amount\":20000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_ccfcea3c-c47e-413f-89eb-b418f9eecef6\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-251309301545\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":20000,\\\"capture_amount\\\":20000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/ewallet-mock-connector.xendit.co\\/v1\\/ewallet_connector\\/checkouts?token=d33r55imkjfc73bvg4u0\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/ewallet-mock-connector.xendit.co\\/v1\\/ewallet_connector\\/checkouts?token=d33r55imkjfc73bvg4u0\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2025-09-15T06:30:46.155051Z\\\",\\\"updated\\\":\\\"2025-09-15T06:30:46.155051Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2025-09-15 13:30:46'),
(99,2,'{\"reference_id\":\"TRX-251309311533\",\"currency\":\"IDR\",\"amount\":51000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+62646464\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_672189f5-1170-4567-805a-ad298d70a857\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-251309311533\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":51000,\\\"capture_amount\\\":51000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_OVO\\\",\\\"channel_properties\\\":{\\\"mobile_number\\\":\\\"+62646464\\\"},\\\"actions\\\":null,\\\"is_redirect_required\\\":false,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2025-09-15T06:31:34.247624Z\\\",\\\"updated\\\":\\\"2025-09-15T06:31:34.247624Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2025-09-15 13:31:34'),
(100,2,'{\"reference_id\":\"TRX-251309341538\",\"currency\":\"IDR\",\"amount\":21000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+6261616\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_9ccf696b-1953-4c0e-b11e-a3dcdd79760c\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-251309341538\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":21000,\\\"capture_amount\\\":21000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_OVO\\\",\\\"channel_properties\\\":{\\\"mobile_number\\\":\\\"+6261616\\\"},\\\"actions\\\":null,\\\"is_redirect_required\\\":false,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2025-09-15T06:34:38.663468Z\\\",\\\"updated\\\":\\\"2025-09-15T06:34:38.663468Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2025-09-15 13:34:38'),
(101,2,'{\"reference_id\":\"TRX-251410490440\",\"currency\":\"IDR\",\"amount\":100000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_dd2e6859-ebf0-49fe-b1db-9c2e070ca536\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-251410490440\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":100000,\\\"capture_amount\\\":100000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_SHOPEEPAY\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":null,\\\"mobile_web_checkout_url\\\":null,\\\"mobile_deeplink_checkout_url\\\":\\\"https:\\/\\/ewallet-mock-connector.xendit.co\\/v1\\/ewallet_connector\\/checkouts?token=d3gd3575999s73fbfpgg\\\",\\\"qr_checkout_string\\\":\\\"test-qr-string\\\"},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2025-10-04T07:49:40.924007Z\\\",\\\"updated\\\":\\\"2025-10-04T07:49:40.924007Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2025-10-04 14:49:40'),
(102,2,'{\"reference_id\":\"TRX-250012100543\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_836ce698-7e17-4866-a07e-d38c3cc20613\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-250012100543\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":10000,\\\"capture_amount\\\":10000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_SHOPEEPAY\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":null,\\\"mobile_web_checkout_url\\\":null,\\\"mobile_deeplink_checkout_url\\\":\\\"https:\\/\\/ewallet-mock-connector.xendit.co\\/v1\\/ewallet_connector\\/checkouts?token=d4os14vgm2sc73er5al0\\\",\\\"qr_checkout_string\\\":\\\"test-qr-string\\\"},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2025-12-04T17:10:43.836809Z\\\",\\\"updated\\\":\\\"2025-12-04T17:10:43.836809Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2025-12-05 00:10:43'),
(103,2,'{\"reference_id\":\"TRX-261101000903\",\"currency\":\"IDR\",\"amount\":100000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_SHOPEEPAY\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_6a5e4ad8-e296-489b-8a3c-468ce33412c7\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-261101000903\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":100000,\\\"capture_amount\\\":100000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_SHOPEEPAY\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":null,\\\"mobile_web_checkout_url\\\":null,\\\"mobile_deeplink_checkout_url\\\":\\\"https:\\/\\/ewallet-mock-connector.xendit.co\\/v1\\/ewallet_connector\\/checkouts?token=d5g7qh2n6ccc73fg6nv0\\\",\\\"qr_checkout_string\\\":\\\"test-qr-string\\\"},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2026-01-09T04:00:04.123167Z\\\",\\\"updated\\\":\\\"2026-01-09T04:00:04.123167Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2026-01-09 11:00:04'),
(104,2,'{\"reference_id\":\"TRX-261304341031\",\"currency\":\"IDR\",\"amount\":10000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_DANA\",\"channel_properties\":{\"success_redirect_url\":\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_80b193e4-0565-486a-9034-6f2fbbdda1a2\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-261304341031\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":10000,\\\"capture_amount\\\":10000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_DANA\\\",\\\"channel_properties\\\":{\\\"success_redirect_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/xendit\\\"},\\\"actions\\\":{\\\"desktop_web_checkout_url\\\":\\\"https:\\/\\/ewallet-service-dev.xendit.co\\/ewallets\\/sandbox\\/checkout?token=5959145bcb416e15aa89f544e62e3c7d3d2a62b58413e6ff66e8b7cd40a5bf9112d452eb5651685290dc7b1dd8e2acb0069c2ffb4ebeac3205856dd6d4b4a33581546e9088874231fe25df660b76ceed25df010bf05d18ddd03ab1b5b1de0723f5a359e073aedbbea329b7bcf53a4d492bccbed057936fa4fac4f3cd0e52912d061e571d8464fdae0d70123fb4e8ba378f30d6c95ee74bec59b4a1c892facde19abe23713d93df8f64b9882232f151acf84ef5bacca52b03db202713cee02ca3ce884595833b7d2b71705a566d806bbaa336d07189b70ed3124d4147ecd7abfda1bed869ef6a18bcedd9bb1e36ef06b7b1531ca2f9d50c5a7c41db041bbebfeb6afd93fce1c9a8cebc53ab16386fdeb70d84afee560a1d561d4c\\\",\\\"mobile_web_checkout_url\\\":\\\"https:\\/\\/ewallet-service-dev.xendit.co\\/ewallets\\/sandbox\\/checkout?token=5959145bcb416e15aa89f544e62e3c7d3d2a62b58413e6ff66e8b7cd40a5bf9112d452eb5651685290dc7b1dd8e2acb0069c2ffb4ebeac3205856dd6d4b4a33581546e9088874231fe25df660b76ceed25df010bf05d18ddd03ab1b5b1de0723f5a359e073aedbbea329b7bcf53a4d492bccbed057936fa4fac4f3cd0e52912d061e571d8464fdae0d70123fb4e8ba378f30d6c95ee74bec59b4a1c892facde19abe23713d93df8f64b9882232f151acf84ef5bacca52b03db202713cee02ca3ce884595833b7d2b71705a566d806bbaa336d07189b70ed3124d4147ecd7abfda1bed869ef6a18bcedd9bb1e36ef06b7b1531ca2f9d50c5a7c41db041bbebfeb6afd93fce1c9a8cebc53ab16386fdeb70d84afee560a1d561d4c\\\",\\\"mobile_deeplink_checkout_url\\\":null,\\\"qr_checkout_string\\\":null},\\\"is_redirect_required\\\":true,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2026-04-10T06:34:31.471221Z\\\",\\\"updated\\\":\\\"2026-04-10T06:34:31.471221Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2026-04-10 13:34:31'),
(105,2,'{\"reference_id\":\"TRX-261406080403\",\"currency\":\"IDR\",\"amount\":21000,\"checkout_method\":\"ONE_TIME_PAYMENT\",\"channel_code\":\"ID_OVO\",\"channel_properties\":{\"mobile_number\":\"+62081327808876\"}}apikey:xnd_REDACTED:1','{\"body\":\"{\\\"id\\\":\\\"ewc_d0f236ab-7a43-470b-92cc-8ec37ddb8ddf\\\",\\\"business_id\\\":\\\"616bb1da6ad38937f91f9829\\\",\\\"reference_id\\\":\\\"TRX-261406080403\\\",\\\"status\\\":\\\"PENDING\\\",\\\"currency\\\":\\\"IDR\\\",\\\"charge_amount\\\":21000,\\\"capture_amount\\\":21000,\\\"payer_charged_currency\\\":null,\\\"payer_charged_amount\\\":null,\\\"refunded_amount\\\":null,\\\"checkout_method\\\":\\\"ONE_TIME_PAYMENT\\\",\\\"channel_code\\\":\\\"ID_OVO\\\",\\\"channel_properties\\\":{\\\"mobile_number\\\":\\\"+62081327808876\\\"},\\\"actions\\\":null,\\\"is_redirect_required\\\":false,\\\"callback_url\\\":\\\"https:\\/\\/admin.ogotindonesiateknologi.com\\/api\\/payment\\/callback_ewallet\\\",\\\"created\\\":\\\"2026-06-04T07:08:04.081745Z\\\",\\\"updated\\\":\\\"2026-06-04T07:08:04.081745Z\\\",\\\"void_status\\\":null,\\\"voided_at\\\":null,\\\"capture_now\\\":true,\\\"customer_id\\\":null,\\\"customer\\\":null,\\\"payment_method_id\\\":null,\\\"failure_code\\\":null,\\\"basket\\\":null,\\\"metadata\\\":null,\\\"shipping_information\\\":null,\\\"payment_detail\\\":{\\\"fund_source\\\":null,\\\"source\\\":null}}\\n\",\"http_code\":202}','2026-06-04 14:08:04');
/*!40000 ALTER TABLE `payment_log_service` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipe` tinyint(1) NOT NULL,
  `jenis` tinyint(1) NOT NULL DEFAULT 0,
  `nama` varchar(50) NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `no_rekening` varchar(50) DEFAULT NULL,
  `nama_rekening` varchar(255) DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `biaya` int(11) NOT NULL,
  `channel_code` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES
(1,1,1,'OVO','Pembayaran dengan OVO','','','','88aae63773210b48c9ea578023fbd0d7.png',1000,'ID_OVO',1,'2022-05-15 17:29:57'),
(2,1,1,'DANA','Pembayaran dengan DANA','','','','b496d57bba20776658cd554faeeb5ec3.png',0,'ID_DANA',1,'2022-05-15 18:18:12'),
(3,1,1,'LINKAJA','Pembayaran dengan LinkAja','','','','d48a0af4a95e3a97a9fa95c4eee297b4.png',0,'ID_LINKAJA',1,'2022-05-15 18:23:31'),
(4,1,1,'ShopeePay','Pembayaran dengan ShopeePay','','','','9185a224bf929d3e312aa4088f984f38.png',0,'ID_SHOPEEPAY',1,'2022-05-15 18:25:59'),
(5,1,2,'BNI Virtual Account','Transfer dari BNI atm, mobile banking atau internet banking','BNI','34643436','OGOT','1f863e627d37b6572126a49c46f8ab2f.png',0,'BNI',1,'2022-05-15 18:46:07'),
(9,1,2,'BRI Virtual Acount','Transver dari BRI atm, mobile banking atau internet banking','','','','fc1ae8cabf404718a2790e198f129483.jpeg',0,'BRI',1,'2023-07-02 22:04:38'),
(10,1,2,'MANDIRI Virtual Acount','Transver dari Mandiri atm, mobile banking atau internet banking','','','','3b19a4e7125057fbf4e88afbc2363b3d.png',0,'MANDIRI',1,'2023-07-02 22:12:23'),
(11,1,2,'BCA Virtual Acount','Transver dari BCA atm, mobile banking atau internet banking','','','','bab3e29fcae2341cbd9d9369cd0bc4f5.jpeg',0,'BCA',1,'2023-07-02 22:13:53');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payment_setting`
--

DROP TABLE IF EXISTS `payment_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `key_demo` varchar(255) NOT NULL,
  `key_production` varchar(255) NOT NULL,
  `is_demo` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_setting`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payment_setting` DISABLE KEYS */;
INSERT INTO `payment_setting` VALUES
(1,'XENDIT','xnd_REDACTED','xnd_REDACTED',1,1);
/*!40000 ALTER TABLE `payment_setting` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payment_transaksi`
--

DROP TABLE IF EXISTS `payment_transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_transaksi` (
  `id` int(11) NOT NULL,
  `invoice` varchar(30) NOT NULL,
  `tipe` varchar(20) NOT NULL,
  `metode` int(11) NOT NULL,
  `note` varchar(255) NOT NULL,
  `reff` varchar(255) NOT NULL,
  `nominal` int(11) NOT NULL,
  `biaya` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `id_user` varchar(20) NOT NULL,
  `regtime` datetime NOT NULL,
  `status` tinyint(1) NOT NULL,
  `saldo_awal` int(11) NOT NULL,
  `saldo_akhir` int(11) NOT NULL,
  `is_demo` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  `bank_code` varchar(20) DEFAULT NULL,
  `account_number` varchar(50) DEFAULT NULL,
  `payment_code` varchar(50) DEFAULT NULL,
  `payment_reff` varchar(255) DEFAULT NULL,
  `expired_date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_transaksi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payment_transaksi` DISABLE KEYS */;
INSERT INTO `payment_transaksi` VALUES
(0,'TRX-221109262716','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'P1662228745','2022-09-27 11:26:19',2,4500,14500,1,'2022-09-27 11:26:32',NULL,NULL,NULL,NULL,''),
(0,'TRX-221109282744','1',5,'VA Has Been Success Created','63327bfe5d24a28422292308',10000,0,10000,'P1662228745','2022-09-27 11:28:46',1,14500,0,1,'2022-09-28 18:29:39','BNI','8808999996886698',NULL,'63327bfe5d24a28422292308','2022-09-28T11:28:44.000Z'),
(0,'TRX-221109372701','1',5,'VA Has Been Success Created','63327def7c7bcac8104fb78d',10000,0,10000,'P1661870458','2022-09-27 11:37:03',1,10000,0,1,'2022-09-28 18:37:10','BNI','8808999979229385',NULL,'63327def7c7bcac8104fb78d','2022-09-28T11:37:01.000Z'),
(0,'TRX-221209152720','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'D1660930016','2022-09-27 12:15:22',2,64325,74325,1,'2022-09-27 12:15:30',NULL,NULL,NULL,NULL,''),
(0,'TRX-221209202716','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'D1660930016','2022-09-27 12:20:19',2,74325,84325,1,'2022-09-27 12:20:28',NULL,NULL,NULL,NULL,''),
(0,'TRX-221509052739','1',5,'VA Has Been Success Created','6332aed47c7bca7e4b4fce05',20000,0,20000,'P1662252234','2022-09-27 15:05:40',1,34000,0,1,'2022-09-28 22:06:06','BNI','8808999985298376',NULL,'6332aed47c7bca7e4b4fce05','2022-09-28T15:05:39.000Z'),
(0,'TRX-221909022754','1',5,'VA Has Been Success Created','6332e671c8bb189bf0bda440',100000,0,100000,'D1664279263','2022-09-27 19:02:57',1,14500,0,1,'2022-09-29 02:03:40','BNI','8808999965999124',NULL,'6332e671c8bb189bf0bda440','2022-09-28T19:02:54.000Z'),
(0,'TRX-222309462743','1',5,'VA Has Been Success Created','633328f4c8bb181686bdbe3b',10000,0,10000,'P1662252234','2022-09-27 23:46:45',1,19000,0,1,'2022-09-29 06:47:11','BNI','8808999917608042',NULL,'633328f4c8bb181686bdbe3b','2022-09-28T23:46:43.000Z'),
(0,'TRX-220009232805','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'M1663230500','2022-09-28 00:23:06',2,50000,60000,1,'2022-09-28 00:23:17',NULL,NULL,NULL,NULL,''),
(0,'TRX-220009552808','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'P1662252234','2022-09-28 00:55:09',2,19000,29000,1,'2022-09-28 00:55:21',NULL,NULL,NULL,NULL,''),
(0,'TRX-220009552851','1',3,'SUCCEEDED','616bb1da6ad38937f91f9829',20000,0,20000,'P1662252234','2022-09-28 00:55:53',2,29000,49000,1,'2022-09-28 00:55:59',NULL,NULL,NULL,NULL,''),
(0,'TRX-222109542819','1',5,'VA Has Been Success Created','6334601dc8bb1801bcbe438f',10000,0,10000,'P1662252234','2022-09-28 21:54:21',1,98000,0,1,'2022-09-30 04:55:12','BNI','8808999942417131',NULL,'6334601dc8bb1801bcbe438f','2022-09-29T21:54:19.000Z'),
(0,'TRX-222109552810','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',10000,0,10000,'P1662252234','2022-09-28 21:55:12',2,98000,108000,1,'2022-09-28 21:55:21',NULL,NULL,NULL,NULL,''),
(0,'TRX-220010070157','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'D1664279263','2022-10-01 00:07:59',1,14500,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220310280109','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'M1663230500','2022-10-01 03:28:12',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220310280141','1',5,'VA Has Been Success Created','6337517c7c7bca23c6516796',10000,0,10000,'M1663230500','2022-10-01 03:28:44',1,60000,0,1,'2022-10-02 10:29:05','BNI','8808999924111227',NULL,'6337517c7c7bca23c6516796','2022-10-02T03:28:41.000Z'),
(0,'TRX-220410330147','1',2,'PENDING','616bb1da6ad38937f91f9829',20000,0,20000,'D1664279263','2022-10-01 04:33:50',1,14500,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220410340100','1',5,'VA Has Been Success Created','633760c97c7bcab0b5516ce8',20000,0,20000,'D1664279263','2022-10-01 04:34:01',1,14500,0,1,'2022-10-02 11:34:23','BNI','8808999992619380',NULL,'633760c97c7bcab0b5516ce8','2022-10-02T04:34:00.000Z'),
(0,'TRX-220510190114','1',5,'VA Has Been Success Created','63376b655d24a292252b1810',50000,0,50000,'D1664279263','2022-10-01 05:19:17',1,14500,0,1,'2022-10-02 12:19:56','BNI','8808999961652481',NULL,'63376b655d24a292252b1810','2022-10-02T05:19:14.000Z'),
(0,'TRX-220510190131','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',50000,0,50000,'D1664279263','2022-10-01 05:19:34',2,14500,64500,1,'2022-10-01 05:19:43',NULL,NULL,NULL,NULL,''),
(0,'TRX-220510280143','1',5,'VA Has Been Success Created','63376d9ec8bb18f09dbfa5b4',10000,0,10000,'M1663230500','2022-10-01 05:28:46',1,60000,0,1,'2022-10-02 12:29:37','BNI','8808999945406961',NULL,'63376d9ec8bb18f09dbfa5b4','2022-10-02T05:28:43.000Z'),
(0,'TRX-220510280158','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'M1663230500','2022-10-01 05:29:01',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220510400116','1',4,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'D1664279263','2022-10-01 05:40:18',1,64500,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220510400127','1',3,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'D1664279263','2022-10-01 05:40:29',1,64500,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-221010250100','1',5,'VA Has Been Success Created','6337b30dc8bb1874a5bfbee5',20000,0,20000,'D1664279263','2022-10-01 10:25:01',1,64500,0,1,'2022-10-02 17:25:57','BNI','8808999913051490',NULL,'6337b30dc8bb1874a5bfbee5','2022-10-02T10:25:00.000Z'),
(0,'TRX-220010260352','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'M1663230500','2022-10-03 00:26:54',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220010270323','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'M1663230500','2022-10-03 00:27:26',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220010280332','1',5,'VA Has Been Success Created','6339ca427c7bcab910521a60',10000,0,10000,'M1663230500','2022-10-03 00:28:34',1,60000,0,1,'2022-10-04 07:29:13','BNI','8808999935320235',NULL,'6339ca427c7bcab910521a60','2022-10-04T00:28:32.000Z'),
(0,'TRX-220210150349','1',2,'PENDING','632f21b2023935116c6f2444',10000,0,10000,'M1663230500','2022-10-03 02:15:51',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220210160336','1',2,'PENDING','632f21b2023935116c6f2444',10000,0,10000,'M1663230500','2022-10-03 02:16:37',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220210170344','1',2,'PENDING','632f21b2023935116c6f2444',10000,0,10000,'M1663230500','2022-10-03 02:17:45',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220210180329','1',2,'PENDING','632f21b2023935116c6f2444',10000,0,10000,'M1663230500','2022-10-03 02:18:30',1,60000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-221110391154','1',4,'PENDING','616bb1da6ad38937f91f9829',100000,0,100000,'P1661870458','2022-10-11 11:39:55',1,10000,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-221210111135','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',100000,0,100000,'P1665464492','2022-10-11 12:11:37',2,0,100000,1,'2022-10-11 12:11:45',NULL,NULL,NULL,NULL,''),
(0,'TRX-221210121147','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',100000,0,100000,'D1665464319','2022-10-11 12:12:48',2,0,100000,1,'2022-10-11 12:12:55',NULL,NULL,NULL,NULL,''),
(0,'TRX-222211271635','1',5,'VA Has Been Success Created','6375016905e1b8773b99c895',50000,0,50000,'P1665481281','2022-11-16 22:27:37',1,40000,0,1,'2022-11-18 05:28:18','BNI','8808999951007009',NULL,'6375016905e1b8773b99c895','2022-11-17T22:27:35.000Z'),
(0,'TRX-222211281625','1',5,'VA Has Been Success Created','6375019bc1cb35696249461f',10000,0,10000,'P1662228745','2022-11-16 22:28:27',1,15500,0,1,'2022-11-18 05:29:22','BNI','8808999916481303',NULL,'6375019bc1cb35696249461f','2022-11-17T22:28:25.000Z'),
(0,'TRX-222311302313','1',5,'VA Has Been Success Created','637e4a98a84200a58a03e107',10000,0,10000,'P1665481281','2022-11-23 23:30:16',1,40000,0,1,'2022-11-25 06:31:56','BNI','8808999913415791',NULL,'637e4a98a84200a58a03e107','2022-11-24T23:30:13.000Z'),
(0,'TRX-222011452959','1',5,'VA Has Been Success Created','63860d1a6477666ce7406483',20000,0,20000,'P1665481281','2022-11-29 20:46:02',1,40000,0,1,'2022-12-01 03:46:27','BNI','8808999930289514',NULL,'63860d1a6477666ce7406483','2022-11-30T20:45:59.000Z'),
(0,'TRX-222011492951','1',5,'{\"account_number\":\"505681842627\",\"amount\":20000,\"bank_code\":\"BNI\",\"callback_virtual_account_id\":\"63860e02659404c1d2018bb2\",\"created\":\"2022-11-29T13:52:15.870Z\",\"currency\":\"IDR\",\"external_id\":\"TRX-222011492951\",\"id\":\"63860e8fe2cacc112547b773\",\"merchant_cod','63860e02659404c1d2018bb2',20000,0,20000,'P1665481281','2022-11-29 20:49:54',2,40000,60000,0,'2022-11-29 20:52:20','BNI','8808505681842627',NULL,'63860e02659404c1d2018bb2','2022-11-30T20:49:51.000Z'),
(0,'TRX-221611503052','1',1,'USER_DECLINED_PAYMENT','616bb1da6ad38937f91f9829',10000,0,10000,'P1665481281','2022-11-30 16:50:54',3,60000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-221112310159','1',5,'VA Has Been Success Created','63882e41ad00c6f0e701d8f5',20000,0,20000,'P1665481281','2022-12-01 11:32:01',1,60000,0,0,'2022-12-01 11:32:04','BNI','8808505687894206',NULL,'63882e41ad00c6f0e701d8f5','2022-12-02T11:31:59.000Z'),
(0,'TRX-221112360158','1',5,'VA Has Been Success Created','63882f6cad00c60bf101dad1',20000,0,20000,'P1665481281','2022-12-01 11:37:00',1,60000,0,0,'2022-12-01 11:37:03','BNI','8808505698836562',NULL,'63882f6cad00c60bf101dad1','2022-12-02T11:36:58.000Z'),
(0,'TRX-221212340348','1',5,'VA Has Been Success Created','638adffae08a5e85bd2b3ccd',20000,0,20000,'M1666133786','2022-12-03 12:34:51',1,0,0,0,'2022-12-03 12:34:54','BNI','8808505693106352',NULL,'638adffae08a5e85bd2b3ccd','2022-12-04T12:34:48.000Z'),
(0,'TRX-222112590316','1',5,'VA Has Been Success Created','638b6446e08a5e437f2c0a56',10000,0,10000,'P1670074296','2022-12-03 21:59:18',1,0,0,0,'2022-12-03 21:59:25','BNI','8808505682717359',NULL,'638b6446e08a5e437f2c0a56','2022-12-04T21:59:16.000Z'),
(0,'TRX-220512420415','1',5,'VA Has Been Success Created','638bd0c9ad00c6666b0699f9',10000,0,10000,'P1670074296','2022-12-04 05:42:18',1,0,0,0,'2022-12-04 05:42:21','BNI','8808505688672551',NULL,'638bd0c9ad00c6666b0699f9','2022-12-05T05:42:15.000Z'),
(0,'TRX-220512520459','1',4,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'P1670074296','2022-12-04 05:53:01',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-220512530413','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'P1670074296','2022-12-04 05:53:16',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230101522956','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'M1667380161','2023-01-29 01:52:58',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230101552921','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'M1667380161','2023-01-29 01:55:23',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-231501442954','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1668574239','2023-01-29 15:44:56',1,58125,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-231501452942','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1668574239','2023-01-29 15:45:45',1,58125,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-231303340413','1',5,'PENDING','6402e6693dbf86042ee2ed20',20000,5000,25000,'M1667380161','2023-03-04 13:34:16',0,0,0,0,'0000-00-00 00:00:00','BNI','8808505683419346',NULL,'6402e6693dbf86042ee2ed20','2023-03-05T13:34:13.000Z'),
(0,'TRX-230004050117','1',1,'PENDING','616bb1da6ad38937f91f9829',20000,1000,21000,'P1661870458','2023-04-01 00:05:19',1,143000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232204371125','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'M1667380161','2023-04-11 22:37:27',1,27250,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232204391153','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'M1667380161','2023-04-11 22:39:54',1,27250,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304331116','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:33:18',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304341105','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:34:06',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304341150','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:34:51',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304351119','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:35:20',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304351136','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:35:38',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304361108','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:36:10',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304381120','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:38:22',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-232304391109','1',1,'PENDING','616bb1da6ad38937f91f9829',0,1000,1000,'D1679735385','2023-04-11 23:39:11',1,17600,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230205150316','1',5,'PENDING','64516149a34e7ebe5bfd89d7',20000,5000,25000,'M1667380161','2023-05-03 02:15:20',0,25500,0,0,'0000-00-00 00:00:00','BNI','8808505698819277',NULL,'64516149a34e7ebe5bfd89d7','2023-05-04T02:15:16.000Z'),
(0,'TRX-231405270644','1',5,'PENDING','645601742d592e162b783a0a',100000,5000,105000,'M1667380161','2023-05-06 14:27:47',0,25500,0,0,'0000-00-00 00:00:00','BNI','8808505699922820',NULL,'645601742d592e162b783a0a','2023-05-07T14:27:44.000Z'),
(0,'TRX-231005462100','1',4,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'P1661870458','2023-05-21 10:46:03',1,18000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-231005552141','1',5,'PENDING','6469963f3931ea8015388202',10000,5000,15000,'D1683054562','2023-05-21 10:55:42',0,0,0,0,'0000-00-00 00:00:00','BNI','8808505692629015',NULL,'6469963f3931ea8015388202','2023-05-22T10:55:41.000Z'),
(0,'TRX-231005572134','1',5,'PENDING','646996b1b139d26f738b7861',10000,5000,15000,'D1683054562','2023-05-21 10:57:35',0,0,0,0,'0000-00-00 00:00:00','BNI','8808505694651361',NULL,'646996b1b139d26f738b7861','2023-05-22T10:57:34.000Z'),
(0,'TRX-231406160947','1',5,'PENDING','6482d1e3a02bea5d057ddb83',10000,5000,15000,'P1686281806','2023-06-09 14:16:51',0,0,0,0,'0000-00-00 00:00:00','BNI','8808505697727831',NULL,'6482d1e3a02bea5d057ddb83','2023-06-10T14:16:47.000Z'),
(0,'TRX-231806332216','1',5,'PENDING','6494317fc19575a8175154d0',10000,5000,15000,'P1686964781','2023-06-22 18:33:19',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505699357288',NULL,'6494317fc19575a8175154d0','2023-06-23T18:33:16.000Z'),
(0,'TRX-232006072302','1',5,'PENDING','649598f93bcd36b03ca412b8',10000,5000,15000,'P1686964781','2023-06-23 20:07:05',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505696643127',NULL,'649598f93bcd36b03ca412b8','2023-06-24T20:07:02.000Z'),
(0,'TRX-232006072327','1',5,'PENDING','6495991012310dd1f703679a',10000,5000,15000,'P1686964781','2023-06-23 20:07:28',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505687863850',NULL,'6495991012310dd1f703679a','2023-06-24T20:07:27.000Z'),
(0,'TRX-232006172307','1',5,'PENDING','64959b5597f17662c9bf1778',10000,5000,15000,'P1686964781','2023-06-23 20:17:09',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505686251632',NULL,'64959b5597f17662c9bf1778','2023-06-24T20:17:07.000Z'),
(0,'TRX-232006372355','1',5,'PENDING','6495a035d2785d625b99ea61',10000,5000,15000,'P1686964781','2023-06-23 20:37:57',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505699293215',NULL,'6495a035d2785d625b99ea61','2023-06-24T20:37:55.000Z'),
(0,'TRX-232006582354','1',5,'PENDING','6495a51fd2785d625b99ed77',10000,5000,15000,'P1686964781','2023-06-23 20:58:55',0,5000,0,0,'0000-00-00 00:00:00','BNI','8808505688434619',NULL,'6495a51fd2785d625b99ed77','2023-06-24T20:58:54.000Z'),
(0,'TRX-231507460235','1',5,'PENDING','64a1396ddbf3175fd9b39559',100000,5000,105000,'P1686281806','2023-07-02 15:46:37',0,2999,0,0,'0000-00-00 00:00:00','BNI','8808505682196544',NULL,'64a1396ddbf3175fd9b39559','2023-07-03T15:46:35.000Z'),
(0,'TRX-230507140353','1',9,'PENDING','64a1f6dfdbf3175fd9b4352b',1000,0,1000,'P1686964781','2023-07-03 05:14:55',0,4000,0,0,'0000-00-00 00:00:00','BRI','2621550561962199441',NULL,'64a1f6dfdbf3175fd9b4352b','2023-07-04T05:14:53.000Z'),
(0,'TRX-230507270326','1',9,'PENDING','64a1f9ced02dc509100e83ff',1000,0,1000,'P1686964781','2023-07-03 05:27:26',0,4000,0,0,'0000-00-00 00:00:00','BRI','2621550566455931282',NULL,'64a1f9ced02dc509100e83ff','2023-07-04T05:27:26.000Z'),
(0,'TRX-230507280331','1',5,'PENDING','64a1fa10d02dc509100e8430',1000,0,1000,'P1686964781','2023-07-03 05:28:32',0,4000,0,0,'0000-00-00 00:00:00','BNI','8808505696453255',NULL,'64a1fa10d02dc509100e8430','2023-07-04T05:28:31.000Z'),
(0,'TRX-230507440330','1',3,'PENDING','616bb1da6ad38937f91f9829',1000,0,1000,'P1686964781','2023-07-03 05:44:32',1,4000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230507450301','1',4,'PENDING','616bb1da6ad38937f91f9829',1000,0,1000,'P1686964781','2023-07-03 05:45:05',1,4000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230507460327','1',4,'PENDING','616bb1da6ad38937f91f9829',50000,0,50000,'P1686964781','2023-07-03 05:46:29',1,4000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230507460339','1',4,'PENDING','616bb1da6ad38937f91f9829',50000,0,50000,'P1686964781','2023-07-03 05:46:41',1,4000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-230507470330','1',2,'PENDING','616bb1da6ad38937f91f9829',50000,0,50000,'P1686964781','2023-07-03 05:47:31',1,4000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-241901121206','1',2,'PENDING','65936e946f900f05f01c7b51',20000,0,20000,'P1703904560','2024-01-12 19:12:07',1,1000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-242001301224','1',2,'PENDING','65936e946f900f05f01c7b51',20000,0,20000,'P1703904560','2024-01-12 20:30:25',1,1000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-240901471454','1',2,'PENDING','65936e946f900f05f01c7b51',20000,0,20000,'P1703904560','2024-01-14 09:47:54',1,1000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-240901591449','1',2,'PENDING','65936e946f900f05f01c7b51',10000,0,10000,'P1703904560','2024-01-14 09:59:50',1,1000,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-240801471544','1',2,'PENDING','65936e946f900f05f01c7b51',10000,0,10000,'D1705126060','2024-01-15 08:47:44',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-241201191612','1',2,'SUCCEEDED','65936e946f900f05f01c7b51',2000,0,2000,'P1703904560','2024-01-16 12:19:12',2,1000,3000,0,'2024-01-16 12:19:55',NULL,NULL,NULL,NULL,''),
(0,'TRX-241501061739','1',5,'VA Has Been Success Created','cb3aaeb2-483a-425e-9571-333d8554a45b',10000,0,10000,'D1705126060','2024-01-17 15:06:39',1,0,0,0,'2024-01-17 15:06:41','BNI','8930610217872703',NULL,'cb3aaeb2-483a-425e-9571-333d8554a45b','2024-01-18T15:06:39.000Z'),
(0,'TRX-241501081720','1',5,'VA Has Been Success Created','b8dcbe5c-9d3b-436c-8cfc-6458ea79aeff',20000,0,20000,'P1703904560','2024-01-17 15:08:21',1,3000,0,0,'2024-01-17 15:08:22','BNI','8930610200915900',NULL,'b8dcbe5c-9d3b-436c-8cfc-6458ea79aeff','2024-01-18T15:08:20.000Z'),
(0,'TRX-241501091702','1',5,'VA Has Been Success Created','2e4b2f29-c6fd-43a5-bd79-13a3404bd9a4',20000,0,20000,'P1703904560','2024-01-17 15:09:03',1,3000,0,0,'2024-01-17 15:09:04','BNI','8930610203472855',NULL,'2e4b2f29-c6fd-43a5-bd79-13a3404bd9a4','2024-01-18T15:09:02.000Z'),
(0,'TRX-241501091759','1',5,'VA Has Been Success Created','7d0ab21e-51c1-40ba-a4cd-292b654bfd12',20000,0,20000,'D1688601576','2024-01-17 15:10:00',1,68349,0,0,'2024-01-17 15:10:02','BNI','8930610218099776',NULL,'7d0ab21e-51c1-40ba-a4cd-292b654bfd12','2024-01-18T15:09:59.000Z'),
(0,'TRX-241501111716','1',5,'VA Has Been Success Created','9000e75f-2159-4e2b-aca9-01c68464095d',20000,0,20000,'P1701034124','2024-01-17 15:11:17',1,942,0,0,'2024-01-17 15:11:18','BNI','8930610200014535',NULL,'9000e75f-2159-4e2b-aca9-01c68464095d','2024-01-18T15:11:16.000Z'),
(0,'TRX-241501111727','1',5,'VA Has Been Success Created','0ad63bca-fa81-42a2-bbcb-c067201939bc',20000,0,20000,'P1701034124','2024-01-17 15:11:27',1,942,0,0,'2024-01-17 15:11:29','BNI','8930610215454216',NULL,'0ad63bca-fa81-42a2-bbcb-c067201939bc','2024-01-18T15:11:27.000Z'),
(0,'TRX-241501121737','1',5,'VA Has Been Success Created','f2a8bb5e-4e91-45c4-bce1-5ff7d3abb98f',20000,0,20000,'M1686320534','2024-01-17 15:12:38',1,19200,0,0,'2024-01-17 15:12:39','BNI','8930610200097146',NULL,'f2a8bb5e-4e91-45c4-bce1-5ff7d3abb98f','2024-01-18T15:12:37.000Z'),
(0,'TRX-241501231734','1',5,'VA Has Been Success Created','906621a4-9e8f-4b06-bcf5-956513049656',10000,0,10000,'P1703347602','2024-01-17 15:23:34',1,0,0,0,'2024-01-17 15:23:36','BNI','8930610211084888',NULL,'906621a4-9e8f-4b06-bcf5-956513049656','2024-01-18T15:23:34.000Z'),
(0,'TRX-241501291752','1',5,'VA Has Been Success Created','c8cb074f-d2f1-4e42-bbda-7440d257df2c',10000,0,10000,'P1703347602','2024-01-17 15:29:52',1,0,0,0,'2024-01-17 15:29:53','BNI','8930610216705735',NULL,'c8cb074f-d2f1-4e42-bbda-7440d257df2c','2024-01-18T15:29:52.000Z'),
(0,'TRX-241501501700','1',5,'VA Has Been Success Created','617bbb96-ff2a-46a5-858b-eea14d60929a',20000,0,20000,'P1703904560','2024-01-17 15:50:01',1,3000,0,0,'2024-01-17 15:50:03','BNI','8930610209474545',NULL,'617bbb96-ff2a-46a5-858b-eea14d60929a','2024-01-18T15:50:00.000Z'),
(0,'TRX-241601091742','1',5,'VA Has Been Success Created','6e93b0f2-8d5e-4cbe-96fb-17e08f99b2d1',20000,0,20000,'P1701034124','2024-01-17 16:09:43',1,942,0,0,'2024-01-17 16:09:44','BNI','8930610218006718',NULL,'6e93b0f2-8d5e-4cbe-96fb-17e08f99b2d1','2024-01-18T16:09:42.000Z'),
(0,'TRX-241601091754','1',5,'VA Has Been Success Created','50f0919f-cbe6-4566-83fb-8282f2967daf',20000,0,20000,'P1701034124','2024-01-17 16:09:55',1,942,0,0,'2024-01-17 16:10:00','BNI','8930610202615788',NULL,'50f0919f-cbe6-4566-83fb-8282f2967daf','2024-01-18T16:09:54.000Z'),
(0,'TRX-241601121738','1',5,'VA Has Been Success Created','5ac8318c-d62f-4459-b2f8-9b975e1ca702',20000,0,20000,'P1701034124','2024-01-17 16:12:39',1,942,0,0,'2024-01-17 16:12:42','BNI','8930610214342036',NULL,'5ac8318c-d62f-4459-b2f8-9b975e1ca702','2024-01-18T16:12:38.000Z'),
(0,'TRX-241601271746','1',5,'VA Has Been Success Created','c37b2cc8-e4f2-45c2-8637-c8880c30d5b5',10000,0,10000,'P1703347602','2024-01-17 16:27:46',1,0,0,0,'2024-01-17 16:27:48','BNI','8930610214182502',NULL,'c37b2cc8-e4f2-45c2-8637-c8880c30d5b5','2024-01-18T16:27:46.000Z'),
(0,'TRX-241601281722','1',5,'VA Has Been Success Created','63228055-3892-4e5b-b24c-ad70833e91a1',10000,0,10000,'P1703347602','2024-01-17 16:28:23',1,0,0,0,'2024-01-17 16:28:24','BNI','8930610213710496',NULL,'63228055-3892-4e5b-b24c-ad70833e91a1','2024-01-18T16:28:22.000Z'),
(0,'TRX-241601311748','1',5,'VA Has Been Success Created','0095e86d-4df7-42b3-9328-860c0b06769c',10000,0,10000,'P1703347602','2024-01-17 16:31:48',1,0,0,0,'2024-01-17 16:31:49','BNI','8930610212349412',NULL,'0095e86d-4df7-42b3-9328-860c0b06769c','2024-01-18T16:31:48.000Z'),
(0,'TRX-241901371749','1',5,'VA Has Been Success Created','65bd61b1-5f3e-4fce-919e-c5c914360ad0',1000,0,1000,'P1701034124','2024-01-17 19:37:49',1,942,0,0,'2024-01-17 19:37:51','BNI','8930610207608615',NULL,'65bd61b1-5f3e-4fce-919e-c5c914360ad0','2024-01-18T19:37:49.000Z'),
(0,'TRX-240801341834','1',5,'VA Has Been Success Created','f478086d-23d9-4c39-bf70-a59824d8fd53',20000,0,20000,'P1701034124','2024-01-18 08:34:35',1,942,0,0,'2024-01-18 08:34:36','BNI','8930610212761635',NULL,'f478086d-23d9-4c39-bf70-a59824d8fd53','2024-01-19T08:34:34.000Z'),
(0,'TRX-241201011804','1',5,'VA Has Been Success Created','9a74671f-8b06-4d7c-9dc2-2575a2e6ba5d',20000,0,20000,'P1701034124','2024-01-18 12:01:04',1,942,0,0,'2024-01-18 12:01:06','BNI','8930610213913516',NULL,'9a74671f-8b06-4d7c-9dc2-2575a2e6ba5d','2024-01-19T12:01:04.000Z'),
(0,'TRX-240701302044','1',5,'VA Has Been Success Created','ad939611-2185-47da-a9c5-06bde290148c',1000,0,1000,'P1701034124','2024-01-20 07:30:45',1,50242,0,0,'2024-01-20 07:30:46','BNI','8930610209219002',NULL,'ad939611-2185-47da-a9c5-06bde290148c','2024-01-21T07:30:44.000Z'),
(0,'TRX-241702091140','1',5,'VA Has Been Success Created','6f17214e-02b5-4edb-b566-a58520a8c3f9',10000,0,10000,'P1703904560','2024-02-11 17:09:41',1,18000,0,0,'2024-02-11 17:09:42','BNI','8930610207069486',NULL,'6f17214e-02b5-4edb-b566-a58520a8c3f9','2024-02-12T17:09:40.000Z'),
(0,'TRX-242202572405','1',5,'VA Has Been Success Created','eb250f87-edc3-482d-9ec4-d25169aee5f5',10000,0,10000,'P1703904560','2024-02-24 22:57:06',1,18000,0,0,'2024-02-24 22:57:08','BNI','8930610206689907',NULL,'eb250f87-edc3-482d-9ec4-d25169aee5f5','2024-02-25T22:57:05.000Z'),
(0,'TRX-241003032316','1',2,'PENDING','65936e946f900f05f01c7b51',50000,0,50000,'P1701034124','2024-03-23 10:03:18',1,22242,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-241204221744','1',5,'VA Has Been Success Created','76a1ad6a-e7ee-45a8-82f2-fcc2bf300b9c',20000,0,20000,'P1703904560','2024-04-17 12:22:45',1,17000,0,0,'2024-04-17 12:22:46','BNI','8930610219811723',NULL,'76a1ad6a-e7ee-45a8-82f2-fcc2bf300b9c','2024-04-18T12:22:44.000Z'),
(0,'TRX-241204221755','1',9,'VA Has Been Success Created','5647abb2-ae79-403b-9104-b8390a6f5e4a',20000,0,20000,'P1703904560','2024-04-17 12:22:56',1,17000,0,0,'2024-04-17 12:22:57','BRI','13282610208095651',NULL,'5647abb2-ae79-403b-9104-b8390a6f5e4a','2024-04-18T12:22:55.000Z'),
(0,'TRX-241804341907','1',5,'VA Has Been Success Created','e5dabc24-574e-473d-9564-64a02cf63296',10000,0,10000,'D1688601576','2024-04-19 18:34:07',1,65657,0,0,'2024-04-19 18:34:09','BNI','8930610202560359',NULL,'e5dabc24-574e-473d-9564-64a02cf63296','2024-04-20T18:34:07.000Z'),
(0,'TRX-240605130816','1',5,'VA Has Been Success Created','01a6e6b0-e958-4994-a149-ff0482d692c6',10000,0,10000,'P1703904560','2024-05-08 06:13:17',1,17000,0,0,'2024-05-08 06:13:18','BNI','8930610215080937',NULL,'01a6e6b0-e958-4994-a149-ff0482d692c6','2024-05-09T06:13:16.000Z'),
(0,'TRX-242305271634','1',2,'PENDING','65936e946f900f05f01c7b51',10000,0,10000,'P1701034124','2024-05-16 23:27:35',1,12242,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-240205511820','1',5,'VA Has Been Success Created','86fb2a97-6a80-4abd-8789-98263341fffb',10000,0,10000,'D1688601576','2024-05-18 02:51:22',1,63497,0,0,'2024-05-18 02:51:23','BNI','8930610219233172',NULL,'86fb2a97-6a80-4abd-8789-98263341fffb','2024-05-19T02:51:20.000Z'),
(0,'TRX-242205092551','1',5,'VA Has Been Success Created','ffd34925-b7e8-4a12-8c39-ba00977e1ba3',10000,0,10000,'P1703904560','2024-05-25 22:09:52',1,17000,0,0,'2024-05-25 22:09:53','BNI','8930610204438114',NULL,'ffd34925-b7e8-4a12-8c39-ba00977e1ba3','2024-05-26T22:09:51.000Z'),
(0,'TRX-242206580932','1',5,'VA Has Been Success Created','6144b29a-2609-4802-b430-6c6dfa33d454',10000,0,10000,'M1686903409','2024-06-09 22:58:33',1,47300,0,0,'2024-06-09 22:58:34','BNI','8930610200691149',NULL,'6144b29a-2609-4802-b430-6c6dfa33d454','2024-06-10T22:58:32.000Z'),
(0,'TRX-240707260427','1',5,'VA Has Been Success Created','cea60f33-dbe0-4135-aacb-6b2498b14b1e',20000,0,20000,'M1686320534','2024-07-04 07:26:28',1,19200,0,0,'2024-07-04 07:26:29','BNI','8930610210476463',NULL,'cea60f33-dbe0-4135-aacb-6b2498b14b1e','2024-07-05T07:26:27.000Z'),
(0,'TRX-241507390715','1',9,'VA Has Been Success Created','ab510530-d9d6-4d49-bac8-c439f2c07db3',20000,0,20000,'P1720341338','2024-07-07 15:39:16',1,0,0,0,'2024-07-07 15:39:17','BRI','13282610211500890',NULL,'ab510530-d9d6-4d49-bac8-c439f2c07db3','2024-07-08T15:39:15.000Z'),
(0,'TRX-241507390731','1',2,'PENDING','65936e946f900f05f01c7b51',20000,0,20000,'P1720341338','2024-07-07 15:39:32',1,0,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-240407360922','1',5,'VA Has Been Success Created','d1973369-e022-48d0-8742-52ddbb1f8a43',10000,0,10000,'D1688601576','2024-07-09 04:36:22',1,65427,0,0,'2024-07-09 04:36:24','BNI','8930610207795658',NULL,'d1973369-e022-48d0-8742-52ddbb1f8a43','2024-07-10T04:36:22.000Z'),
(0,'TRX-240407440933','1',5,'VA Has Been Success Created','59a450af-0bf0-490e-a8ad-c8a10d619b7b',10000,0,10000,'M1686320534','2024-07-09 04:44:34',1,19200,0,0,'2024-07-09 04:44:35','BNI','8930610215448847',NULL,'59a450af-0bf0-490e-a8ad-c8a10d619b7b','2024-07-10T04:44:33.000Z'),
(0,'TRX-242307080950','1',2,'PENDING','65936e946f900f05f01c7b51',100000,0,100000,'P1701034124','2024-07-09 23:08:51',1,12242,0,0,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-250707312412','1',10,'VA Has Been Success Created','400aaafa-e689-4898-ab80-b6fe3545f846',10000,0,10000,'P1735004105','2025-07-24 07:31:13',1,49800,0,1,'2025-07-24 07:31:14','MANDIRI','889089999984235',NULL,'400aaafa-e689-4898-ab80-b6fe3545f846','2025-07-25T07:31:12.000Z'),
(0,'TRX-251207492408','1',5,'VA Has Been Success Created','5f1e99d3-55e7-403f-ab7d-10702fa1fc5f',10000,0,10000,'P1735004105','2025-07-24 12:49:09',1,49800,0,1,'2025-07-24 12:49:09','BNI','8808999970977707',NULL,'5f1e99d3-55e7-403f-ab7d-10702fa1fc5f','2025-07-25T12:49:08.000Z'),
(0,'TRX-252207182515','1',5,'VA Has Been Success Created','b2911c6f-ae9f-458c-b53d-a615132d73b6',100000,0,100000,'P1735004105','2025-07-25 22:18:16',1,49800,0,1,'2025-07-25 22:18:17','BNI','8808999919190458',NULL,'b2911c6f-ae9f-458c-b53d-a615132d73b6','2025-07-26T22:18:15.000Z'),
(0,'TRX-250207543003','1',5,'VA Has Been Success Created','25a06d14-d9e7-47da-98ce-674a1326d83d',50000,0,50000,'P1735004105','2025-07-30 02:54:04',1,38800,0,1,'2025-07-30 02:54:05','BNI','8808999902127453',NULL,'25a06d14-d9e7-47da-98ce-674a1326d83d','2025-07-31T02:54:03.000Z'),
(0,'TRX-251408311914','1',5,'VA Has Been Success Created','54d0a8d6-27bd-40d6-a4ca-8e56e9f6ebed',20000,0,20000,'P1735004105','2025-08-19 14:31:15',1,38800,0,1,'2025-08-19 14:31:16','BNI','8808999936611381',NULL,'54d0a8d6-27bd-40d6-a4ca-8e56e9f6ebed','2025-08-20T14:31:14.000Z'),
(0,'TRX-251408331946','1',5,'VA Has Been Success Created','337f848d-6cb6-48d8-8aab-db1e1b9e0285',10000,0,10000,'D1754050053','2025-08-19 14:33:46',1,50000,0,1,'2025-08-19 14:33:49','BNI','8808999921495810',NULL,'337f848d-6cb6-48d8-8aab-db1e1b9e0285','2025-08-20T14:33:46.000Z'),
(0,'TRX-251708251953','1',5,'VA Has Been Success Created','0cbeda6e-4a23-46bc-a348-1ddedc5c062b',20000,0,20000,'P1735004105','2025-08-19 17:25:54',1,38800,0,1,'2025-08-19 17:25:55','BNI','8808999945759737',NULL,'0cbeda6e-4a23-46bc-a348-1ddedc5c062b','2025-08-20T17:25:53.000Z'),
(0,'TRX-251708482254','1',9,'VA Has Been Success Created','2012057e-aa77-449f-b9b6-75029bbf9171',10000,0,10000,'P1735004105','2025-08-22 17:48:55',1,28800,0,1,'2025-08-22 17:48:56','BRI','262159999480787',NULL,'2012057e-aa77-449f-b9b6-75029bbf9171','2025-08-23T17:48:54.000Z'),
(0,'TRX-250809011011','1',5,'VA Has Been Success Created','8909b48a-325d-4e53-b4f3-ddf111d63256',10000,0,10000,'P1735004105','2025-09-10 08:01:11',1,28800,0,1,'2025-09-10 08:01:13','BNI','8808999913069940',NULL,'8909b48a-325d-4e53-b4f3-ddf111d63256','2025-09-11T08:01:11.000Z'),
(0,'TRX-250809011031','1',5,'VA Has Been Success Created','72847d28-7467-4316-b763-351abcbce3d0',10000,0,10000,'P1735004105','2025-09-10 08:01:31',1,28800,0,1,'2025-09-10 08:01:33','BNI','8808999966132876',NULL,'72847d28-7467-4316-b763-351abcbce3d0','2025-09-11T08:01:31.000Z'),
(0,'TRX-251109301215','1',9,'VA Has Been Success Created','a479a2a4-6441-4e14-93ae-6992da5d31a4',10000,0,10000,'P1735004105','2025-09-12 11:30:16',1,28800,0,1,'2025-09-12 11:30:17','BRI','262159999678673',NULL,'a479a2a4-6441-4e14-93ae-6992da5d31a4','2025-09-13T11:30:15.000Z'),
(0,'TRX-251309301545','1',2,'SUCCEEDED','616bb1da6ad38937f91f9829',20000,0,20000,'M1735004384','2025-09-15 13:30:46',2,44300,64300,1,'2025-09-15 13:30:59',NULL,NULL,NULL,NULL,''),
(0,'TRX-251309311533','1',1,'SUCCEEDED','616bb1da6ad38937f91f9829',50000,1000,51000,'M1735004384','2025-09-15 13:31:34',2,64300,114300,1,'2025-09-15 13:31:36',NULL,NULL,NULL,NULL,''),
(0,'TRX-251309341538','1',1,'SUCCEEDED','616bb1da6ad38937f91f9829',20000,1000,21000,'D1735003893','2025-09-15 13:34:38',2,39280,59280,1,'2025-09-15 13:34:41',NULL,NULL,NULL,NULL,''),
(0,'TRX-251410490440','1',4,'PENDING','616bb1da6ad38937f91f9829',100000,0,100000,'P1759559503','2025-10-04 14:49:40',1,0,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-251410500403','1',5,'VA Has Been Success Created','b03487a6-0ef3-475a-ae19-79efe480eb2a',100000,0,100000,'P1759559503','2025-10-04 14:50:03',1,0,0,1,'2025-10-04 14:50:05','BNI','8808999911494765',NULL,'b03487a6-0ef3-475a-ae19-79efe480eb2a','2025-10-05T14:50:03.000Z'),
(0,'TRX-251410510422','1',5,'VA Has Been Success Created','30b05004-4f77-4137-bed8-a1e067ec36b8',20000,0,20000,'P1759559503','2025-10-04 14:51:22',1,0,0,1,'2025-10-04 14:51:24','BNI','8808999927386196',NULL,'30b05004-4f77-4137-bed8-a1e067ec36b8','2025-10-05T14:51:22.000Z'),
(0,'TRX-251410520412','1',11,'VA Has Been Success Created','5d323e82-c52d-4dfa-8228-5fda40b997aa',20000,0,20000,'P1759559503','2025-10-04 14:52:12',1,0,0,1,'2025-10-04 14:52:14','BCA','107669999529651',NULL,'5d323e82-c52d-4dfa-8228-5fda40b997aa','2025-10-05T14:52:12.000Z'),
(0,'TRX-251311050118','1',11,'VA Has Been Success Created','e2ca8a51-bdd6-478f-b2bf-45052de79c60',20000,0,20000,'P1761976856','2025-11-01 13:05:19',1,0,0,1,'2025-11-01 13:05:21','BCA','107669999935819',NULL,'e2ca8a51-bdd6-478f-b2bf-45052de79c60','2025-11-02T13:05:18.000Z'),
(0,'TRX-250012100543','1',4,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'P1759559503','2025-12-05 00:10:43',1,0,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-250012100557','1',5,'VA Has Been Success Created','2d14114d-3a87-40fd-b121-01b62a50af3d',10000,0,10000,'P1759559503','2025-12-05 00:10:57',1,0,0,1,'2025-12-05 00:10:59','BNI','8808999949757530',NULL,'2d14114d-3a87-40fd-b121-01b62a50af3d','2025-12-06T00:10:57.000Z'),
(0,'TRX-262001590801','1',5,'VA Has Been Success Created','96f55cdb-4884-4e88-88c7-529ed25e3b6d',100000,0,100000,'P1735004105','2026-01-08 20:59:01',1,1800,0,1,'2026-01-08 20:59:03','BNI','8808999987417644',NULL,'96f55cdb-4884-4e88-88c7-529ed25e3b6d','2026-01-09T20:59:01.000Z'),
(0,'TRX-261001390906','1',11,'VA Has Been Success Created','9ef71957-c274-4a9d-8036-f894857b9db1',100000,0,100000,'D1735003893','2026-01-09 10:39:06',1,139280,0,1,'2026-01-09 10:39:07','BCA','107669999984235',NULL,'9ef71957-c274-4a9d-8036-f894857b9db1','2026-01-10T10:39:06.000Z'),
(0,'TRX-261001590926','1',5,'VA Has Been Success Created','0e67be49-4aea-4c7b-bd99-c79bc66a8b6c',100000,0,100000,'D1735003893','2026-01-09 10:59:26',1,-241820,0,1,'2026-01-09 10:59:27','BNI','8808999971867416',NULL,'0e67be49-4aea-4c7b-bd99-c79bc66a8b6c','2026-01-10T10:59:26.000Z'),
(0,'TRX-261101000903','1',4,'PENDING','616bb1da6ad38937f91f9829',100000,0,100000,'D1735003893','2026-01-09 11:00:04',1,-241820,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-261101040945','1',11,'VA Has Been Success Created','8fe5f14a-74a4-4cb6-b71e-5e51e98df80f',100000,0,100000,'P1735004105','2026-01-09 11:04:45',1,800,0,1,'2026-01-09 11:04:46','BCA','107669999819430',NULL,'8fe5f14a-74a4-4cb6-b71e-5e51e98df80f','2026-01-10T11:04:45.000Z'),
(0,'TRX-261101080902','1',5,'VA Has Been Success Created','7b4e3f2a-9b21-4d7d-b57f-2051a543b4e9',100000,0,100000,'P1735004105','2026-01-09 11:08:03',1,800,0,1,'2026-01-09 11:08:05','BNI','8808999921295786',NULL,'7b4e3f2a-9b21-4d7d-b57f-2051a543b4e9','2026-01-10T11:08:02.000Z'),
(0,'TRX-261304341031','1',2,'PENDING','616bb1da6ad38937f91f9829',10000,0,10000,'P1775197589','2026-04-10 13:34:31',1,0,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,''),
(0,'TRX-261406080403','1',1,'SUCCEEDED','616bb1da6ad38937f91f9829',20000,1000,21000,'P1735004105','2026-06-04 14:08:04',2,96800,116800,1,'2026-06-04 14:08:06',NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `payment_transaksi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `payusettings`
--

DROP TABLE IF EXISTS `payusettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payusettings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payu_key` varchar(250) NOT NULL,
  `payu_id` varchar(250) NOT NULL,
  `payu_salt` varchar(250) NOT NULL,
  `payu_debug` varchar(250) NOT NULL,
  `active` varchar(200) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payusettings`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `payusettings` DISABLE KEYS */;
INSERT INTO `payusettings` VALUES
(1,'4JreBobn','7094565','gIY79pFnX9','0','0');
/*!40000 ALTER TABLE `payusettings` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `pelanggan`
--

DROP TABLE IF EXISTS `pelanggan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelanggan` (
  `id` varchar(200) NOT NULL,
  `fullnama` varchar(500) NOT NULL,
  `email` varchar(50) NOT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `countrycode` varchar(20) NOT NULL,
  `phone` varchar(250) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `tgl_lahir` varchar(50) NOT NULL,
  `rating_pelanggan` double NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 1,
  `token` varchar(250) NOT NULL DEFAULT '1234',
  `fotopelanggan` varchar(500) NOT NULL,
  `israte` int(11) NOT NULL DEFAULT 0,
  `id_driver` varchar(255) DEFAULT '0',
  `id_transaksi` varchar(255) DEFAULT '0',
  `total_biaya` varchar(255) DEFAULT '0',
  `pakai_wallet` varchar(255) NOT NULL DEFAULT 'false',
  `nama_driver` varchar(255) DEFAULT '',
  `foto_driver` varchar(255) DEFAULT '',
  `fitur` varchar(255) DEFAULT '0',
  `response` varchar(255) DEFAULT '0',
  `point_driver` int(11) NOT NULL DEFAULT 0,
  `istopup` int(1) NOT NULL DEFAULT 0,
  `noreff` varchar(250) NOT NULL,
  `islogin` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`,`pakai_wallet`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `no_telepon` (`no_telepon`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelanggan`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `pelanggan` DISABLE KEYS */;
INSERT INTO `pelanggan` VALUES
('P1735004105','demo','ogottolis@gmail.com','6282335382016','+62','82335382016','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2024-12-24 01:35:05','2025-11-04',0,1,'fbLaRZSESIu7jkmllqI7oK:APA91bEezfQNaR2uYzufHXh631oBXBjNQcvo8vMD8sQjTxYBY6GzqOSuQXHSXwiaYz0-8nCmdEjdb_pfqyGq1-6wgK4dufNbP0mA444_h8nvfdtrx9x9_b4','f22a8f56a84f0223028273d812e885f6.jpeg',0,'0','0','0','false',NULL,NULL,'0','0',0,0,'',0),
('P1735023118','ego','egosandijumran@gmail.com','6282292609816','+62','82292609816','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2024-12-24 06:51:58','',0,1,'dOUd7ShPQgaQDYAvbHYNvp:APA91bHoRKxp9uXyNN1qFQYhsbtyu6Aon1ldy2J-T69Q7lMQ3GH-yrX92e2Euli98KcSJ6SFcHsjOe3WW2yzm3luMRC24jjQl4KyMr7hP4fu45UGLRopOgo','',0,'0','0','0','false',NULL,NULL,'0','0',0,0,'',0),
('P1735092903','arfan123','vanpelarisiput@gmail.com','6285198144756','+62','85198144756','0a5a795c27d958e44ba5b28082c1e17ebcd8c42f','2024-12-25 02:15:03','',0,1,'eSt14z66RAyRuxI1200jXN:APA91bH1iYln8gracPmhR6s93ys4FPw_dKXJzJ9f9wXWjVgKEWkFBXD8dxqdSskm_vLNY9vnUOaM6OsoX2LBHpdNkJ1O7IrF3-B2hcXxsgk256SD6FGjuu0','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1741272299','tosin','tosin@gmail.com','6282124100984','+62','82124100984','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2025-03-06 14:44:59','2025-03-06',0,1,'c-Y_jodKSjiaKP_3A6iKmm:APA91bGpPTIKc2Pga9hX8wJyesuBKiDhKD9R8v_-OR6sgCzSDjTT4VeMCgYw32a_LQBmU2Pca6-7pY7OQoeUk1dpBp1Dp1t6kSs8s57Gj9OWC5XfK6BcpPw','6b4b71a45fdb0dbcb6968278093c9c05.jpeg',0,'0','0','0','false',NULL,NULL,'0','0',0,0,'',0),
('P1753033513','test','test@gmail.com','621472583690','+62','1472583690','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2025-07-20 17:45:13','',0,1,'fCmEaaCQT82FHLAOmWuJR0:APA91bFrb8vEcgXWknSZbhyhS-Hkk6mjLZyfl6ODSslxf7LlMiL5VOKlWQbyEwL-UMhBB7k8dHbBvlujncln2BtAZ4fpFDAkZ5GMG7I68bEEEEGv6k_oY1M','',0,'0','0','0','false',NULL,NULL,'0','0',0,0,'',0),
('P1754050466','endru','endru.phoenix@gmail.com','6285324667722','+62','85324667722','8cb2237d0679ca88db6464eac60da96345513964','2025-08-01 12:14:26','',0,1,'ckMgL8SITeahcuuaOvjRLC:APA91bEAsEm7orD_PgH6975hULuw5RwCcuOvWeLz9e7L_CxxxE7276idULHsNUlsbLsP90Y8Hk0TNrq55VZoRlRENsCi6pyJwFj6rGRKSH3b_gyP4vuzViE','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1759559503','Leon','leonmuslih@gmail.com','6282169168169','+62','82169168169','8b001adb6b74a16600a509ee3ffe72dbb92a32fd','2025-10-04 06:31:43','',0,1,'d0sHGOsSTWyTVgzGptAwpj:APA91bHRFB1pf57UKP1AGB3O1hyHCENIspKkXQG5M_Vu9s3nY8CAi7_nkYfSTNoX599VuAHTVbsB7S5PFTPi0M4G5ILn741akUg_QVagfwqIpUNPioW0Uv8','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1759904007','admin','ogot@gmail.com','62  82335382016','+62','  82335382016','b1b3773a05c0ed0176787a4f1574ff0075f7521e','2025-10-08 06:13:27','',0,1,'eGetY450RiK8Pzx7lpXMJi:APA91bEz7zloQakmABGeewL1DuYHqpX4fvNpzt2EoiATDJda-v9WgqKZ7BV_VuShFL5bWugI8todb4eAYpVAMZmdcn38y4bUjLgMhiffDCXRPQbQ4ksHgEg','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1760420146','dhehhe','eldatatolis@gmail.com','628233538201664','+62','8233538201664','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2025-10-14 05:35:46','',0,1,'edGXXeIQTC6pX5wGoYr1rV:APA91bEtzl35167CdrQKcvPlQfBbwqNNM3P3IexFrLrfUJxksXB6-VKTQx9gkMNQ6aYHftK2uDrgo8x6dfODi4m-d4llo7v1Ui3PLtp5ZZfBepI9YVsYctk','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1761976856','agungbossjek@gmail.com','agungbossjek@gmail.com','6287719263999','+62','87719263999','d08230d9eaad2421d49c04da6ee3eaf191fdee3e','2025-11-01 06:00:56','',0,1,'dcR5HxJxTCe_XPoxA8WRT0:APA91bFysCheIp7tckOkYretuBnZqrPkgJpSRgB3HiBWY35AHPud3xjU0oT4P17Va8r9jBB7isnFlG4n7C5cfbzuEpBkwEb-pTppIwWSBK8SnT5Q4bpQKmw','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1762075498','Naufal Rasyid Aswan ','naufalrasyid42@gmail.com','62895388110010','+62','895388110010','701e0c52e412d45c19d56ccd5dc60dc0011273eb','2025-11-02 09:24:58','',0,1,'dGA01fGFRs-tLbk5bRvMGx:APA91bFE4iowM_KXZ3Pmdu4rkxN8o2Nnt9y5No3t6PX89ECb747mMbwsGkw8dl8SC713fO1BDJufUMcmvFCPNFN7o1yCtAa_x_hKLmNyesyo9Mok-AnW2dE','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1765585531','widaynila@gmail.com','widaynila@gmail.com','628128740821','+62','8128740821','7c4a8d09ca3762af61e59520943dc26494f8941b','2025-12-13 00:25:31','',0,0,'fwzY0qnmTlWpeA_Fk4RxCH:APA91bHsC0rRy-no0xaPAK2GVmlEQy8X6vXC3RUy00TxVQ_2lQNcGEM9qnbUzh_kB4hG0lad9Kah99uj3rkSQrN0llsZGU6xebT0nmI9R4o8Zbl57_0PRag','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1775197589','yus','yusufw77@gmail.com','628385925875','+62','8385925875','d9a4c340cad469f82a48d65e3208237ea9f36771','2026-04-03 06:26:29','',0,1,'fYdlrhY1RKCwu-O9rZRzRV:APA91bHxo0t_be0jbCJK17FdV7CRxJdT0gn8iP36qsnQ6qF4oq6IeVP1seBqnVXtF9dSpuVMLL3V1xdIiWbYc4YiV0PkqYG7DGoARQmXD1YY5qqQyTzOMU4','',0,'D1775207413','304',NULL,'false','yus','1775211076-64317.jpg','1','5',0,0,'',0),
('P1776169472','uqi','uqialhafidz@gmail.com','6282147838210','+62','82147838210','40bd001563085fc35165329ea1ff5c5ecbdbbeef','2026-04-14 12:24:32','',0,1,'d6cXgKhSQtyd6cOhoEtSvZ:APA91bGFE5VXLSUa_KrX-EGALxqLtfpUHUGHRTHGKoz2Z3A2dDlIeTb177FvNi8a8Xk73AkpU19m3vmtMImngi6hRbf_7WLl7FfiJQiQj1mTHyB7L5R-vjs','',0,'0','0','0','false','','','0','0',0,0,'',0),
('P1780559025','agus','ssss@gmail.com','6281327808876','+62','81327808876','c2fd439e0862fb284a51e228454eebe287c47aa7','2026-06-04 07:43:45','',0,1,'fG-cZoaQQceeKT-PtGphOt:APA91bH2T6dz65TIjWj0mscUrU1s4PMiQxX7Sw7KaZBa5ndHaLNfISCARZLG77HaIANWSp14tDxCf_q4kaUf2RtlwVeRpn3qlHM_Go8ylJTEJoYJ8lt2-hM','',0,'0','0','0','false','','','0','0',0,0,'',0);
/*!40000 ALTER TABLE `pelanggan` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `poin`
--

DROP TABLE IF EXISTS `poin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `poin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `kode` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `poin` varchar(255) DEFAULT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `nilai` varchar(255) DEFAULT NULL,
  `isdriver` varchar(255) DEFAULT NULL,
  `image_poin` varchar(255) DEFAULT NULL,
  `expire` date DEFAULT NULL,
  `status` varchar(255) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poin`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `poin` DISABLE KEYS */;
INSERT INTO `poin` VALUES
(3,'10','Target','20','Tukar Poin Anda','30000','1',NULL,'2026-01-30','1'),
(4,'2','ChassBack','1','Uang kecil','1000','1',NULL,'2025-12-06','1');
/*!40000 ALTER TABLE `poin` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `point`
--

DROP TABLE IF EXISTS `point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `point` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(200) NOT NULL,
  `id_user` varchar(200) NOT NULL DEFAULT '0',
  `point` varchar(200) DEFAULT '0',
  `tipe` varchar(200) NOT NULL,
  `reward` varchar(200) NOT NULL,
  `status` varchar(200) NOT NULL DEFAULT '0',
  `update_at` datetime NOT NULL DEFAULT current_timestamp(),
  `kode` varchar(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `point` DISABLE KEYS */;
/*!40000 ALTER TABLE `point` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `ppob`
--

DROP TABLE IF EXISTS `ppob`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ppob` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(250) NOT NULL,
  `reff` varchar(250) NOT NULL,
  `code` varchar(250) NOT NULL,
  `tipe` varchar(250) DEFAULT NULL,
  `operator` varchar(250) NOT NULL,
  `price` varchar(250) NOT NULL,
  `hp` varchar(250) NOT NULL,
  `sukses` int(1) NOT NULL DEFAULT 0,
  `onstatus` int(1) NOT NULL DEFAULT 0,
  `tanggal` date NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ppob`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `ppob` DISABLE KEYS */;
/*!40000 ALTER TABLE `ppob` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `ppob_fitur`
--

DROP TABLE IF EXISTS `ppob_fitur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ppob_fitur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(250) NOT NULL,
  `tipe` varchar(255) DEFAULT NULL,
  `kode` varchar(255) DEFAULT NULL,
  `ikon` varchar(255) DEFAULT 'noimage.png',
  `status` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ppob_fitur`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `ppob_fitur` DISABLE KEYS */;
INSERT INTO `ppob_fitur` VALUES
(1,'Axis Paket Internet','data','axis_paket_internet','4fea8d8d69e5435d2c73fa4cd200aa88.png',1),
(2,'Indosat Paket Internet','data','indosat_paket_internet','cd8c6a737c7703945ddc7b79f702953f.png',1),
(3,'Telkomsel','pulsa','telkomsel','619e846dd62eac14b97d8221a22ee549.png',1),
(4,'XL Paket Internet','data','xl_paket_internet','6bc87ca40cf5883ddd9e5e895dbe27e8.png',1),
(5,'Tri Paket Internet','data','tri_paket_internet','64eef8106917ebbf90b96ae52192fad3.png',1),
(6,'Telkomsel Paket Internet','data','telkomsel_paket_internet','6c437628dbc14f06d1b7828563fc3427.png',1),
(7,'Smartfren Paket Internet','data','smartfren_paket_internet','noimage.png',0),
(8,'Telkomsel','pulsa','telkomsel','619e846dd62eac14b97d8221a22ee549.png',1),
(9,'PLN','pln','pln','b0da8a03e57b95eccb91f0f106a8bc54.jpg',1),
(11,'Tri','pulsa','three','33b4d70e71fb31974b610aa90938ad76.png',1),
(12,'Axis','pulsa','axis','f9d7c397f9c7418af8e4073e844f0287.png',1),
(13,'OVO','data','ovo','ecb16c5ab15efdc9330b047359dda7ca.png',1),
(14,'Shoope Pay','etoll','shopee_pay','b27aeab85511bd5bcbff70f4487d44a5.png',1);
/*!40000 ALTER TABLE `ppob_fitur` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `ppob_histori`
--

DROP TABLE IF EXISTS `ppob_histori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ppob_histori` (
  `trx` varchar(255) NOT NULL,
  `iduser` varchar(255) DEFAULT NULL,
  `reff` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `biaya` varchar(255) DEFAULT NULL,
  `notujuan` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tanggal` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`trx`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ppob_histori`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `ppob_histori` DISABLE KEYS */;
/*!40000 ALTER TABLE `ppob_histori` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `promosi`
--

DROP TABLE IF EXISTS `promosi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `promosi` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tanggal_dibuat` timestamp NOT NULL DEFAULT current_timestamp(),
  `tanggal_berakhir` date NOT NULL,
  `fitur_promosi` int(11) NOT NULL,
  `link_promosi` varchar(500) DEFAULT NULL,
  `type_promosi` varchar(250) NOT NULL,
  `foto` varchar(50) NOT NULL,
  `is_show` varchar(3) NOT NULL,
  `action` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promosi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `promosi` DISABLE KEYS */;
INSERT INTO `promosi` VALUES
(9,'2021-10-08 10:01:40','2026-01-30',1,'service','service','5e862dfbae62a1fd4433d340c71898f9.jpg','1',0),
(23,'2024-01-19 22:56:28','2026-01-30',1,'service','service','fb922a2be61b8a2cac0d5b0f4e2820da.png','1',0),
(24,'2024-01-19 23:30:14','2026-12-31',0,'https://google.com','link','45b20b767959781e3e5af649c63a6438.png','1',0);
/*!40000 ALTER TABLE `promosi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `rating_driver`
--

DROP TABLE IF EXISTS `rating_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating_driver` (
  `nomor` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_pelanggan` varchar(200) NOT NULL,
  `id_driver` varchar(200) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `catatan` varchar(200) DEFAULT 'Good job',
  `rating` int(11) NOT NULL,
  `update_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`nomor`) USING BTREE,
  UNIQUE KEY `nomor` (`nomor`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating_driver`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `rating_driver` DISABLE KEYS */;
INSERT INTO `rating_driver` VALUES
(1,'P1735004105','D1735003893',4,'Good job',5,'2024-12-24 03:29:54'),
(2,'P1735004105','D1735003893',5,'Good job',5,'2024-12-24 06:45:39'),
(3,'P1735023118','D1735025353',7,'Good job',5,'2024-12-24 07:35:58'),
(4,'P1735023118','D1735025353',12,'Good job',5,'2025-01-10 06:45:40'),
(5,'P1735004105','D1735003893',34,'jos\n',1,'2025-03-06 14:21:09'),
(6,'P1735004105','D1735003893',32,'Good job',1,'2025-03-06 14:29:23'),
(7,'P1741272299','D1735003893',70,'Good job',5,'2025-03-06 14:51:56'),
(8,'P1741272299','D1735003893',86,'Good job',5,'2025-03-06 15:08:10'),
(9,'P1741272299','D1735003893',89,'wapik ',5,'2025-03-06 15:11:50'),
(10,'P1741272299','D1735003893',103,'Good job',5,'2025-03-06 15:24:27'),
(11,'P1741272299','D1735003893',104,'Good job',5,'2025-03-06 15:25:32'),
(12,'P1741272299','D1735003893',106,'Good job',5,'2025-03-07 03:16:58'),
(13,'P1741272299','D1735003893',107,'Good job',5,'2025-03-07 04:29:47'),
(14,'P1735004105','D1735003893',112,'Good job',5,'2025-03-25 15:25:27'),
(15,'P1735004105','D1735003893',115,'Good job',5,'2025-04-16 11:24:25'),
(16,'P1735004105','D1735003893',133,'Good job',2,'2025-07-19 16:11:02'),
(17,'P1735023118','D1735025353',210,'Good job',5,'2025-07-20 16:47:36'),
(18,'P1735023118','D1735025353',212,'Good job',5,'2025-07-20 16:52:25'),
(19,'P1735004105','D1735003893',213,'Good job',5,'2025-07-20 17:17:55'),
(20,'P1735004105','D1735003893',214,'Good job',5,'2025-07-20 17:23:32'),
(21,'P1735004105','D1735003893',215,'Good job',5,'2025-07-20 17:31:27'),
(22,'P1735004105','D1735003893',216,'Good job',5,'2025-07-20 17:36:40'),
(23,'P1735004105','D1735003893',218,'Good job',1,'2025-07-20 17:40:25'),
(24,'P1735004105','D1735003893',221,'keren',5,'2025-07-24 06:05:12'),
(25,'P1735004105','D1735025353',231,'Good job',5,'2025-07-24 15:46:35'),
(26,'P1735004105','D1735025353',232,'Good job',5,'2025-07-24 15:51:24'),
(27,'P1735004105','D1735003893',233,'Good job',5,'2025-07-24 15:57:03'),
(28,'P1753033513','D1735003893',234,'Good job',5,'2025-07-24 16:03:08'),
(29,'P1753033513','D1735025353',235,'Good job',5,'2025-07-24 16:06:50'),
(30,'P1753033513','D1735025353',243,'Good job',1,'2025-07-27 09:20:45'),
(31,'P1753033513','D1735025353',248,'Good job',4,'2025-07-28 04:08:39'),
(32,'P1753033513','D1735025353',251,'Good job',5,'2025-07-28 04:16:46'),
(33,'P1735004105','D1735003893',257,'Good job',1,'2025-09-12 13:27:08'),
(34,'P1735004105','D1735003893',261,'Good job',3,'2025-09-12 13:35:14'),
(35,'P1735004105','D1735003893',267,'Good job',1,'2025-09-12 13:50:56'),
(36,'P1735004105','D1735003893',269,'Good job',3,'2025-09-13 07:10:54'),
(37,'P1735004105','D1735003893',271,'Good job',1,'2025-09-14 13:50:33'),
(38,'P1735004105','D1735003893',279,'oke',1,'2026-01-09 03:12:17'),
(39,'P1735004105','D1735003893',281,'oke',5,'2026-01-09 03:39:43'),
(40,'P1735004105','D1735003893',282,'ok',5,'2026-01-09 03:42:20'),
(41,'P1735004105','D1735003893',288,'Good job',5,'2026-01-09 04:04:21'),
(42,'P1735004105','D1735003893',289,'ok',5,'2026-01-09 04:15:28'),
(43,'P1735004105','D1775207413',307,'Good job',5,'2026-04-14 12:28:18'),
(44,'P1735004105','D1775207413',311,'Good job',5,'2026-04-14 22:40:23'),
(45,'P1735004105','D1735003893',316,'Good job',5,'2026-06-03 08:48:08'),
(46,'P1735004105','D1735003893',314,'Good job',5,'2026-06-03 10:16:15'),
(47,'P1735004105','D1735003893',317,'Good job',1,'2026-06-03 14:50:26');
/*!40000 ALTER TABLE `rating_driver` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `redeem`
--

DROP TABLE IF EXISTS `redeem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `redeem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `iduser` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `poin` varchar(255) DEFAULT NULL,
  `nominal` varchar(255) DEFAULT NULL,
  `tanggal` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redeem`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `redeem` DISABLE KEYS */;
INSERT INTO `redeem` VALUES
(1,'D1735003893','Target','20','30000','23-07-2025'),
(2,'D1735003893','ChassBack','1','1000','24-07-2025'),
(3,'D1735003893','Target','20','30000','13-09-2025'),
(4,'D1735003893','ChassBack','1','1000','13-09-2025'),
(5,'D1735003893','Target','20','30000','09-01-2026');
/*!40000 ALTER TABLE `redeem` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `saldo`
--

DROP TABLE IF EXISTS `saldo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `saldo` (
  `nomor` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` varchar(200) NOT NULL,
  `saldo` int(11) DEFAULT 0,
  `update_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`nomor`) USING BTREE,
  UNIQUE KEY `nomor` (`nomor`) USING BTREE,
  UNIQUE KEY `id_user` (`id_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `saldo` DISABLE KEYS */;
INSERT INTO `saldo` VALUES
(1,'D1735003893',680780,'2026-01-09 10:45:58'),
(2,'P1735004105',116800,'2026-06-04 14:08:06'),
(3,'M1735004384',9900,'2025-09-15 13:31:36'),
(4,'P1735023118',16000,'2025-01-09 09:40:11'),
(5,'D1735025353',53190,'2025-01-09 09:33:33'),
(6,'P1735092903',1000000,'2024-12-25 02:15:03'),
(7,'D1735092793',0,'2024-12-25 02:28:19'),
(8,'P1736502310',0,'2025-01-10 09:45:10'),
(9,'P1736502326',0,'2025-01-10 09:45:26'),
(10,'P1736502329',0,'2025-01-10 09:45:29'),
(11,'P1736502330',0,'2025-01-10 09:45:30'),
(12,'P1736502337',0,'2025-01-10 09:45:37'),
(13,'P1737201561',0,'2025-01-18 11:59:21'),
(14,'P1737201569',0,'2025-01-18 11:59:29'),
(15,'P1737201583',0,'2025-01-18 11:59:43'),
(16,'P1737201584',0,'2025-01-18 11:59:44'),
(17,'P1737201597',0,'2025-01-18 11:59:57'),
(18,'P1737201599',0,'2025-01-18 11:59:59'),
(20,'P1741272299',20000,'2025-03-06 14:44:59'),
(21,'P1741630877',0,'2025-03-10 18:21:17'),
(22,'P1741630879',0,'2025-03-10 18:21:19'),
(23,'P1741630882',0,'2025-03-10 18:21:22'),
(24,'P1741630883',0,'2025-03-10 18:21:23'),
(25,'P1741630884',0,'2025-03-10 18:21:24'),
(26,'P1742031893',0,'2025-03-15 09:44:53'),
(27,'P1742031914',0,'2025-03-15 09:45:14'),
(28,'P1742031922',0,'2025-03-15 09:45:22'),
(29,'P1742031939',0,'2025-03-15 09:45:39'),
(30,'P1742031944',0,'2025-03-15 09:45:44'),
(31,'P1742031947',0,'2025-03-15 09:45:47'),
(32,'P1742031948',0,'2025-03-15 09:45:48'),
(33,'P1742031949',0,'2025-03-15 09:45:49'),
(34,'P1742031974',0,'2025-03-15 09:46:14'),
(35,'P1742031978',0,'2025-03-15 09:46:18'),
(36,'P1742031979',0,'2025-03-15 09:46:19'),
(37,'D1752155139',50000,'2025-07-10 13:45:39'),
(38,'P1752155264',0,'2025-07-10 13:47:44'),
(39,'P1752155282',0,'2025-07-10 13:48:02'),
(40,'P1752155306',0,'2025-07-10 13:48:26'),
(41,'P1752155310',0,'2025-07-10 13:48:30'),
(42,'P1752155311',0,'2025-07-10 13:48:31'),
(43,'P1752155312',0,'2025-07-10 13:48:32'),
(44,'P1752155313',0,'2025-07-10 13:48:33'),
(45,'P1752155314',0,'2025-07-10 13:48:34'),
(46,'P1752155315',0,'2025-07-10 13:48:35'),
(47,'P1752155316',0,'2025-07-10 13:48:36'),
(48,'P1752155317',0,'2025-07-10 13:48:37'),
(49,'P1752155320',0,'2025-07-10 13:48:40'),
(50,'P1752155322',0,'2025-07-10 13:48:42'),
(51,'P1752155323',0,'2025-07-10 13:48:43'),
(52,'P1752155324',0,'2025-07-10 13:48:44'),
(53,'P1752155328',0,'2025-07-10 13:48:48'),
(54,'P1752155867',0,'2025-07-10 13:57:47'),
(55,'P1752155869',0,'2025-07-10 13:57:49'),
(56,'P1752155877',0,'2025-07-10 13:57:57'),
(57,'P1752155886',0,'2025-07-10 13:58:06'),
(58,'P1752155894',0,'2025-07-10 13:58:14'),
(59,'P1752155936',0,'2025-07-10 13:58:56'),
(60,'P1752155941',0,'2025-07-10 13:59:01'),
(61,'P1752155942',0,'2025-07-10 13:59:02'),
(62,'P1752155943',0,'2025-07-10 13:59:03'),
(63,'P1752499890',0,'2025-07-14 13:31:30'),
(64,'P1752499893',0,'2025-07-14 13:31:33'),
(65,'P1752499979',0,'2025-07-14 13:32:59'),
(66,'P1752499985',0,'2025-07-14 13:33:05'),
(67,'P1752499991',0,'2025-07-14 13:33:11'),
(68,'P1752499994',0,'2025-07-14 13:33:14'),
(69,'P1752946763',0,'2025-07-19 17:39:23'),
(70,'P1752946771',0,'2025-07-19 17:39:31'),
(71,'P1752946775',0,'2025-07-19 17:39:35'),
(73,'D1752949043',0,'2025-07-19 18:17:23'),
(74,'P1752978008',0,'2025-07-20 02:20:08'),
(75,'P1752978031',0,'2025-07-20 02:20:31'),
(76,'P1752981378',0,'2025-07-20 03:16:18'),
(77,'D1752995881',0,'2025-07-20 07:18:01'),
(78,'D1752996190',0,'2025-07-20 07:23:10'),
(79,'D1752996584',50000,'2025-07-20 07:33:57'),
(80,'P1753005353',0,'2025-07-20 09:55:53'),
(81,'P1753005413',0,'2025-07-20 09:56:53'),
(82,'P1753005509',0,'2025-07-20 09:58:29'),
(83,'P1753028290',0,'2025-07-20 16:18:10'),
(84,'P1753033513',24000,'2025-07-20 17:45:13'),
(87,'M1754040626',50000,'2025-08-01 09:30:26'),
(88,'P1754050466',0,'2025-08-01 12:14:26'),
(89,'D1754050053',50000,'2025-08-01 12:21:36'),
(90,'M1755594528',0,'2025-08-19 09:08:48'),
(92,'M1757916707',20000,'2025-09-15 06:11:47'),
(93,'M1757918481',0,'2025-09-15 06:41:21'),
(94,'P1759559503',0,'2025-10-04 06:31:43'),
(95,'M1759568052',0,'2025-10-04 08:54:12'),
(96,'P1759904007',0,'2025-10-08 06:13:27'),
(97,'P1760420146',0,'2025-10-14 05:35:46'),
(98,'P1761976856',0,'2025-11-01 06:00:56'),
(99,'P1762075498',0,'2025-11-02 09:24:58'),
(100,'P1765585531',0,'2025-12-13 00:25:31'),
(101,'P1775197589',500000,'2026-04-03 06:26:29'),
(102,'D1775205550',0,'2026-04-03 08:39:10'),
(103,'D1775207413',700000,'2026-04-03 10:11:16'),
(104,'P1776169472',0,'2026-04-14 12:24:32'),
(105,'P1780559025',0,'2026-06-04 07:43:45');
/*!40000 ALTER TABLE `saldo` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `status_transaksi`
--

DROP TABLE IF EXISTS `status_transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_transaksi` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `status_transaksi` varchar(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_transaksi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `status_transaksi` DISABLE KEYS */;
INSERT INTO `status_transaksi` VALUES
(1,'near'),
(2,'accept'),
(3,'start'),
(4,'finish'),
(5,'cancel'),
(6,'proses'),
(9,'Pending');
/*!40000 ALTER TABLE `status_transaksi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_pelanggan` varchar(200) NOT NULL,
  `id_driver` varchar(200) DEFAULT NULL,
  `order_fitur` tinyint(4) NOT NULL,
  `start_latitude` varchar(20) NOT NULL,
  `start_longitude` varchar(20) NOT NULL,
  `end_latitude` varchar(20) NOT NULL,
  `end_longitude` varchar(20) NOT NULL,
  `jarak` double NOT NULL,
  `harga` int(11) NOT NULL,
  `waktu_order` datetime NOT NULL,
  `waktu_selesai` timestamp NULL DEFAULT NULL,
  `estimasi_time` varchar(500) NOT NULL,
  `alamat_asal` varchar(500) NOT NULL,
  `alamat_tujuan` varchar(500) NOT NULL,
  `kredit_promo` int(11) NOT NULL DEFAULT 0,
  `biaya_akhir` int(11) DEFAULT 0,
  `pakai_wallet` tinyint(1) NOT NULL DEFAULT 0,
  `rate` varchar(11) NOT NULL,
  PRIMARY KEY (`id_pelanggan`,`waktu_order`) USING BTREE,
  UNIQUE KEY `nomor` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `transaksi` DISABLE KEYS */;
INSERT INTO `transaksi` VALUES
(1,'P1735004105',NULL,4,'-7.0916207644474','113.47730241716','-7.1045158070418','113.4761825949',1.7239999771118,9000,'2024-12-24 10:16:36',NULL,'OgotSend','WF5H+836, Tengracak, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia','VFWG+4FV, Dusun Pokpoh, Larangan Badung, Kec. Palengaan, Kabupaten Pamekasan, Jawa Timur 69362, Indonesia',0,9000,0,''),
(2,'P1735004105',NULL,4,'-7.0916207644474','113.47730241716','-7.1045158070418','113.4761825949',1.7239999771118,9000,'2024-12-24 10:20:28',NULL,'OgotSend','WF5H+836, Tengracak, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia','VFWG+4FV, Dusun Pokpoh, Larangan Badung, Kec. Palengaan, Kabupaten Pamekasan, Jawa Timur 69362, Indonesia',0,9000,0,''),
(3,'P1735004105',NULL,4,'-7.0916207644474','113.47730241716','-7.0975719323944','113.47471643239',0.73000001907349,4000,'2024-12-24 10:20:54',NULL,'OgotSend','WF5H+836, Tengracak, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia','WF2G+R5C, Bunut, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia',0,4000,0,''),
(4,'P1735004105','D1735003893',4,'-7.0916207644474','113.47730241716','-7.0963395841476','113.47474962473',0.65299999713898,3500,'2024-12-24 10:26:02','2024-12-24 10:29:41','OgotSend','WF5H+836, Tengracak, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia','WF3G+87M, Bunut, Plakpak, Kec. Pegantenan, Kabupaten Pamekasan, Jawa Timur 69361, Indonesia',0,3500,0,'5.0'),
(5,'P1735004105','D1735003893',10,'-7.0866051148128','113.47659498453','0','0',0,16500,'2024-12-24 10:30:03','2024-12-24 13:45:25','6 hr','WF7G+9J Patoan Dajah, Pamekasan Regency, East Java, Indonesia','',0,16500,0,'5.0'),
(14,'P1735004105',NULL,1,'0.6279539140487','123.00595924258','0.53772841001575','123.06200433522',13.241000175476,17500,'2025-01-10 18:37:05',NULL,'1','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0,17500,0,''),
(15,'P1735004105',NULL,1,'0.62794620316093','123.00596192479','0.62699843438819','122.97988817096',3.1410000324249,7200,'2025-01-10 18:51:38',NULL,'1','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','Jl. Baso Bobihoe No.308, Kayubulan, Kec. Limboto, Kabupaten Gorontalo, Gorontalo 96214, Indonesia',0,7200,0,''),
(16,'P1735004105',NULL,1,'0.61932106519493','123.00289481878','0.63182075521136','122.57566183805',56.569000244141,74000,'2025-01-10 18:53:36',NULL,'1','J293+M7M, Jl. Kyai Hi. Saleh Kadir, Hutuo, Kec. Telaga Biru, Kabupaten Gorontalo, Gorontalo 96214, Indonesia','JHJG+P7F, Molombulahe, Paguyaman, Boalemo Regency, Gorontalo, Indonesia',0,74000,0,''),
(17,'P1735004105',NULL,1,'0.62793916278511','123.00593040884','0.53772841001575','123.06200433522',13.239000320435,17500,'2025-01-10 18:55:19',NULL,'1','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0,17500,0,''),
(18,'P1735004105',NULL,1,'0.62793715124916','123.00593644381','0.6218445405734','123.00936900079',1.0640000104904,7200,'2025-01-10 18:57:33',NULL,'1','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','J2C5+PPQ, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia',0,7200,0,''),
(19,'P1735004105',NULL,2,'0.62793681599318','123.00593744963','0.53772841001575','123.06200433522',13.239000320435,130000,'2025-01-10 18:58:23',NULL,'2','J2G4+G5Q, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo, Indonesia','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0,130000,0,''),
(20,'P1735004105',NULL,1,'-7.2192200413545','109.26557410508','-7.2022099565936','109.28784582764',4.298999786377,7200,'2025-01-18 19:10:56',NULL,'1','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','Q7XQ+86J, Kandanggotong Lor, Gombong, Kec. Belik, Kabupaten Pemalang, Jawa Tengah 52356, Indonesia',0,7200,0,''),
(21,'P1735004105',NULL,1,'-7.2193155027849','109.26554393023','-7.2042127219361','109.28415175527',3.4140000343323,7200,'2025-01-18 19:14:14',NULL,'1','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','Q7WM+8M Gombong, Pemalang Regency, Central Java, Indonesia',0,7200,0,''),
(22,'P1735004105',NULL,1,'-7.2192203739727','109.26557209343','-7.2115098844086','109.27301287651',1.557000041008,7200,'2025-01-18 19:37:08',NULL,'1','Q7J8+86R, Jawar, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia','Q7QF+959, Jl. Nur Supeno, Soyi, Clekatakan, Kec. Pulosari, Kabupaten Pemalang, Jawa Tengah 52355, Indonesia',0,7200,0,''),
(23,'P1735004105',NULL,1,'-7.5870378070347','111.39523230493','-7.5683246119061','111.41600701958',3.8880000114441,7200,'2025-02-26 09:09:20',NULL,'1','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CCJ8+W75, Sambirejo, Pelem, Kec. Karangrejo, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0,7200,0,''),
(24,'P1735004105',NULL,1,'-7.5876449934861','111.39115735888','-7.5719146996736','111.39649093151',3.8619999885559,7200,'2025-02-26 14:22:02',NULL,'1','C96R+WFH, Babadan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','C9HW+HFQ, Jenangsari, Jungke, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0,7200,0,''),
(26,'P1735004105',NULL,1,'-7.5872039774678','111.39439914376','-7.5684532334153','111.39772709459',3.9670000076294,7200,'2025-02-27 08:11:33',NULL,'1','C96V+XMM, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','C9JX+J3 Jungke, Magetan Regency, East Java, Indonesia',0,7200,0,''),
(27,'P1735004105',NULL,1,'-7.5870238487154','111.39522626996','-7.5614222003439','111.40376105905',4.3039999008179,7200,'2025-02-27 08:13:01',NULL,'1','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CCQ3+PGG, Padasbolong, Patihan, Kec. Karangrejo, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0,7200,0,''),
(28,'P1735004105',NULL,4,'-7.5870122167823','111.39522492886','-7.5785301274557','111.40130180866',1.4950000047684,7500,'2025-02-27 08:14:11',NULL,'OgotSend','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CCC2+HG Temenggungan, Magetan Regency, East Java, Indonesia',0,7500,0,''),
(29,'P1735004105',NULL,1,'-8.2623765180689','114.3315410614','-8.2870580559913','114.33117192239',5.7160000801086,7500,'2025-03-05 10:28:52',NULL,'1','P8QJ+3G, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P87J+5FF, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia',0,7500,0,''),
(30,'P1735004105',NULL,1,'-8.2803135','114.3532887','-8.2621422699641','114.32522915304',5.9450001716614,8000,'2025-03-05 10:30:20',NULL,'1','P993+V5H, Jalan Raya, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia\n','P8QG+43X, Dusun Babakan, Kedayunan, Kabat, Banyuwangi Regency, East Java 68461, Indonesia',0,8000,0,''),
(31,'P1735004105',NULL,1,'-8.2828952493059','114.35237042606','-8.2932539108727','114.33538533747',2.8929998874664,7200,'2025-03-05 10:32:11',NULL,'1','P982+RXW, Jalan Raya, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P84M+JP9, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(32,'P1735004105','D1735003893',1,'-8.2856128401389','114.35036111623','-8.2891080902148','114.34518311173',1.0759999752045,7200,'2025-03-05 10:38:55','2025-03-06 14:05:31','1','P972+Q4F, Dusun Krajan, Sukojati, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P86V+GQX, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,'1.0'),
(33,'P1735004105',NULL,1,'-8.2889365632424','114.31843243539','-8.3220514921232','114.3154508248',6.6459999084473,9000,'2025-03-06 14:03:46',NULL,'1','P869+F7F, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jalan No.Km 5, Krajan, Watukebo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',0,9000,0,''),
(34,'P1735004105','D1735003893',1,'-8.2874890318672','114.33046080172','-8.3055872696452','114.33238763362',4.9920001029968,7200,'2025-03-06 14:04:56','2025-03-06 14:07:56','1','P87J+5FF, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia','M8VJ+HJ7, Krajan, Karangbendo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',500,6700,1,'1.0'),
(35,'P1735004105',NULL,2,'-8.2883825007508','114.32474333793','-8.2875374710046','114.31565567851',1.0119999647141,50000,'2025-03-06 20:32:43',NULL,'2','P86F+JW6, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P878+287, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(36,'P1735004105',NULL,2,'-8.2867631076409','114.31541394442','-8.2769195358404','114.31736256927',1.2350000143051,50000,'2025-03-06 21:17:06',NULL,'2','P878+75J, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(37,'P1735004105',NULL,2,'-8.2849028377525','114.33131139725','-8.2768362580868','114.31746549904',3.5610001087189,50000,'2025-03-06 21:18:01',NULL,'2','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(38,'P1735004105',NULL,1,'-8.2858905369854','114.33069180697','-8.2746464781685','114.31921999902',3.7000000476837,7200,'2025-03-06 21:20:24',NULL,'1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8F9+XHX, Jl. Raya Jember, Krajan, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(39,'P1735004105',NULL,1,'-8.2844818123678','114.33065995574','-8.2772881475183','114.31716408581',3.6110000610352,7200,'2025-03-06 21:22:20',NULL,'1','P87J+Q33, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',500,6700,1,''),
(40,'P1735004105',NULL,2,'-8.2783730781774','114.33048058301','-8.2611004282665','114.3316674605',6.5710000991821,64500,'2025-03-06 21:24:02',NULL,'2','P8CJ+M5 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P8QJ+CMH, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,64500,0,''),
(41,'P1735004105',NULL,2,'-8.2816955353077','114.33267127723','-8.2813985922574','114.31436285377',3.5230000019073,50000,'2025-03-06 21:24:05',NULL,'2','P89M+74V, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P897+CP Labanasem, Banyuwangi Regency, East Java, Indonesia',0,50000,0,''),
(42,'P1735004105',NULL,2,'-8.2815571830987','114.33265116066','-8.2695508795468','114.32229716331',4.9730000495911,50000,'2025-03-06 21:24:56',NULL,'2','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8JC+3VH, RT.02/RW.05, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(43,'P1735004105',NULL,2,'-8.2619697358603','114.33116555214','-8.2482706191515','114.32231292129',2.5699999332428,50000,'2025-03-06 21:24:59',NULL,'2','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q82C+9R, Dusun Kertosari, Pendarungan, Kabat, Banyuwangi Regency, East Java, Indonesia',0,50000,0,''),
(44,'P1735004105',NULL,4,'-8.2818136489056','114.33252409101','-8.2815352856224','114.31426629424',3.5199999809265,18000,'2025-03-06 21:26:10',NULL,'OgotSend','P89J+8XC, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P897+9P Labanasem, Banyuwangi Regency, East Java, Indonesia',0,18000,0,''),
(45,'P1735004105',NULL,4,'-8.2818136489056','114.33252409101','-8.2815352856224','114.31426629424',3.5199999809265,18000,'2025-03-06 21:26:47',NULL,'OgotSend','P89J+8XC, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P897+9P Labanasem, Banyuwangi Regency, East Java, Indonesia',0,18000,0,''),
(46,'P1735004105',NULL,2,'-8.2621943619768','114.33104082942','-8.2621943619768','114.33104082942',0,50000,'2025-03-06 21:27:19',NULL,'2','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(47,'P1735004105',NULL,2,'-8.286405453439','114.33143042028','-8.2772443520895','114.31749098003',3.5750000476837,50000,'2025-03-06 21:28:15',NULL,'2','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(48,'P1735004105',NULL,2,'-8.2622102881958','114.33100931346','-8.2031866357171','114.34311512858',9.8599996566772,97000,'2025-03-06 21:28:39',NULL,'2','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q8WV+GVW, Krajan Satu, Boyolangu, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur 68424, Indonesia',0,97000,0,''),
(49,'P1735004105',NULL,2,'-8.2793607937959','114.33280136436','-8.2796282098253','114.31593596935',3.9730000495911,50000,'2025-03-06 21:29:37',NULL,'2','P8CM+564, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8C8+483, RT.02/RW.04, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(50,'P1735004105',NULL,2,'-8.261954141428','114.33107770979','-8.236256003084','114.35853246599',4.4850001335144,50000,'2025-03-06 21:29:39',NULL,'2','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q975+G94, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0,50000,0,''),
(51,'P1735004105',NULL,1,'-8.2619212935791','114.33124635369','-8.2359703076702','114.35840070248',4.2969999313354,7200,'2025-03-06 21:30:34',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Adi Sucipto No.143, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0,7200,0,''),
(52,'P1735004105',NULL,1,'-8.2848172392474','114.33127518743','-8.2650212557894','114.32165108621',5.3410000801086,7200,'2025-03-06 21:30:40',NULL,'1','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8MC+PHX, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',500,6700,1,''),
(53,'P1735004105',NULL,1,'-8.2829569601594','114.32773701847','-8.2731876134885','114.31172188371',5.7940001487732,8000,'2025-03-06 21:32:06',NULL,'1','P88H+R3 Pakistaji, Banyuwangi Regency, East Java, Indonesia','Jl. Karang Rejo No.1, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,8000,0,''),
(54,'P1735004105',NULL,2,'-8.2847090798694','114.32908013463','-8.2792450019352','114.31611701846',3.0880000591278,50000,'2025-03-06 21:32:08',NULL,'2','P87H+VM7, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8C8+6CQ, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(55,'P1735004105',NULL,2,'-8.2854851061592','114.33267462999','-8.2103570924912','114.38546989113',14.937999725342,146500,'2025-03-06 21:32:15',NULL,'2','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Ikan Cucut No.28, Kampungmandar, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68419, Indonesia',0,146500,0,''),
(56,'P1735004105',NULL,1,'-8.2892331688303','114.3308949843','-8.2648945105147','114.32159945369',4.8070001602173,7200,'2025-03-06 21:32:59',NULL,'1','P85J+WJ3, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8MC+PHX, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(57,'P1735004105',NULL,4,'-8.2858427611978','114.33085307479','-8.2828902726238','114.32320810854',0.86799997091293,4500,'2025-03-06 21:33:17',NULL,'OgotSend','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P88F+R7 Pakistaji, Banyuwangi Regency, East Java, Indonesia',0,4500,0,''),
(58,'P1735004105',NULL,4,'-8.26777314446','114.32653773576','-8.2593561667577','114.33663792908',1.6430000066757,8500,'2025-03-06 21:33:55',NULL,'OgotSend','Jl. Pak Guru Harjo No.20-24, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.188, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,8500,0,''),
(59,'P1735004105',NULL,1,'-8.2622325185422','114.33112666011','-8.2357858166778','114.3582303822',4.2760000228882,7200,'2025-03-06 21:34:04',NULL,'1','P8QJ+4F8, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Adi Sucipto No.118, Sobo, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68418, Indonesia',0,7200,0,''),
(60,'P1735004105',NULL,4,'-8.26777314446','114.32653773576','-8.2593561667577','114.33663792908',1.6430000066757,8500,'2025-03-06 21:34:31',NULL,'OgotSend','Jl. Pak Guru Harjo No.20-24, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.188, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,8500,0,''),
(61,'P1735004105',NULL,4,'-8.26777314446','114.32653773576','-8.2593561667577','114.33663792908',1.6430000066757,8500,'2025-03-06 21:35:26',NULL,'OgotSend','Jl. Pak Guru Harjo No.20-24, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.188, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,8500,0,''),
(62,'P1735004105',NULL,4,'-8.26777314446','114.32653773576','-8.2593561667577','114.33663792908',1.6430000066757,8500,'2025-03-06 21:36:01',NULL,'OgotSend','Jl. Pak Guru Harjo No.20-24, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.188, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,8500,0,''),
(63,'P1735004105',NULL,2,'-8.2618718559025','114.3311759457','-8.205435879378','114.36332322657',9.5590000152588,94000,'2025-03-06 21:36:17',NULL,'2','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Villa Bukit Mas Perum Griya Permata Husada No.i-7, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia',0,94000,0,''),
(64,'P1735004105',NULL,1,'-8.2867196450418','114.3396922946','-8.2569718546494','114.33733128011',7.1719999313354,9500,'2025-03-06 21:37:07',NULL,'1','P87Q+5VC, Dusun Krajan, Pakistaji, Kabat, Banyuwangi Regency, East Java 68461, Indonesia','Jl. Raya Jember No.98, Dusun Krajan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,9500,0,''),
(65,'P1735004105',NULL,2,'-8.2895709140707','114.33093957603','-8.2882232491118','114.31798048317',1.4500000476837,50000,'2025-03-06 21:38:33',NULL,'2','P85J+WJ3, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P869+P4X, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(66,'P1735004105',NULL,1,'-8.2846198317713','114.3276045844','-8.2963151413893','114.30717520416',3.4609999656677,7200,'2025-03-06 21:39:05',NULL,'1','P88H+52 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P834+FWC, Jl. Raya Jember, Kawang, Labanasem, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',500,6700,1,''),
(67,'P1735004105',NULL,1,'-8.2812715204703','114.33278761804','-8.2769012877286','114.31746382266',4.076000213623,7200,'2025-03-06 21:39:17',NULL,'1','P89M+F4P, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Kabat No.280, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(68,'P1735004105',NULL,1,'-8.2858812472494','114.33087553829','-8.2863417524709','114.31546691805',2.4449999332428,7200,'2025-03-06 21:40:01',NULL,'1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P878+C5F, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(71,'P1735004105',NULL,1,'-8.2857412376299','114.33073841035','-8.2831988267982','114.31704908609',2.6889998912811,7200,'2025-03-06 21:49:21',NULL,'1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P888+J97, Jl. Macan Putih, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(73,'P1735004105',NULL,1,'-8.2619538096315','114.33126915246','-8.2312037097541','114.38985329121',9.7309999465942,13000,'2025-03-06 21:52:45',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q99Q+GW Kertosari, Banyuwangi Regency, East Java, Indonesia',0,13000,0,''),
(74,'P1735004105',NULL,1,'-8.262225882618','114.33114409447','-8.2114714086118','114.3851922825',8.9040002822876,12000,'2025-03-06 21:53:32',NULL,'1','P8QJ+4F8, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0,12000,0,''),
(76,'P1735004105',NULL,1,'-8.2621794311458','114.33104284108','-8.2072802629528','114.36290379614',9.2360000610352,12500,'2025-03-06 21:54:42',NULL,'1','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9V7+45F, Jl. Letkol Istiqlah, Lingkungan Mojoroto R, Mojopanggung, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0,12500,0,''),
(77,'P1735004105',NULL,1,'-8.2616462342123','114.33133117855','-8.2114999467442','114.38510075212',8.9820003509521,12000,'2025-03-06 21:55:39',NULL,'1','P8QJ+FF9, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0,12000,0,''),
(79,'P1735004105',NULL,1,'-8.2621094221309','114.33122724295','-8.2192041719164','114.3527841568',6.9320001602173,9500,'2025-03-06 21:57:21',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9J3+75W, Lingkungan Cuking Rw., Mojopanggung, Kec. Giri, Kabupaten Banyuwangi, Jawa Timur 68425, Indonesia',0,9500,0,''),
(94,'P1735004105',NULL,1,'-8.2617636902259','114.33125775307','-8.1982716389514','114.38237462193',11.071000099182,14500,'2025-03-06 22:16:12',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','R92J+WV, Lateng, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68413, Indonesia',0,14500,0,''),
(95,'P1735004105',NULL,1,'-8.2617636902259','114.33125775307','-8.1982716389514','114.38237462193',11.071000099182,14500,'2025-03-06 22:16:34',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','R92J+WV, Lateng, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68413, Indonesia',0,14500,0,''),
(96,'P1735004105',NULL,1,'-8.2620603162742','114.33097779751','-8.2110211031588','114.38540484756',8.9280004501343,12000,'2025-03-06 22:16:59',NULL,'1','P8QJ+4FM, Jl. Raya Jember, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0,12000,0,''),
(97,'P1735004105',NULL,1,'-8.2619816805314','114.33139655739','-8.2115059198414','114.38532203436',8.8649997711182,12000,'2025-03-06 22:18:23',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0,12000,0,''),
(99,'P1735004105',NULL,1,'-8.2620477080127','114.33133620769','-8.2114432023162','114.38502732664',8.8739995956421,12000,'2025-03-06 22:19:27',NULL,'1','P8QJ+4FQ, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Q9QP+46C, Banyuwangi Sub-District, Banyuwangi Regency, East Java, Indonesia',0,12000,0,''),
(105,'P1735004105',NULL,2,'-8.3938048175152','114.25348710269','-8.2023523747994','114.36665989459',29.736999511719,291500,'2025-03-07 04:05:08',NULL,'2','J743+J4H, Sukomukti, Kebaman, Kec. Srono, Kabupaten Banyuwangi, Jawa Timur 68471, Indonesia','Jl. Moh. Husni Thamrin No.110, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia',0,291500,0,''),
(108,'P1735004105',NULL,2,'-2.1398706178882','106.11226893961','-2.1474609829043','106.11140862107',1.4960000514984,50000,'2025-03-11 01:26:23',NULL,'2','V467+32R, Parit Lalang, Rangkui, Pangkal Pinang City, Bangka Belitung Islands 33684, Indonesia','V426+WHQ, Jl. Basuki Rahmat, Bukitintan, Kec. Girimaya, Kota Pangkal Pinang, Kepulauan Bangka Belitung 33684, Indonesia',0,50000,0,''),
(109,'P1735004105',NULL,1,'1.0093073043145','103.43289267272','1.0093073043145','103.43289267272',0,7200,'2025-03-15 17:20:08',NULL,'1','2C5M+M5, Lubuk Semut, Karimun, Karimun Regency, Riau Islands, Indonesia','2C5M+M5, Lubuk Semut, Karimun, Karimun Regency, Riau Islands, Indonesia',0,7200,0,''),
(110,'P1735004105',NULL,1,'1.005064370019','103.43430116773','1.0084025343219','103.4264074266',1.5829999446869,7200,'2025-03-17 13:51:04',NULL,'1','2C3M+WPX, Jalan, Lubuk Semut, Kec. Karimun, Karimun, Kepulauan Riau 29663, Indonesia','Jl. Kapling Gg. Nusa Indah No.12, Kapling, Kec. Tebing, Kabupaten Karimun, Kepulauan Riau 29663, Indonesia',0,7200,0,''),
(111,'P1735004105',NULL,1,'0.63066848109889','123.00618488342','0.53772841001575','123.06200433522',13.522999763489,18000,'2025-03-25 22:21:27',NULL,'1','J2J4+68W, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo 96137, Indonesia','Jl. Sultan Botutihe No.68, Heledulaa Sel., Kec. Kota Tim., Kota Gorontalo, Gorontalo 96134, Indonesia',0,18000,0,''),
(112,'P1735004105','D1735003893',1,'0.63067015737796','123.00619058311','0.59826221950354','123.0324658379',5.1170001029968,7200,'2025-03-25 22:22:28','2025-03-25 22:24:50','1','J2J4+68W, Pentadio Barat, Telaga Biru, Gorontalo Regency, Gorontalo 96137, Indonesia','Jl. Ahmad A. Wahab No.17, Pantungo, Kec. Telaga Biru, Kabupaten Gorontalo, Gorontalo 96211, Indonesia',0,7200,0,'5.0'),
(113,'P1735004105',NULL,1,'-7.0290554974004','110.45044563711','-6.9969533932897','110.45421481133',4.558000087738,7200,'2025-04-16 18:12:43',NULL,'1','Kp. Tlumpak rt0701, Sambiroto, Kec. Tembalang, Kota Semarang, Jawa Tengah 50274, Indonesia','Jl. Sendangsari Utara XV No.27, RW.5, Kalicari, Kec. Pedurungan, Kota Semarang, Jawa Tengah 50198, Indonesia',500,6700,1,''),
(114,'P1735004105',NULL,1,'-7.0290554974004','110.45044563711','-6.9969533932897','110.45421481133',4.558000087738,7200,'2025-04-16 18:13:13',NULL,'1','Kp. Tlumpak rt0701, Sambiroto, Kec. Tembalang, Kota Semarang, Jawa Tengah 50274, Indonesia','Jl. Sendangsari Utara XV No.27, RW.5, Kalicari, Kec. Pedurungan, Kota Semarang, Jawa Tengah 50198, Indonesia',500,6700,1,''),
(115,'P1735004105','D1735003893',1,'-7.0282459006999','110.45049358159','-6.951531153576','110.44828478247',10.045000076294,13500,'2025-04-16 18:16:14','2025-04-16 18:23:49','1','XFC2+Q55, Sambiroto, Tembalang, Semarang City, Central Java 50274, Indonesia','2CXX+98 Tambakrejo, Semarang City, Central Java, Indonesia',0,13500,0,'5.0'),
(116,'P1735004105',NULL,2,'-14.325601439349','-170.72356235236','-14.29153922356','-170.68444099277',9.0500001907349,89000,'2025-04-30 18:45:58',NULL,'2','M7FG+9GX, Tafuna, Western District 96799, American Samoa','P858+96 Faga\'alu, Eastern District, American Samoa',0,89000,0,''),
(117,'P1735004105',NULL,2,'-14.325268142175','-170.72338968515','-14.344587190694','-170.75491502881',6.5199999809265,64000,'2025-04-30 18:47:36',NULL,'2','M7GG+2VH, Tafuna, Western District 96799, American Samoa','M64V+JXH, \'Ili\'ili, Western District 96799, American Samoa',0,64000,0,''),
(120,'P1735004105',NULL,1,'-7.5871205599184','111.39522258192','-7.5939913201021','111.41282659024',2.5610001087189,7200,'2025-05-08 06:55:33',NULL,'1','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CC46+RXQ, Jl. Cemp., Tanjungsepreh II, Tanjungsepreh, Kec. Maospati, Kabupaten Magetan, Jawa Timur 63392, Indonesia',0,7200,0,''),
(121,'P1735004105',NULL,1,'-7.5871205599184','111.39522258192','-7.5939913201021','111.41282659024',2.5610001087189,7200,'2025-05-08 06:55:47',NULL,'1','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CC46+RXQ, Jl. Cemp., Tanjungsepreh II, Tanjungsepreh, Kec. Maospati, Kabupaten Magetan, Jawa Timur 63392, Indonesia',0,7200,0,''),
(123,'P1735004105',NULL,2,'-7.5858782679657','111.39757957309','-7.604750548968','111.44185546786',6.3309998512268,62500,'2025-05-08 07:00:22',NULL,'2','C97W+PV2, Gg. Madinah 5, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','9CWR+JVJ, Bakung, Maospati, Kec. Maospati, Kabupaten Magetan, Jawa Timur 63392, Indonesia',0,62500,0,''),
(124,'P1735004105',NULL,2,'-7.5871295331217','111.39519575983','-7.6093718665337','111.38908199966',4.9539999961853,50000,'2025-05-08 07:01:29',NULL,'2','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','99RQ+7J Kembangan, Magetan Regency, East Java, Indonesia',0,50000,0,''),
(125,'P1735004105',NULL,1,'-7.587014210828','111.39519374818','-7.5700086532808','111.42420485616',4.6090002059937,7200,'2025-05-08 07:05:42',NULL,'1','C97W+742, Jl. Madinah Gang Madinah 1, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','Jl. Raya Maospati - Ngawi No.25, Glodog, Karangrejo, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0,7200,0,''),
(126,'P1735004105',NULL,2,'-14.323679288899','-170.72258468717','-14.352355530001','-170.73704112321',5.1729998588562,51000,'2025-05-21 05:26:06',NULL,'2','M7GG+JW7, Tafuna, Western District 96799, American Samoa','J7X7+45X, Vaitogi, Western District, American Samoa',0,51000,0,''),
(127,'P1735004105',NULL,1,'-6.9313214','109.1276912','-7.0888466095605','109.04829204082',27.402000427246,36000,'2025-07-11 09:36:05',NULL,'1','Jl. Raya Singkil No.1, Pesalakan, Adiwerna, Kec. Adiwerna, Kabupaten Tegal, Jawa Tengah 52194, Indonesia\n','W26X+F87, Jembayat, Kec. Margasari, Kabupaten Tegal, Jawa Tengah 52463, Indonesia',500,35500,1,''),
(128,'P1735004105',NULL,1,'-1.2545931875395','116.84974003583','-1.2377840723656','116.85800291598',5.1680002212524,7200,'2025-07-15 05:31:24',NULL,'1','PRWX+5X2, Gunungsari Ulu, Balikpapan Tengah, Balikpapan City, East Kalimantan 76122, Indonesia','QV75+492, Jl. Tiga Dalam Gg. Sudimapan, Gn. Samarinda, Kec. Balikpapan Utara, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0,7200,0,''),
(129,'P1735004105',NULL,2,'-0.60095165731446','117.15276192874','-0.56982021201255','117.13560417295',13.199999809265,129500,'2025-07-15 16:42:28',NULL,'2','95X3+J4 Handil Bakti, Samarinda City, East Kalimantan, Indonesia','C4JP+36 Simpang Pasir, Samarinda City, East Kalimantan, Indonesia',0,129500,0,''),
(130,'P1735004105',NULL,1,'-7.5870879905123','111.39521386474','-7.5846592364201','111.40422306955',1.5789999961853,7200,'2025-07-16 05:54:47',NULL,'1','C97W+35J, Jambangan, Temboro, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia','CC83+6H9, Doro, Temenggungan, Kec. Karas, Kabupaten Magetan, Jawa Timur 63395, Indonesia',0,7200,0,''),
(131,'P1735004105',NULL,1,'-8.1950423955989','111.09217151999','-8.1863723233324','111.10204875469',2.2509999275208,7200,'2025-07-19 23:01:56',NULL,'1','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,7200,0,''),
(132,'P1735004105',NULL,1,'-8.2003314252231','111.10206216574','-8.1891758642574','111.11369524151',2.0469999313354,7200,'2025-07-19 23:03:13',NULL,'1','R434+FFF, Jl. Gatot Subroto, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Tentara Pelajar No.172, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(133,'P1735004105','D1735003893',1,'-8.2009138181498','111.10309816897','-8.1961607368766','111.11722636968',2.3550000190735,7200,'2025-07-19 23:04:49','2025-07-19 23:07:57','1','Terminal Bus Pacitan, Kios No. 29, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R438+GVC, Pager, Arjowinangun, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0,7200,0,'2.0'),
(134,'P1735004105',NULL,1,'-8.2108654705732','111.0995586589','-8.1956729148068','111.10011689365',3.1440000534058,7200,'2025-07-19 23:11:34',NULL,'1','Q3QX+9JH, Temon, Kembang, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R432+M43, Jl. Jend. A. Yani, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(135,'P1735004105',NULL,1,'-8.2122286639406','111.10071569681','-8.2041483233371','111.11624937505',3.8410000801086,7200,'2025-07-19 23:33:20',NULL,'1','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','Jl. Ki Ageng Buwono Keling No.59, Ngemplak, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(136,'P1735004105',NULL,1,'-8.2122286639406','111.10071569681','-8.195321815287','111.09209608287',2.8120000362396,7200,'2025-07-19 23:39:54',NULL,'1','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(137,'P1735004105',NULL,1,'-8.1947234852401','111.1192048341','-8.1824882184008','111.11624401063',3.9289999008179,7200,'2025-07-19 23:53:33',NULL,'1','R449+6PP, Gg. Masjid, Menadi, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','Jl. Tentara Pelajar No.48, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(138,'P1735004105',NULL,1,'-8.1924144471503','111.10775213689','-8.1865505319544','111.10170107335',1.4500000476837,7200,'2025-07-19 23:55:00',NULL,'1','R445+V73, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,7200,0,''),
(139,'P1735004105',NULL,1,'-8.1989054715328','111.10317528248','-8.19229697058','111.10273271799',2.0880000591278,7200,'2025-07-19 23:57:36',NULL,'1','R423+C7P, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Imam Bonjol No.8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,7200,0,''),
(140,'P1735004105',NULL,1,'-8.1940534739866','111.1034136638','-8.187316131217','111.1137361452',2.1310000419617,7200,'2025-07-19 23:59:27',NULL,'1','R443+F92, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','R467+PMX, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(141,'P1735004105',NULL,1,'-8.1941845560195','111.11208524555','-8.18055809886','111.11750934273',1.8009999990463,7200,'2025-07-20 00:03:54',NULL,'1','Jl. Basuki Rahmat No.1d, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(142,'P1735004105',NULL,1,'-8.1914620231098','111.10170207918','-8.1863965490901','111.11503902823',2.305999994278,7200,'2025-07-20 00:05:03',NULL,'1','Jl. Veteran No.38, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Tentara Pelajar No.46a, tegalrejo, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(143,'P1735004105',NULL,1,'-8.192684244905','111.10372010618','-8.1890889176172','111.11427795142',1.7359999418259,7200,'2025-07-20 00:06:56',NULL,'1','R453+2C8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Tentara Pelajar No.127, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(144,'P1735004105',NULL,1,'-8.1948177314081','111.10718015581','-8.1828439753151','111.11630536616',2.1610000133514,7200,'2025-07-20 00:08:14',NULL,'1','Jl. Basuki Rahmat No.36b, Purwoharjo, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R488+VF5, RT.2/RW.01, Dsn. Tegalrejo, Nanggungan, Pacitan, Pacitan Regency, East Java 63518, Indonesia',0,7200,0,''),
(145,'P1735004105',NULL,1,'-8.1956317651626','111.10365439206','-8.1854271857692','111.10294260085',1.6119999885559,7200,'2025-07-20 00:09:43',NULL,'1','R433+PFJ, Jl. Jend. A. Yani, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R473+V3R, Jl. Sultan Hasanudin, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0,7200,0,''),
(146,'P1735004105',NULL,1,'-8.1914799433044','111.10908921808','-8.1950981468113','111.10088970512',1.9950000047684,7200,'2025-07-20 00:12:49',NULL,'1','R455+8JG, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','R432+W96, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(147,'P1735004105',NULL,1,'-8.1930688640201','111.10089339316','-8.2002843027951','111.0868781805',2.2539999485016,7200,'2025-07-20 00:14:27',NULL,'1','Jl. Hos Cokroaminoto No.17, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Dewi Sartika No.49, Barean, Sidoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0,7200,0,''),
(148,'P1735004105',NULL,1,'-8.1939323475128','111.10630206764','-8.1936502720198','111.09844721854',1.5809999704361,7200,'2025-07-20 00:16:35',NULL,'1','R444+CFR, Jl. Dr. Sutomo No.37, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','R34X+HCX, Krajan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(149,'P1735004105',NULL,1,'-8.1970418017521','111.10428940505','-8.1931518275423','111.11054364592',1.1299999952316,7200,'2025-07-20 00:20:09',NULL,'1','R433+6M4, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Dr. Sutomo No.10, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(150,'P1735004105',NULL,1,'-8.1920623491904','111.10827215016','-8.1889657984872','111.113743186',1.3500000238419,7200,'2025-07-20 00:22:16',NULL,'1','R445+V73, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0,7200,0,''),
(151,'P1735004105',NULL,1,'-8.1986214084694','111.10920522362','-8.1943279165731','111.10512759537',1.0490000247955,7200,'2025-07-20 00:24:12',NULL,'1','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. R.A. Kartini No.5, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(152,'P1735004105',NULL,1,'-8.1954714806211','111.10992606729','-8.1891569483869','111.10091216862',1.6460000276566,7200,'2025-07-20 00:30:07',NULL,'1','Jl. Jend. Sudirman No.132, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Moh. Yamin. Sh No.33, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0,7200,0,''),
(153,'P1735004105',NULL,1,'-8.193394744636','111.10164072365','-8.2034288829904','111.09731063247',2.2869999408722,7200,'2025-07-20 00:31:48',NULL,'1','R442+JPC, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Drainase Blumbang - SPBU, JL. Jend Gatot Subroto, 9-A, Pacitan, 63511, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(154,'P1735004105',NULL,1,'-8.1917361356282','111.11162625253','-8.1975631390325','111.10923908651',1.097000002861,7200,'2025-07-20 00:34:26',NULL,'1','R456+PWG, Jl. Sunan Gn. Jati, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+WMF, Tanjung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(155,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 00:58:15',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,112111,0,''),
(156,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 00:59:30',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,112111,0,''),
(157,'P1735004105',NULL,4,'-8.1947191711544','111.10783696175','-8.1886067284623','111.10488384962',1.1399999856949,6000,'2025-07-20 01:00:46',NULL,'OgotSend','Jl. Kanjeng Jimat No.21, RT.01/RW.01, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R463+FH8, Jl. Brigjend Katamso, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,6000,0,''),
(158,'P1735004105',NULL,1,'-8.1971519762057','111.10479868948','-8.1925485164141','111.10847130418',1.1879999637604,7200,'2025-07-20 01:34:02',NULL,'1','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n    ','R445+QCX, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(159,'P1735004105',NULL,1,'-8.1940601110526','111.11003503203','-8.1937139879134','111.11003033817',0.0060000000521541,7200,'2025-07-20 02:32:44',NULL,'1','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','Jl. Dr. Sutomo No.12, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(160,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1891443378061','111.1140647158',1.8309999704361,5500,'2025-07-20 02:37:36',NULL,'1.8309999704360962','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Ponorogo - Pacitan No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,119611,0,''),
(161,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.194908659028','111.11958134919',1.7879999876022,5000,'2025-07-20 02:39:31',NULL,'1.7879999876022339','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Manggribi No.27, Menadi, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63517, Indonesia\n',0,119111,0,''),
(162,'P1735004105',NULL,1,'-8.191612021751','111.10252685845','-8.1948678411574','111.09205015004',2.0729999542236,7200,'2025-07-20 02:42:10',NULL,'1','Jl. Veteran Gg. 8 No.2, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(163,'P1735004105',NULL,1,'-8.1832621220284','111.11556071788','-8.1949909584616','111.11910626292',2.9670000076294,7200,'2025-07-20 02:43:36',NULL,'1','JL. Tentara Pelajar, No. 164 Rt. 004 Rw. 001, Tanjungsari, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R449+2J8, Menadi, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia',0,7200,0,''),
(164,'P1735004105',NULL,1,'-8.1872676798118','111.11048128456','-8.1886694495898','111.11794319004',1.4769999980927,7200,'2025-07-20 02:45:24',NULL,'1','Jl. Sunan Gn. Jati No.4, RT.4/RW.RW, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R469+QC2, Jl. Sunan Kalijogo, Tawang, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(165,'P1735004105',NULL,1,'-8.1829992870026','111.11600663513','-8.1728902947292','111.11996792257',1.3830000162125,7200,'2025-07-20 02:47:18',NULL,'1','JL. Tentara Pelajar, Rt. 03 Rw. 01, Nanggungan, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(166,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 02:48:16',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,115111,0,''),
(167,'P1735004105',NULL,1,'-8.188327635918','111.11162692308','-8.1721183621986','111.11971277744',2.9869999885559,7200,'2025-07-20 02:51:25',NULL,'1','R466+MHV, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4G9+VJ6, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(168,'P1735004105',NULL,1,'-8.1869105994112','111.1017228663','-8.1950739215831','111.0920689255',1.9079999923706,7200,'2025-07-20 02:52:22',NULL,'1','Jl. Brigjend Katamso No.90 C, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(169,'P1735004105',NULL,1,'-8.1890540725842','111.11422263086','-8.2033319840988','111.10510546714',2.4809999465942,7200,'2025-07-20 09:13:24',NULL,'1','Jl. Tentara Pelajar No.127, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Q4W4+F4P, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(170,'P1735004105',NULL,1,'-8.1915931059963','111.10284838825','-8.212629524196','111.09554372728',3.6559998989105,7200,'2025-07-20 09:22:44',NULL,'1','Jl. Veteran No.21, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Q3PW+W62, Plelen, Sidoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0,7200,0,''),
(171,'P1735004105',NULL,1,'-8.1890401345702','111.11363824457','-8.1752163712974','111.11159607768',2.3659999370575,7200,'2025-07-20 09:23:50',NULL,'1','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(172,'P1735004105',NULL,1,'-8.1940571243729','111.11001525074','-8.1909788412618','111.11266024411',0.91200000047684,7200,'2025-07-20 09:26:23',NULL,'1','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia','R456+PWG, Jl. Sunan Gn. Jati, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(173,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 09:28:46',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,115111,0,''),
(174,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 09:29:26',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,115111,0,''),
(175,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1986225','111.1091996',0.050999999046326,1000,'2025-07-20 09:31:02',NULL,'0.050999999046325684','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,115111,0,''),
(176,'P1735004105',NULL,1,'-8.1887391397199','111.11441273242','-8.1722381678182','111.12029649317',2.183000087738,7200,'2025-07-20 09:32:33',NULL,'1','Jl. Ponorogo - Pacitan No.123, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(177,'P1735004105',NULL,1,'-8.1866690057987','111.1117586866','-8.1767436294879','111.1242929846',2.0590000152588,7200,'2025-07-20 09:35:59',NULL,'1','R476+CM3, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4FF+8P Purworejo, Pacitan Regency, East Java, Indonesia',0,7200,0,''),
(178,'P1735004105',NULL,1,'-8.1880123706208','111.10213190317','-8.2036137208791','111.09739277512',2.6700000762939,7200,'2025-07-20 09:41:52',NULL,'1','Jl. Brigjend Katamso No.24, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Jl. Gatot Subroto No.81, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(179,'P1735004105',NULL,1,'-8.1892571693046','111.1116842553','-8.1928657692446','111.11523114145',2.8389999866486,7200,'2025-07-20 09:44:30',NULL,'1','R466+8G8, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R448+MCH, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(180,'P1735004105',NULL,4,'-8.1825041478217','111.11570589244','-8.1942373206737','111.12430673093',3.6240000724792,18500,'2025-07-20 09:45:29',NULL,'OgotSend','JL. Tentara Pelajar, Rt. 03 Rw. 01, Nanggungan, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Manggribi No.27, Kedawung, Mentoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63517, Indonesia',0,18500,0,''),
(181,'P1735004105',NULL,4,'-8.1825041478217','111.11570589244','-8.1942373206737','111.12430673093',3.6240000724792,18500,'2025-07-20 09:46:14',NULL,'OgotSend','JL. Tentara Pelajar, Rt. 03 Rw. 01, Nanggungan, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Manggribi No.27, Kedawung, Mentoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63517, Indonesia',0,18500,0,''),
(182,'P1735004105',NULL,1,'-8.1895611504767','111.11372441053','-8.1822871094076','111.1161025241',0.93999999761581,7200,'2025-07-20 09:47:31',NULL,'1','Jl. Tentara Pelajar No.137, Gemulung, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Tentara Pelajar No.12, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(183,'P1735004105',NULL,1,'-8.1910929996634','111.10274445266','-8.2126540801919','111.11777320504',4.4569997787476,7200,'2025-07-20 09:49:06',NULL,'1','Jl. Veteran No.60, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Q4P9+V3W, Krajan, Kembang, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(184,'P1735004105',NULL,1,'-8.1871900248077','111.10199511051','-8.2225991335897','111.13399587572',7.4050002098083,10000,'2025-07-20 09:50:26',NULL,'1','Jl. Brigjend Katamso No.90c, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Q4GM+HFR, Area Pegunungan, Sukoharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,10000,0,''),
(185,'P1735004105',NULL,1,'-8.1893723237715','111.11370831728','-8.1899630291591','111.0908569023',3.7030000686646,7200,'2025-07-20 10:20:11',NULL,'1','R467+597, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R36R+38F, Jl. Dr. GS Sam J Ratulangi, RT.02/RW.01, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(186,'P1735004105',NULL,1,'-8.1888247590059','111.1118190363','-8.1809384154512','111.11835356802',1.3899999856949,7200,'2025-07-20 10:21:29',NULL,'1','R466+GHV, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R499+R97, Krajan, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(187,'P1735004105',NULL,1,'-8.2015851452341','111.10680799931','-8.2114657673528','111.11535452306',3.8020000457764,7200,'2025-07-20 10:24:07',NULL,'1','Jl. Raden Saleh No.21b, Purwoharjo, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','Q4Q8+66H, krajan, Krajan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(188,'P1735004105',NULL,1,'-8.1962719071286','111.09948690981','-8.1867051784781','111.10172387213',1.5939999818802,7200,'2025-07-20 10:24:58',NULL,'1','Jl. DI. Panjaitan No.3a, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,7200,0,''),
(189,'P1735004105',NULL,1,'-8.1935931931899','111.111869663','-8.2010442342267','111.09737433493',2.0699999332428,7200,'2025-07-20 10:25:49',NULL,'1','Desa Arjowinangun, R446+GJ5, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','JL. MT. Haryono, No. 45, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0,7200,0,''),
(190,'P1735004105',NULL,1,'-8.1922096927667','111.11050542444','-8.1859847110075','111.11786741763',1.8660000562668,7200,'2025-07-20 10:27:56',NULL,'1','R445+XX5, RT.04/RW.03, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R479+895, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(191,'P1735004105',NULL,1,'-8.1905792865983','111.10894504935','-8.1847296144018','111.11637577415',2.2279999256134,7200,'2025-07-20 10:32:21',NULL,'1','R455+XG8, Jl. Tj. Rejo, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Nasional III No.69, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(192,'P1735004105',NULL,1,'-8.1985520519196','111.10334660858','-8.20877155928','111.09336979687',1.9529999494553,7200,'2025-07-20 10:38:12',NULL,'1','Jl. Gatot Subroto No.42, Kuwarasan, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Q3RV+883, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(193,'P1735004105',NULL,1,'-8.190705060078','111.10777895898','-8.1859375868807','111.09967768192',1.8120000362396,7200,'2025-07-20 12:14:27',NULL,'1','R454+JJQ, Gantung, Pacitan, Pacitan Regency, East Java, Indonesia','Jln. Rahman Hakim Rt.04 Rw.03 lingk. Pucangmulyo, Blimbing, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia',0,7200,0,''),
(194,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1940946','111.1101823',1.0599999427795,3000,'2025-07-20 12:42:24',NULL,'1.059999942779541','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R446+943, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,117111,0,''),
(195,'P1735004105',NULL,1,'-8.1887291839878','111.10098727047','-8.2089447802527','111.09313040972',3.0309998989105,7200,'2025-07-20 12:43:26',NULL,'1','R462+GC2, Jl. Moh. Yamin. Sh, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia','Q3RV+883, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7200,0,''),
(196,'P1735004105',NULL,4,'-8.1888665730691','111.1137669906','-8.1693047450883','111.12142704427',2.3599998950958,12000,'2025-07-20 12:44:34',NULL,'OgotSend','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','R4JC+4JH, Jl. Ponorogo - Pacitan, Krajan III, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,12000,0,''),
(197,'P1735004105',NULL,4,'-8.1888665730691','111.1137669906','-8.1693047450883','111.12142704427',2.3599998950958,12000,'2025-07-20 12:45:19',NULL,'OgotSend','Jl. Tentara Pelajar No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63516, Indonesia','R4JC+4JH, Jl. Ponorogo - Pacitan, Krajan III, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,12000,0,''),
(198,'P1735004105',NULL,2,'-8.1990289194364','111.10342338681','-8.2077169671299','111.10497571528',1.6269999742508,50000,'2025-07-20 12:47:32',NULL,'2','R423+99Q, Jl. Gatot Subroto, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Q4R4+X25, Krajan Kidul, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0,50000,0,''),
(199,'P1735004105',NULL,2,'-8.1956802155496','111.10045384616','-8.1862515263812','111.10187139362',1.4620000123978,50000,'2025-07-20 12:48:25',NULL,'2','R432+P56, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Brigjend Katamso No.88, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,50000,0,''),
(200,'P1735004105',NULL,1,'-8.187403741962','111.11344881356','-8.1753049803092','111.11959341913',1.6180000305176,7200,'2025-07-20 12:50:42',NULL,'1','R467+PMX, Jl. Tentara Pelajar, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4F9+PXW, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(201,'P1735004105',NULL,2,'-8.1893255319028','111.10075559467','-8.1855280713464','111.08955904841',1.5160000324249,50000,'2025-07-20 12:51:33',NULL,'2','Jl. Moh. Yamin. Sh No.33, Palihan, Pucangsewu, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63513, Indonesia','R37Q+HR2, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,50000,0,''),
(202,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1940946','111.1101823',1.0599999427795,3000,'2025-07-20 12:52:14',NULL,'1.059999942779541','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R446+943, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,117111,0,''),
(203,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1940946','111.1101823',1.0599999427795,3000,'2025-07-20 12:54:20',NULL,'1.059999942779541','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R446+943, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,117111,0,''),
(204,'P1735004105',NULL,5,'-8.1986562526645','111.10904596746','-8.1940946','111.1101823',1.0599999427795,3000,'2025-07-20 12:54:21',NULL,'1.059999942779541','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R446+943, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia\n',0,117111,0,''),
(205,'P1735004105',NULL,1,'-8.1861367028716','111.11363489181','-8.178096651903','111.11913777888',1.2960000038147,7200,'2025-07-20 12:55:03',NULL,'1','R477+F2C, Jl. Sunan Muria, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Tentara Pelajar No.75, Ngetol, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(206,'P1735004105',NULL,2,'-8.1941805737811','111.10340662301','-8.180526239862','111.11754957587',2.8680000305176,50000,'2025-07-20 12:55:58',NULL,'2','R443+77F, Jl. Jaksa Agung Suprapto, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,50000,0,''),
(207,'P1735004105',NULL,1,'-8.1863650224188','111.11507188529','-8.1754360685116','111.11162658781',2.0209999084473,7200,'2025-07-20 14:37:55',NULL,'1','Jl. Tentara Pelajar No.46a, tegalrejo, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(208,'P1735004105',NULL,1,'-8.1880004237204','111.10419016331','-8.1952501351957','111.10033515841',1.4240000247955,7200,'2025-07-20 14:38:41',NULL,'1','R473+7G8, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','R432+R3V, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia',0,7200,0,''),
(209,'P1735004105',NULL,1,'-8.1890643601657','111.11411768943','-8.1810160716739','111.11795995384',1.0099999904633,7200,'2025-07-20 15:59:05',NULL,'1','Jl. Ponorogo - Pacitan No.160, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R499+F34, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(213,'P1735004105','D1735003893',1,'-8.1823810266563','111.1160197109','-8.2082976900259','111.0986232385',4.4270000457764,7200,'2025-07-21 00:10:42','2025-07-21 00:17:30','1','Jl. Tentara Pelajar No.12, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Q3RX+RCC, Jl. KH. Ahmad Dahlan, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0,7200,0,'5.0'),
(214,'P1735004105','D1735003893',4,'-8.2087304109918','111.09320819378','-8.1856282931773','111.08971595764',3.3540000915527,17000,'2025-07-21 00:20:57','2025-07-21 00:23:16','OgotSend','Q3RV+883, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','R37Q+HR2, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',500,16500,1,'5.0'),
(215,'P1735004105','D1735003893',5,'-8.1986562526645','111.10904596746','-8.1940681','111.1100508',1.0759999752045,3000,'2025-07-21 00:26:50','2025-07-21 00:31:03','1.0759999752044678','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia\n',0,117111,0,'5.0'),
(216,'P1735004105','D1735003893',5,'-8.1986562526645','111.10904596746','-8.1940681','111.1100508',1.0759999752045,3000,'2025-07-21 00:32:43','2025-07-21 00:36:03','1.0759999752044678','R425+FQG, Jl. Tj. Perak, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Dr. Sutomo No.14, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63581, Indonesia\n',0,6000,0,'5.0'),
(217,'P1735004105',NULL,2,'-8.1754038772355','111.11173320562','-8.1901216566363','111.08769960701',6.6449999809265,65500,'2025-07-21 00:37:26',NULL,'2','R4F6+VGX, Jl. Sunan Maulana Malik Ibrahim, Ngetol, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Janur Sari III No.45, Jambu, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,65500,0,''),
(218,'P1735004105','D1735003893',2,'-8.1949239242468','111.09177388251','-8.2081855276127','111.09858501703',2.6159999370575,50000,'2025-07-21 00:38:15','2025-07-21 00:40:16','2','R34R+6V5, Jl. Kusuma Admaja, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Q3RX+RCC, Jl. KH. Ahmad Dahlan, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia',0,50000,0,'1.0'),
(220,'P1735004105',NULL,1,'0.42791028379106','101.39717951417','0.50456225634798','101.45009413362',16.870000839233,22000,'2025-07-24 12:52:42',NULL,'1','Blk. D No.12, Kubang Jaya, Kec. Siak Hulu, Kabupaten Kampar, Riau 28293, Indonesia','GF32+R27, Jl. Nangka, Wonorejo, Kec. Marpoyan Damai, Kota Pekanbaru, Riau 28128, Indonesia',0,22000,0,''),
(221,'P1735004105','D1735003893',1,'0.43347705131873','101.39601711184','0.43347705131873','101.39601711184',0,7200,'2025-07-24 12:57:34','2025-07-24 13:03:51','1','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia',0,7200,0,'5.0'),
(222,'P1735004105',NULL,1,'0.43347705131873','101.39601711184','0.43347705131873','101.39601711184',0,7200,'2025-07-24 12:58:01',NULL,'1','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia','C9MW+GH7, Sidomulyo Barat, Tampan, Pekanbaru City, Riau 28294, Indonesia',0,7200,0,''),
(223,'P1735004105','D1735003893',1,'0.42787005177779','101.3971426338','0.50456225634798','101.45009413362',16.871999740601,22000,'2025-07-24 13:32:45',NULL,'1','Blk. D No.12, Kubang Jaya, Kec. Siak Hulu, Kabupaten Kampar, Riau 28293, Indonesia','GF32+R27, Jl. Nangka, Wonorejo, Kec. Marpoyan Damai, Kota Pekanbaru, Riau 28128, Indonesia',0,22000,0,''),
(224,'P1735004105',NULL,1,'-8.1951847602864','111.09209105372','-8.2074043719395','111.11448448151',4.4939999580383,7200,'2025-07-24 21:41:02',NULL,'1','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','Q4R7+XP8, Suruhan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(225,'P1735004105',NULL,1,'-8.1951847602864','111.09209105372','-8.2074043719395','111.11448448151',4.4939999580383,7200,'2025-07-24 21:41:31',NULL,'1','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','Q4R7+XP8, Suruhan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(226,'P1735004105',NULL,1,'-8.19620055906','111.12875651568','-8.1685878967939','111.12181127071',3.9779999256134,7200,'2025-07-24 21:43:36',NULL,'1','R43H+GH8, Jl. Sultan Agung, RT.05/RW.3, Krajan, Mentoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R4JC+QWJ, Krajan III, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(227,'P1735004105','D1735025353',1,'-8.1852881361983','111.0894594714','-8.1681080059663','111.0944872722',5.0390000343323,7200,'2025-07-24 21:44:26',NULL,'1','R38Q+5M9, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R3JW+Q4R, Sawahan, Sambong, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(228,'P1735004105',NULL,1,'-8.1852881361983','111.0894594714','-8.1681080059663','111.0944872722',5.0390000343323,7200,'2025-07-24 21:44:51',NULL,'1','R38Q+5M9, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','R3JW+Q4R, Sawahan, Sambong, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(229,'P1735004105','D1735003893',1,'-8.1804558845659','111.11778996885','-8.1624687477164','111.12422123551',2.1659998893738,7200,'2025-07-24 21:46:39',NULL,'1','Jl. Ponorogo - Pacitan No.24, Krajan, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Ngawen, Krajan II, Semanten, Pacitan, Pacitan Regency, East Java 63518, Indonesia',0,7200,0,''),
(230,'P1735004105',NULL,1,'-8.193338993185','111.10380828381','-8.1722969090646','111.12058516592',4.058000087738,7200,'2025-07-24 21:49:02',NULL,'1','Jl. Diponegoro No.23, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R4GC+R56, Krajan IV, Semanten, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(231,'P1735004105','D1735025353',4,'-8.201908695945','111.1062380299','-8.195246152968','111.09205450863',2.6630001068115,13500,'2025-07-24 22:19:26','2025-07-24 22:25:50','OgotSend','Jl. Bunga Flamboyan No.A-1, Purwoharjo, Baleharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R33R+PVW, Kradenan, Bangunsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,13500,0,'5.0'),
(232,'P1735004105','D1735025353',1,'-8.196268588614','111.09689254314','-8.2089504215475','111.11934363842',5.6500000953674,7500,'2025-07-24 22:47:19','2025-07-24 22:50:24','1','Jl. Jend. A. Yani No.60, Caruban, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia','Q4R9+CW4, Jembatan Jalan raya JLS, RT.02/RW.04, Krajan, Sirnoboyo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia',0,7500,0,'5.0'),
(233,'P1735004105','D1735003893',4,'-8.1931461860233','111.10321953893','-8.192954374331','111.07523672283',3.6159999370575,18500,'2025-07-24 22:52:14','2025-07-24 22:56:22','OgotSend','R443+P5V, Jl. Jaksa Agung Suprapto, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R34F+PX7, Kebon, Sedeng, Kec. Pringkuku, Kabupaten Pacitan, Jawa Timur 63514, Indonesia',0,18500,0,'5.0'),
(255,'P1735004105',NULL,1,'-1.2480719460552','116.85608480126','-1.2521881569083','116.85445200652',1.7380000352859,7000,'2025-08-21 14:22:23',NULL,'1','Jl. Joko Tole 2 Gang Posyandu No.66, Sumber Rejo, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia','PVX3+4R6, Jl. Mayor Pol. Zainal Arifin, Gunungsari Ulu, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0,7000,0,''),
(256,'P1735004105',NULL,1,'-1.2480719460552','116.85608480126','-1.2521881569083','116.85445200652',1.7380000352859,7000,'2025-08-21 14:22:49',NULL,'1','Jl. Joko Tole 2 Gang Posyandu No.66, Sumber Rejo, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia','PVX3+4R6, Jl. Mayor Pol. Zainal Arifin, Gunungsari Ulu, Kec. Balikpapan Tengah, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0,7000,0,''),
(257,'P1735004105','D1735003893',5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 19:30:47','2025-09-12 19:55:56','-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,13000,0,'1.0'),
(258,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 19:30:48',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,13000,0,''),
(259,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 19:31:27',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,13000,0,''),
(260,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4723336429503','124.48212135583',-0.0010000000474975,1000,'2025-09-12 20:30:13',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFHJ+3RR, Jl. Eltari, Kefamenanu Sel., Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia\n',0,13000,0,''),
(261,'P1735004105','D1735003893',5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:31:32','2025-09-12 20:34:07','-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,13000,0,'3.0'),
(262,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:38:10',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,11000,0,''),
(263,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:38:44',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,3000,0,''),
(264,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:40:05',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,3000,0,''),
(265,'P1735004105',NULL,5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:41:02',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,3000,0,''),
(266,'P1735004105','D1735003893',5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:42:18',NULL,'-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,13000,0,''),
(267,'P1735004105','D1735003893',5,'-9.4878373904366','124.49068330228','-9.4626174','124.4838542',-0.0010000000474975,1000,'2025-09-12 20:47:19','2025-09-12 20:50:13','-0.0010000000474974513','GF6R+R77, Jl. Eltari, Maubeli, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFPM+XHF, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,11000,0,'1.0'),
(269,'P1735004105','D1735003893',5,'-9.4404780292141','124.48014993221','-9.4721798','124.481127',-0.0010000000474975,5000,'2025-09-13 14:08:11','2025-09-13 14:10:06','-0.0010000000474974513','HF5J+R5F, Jl. Kartini, Kefamenanu Tengah, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFHJ+4FJ, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,15000,0,'3.0'),
(270,'P1735004105',NULL,5,'-9.4404780292141','124.48014993221','-9.4721798','124.481127',-0.0010000000474975,5000,'2025-09-13 14:15:06',NULL,'-0.0010000000474974513','HF5J+R5F, Jl. Kartini, Kefamenanu Tengah, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFHJ+4FJ, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',0,17000,0,''),
(271,'P1735004105','D1735003893',5,'-9.4404780292141','124.48014993221','-9.4721798','124.481127',-0.0010000000474975,5000,'2025-09-13 14:17:24','2025-09-13 14:20:44','-0.0010000000474974513','HF5J+R5F, Jl. Kartini, Kefamenanu Tengah, Kec. Kota Kefamenanu, Kabupaten Timor Tengah Utara, Nusa Tenggara Tim., Indonesia','GFHJ+4FJ, Kefamenanu Selatan, Kota Kefamenanu, North Central Timor Regency, East Nusa Tenggara, Indonesia\n',500,14500,1,'1.0'),
(272,'P1735004105',NULL,1,'-4.0022191989403','121.8697245419','-4.0037363013134','121.85890451074',1.6599999666214,7000,'2025-10-24 08:55:48',NULL,'1','XVX9+6VH, Jl. Perkantoran, Lalingato, Kec. Tirawuta, Kabupaten Kolaka Timur, Sulawesi Tenggara 93572, Indonesia','XVW5+3R, Lalingato, Tirawuta, Kolaka Regency, South East Sulawesi 93572, Indonesia',0,7000,0,''),
(273,'P1735004105',NULL,1,'-4.001901463299','121.86983451247','-4.004715927873','121.86044879258',1.4529999494553,7000,'2025-10-24 09:14:46',NULL,'1','XVX9+6VH, Jl. Perkantoran, Lalingato, Kec. Tirawuta, Kabupaten Kolaka Timur, Sulawesi Tenggara 93572, Indonesia','XVV6+Q2M, Lalingato, Tirawuta, East Kolaka Regency, South East Sulawesi 93572, Indonesia',0,7000,0,''),
(274,'P1735004105',NULL,1,'-6.2200073','106.8266426','-6.195261438683','106.82040296495',4.9359998703003,12500,'2025-11-04 22:16:45',NULL,'1','Jl. Karet Pedurenan No.27, RT.10/RW.7, Kuningan, Karet Kuningan, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12940, Indonesia\n','Jl. M.H. Thamrin No.1, Kb. Melati, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10310, Indonesia',0,12500,0,''),
(277,'P1735004105',NULL,1,'-6.1078974','106.7776198','-6.1785829387343','106.7922129482',11.493000030518,29000,'2025-12-14 17:53:14',NULL,'1','Apartement GreenBay Pluit, Tower F, Pluit, Kecamatan Penjaringan, Jkt Utara, Daerah Khusus Ibukota Jakarta 14450, Indonesia\n','Mall Taman Anggrek LT . G 9A, CHATIME MALL TAMAN ANGGREK, Letjen S. Parman St, RT.12/RW.1, South Tanjung Duren, Grogol petamburan, West Jakarta City, Jakarta 11470, Indonesia',0,29000,0,''),
(278,'P1735004105',NULL,1,'-6.1948231252281','106.85274034739','-6.2113231256428','106.87015559524',3.6300001144409,9500,'2026-01-08 20:55:51',NULL,'1','Jl. Paseban Tim. Gg. 6 No.C59D, RT.14/RW.4, Paseban, Kec. Senen, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10440, Indonesia','Jl. Pisangan Baru Tengah No.10, RT.2/RW.14, Pisangan Baru, Kec. Matraman, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13110, Indonesia',0,9500,0,''),
(279,'P1735004105','D1735003893',1,'-6.1801252479029','106.81795511395','-6.1725183235146','106.83419689536',3.1689999103546,8000,'2026-01-09 10:04:10','2026-01-09 10:11:53','1','RR99+X67, RT.2/RW.3, Gambir, Central Jakarta City, Jakarta 10110, Indonesia','Kementerian Agama, Ps. Baru, Kecamatan Sawah Besar, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10710, Indonesia',0,8000,0,'1.0'),
(280,'P1735004105','D1735003893',1,'-6.1822918730293','106.81785486639','-6.1659233006662','106.81295815855',3.1670000553131,8000,'2026-01-09 10:12:30',NULL,'1','Jl. Taman Kb. Sirih No.2, RT.1/RW.8, Kp. Bali, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10250, Indonesia','Jl. A.M Sangaji No.2, RT.15/RW.5, Petojo Utara, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10130, Indonesia',0,8000,0,''),
(281,'P1735004105','D1735003893',1,'-6.1780469460797','106.83290071785','-6.181367558503','106.81978337467',3.3150000572205,8500,'2026-01-09 10:36:55','2026-01-09 10:38:16','1','Jl. Medan Merdeka Tim. No.1A 6, RT.6/RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Bank Indonesia Jl. MH Thamrin, Jl. Kebon Sirih, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia',0,8500,0,'5.0'),
(282,'P1735004105','D1735003893',4,'-6.1789539333321','106.83335736394','-6.1859594491495','106.8166173622',4.143000125885,21000,'2026-01-09 10:41:09','2026-01-09 10:41:35','OgotSend','Jl. Batu III No.6, RT.6/RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Jl. Kp. Bali IV No.26, RT.9/RW.7, Kp. Bali, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10250, Indonesia',0,21000,0,'5.0'),
(283,'P1735004105',NULL,5,'-6.1817609','106.819371','-6.1780335','106.834231',4.3550000190735,17500,'2026-01-09 10:53:45',NULL,'4.355000019073486','gambir','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n',0,37500,0,''),
(285,'P1735004105',NULL,2,'-6.1780335','106.834231','-6.249116218195','107.01356392354',28.680000305176,344500,'2026-01-09 10:55:48',NULL,'2','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n    ','Q227+9C4, Margahayu, Bekasi Timur, Bekasi, West Java, Indonesia',0,344500,0,''),
(286,'P1735004105',NULL,2,'-6.1780335','106.834231','-6.249116218195','107.01356392354',28.680000305176,344500,'2026-01-09 10:56:36',NULL,'2','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n    ','Q227+9C4, Margahayu, Bekasi Timur, Bekasi, West Java, Indonesia',0,344500,0,''),
(287,'P1735004105',NULL,2,'-6.1780335','106.834231','-6.249116218195','107.01356392354',28.680000305176,344500,'2026-01-09 10:57:05',NULL,'2','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n    ','Q227+9C4, Margahayu, Bekasi Timur, Bekasi, West Java, Indonesia',0,344500,0,''),
(288,'P1735004105','D1735003893',5,'-6.1819605466232','106.81804966182','-6.1780335','106.834231',4.3800001144409,18000,'2026-01-09 10:57:31','2026-01-09 10:58:18','4.380000114440918','no Jl. Kebon Sirih No.87 2, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Sdn Merdeka Timur 01 Pagi, RW.1, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia\n',0,28000,0,'5.0'),
(289,'P1735004105','D1735003893',5,'-6.1819605466232','106.81804966182','-6.2220831','106.8585217',9.0690002441406,36500,'2026-01-09 11:08:33','2026-01-09 11:12:03','9.069000244140625','no Jl. Kebon Sirih No.87 2, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Gg. Langgar No.56, RT.13/RW.6, Bukit Duri, Kec. Tebet, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12840, Indonesia\n',0,48500,0,'5.0'),
(291,'P1735004105',NULL,1,'-6.2218742154359','106.85898218304','-6.2140612431024','106.88543681055',3.9509999752045,10000,'2026-01-11 20:55:39',NULL,'1','Jl. Bukit Duri Tanjakan No.13, RT.13/RW.8, Bukit Duri, Kec. Tebet, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12840, Indonesia','9, RW.9, Pisangan Tim., Kec. Pulo Gadung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta, Indonesia',0,10000,0,''),
(292,'P1735004105',NULL,1,'-1.2632915028315','116.86038840562','-1.2481852424988','116.8667633459',2.6349999904633,7000,'2026-02-14 22:41:01',NULL,'1','PVP6+M4P, Jl. MT Haryono, Damai, Kec. Balikpapan Kota, Kota Balikpapan, Kalimantan Timur, Indonesia','Jl. Agung Tunggal No.30, Damai, Kecamatan Balikpapan Selatan, Kota Balikpapan, Kalimantan Timur 76114, Indonesia',0,7000,0,''),
(293,'P1735004105',NULL,1,'0.49544912769837','101.37148194015','0.51652074573158','101.44387912005',11.133999824524,28000,'2026-03-19 02:04:54',NULL,'1','F9WC+97W, Simpang Baru, Tampan, Pekanbaru City, Riau 28291, Indonesia','GC8V+JH2, Jadirejo, Sukajadi, Pekanbaru City, Riau 28156, Indonesia',0,28000,0,''),
(294,'P1735004105',NULL,1,'-8.3678341','114.1416079','-8.3028668310659','114.13980100304',7.9470000267029,20000,'2026-03-25 20:10:42',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',500,19500,1,''),
(307,'P1735004105','D1775207413',1,'-8.3679141','114.1415896','-8.365338263488','114.14672713727',1.0859999656677,7000,'2026-04-14 19:27:24','2026-04-14 19:28:08','1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n','J4MW+VM9, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0,7000,0,'5.0'),
(308,'P1735004105',NULL,1,'-8.3679141','114.1415896','-8.3028668310659','114.13980100304',7.9770002365112,20000,'2026-04-14 19:29:58',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0,20000,0,''),
(309,'P1735004105',NULL,1,'-8.3679285701878','114.14152700454','-8.3743201752894','114.14531093091',1.8430000543594,7000,'2026-04-14 19:31:11',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia','Jl. Diponegoro No.245, Dusun Krajan II, Gambiran, Kec. Gambiran, Kabupaten Banyuwangi, Jawa Timur 68486, Indonesia',0,7000,0,''),
(310,'P1735004105',NULL,1,'-8.3679141','114.1415896','-8.3645358586745','114.1548455134',1.875,7000,'2026-04-14 19:34:43',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','J5P3+5WQ, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia',0,7000,0,''),
(311,'P1735004105','D1775207413',1,'-8.3679141','114.1415896','-8.3028668310659','114.13980100304',7.9770002365112,20000,'2026-04-15 05:39:12','2026-04-15 05:40:14','1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','Jln raya sempu , timur stasiun kalisetail ( tikungan, M4WQ+VW6, Tlogosari, Jambewangi, Kec. Sempu, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0,20000,0,'5.0'),
(312,'P1735004105',NULL,1,'-8.3679141','114.1415896','-8.2228785338913','114.34079434723',37.598999023438,94000,'2026-04-15 05:41:35',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','Banyuwangi City Train Station, Lingkungan Karang Ase, Bakungan, Kec. Glagah, Kabupaten Banyuwangi, Jawa Timur 68431, Indonesia',0,94000,0,''),
(313,'P1735004105',NULL,1,'-8.3679141','114.1415896','-8.365338263488','114.14672713727',1.0859999656677,7000,'2026-04-15 05:45:48',NULL,'1','Jl. Tegalsari No.10, Jalen I, Setail, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur 68465, Indonesia\n    ','J4MW+VM9, Dusun Krajan, Genteng Kulon, Kec. Genteng, Kabupaten Banyuwangi, Jawa Timur, Indonesia',0,7000,0,''),
(314,'P1735004105','D1735003893',4,'-6.1590043','106.8005271','-6.1608562273966','106.79276581854',2.7320001125336,14000,'2026-06-02 07:01:40','2026-06-02 07:02:08','Travel','Jl. Kalianyar X No.24, RT.1/RW.6, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n','RQQV+M43, RT.3/RW.8, Grogol, Grogol petamburan, West Jakarta City, Jakarta 11450, Indonesia',0,14000,0,'5.0'),
(315,'P1735004105',NULL,2,'-6.1590043','106.8005271','-6.1616979095851','106.78464107215',3.6219999790192,50000,'2026-06-02 07:05:48',NULL,'2','Jl. Kalianyar X No.24, RT.1/RW.6, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n','RQQM+8R9, RT.11/RW.3, Jelambar, Grogol petamburan, West Jakarta City, Jakarta 11460, Indonesia',500,49500,1,''),
(316,'P1735004105','D1735003893',1,'-6.1590043','106.8005271','-6.1608562273966','106.79276581854',2.7320001125336,7000,'2026-06-02 07:12:38','2026-06-03 15:43:47','1','Jl. Kalianyar X No.24, RT.1/RW.6, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n','RQQV+M43, RT.3/RW.8, Grogol, Grogol petamburan, West Jakarta City, Jakarta 11450, Indonesia',0,7000,0,'5.0'),
(317,'P1735004105','D1735003893',5,'-6.1819605466232','106.81804966182','-6.1590033','106.8005927',8.4099998474121,34000,'2026-06-03 21:44:05','2026-06-03 21:47:19','8.40999984741211','no Jl. Kebon Sirih No.87 2, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Jl. Kali Anyar X Gg. 5 No.12, RT.1/RW.9, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n',0,56000,0,'1.0'),
(319,'P1735004105',NULL,5,'-6.1819605466232','106.81804966182','-6.1590033','106.8005927',8.4099998474121,34000,'2026-06-03 21:44:06',NULL,'8.40999984741211','no Jl. Kebon Sirih No.87 2, RT.2/RW.3, Gambir, Kecamatan Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10110, Indonesia','Jl. Kali Anyar X Gg. 5 No.12, RT.1/RW.9, Kali Anyar, Kec. Tambora, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11310, Indonesia\n',0,56000,0,''),
(7,'P1735023118','D1735025353',1,'1.3390329675353','120.98129946738','1.3317892831057','120.98108187318',1.2029999494553,7200,'2024-12-24 14:35:03','2024-12-24 14:35:35','1','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','8XJJ+VC6, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia',0,7200,0,'5.0'),
(8,'P1735023118',NULL,1,'1.3392300560546','120.98101884127','1.3344221642566','120.97745217383',0.67400002479553,7200,'2024-12-24 14:36:13',NULL,'1','8XQJ+M8Q, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi 94562, Indonesia','8XMG+QX Salumpaga, Toli-Toli Regency, Central Sulawesi, Indonesia',0,7200,0,''),
(9,'P1735023118',NULL,1,'1.3308306522234','120.97787294537','1.3380190340009','120.98017528653',1,7200,'2024-12-24 14:36:49',NULL,'1','8XJH+84 Salumpaga, Toli-Toli Regency, Central Sulawesi, Indonesia','8XQJ+34V, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi, Indonesia',0,7200,0,''),
(10,'P1735023118',NULL,1,'1.3308306522234','120.97787294537','1.3380190340009','120.98017528653',1,7200,'2024-12-24 14:36:56',NULL,'1','8XJH+84 Salumpaga, Toli-Toli Regency, Central Sulawesi, Indonesia','8XQJ+34V, Salumpaga, North Tolitoli, Toli-Toli Regency, Central Sulawesi, Indonesia',0,7200,0,''),
(12,'P1735023118','D1735025353',5,'1.3389334177159','120.9812669456','1.3387416921265','120.97637224942',0.68300002813339,2000,'2025-01-10 13:43:32','2025-01-10 13:45:22','0.6830000281333923','8XQJ+M8Q, Salumpaga, Kec. Toli-Toli Utara, Kabupaten Toli-Toli, Sulawesi Tengah 94562, Indonesia','8XQG+JH8, Jl. Buol - Tolitoli, Salumpaga, Kec. Toli-Toli Utara, Kabupaten Toli-Toli, Sulawesi Tengah 94562, Indonesia\n',0,32000,0,'5.0'),
(210,'P1735023118','D1735025353',1,'1.0883333911594','120.80622062087','1.0786992782326','120.80375868827',1.3309999704361,7200,'2025-07-20 23:47:03','2025-07-20 23:47:22','1','3RQ4+2GF, Kalangkangan, Galang, Toli-Toli Regency, Central Sulawesi 94561, Indonesia','Jl. Hi.Moh.Saleh No.26, Sandana, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia',0,7200,0,'5.0'),
(211,'P1735023118',NULL,1,'1.1068077387842','120.81552252173','1.0729506414655','120.80132558942',4.5209999084473,7200,'2025-07-20 23:48:27',NULL,'1','Togaso, Kalangkangan, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia','3RF2+2RF, Ogomoli, Galang, Toli-Toli Regency, Central Sulawesi 94561, Indonesia',0,7200,0,''),
(212,'P1735023118','D1735025353',1,'1.1066632617309','120.81565998495','1.0807068904415','120.80423008651',3.6670000553131,7200,'2025-07-20 23:51:07','2025-07-20 23:52:08','1','4R48+J73, Togaso, Kalangkangan, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia','3RJ3+8QX, Dusun subur, Sandana, Kec. Galang, Kabupaten Toli-Toli, Sulawesi Tengah 94561, Indonesia',0,7200,0,'5.0'),
(69,'P1741272299',NULL,2,'-8.2838653693449','114.32774037123','-8.2800296653102','114.31562382728',3.0090000629425,50000,'2025-03-06 21:47:58',NULL,'2','P88H+F3 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P898+X75, Karangrejo, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(70,'P1741272299','D1735003893',1,'-8.2858832379071','114.3308416754','-8.2850143148475','114.3245485425',0.80900001525879,7200,'2025-03-06 21:48:46','2025-03-06 21:51:35','1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P87F+XR Pakistaji, Banyuwangi Regency, East Java, Indonesia',0,7200,0,'5.0'),
(72,'P1741272299',NULL,2,'-8.2816557217274','114.33275476098','-8.2867986076227','114.32102311403',2.0610001087189,50000,'2025-03-06 21:52:20',NULL,'2','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P87C+3H, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(75,'P1741272299',NULL,1,'-8.2859084529043','114.33081552386','-8.2898761452762','114.31748628616',2.0699999332428,7200,'2025-03-06 21:53:32',NULL,'1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P859+R4R, RT.001/RW.012, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 48461, Indonesia',0,7200,0,''),
(78,'P1741272299',NULL,1,'-8.2918100448751','114.33041285723','-8.2882119687849','114.31810017675',1.6640000343323,7200,'2025-03-06 21:55:41',NULL,'1','P85J+75 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(80,'P1741272299',NULL,1,'-8.2816295111181','114.33288183063','-8.2747785288782','114.32246278971',5.0900001525879,7200,'2025-03-06 21:58:18',NULL,'1','JAYA STONE BANYUWANGI, Timur masjid baitussallam dusun kepuh ( utara perempatan/sebelah gedung walet, Dusun Dadapan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8GF+G3F, Krajan, Kabat, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(81,'P1741272299',NULL,2,'-8.2848686647081','114.33123294264','-8.2882252397576','114.31820847094',1.9839999675751,50000,'2025-03-06 22:00:10',NULL,'2','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(82,'P1741272299',NULL,1,'-8.2817678633017','114.3327614665','-8.2828458142604','114.32306192815',1.4739999771118,7200,'2025-03-06 22:01:50',NULL,'1','P89M+74V, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P88F+V6 Pakistaji, Banyuwangi Regency, East Java, Indonesia',0,7200,0,''),
(83,'P1741272299',NULL,1,'-8.284463896384','114.33127854019','-8.2881933894223','114.31806329638',1.9730000495911,7200,'2025-03-06 22:02:55',NULL,'1','P87J+QGV, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P869+Q77, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,''),
(84,'P1741272299',NULL,2,'-8.2811826033729','114.33271955699','-8.2926198977571','114.31640636176',2.9769999980927,50000,'2025-03-06 22:04:06',NULL,'2','P89M+F4P, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P848+XH Pakistaji, Banyuwangi Regency, East Java, Indonesia',0,50000,0,''),
(85,'P1741272299',NULL,10,'-8.2814148494805','114.33283489197','0','0',0,16500,'2025-03-06 22:05:14',NULL,'6 hr','P89M+F4P, Dusun Kepuh, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','',0,16500,0,''),
(86,'P1741272299','D1735003893',1,'-8.2894345552708','114.32877201587','-8.2893665417398','114.3122247979',2.0610001087189,7200,'2025-03-06 22:06:16','2025-03-06 22:07:34','1','P86H+4MH, Jl. Kh Ahmad Asyari, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','Jl. Raya Jember No.28, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,'5.0'),
(87,'P1741272299',NULL,2,'-8.2820588340422','114.33274906129','-8.2948507169773','114.30190064013',4.9920001029968,50000,'2025-03-06 22:08:24',NULL,'2','P89M+53 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P833+Q22, Kawang, Labanasem, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,50000,0,''),
(88,'P1741272299',NULL,2,'-8.2790014739567','114.34268597513','-8.2681367878249','114.30631823838',7.3709998130798,72500,'2025-03-06 22:09:17',NULL,'2','P8CV+93 Pakistaji, Banyuwangi Regency, East Java, Indonesia','P8J4+PG Kabat, Banyuwangi Regency, East Java, Indonesia',0,72500,0,''),
(89,'P1741272299','D1735003893',1,'-8.2813680684898','114.34673275799','-8.256343423761','114.3518755585',4.2890000343323,7200,'2025-03-06 22:10:28','2025-03-06 22:11:28','1','P87J+MFH, Dusun Krajan, Pakistaji, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia\n    ','P9V2+FQ Dadapan, Banyuwangi Regency, East Java, Indonesia',0,7200,0,'5.0'),
(90,'P1741272299',NULL,2,'-8.2673643770655','114.34875011444','-8.2500461194281','114.33719582856',2.5940001010895,50000,'2025-03-06 22:12:00',NULL,'2','P8MX+7GF, Dukuh segoling, Dusun Secawan, Dadapan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8XP+XV Dadapan, Banyuwangi Regency, East Java, Indonesia',0,50000,0,''),
(91,'P1741272299',NULL,2,'-8.2617271925983','114.35047309846','-8.2617271925983','114.35047309846',0,50000,'2025-03-06 22:12:57',NULL,'2','P9Q2+85 Pondoknongko, Banyuwangi Regency, East Java, Indonesia','P9Q2+85 Pondoknongko, Banyuwangi Regency, East Java, Indonesia',0,50000,0,''),
(93,'P1741272299',NULL,2,'-8.3191642984654','114.3395897001','-8.3634710692482','114.30807441473',12.175000190735,119500,'2025-03-06 22:14:45',NULL,'2','M8JQ+7QP, Patoman, Watukebo, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia','J8P5+R9M, Jl. K.H. Nawawi, Pekiwen, Kaligung, Kec. Rogojampi, Kabupaten Banyuwangi, Jawa Timur 68462, Indonesia',0,119500,0,''),
(98,'P1741272299',NULL,2,'-8.3033376009934','114.33539606631','-8.331896917874','114.2961107567',9.8839998245239,97000,'2025-03-06 22:18:52',NULL,'2','M8WP+H4P, Krajan, Badean, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur, Indonesia','M79W+6C Gladag, Banyuwangi Regency, East Java, Indonesia',0,97000,0,''),
(100,'P1741272299',NULL,2,'-8.2834154781331','114.33445829898','-8.314386432242','114.31527346373',7.6500000953674,75000,'2025-03-06 22:19:52',NULL,'2','P88M+JQ Pakistaji, Banyuwangi Regency, East Java, Indonesia','M8P8+64 Karangbendo, Banyuwangi Regency, East Java, Indonesia',0,75000,0,''),
(101,'P1741272299',NULL,2,'-8.2968552584852','114.34195775539','-8.3383236133064','114.31908722967',9.0719995498657,89000,'2025-03-06 22:20:54',NULL,'2','P83V+8G3, Donosuko, Badean, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','M869+MJ Gintangan, Banyuwangi Regency, East Java, Indonesia',0,89000,0,''),
(102,'P1741272299',NULL,2,'-8.2980871075362','114.33295324445','-8.3477692340313','114.31712083519',13.800000190735,135500,'2025-03-06 22:21:45',NULL,'2','P82M+Q5 Badean, Banyuwangi Regency, East Java, Indonesia','M828+VR Gintangan, Banyuwangi Regency, East Java, Indonesia',0,135500,0,''),
(103,'P1741272299','D1735003893',1,'-8.2846928227824','114.33657757938','-8.3093732925383','114.36016257852',7.353000164032,10000,'2025-03-06 22:23:24','2025-03-06 22:24:19','1','P88P+4J Pakistaji, Banyuwangi Regency, East Java, Indonesia','M9R6+73 Badean, Banyuwangi Regency, East Java, Indonesia',0,10000,0,'5.0'),
(104,'P1741272299','D1735003893',1,'-8.2637100042769','114.33624632657','-8.2605217736005','114.31108351797',4.298999786377,7200,'2025-03-06 22:24:42','2025-03-06 22:25:24','1','P8PP+7F7, Dusun Krajan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia','P8Q6+PFV, Dusun Babakan, Kedayunan, Kec. Kabat, Kabupaten Banyuwangi, Jawa Timur 68461, Indonesia',0,7200,0,'5.0'),
(106,'P1741272299','D1735003893',1,'-8.2041516417859','114.37011256814','-8.2124433631509','114.36556790024',1.6189999580383,7200,'2025-03-07 10:15:23','2025-03-07 10:16:48','1','Jl. Bromo No.10, RT.02/RW.01, Singotrunan, Pengantigan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia','Q9Q8+C6F, Singonegaran, Banyuwangi Sub-District, Banyuwangi Regency, East Java 68415, Indonesia',0,7200,0,'5.0'),
(107,'P1741272299','D1735003893',1,'-8.2039435749947','114.37381770462','-8.1738623434996','114.3435094133',5.9939999580383,8000,'2025-03-07 11:28:28','2025-03-07 11:29:30','1','Jl. Basuki Rahmat No.43, RT.1, Singotrunan, Kec. Banyuwangi, Kabupaten Banyuwangi, Jawa Timur 68414, Indonesia','R8GV+FC Kelir, Banyuwangi Regency, East Java, Indonesia',0,8000,0,'5.0'),
(276,'P1741272299',NULL,1,'-6.5480432','106.7694939','-6.6014290807585','106.8069030717',10.696999549866,27000,'2025-12-14 09:45:54',NULL,'1','Jl. Komp. Bogor Raya Permai Blok FE6 No.12, Curug, Kec. Bogor Bar., Kota Bogor, Jawa Barat 16113, Indonesia\n','Jl. Raya Pajajaran No.40, RT.04/RW.05, Tugu Kujang, Kecamatan Bogor Tengah, Kota Bogor, Jawa Barat 16127, Indonesia',0,27000,0,''),
(219,'P1753033513',NULL,1,'-8.1898853746806','111.11016780138','-8.1838890094137','111.11611392349',1.3630000352859,7200,'2025-07-21 17:06:37',NULL,'1','R466+244, Kebonredi, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia','Jl. Tentara Pelajar No.92, Bulu, Nanggungan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(234,'P1753033513','D1735003893',1,'-8.212998859487','111.10071334988','-8.1807306683879','111.11780002713',5.1290001869202,7200,'2025-07-24 23:00:11','2025-07-24 23:02:54','1','Q4P2+Q9Q, Jl. Kusudana, Temon, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','Rt3/1, Krajan, Widoro, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,'5.0'),
(235,'P1753033513','D1735025353',4,'-8.2083381746761','111.09282799065','-8.2226638403279','111.10862888396',2.6489999294281,13500,'2025-07-24 23:03:41','2025-07-24 23:06:33','OgotSend','Q3RV+R38, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','Q4G5+WF Kembang, Pacitan Regency, East Java, Indonesia',0,13500,0,'5.0'),
(236,'P1753033513',NULL,1,'-8.2039608309368','111.09760265797','-8.1855240890214','111.08960531652',3.4779999256134,7200,'2025-07-25 15:36:46',NULL,'1','Q3WX+82M, Barehan, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','R37Q+HR2, Craken Wetan, Sumberharjo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7200,0,''),
(237,'P1753033513',NULL,1,'-8.2085316381527','111.09330207109','-8.1866680102203','111.10208395869',3.4270000457764,7200,'2025-07-25 15:38:21',NULL,'1','Q3RV+QQ7, Ngampel, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur, Indonesia','Jl. Brigjend Katamso No.88c, Gantung, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63512, Indonesia',0,7200,0,''),
(238,'P1753033513',NULL,1,'-8.2123819731258','111.10071737319','-8.1172250278762','111.14559341222',14.159000396729,35500,'2025-07-27 12:53:58',NULL,'1','Q4Q2+383, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','V4MW+36P, Jl. Patrem, Tj. II, Tremas, Kec. Arjosari, Kabupaten Pacitan, Jawa Timur 63581, Indonesia',0,35500,0,''),
(239,'P1753033513',NULL,1,'-7.862623566529','111.47943288088','-7.9115731180188','111.5250813961',13.746000289917,34500,'2025-07-27 12:58:22',NULL,'1','4FPH+XPV, Jl. Cinde Wilis Gg. 2, Ronowijayan, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63491, Indonesia','3GQG+G3W, Ngledok, Mlarak, Kec. Mlarak, Kabupaten Ponorogo, Jawa Timur 63472, Indonesia',0,34500,0,''),
(240,'P1753033513',NULL,1,'-8.2138659498845','111.10337007791','-8.1531560977918','111.12537458539',8.3409996032715,21000,'2025-07-27 12:59:55',NULL,'1','Q4P3+696 Grindulu Bridge, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','R4WG+M7C, Dayakan, Bolosingo, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,21000,0,''),
(241,'P1753033513',NULL,1,'-7.8704672688104','111.46320719272','-7.6539019161536','111.33134175092',35.206001281738,88500,'2025-07-27 13:17:10',NULL,'1','Jl. Alun-Alun Timur No.7, Ponorogo, Mangkujayan, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63413, Indonesia','Jl. Jend. Sudirman No.38, Dusun Kebonagung, Kebonagung, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63317, Indonesia',0,88500,0,''),
(242,'P1753033513',NULL,1,'-7.6548329890392','111.32815796882','-7.6531592494928','111.33405312896',1.9349999427795,7000,'2025-07-27 13:19:06',NULL,'1','Jl. Basuki Rahmat Barat No.10, Dusun Magetan, Magetan, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63361, Indonesia','88WM+QH5, Tambran, Kec. Magetan, Kabupaten Magetan, Jawa Timur 63318, Indonesia',0,7000,0,''),
(243,'P1753033513','D1735025353',1,'-8.0496370578491','110.95130223781','-8.021344362239','110.90919591486',8.1719999313354,20500,'2025-07-27 16:09:52','2025-07-27 16:19:51','1','XX22+4MJ, Sidorejo, Guwotirto, Kec. Giriwoyo, Kabupaten Wonogiri, Jawa Tengah 57675, Indonesia','XWH5+MCC, Jl. Ngampohan Platarejo, Ngompuan, Platarejo, Kec. Giriwoyo, Kabupaten Wonogiri, Jawa Tengah 57675, Indonesia',0,20500,0,'1.0'),
(244,'P1753033513',NULL,1,'-8.0813119884393','111.70715544373','-8.0540665452841','111.70870408416',3.2829999923706,8500,'2025-07-28 10:55:34',NULL,'1','WP95+934, Karanggayam, Karangsoko, Kec. Trenggalek, Kabupaten Trenggalek, Jawa Timur 66319, Indonesia','Jl. Panglima Sudirman No.65, Sawahan, Ngantru, Kec. Trenggalek, Kabupaten Trenggalek, Jawa Timur 66311, Indonesia',0,8500,0,''),
(245,'P1753033513',NULL,1,'-7.9008233909751','111.51965931058','-7.8735801980545','111.60600531846',14.96399974823,37500,'2025-07-28 10:56:35',NULL,'1','4G29+3H4, Jl. Mat Sarwan, Krajan, Jarak, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia','4JG4+M96, Krajan, Sidoharjo, Kec. Pulung, Kabupaten Ponorogo, Jawa Timur 63481, Indonesia',0,37500,0,''),
(246,'P1753033513',NULL,1,'-8.1935367780592','111.10301736742','-8.1767180756723','111.07246365398',6.5289998054504,16500,'2025-07-28 10:57:32',NULL,'1','Jl. Jaksa Agung Suprapto No.8, Krajan, Pacitan, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63511, Indonesia','R3FF+C2G, Kembang Kidul, Sedeng, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,16500,0,''),
(247,'P1753033513',NULL,1,'-7.6554865996609','111.5146522969','-7.7201791096316','111.46308716387',15.505999565125,39000,'2025-07-28 11:01:24',NULL,'1','8GV7+RW2, Jl. Sendang, Kuncen, Kec. Taman, Kota Madiun, Jawa Timur 63135, Indonesia','7FH7+C32, Jati, Rejosari, Kec. Kb. Sari, Kabupaten Madiun, Jawa Timur 63173, Indonesia',0,39000,0,''),
(248,'P1753033513','D1735025353',1,'-7.8664898059392','111.41175102443','-7.9504829904608','111.41015343368',9.6759996414185,24500,'2025-07-28 11:02:53','2025-07-28 11:08:19','1','Jl. Ponorogo - Wonogiri No.196, Wetan Dalem, Carat, Kec. Kauman, Kabupaten Ponorogo, Jawa Timur 63451, Indonesia','2CX6+P2C, Jl. Sumoroto, Kedung, Bringinan, Kec. Jambon, Kabupaten Ponorogo, Jawa Timur 63461, Indonesia',0,24500,0,'4.0'),
(249,'P1753033513','D1735025353',1,'-7.8707641820932','111.47103823721','-7.8684483192711','111.49853892624',3.4590001106262,9000,'2025-07-28 11:10:38',NULL,'1','Jl. Gajah Mada No.11, Bangunsari, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63419, Indonesia','4FJX+CF3, Godang, Ronowijayan, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia',0,9000,0,''),
(250,'P1753033513',NULL,1,'-7.8771554048005','111.47238604724','-7.8474252864675','111.49598579854',5.3660001754761,13500,'2025-07-28 11:11:51',NULL,'1','4FFC+5X3, Jl. Kumbokarno, RT.2/RW.4, Pesantren, Surodikraman, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63419, Indonesia','Jl. Raden Wijaya No.28A, Kebon, Kadipaten, Kec. Babadan, Kabupaten Ponorogo, Jawa Timur 63491, Indonesia',0,13500,0,''),
(251,'P1753033513','D1735025353',1,'-7.8986853673954','111.49185117334','-7.7960635491657','111.6407751292',26.788000106812,67000,'2025-07-28 11:13:44','2025-07-28 11:16:28','1','4F2R+GP Brahu, Ponorogo Regency, East Java, Indonesia','6J3R+J65, Dukuh Nglingi, Ngebel, Kec. Ngebel, Kabupaten Ponorogo, Jawa Timur 63493, Indonesia',0,67000,0,'5.0'),
(252,'P1753033513',NULL,1,'-8.2058380738634','111.10070429742','-8.1892070588489','111.11365668476',2.9300000667572,7500,'2025-07-28 12:28:22',NULL,'1','Q4V2+Q4R, Peden, Ploso, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63515, Indonesia','Jl. Tentara Pelajar No.172, Bengkal, Tanjungsari, Kec. Pacitan, Kabupaten Pacitan, Jawa Timur 63518, Indonesia',0,7500,0,''),
(253,'P1753033513',NULL,1,'-7.8657837159406','111.46323535591','-7.7971620578038','111.51264332235',10.131999969482,25500,'2025-07-28 12:29:20',NULL,'1','4FM7+G7W, Jl. Banda, Temengungan, Mangkujayan, Kec. Ponorogo, Kabupaten Ponorogo, Jawa Timur 63413, Indonesia','Jl. RA. Kartini RT. 06/ RW.02, Brahu, Mlilir, Kec. Dolopo, Kabupaten Madiun, Jawa Timur 63174, Indonesia',0,25500,0,''),
(275,'P1759559503',NULL,1,'-8.5405099313111','118.4673313424','-8.5336028465751','118.45189254731',3.0710000991821,8000,'2025-12-04 23:57:00',NULL,'1','FF58+XXC, Bada, Dompu, Dompu Regency, West Nusa Tenggara, Indonesia','FF82+JPQ, Jl. Lkr. Utara, Karijawa, Kec. Dompu, Kabupaten Dompu, Nusa Tenggara Bar., Indonesia',0,8000,0,''),
(295,'P1775197589',NULL,1,'-7.9059415','111.4571995','-7.6198572140931','111.51570037007',37.340000152588,93500,'2026-04-03 13:47:54',NULL,'1','3FV4+JWR, Majasem, Madusari, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia\n    ','Jl. Ronggolawe No.16, Winongo, Kec. Manguharjo, Kota Madiun, Jawa Timur 63126, Indonesia',0,93500,0,''),
(296,'P1775197589',NULL,4,'-7.9059415','111.4571995','-7.925396582679','111.49838805199',6.2620000839233,31500,'2026-04-03 13:49:01',NULL,'Travel','3FV4+JWR, Majasem, Madusari, Kec. Siman, Kabupaten Ponorogo, Jawa Timur 63471, Indonesia\n    ','3FFX+V92, Gontor 1, Gontor, Kec. Mlarak, Kabupaten Ponorogo, Jawa Timur 63472, Indonesia',0,31500,0,''),
(297,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.933610344099','112.65855778009',12.784999847412,32000,'2026-04-10 13:06:16',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Raden Intan No.1, Arjosari, Kec. Blimbing, Kota Malang, Jawa Timur 65126, Indonesia',0,32000,0,''),
(298,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.933610344099','112.65855778009',12.784999847412,32000,'2026-04-10 13:06:27',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Raden Intan No.1, Arjosari, Kec. Blimbing, Kota Malang, Jawa Timur 65126, Indonesia',0,32000,0,''),
(299,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.9666204702821','112.6326322183',12.46399974823,31500,'2026-04-10 13:31:25',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','2JMM+932, Samaan, Klojen, Malang City, East Java 65112, Indonesia',0,31500,0,''),
(300,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.9666204702821','112.6326322183',12.46399974823,31500,'2026-04-10 13:31:39',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','2JMM+932, Samaan, Klojen, Malang City, East Java 65112, Indonesia',0,31500,0,''),
(301,'P1775197589',NULL,2,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,152000,'2026-04-10 13:40:24',NULL,'2','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,152000,0,''),
(302,'P1775197589',NULL,2,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,152000,'2026-04-10 13:45:07',NULL,'2','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,152000,0,''),
(303,'P1775197589',NULL,2,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,152000,'2026-04-10 13:45:18',NULL,'2','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,152000,0,''),
(304,'P1775197589','D1775207413',1,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,32000,'2026-04-10 13:55:59',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,32000,0,''),
(305,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,32000,'2026-04-10 13:56:13',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,32000,0,''),
(306,'P1775197589',NULL,1,'-7.8967801326452','112.58385658264','-7.9775235346774','112.63704579324',12.633999824524,32000,'2026-04-10 13:56:39',NULL,'1','4H3M+F9G, Pendem, Junrejo, Batu City, East Java 65324, Indonesia','Jl. Trunojoyo No.79, Klojen, Kec. Klojen, Kota Malang, Jawa Timur 65111, Indonesia',0,32000,0,'');
/*!40000 ALTER TABLE `transaksi` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `transaksi_detail_merchant`
--

DROP TABLE IF EXISTS `transaksi_detail_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_detail_merchant` (
  `id_trans_merchant` int(11) NOT NULL AUTO_INCREMENT,
  `id_transaksi` varchar(250) NOT NULL,
  `id_merchant` varchar(250) NOT NULL,
  `total_biaya` varchar(250) NOT NULL,
  `harga_akhir` varchar(250) NOT NULL,
  `struk` varchar(250) NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_trans_merchant`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_detail_merchant`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `transaksi_detail_merchant` DISABLE KEYS */;
INSERT INTO `transaksi_detail_merchant` VALUES
(1,'12','1','30000','','7031','2025-01-10 06:43:32'),
(2,'155','2','111111','','2711','2025-07-19 17:58:15'),
(3,'156','2','111111','','3651','2025-07-19 17:59:30'),
(4,'160','2','114111','','3241','2025-07-19 19:37:36'),
(5,'161','2','114111','','3744','2025-07-19 19:39:31'),
(6,'166','2','114111','','5635','2025-07-19 19:48:16'),
(7,'173','2','114111','','8379','2025-07-20 02:28:46'),
(8,'174','2','114111','','2707','2025-07-20 02:29:26'),
(9,'175','2','114111','','7475','2025-07-20 02:31:02'),
(10,'194','2','114111','','3539','2025-07-20 05:42:24'),
(11,'202','2','114111','','1498','2025-07-20 05:52:14'),
(12,'203','2','114111','','898','2025-07-20 05:54:20'),
(13,'204','2','114111','','5579','2025-07-20 05:54:21'),
(14,'215','2','114111','','4644','2025-07-20 17:26:50'),
(15,'216','2','3000','','6423','2025-07-20 17:32:43'),
(16,'257','1','12000','','6372','2025-09-12 12:30:47'),
(17,'258','1','12000','','3772','2025-09-12 12:30:48'),
(18,'259','1','12000','','6134','2025-09-12 12:31:27'),
(19,'260','1','12000','','1143','2025-09-12 13:30:13'),
(20,'261','1','12000','','2460','2025-09-12 13:31:32'),
(21,'262','1','10000','','8473','2025-09-12 13:38:10'),
(22,'263','1','2000','','8257','2025-09-12 13:38:44'),
(23,'264','1','2000','','2222','2025-09-12 13:40:05'),
(24,'265','1','2000','','2404','2025-09-12 13:41:02'),
(25,'266','1','12000','','4327','2025-09-12 13:42:18'),
(26,'267','1','10000','','4002','2025-09-12 13:47:19'),
(27,'268','1','10000','','8585','2025-09-12 13:58:49'),
(28,'269','1','10000','','3538','2025-09-13 07:08:11'),
(29,'270','1','12000','','8257','2025-09-13 07:15:06'),
(30,'271','1','10000','','2362','2025-09-13 07:17:24'),
(31,'283','1','20000','','3121','2026-01-09 03:53:45'),
(32,'288','1','10000','','1940','2026-01-09 03:57:31'),
(33,'289','1','12000','','2558','2026-01-09 04:08:33'),
(34,'317','1','22000','','2881','2026-06-03 14:44:05'),
(35,'319','1','22000','','9598','2026-06-03 14:44:06');
/*!40000 ALTER TABLE `transaksi_detail_merchant` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `transaksi_detail_send`
--

DROP TABLE IF EXISTS `transaksi_detail_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_detail_send` (
  `id_send` int(11) NOT NULL AUTO_INCREMENT,
  `id_transaksi` varchar(250) NOT NULL,
  `nama_barang` varchar(250) NOT NULL,
  `nama_pengirim` varchar(250) NOT NULL,
  `nama_penerima` varchar(250) NOT NULL,
  `telepon_pengirim` varchar(250) NOT NULL,
  `telepon_penerima` varchar(250) NOT NULL,
  PRIMARY KEY (`id_send`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_detail_send`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `transaksi_detail_send` DISABLE KEYS */;
INSERT INTO `transaksi_detail_send` VALUES
(1,'1','document','','','+62','+62'),
(2,'2','document','','','+62','+62'),
(3,'3','document','','','+62','+62'),
(4,'4','document','','','+62','+62'),
(5,'28','document','','','+62','+62'),
(6,'44','document','','','+62','+62'),
(7,'45','document','','','+62','+62'),
(8,'57','document','','','+62','+62'),
(9,'58','document','','','+62','+62'),
(10,'60','document','','','+62','+62'),
(11,'61','fashion','','','+62','+62'),
(12,'62','fashion','','','+62','+62'),
(13,'157','document','','','+62','+62'),
(14,'180','document','','','+62','+62'),
(15,'181','box','','','+62','+62'),
(16,'196','document','','','+62','+62'),
(17,'197','document','','','+62','+62'),
(18,'214','document','','','+62','+62'),
(19,'231','document','','','+62','+62'),
(20,'233','test','','','+62','+62'),
(21,'235','document','','','+62','+62'),
(22,'282','komputer','','','+62','+62'),
(23,'296','document','','','+62','+62'),
(24,'314','fashion','','','+62','+62');
/*!40000 ALTER TABLE `transaksi_detail_send` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `transaksi_item`
--

DROP TABLE IF EXISTS `transaksi_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_item` (
  `id_trans_item` int(11) NOT NULL AUTO_INCREMENT,
  `tipe` tinyint(1) NOT NULL DEFAULT 0,
  `id_item` varchar(200) NOT NULL,
  `nama_pesanan` varchar(255) DEFAULT NULL,
  `id_merchant` varchar(100) NOT NULL,
  `id_transaksi` varchar(200) NOT NULL,
  `jumlah_item` varchar(200) NOT NULL,
  `catatan_item` text NOT NULL,
  `total_harga` varchar(200) NOT NULL,
  PRIMARY KEY (`id_trans_item`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_item`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `transaksi_item` DISABLE KEYS */;
INSERT INTO `transaksi_item` VALUES
(1,0,'1','','1','12','3','','30000'),
(2,0,'4','','2','155','1','','111111'),
(3,0,'4','','2','156','1','','111111'),
(4,0,'4','','2','160','1','','111111'),
(5,0,'5','','2','160','1','','3000'),
(6,0,'4','','2','161','1','','111111'),
(7,0,'5','','2','161','1','','3000'),
(8,0,'5','','2','166','1','','3000'),
(9,0,'4','','2','166','1','','111111'),
(10,0,'4','','2','173','1','','111111'),
(11,0,'5','','2','173','1','','3000'),
(12,0,'4','','2','174','1','','111111'),
(13,0,'5','','2','174','1','','3000'),
(14,0,'4','','2','175','1','','111111'),
(15,0,'4','','2','194','1','','111111'),
(16,0,'5','','2','194','1','','3000'),
(17,0,'4','','2','202','1','','111111'),
(18,0,'5','','2','202','1','','3000'),
(19,0,'4','','2','203','1','','111111'),
(20,0,'5','','2','203','1','','3000'),
(21,0,'4','','2','204','1','','111111'),
(22,0,'5','','2','204','1','','3000'),
(23,0,'4','','2','215','1','','111111'),
(24,0,'5','','2','215','1','','3000'),
(25,0,'5','','2','216','1','','3000'),
(26,0,'1','','1','257','1','','10000'),
(27,0,'2','','1','257','1','','2000'),
(28,0,'1','','1','258','1','','10000'),
(29,0,'2','','1','258','1','','2000'),
(30,0,'1','','1','259','1','','10000'),
(31,0,'2','','1','259','1','','2000'),
(32,0,'1','','1','260','1','','10000'),
(33,0,'2','','1','260','1','','2000'),
(34,0,'1','','1','261','1','','10000'),
(35,0,'2','','1','261','1','','2000'),
(36,0,'1','','1','262','1','','10000'),
(37,0,'2','','1','263','1','','2000'),
(38,0,'2','','1','264','1','','2000'),
(39,0,'2','','1','265','1','','2000'),
(40,0,'2','','1','266','1','','2000'),
(41,0,'1','','1','266','1','','10000'),
(42,0,'1','','1','267','1','test','10000'),
(43,0,'1','','1','268','1','','10000'),
(44,0,'1','','1','269','1','test','0'),
(45,0,'1','','1','270','1','','10000'),
(46,0,'2','','1','270','1','','2000'),
(47,0,'1','','1','271','1','ok','0'),
(48,0,'1','','1','283','1','','10000'),
(49,0,'11','','1','283','1','','10000'),
(50,0,'1','','1','288','1','','10000'),
(51,0,'1','','1','289','1','','10000'),
(52,0,'2','','1','289','1','','2000'),
(53,0,'1','','1','317','1','','10000'),
(54,0,'2','','1','317','1','','2000'),
(55,0,'11','','1','317','1','','10000'),
(56,0,'1','','1','319','1','','10000'),
(57,0,'2','','1','319','1','','2000'),
(58,0,'11','','1','319','1','','10000');
/*!40000 ALTER TABLE `transaksi_item` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `transaksi_saldo`
--

DROP TABLE IF EXISTS `transaksi_saldo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_saldo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipe` tinyint(1) NOT NULL,
  `invoice` varchar(20) NOT NULL,
  `sender_wallet_id` int(11) NOT NULL,
  `receiver_wallet_id` int(11) NOT NULL,
  `sender_user_id` varchar(30) NOT NULL,
  `receiver_user_id` varchar(30) NOT NULL,
  `saldo_sender_awal` int(11) NOT NULL,
  `saldo_receiver_awal` int(11) NOT NULL,
  `nominal` int(11) NOT NULL,
  `fee` int(11) NOT NULL,
  `note` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_saldo`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `transaksi_saldo` DISABLE KEYS */;
INSERT INTO `transaksi_saldo` VALUES
(1,1,'TRF-250601210945',2,5,'P1735004105','D1735025353',100000,49280,10000,0,'',1,'2025-01-09 06:21:45'),
(2,1,'TRF-250601220940',2,5,'P1735004105','D1735025353',90000,59280,10000,0,'',1,'2025-01-09 06:22:40'),
(3,1,'TRF-250601290917',5,1,'D1735025353','D1735003893',69280,48000,10000,0,'',1,'2025-01-09 06:29:17'),
(4,1,'TRF-250601290938',5,4,'D1735025353','P1735023118',59280,0,10000,0,'',1,'2025-01-09 06:29:38'),
(5,1,'TRF-250901120957',5,2,'D1735025353','P1735004105',49280,80000,20000,0,'',1,'2025-01-09 09:12:57'),
(6,1,'TRF-250901290944',5,4,'D1735025353','P1735023118',29280,10000,20000,0,'',1,'2025-01-09 09:29:44'),
(7,1,'TRF-250901330910',5,4,'D1735025353','P1735023118',9280,30000,1000,0,'',1,'2025-01-09 09:33:10'),
(8,1,'TRF-250901330933',5,1,'D1735025353','D1735003893',8280,58000,1000,0,'',1,'2025-01-09 09:33:33'),
(9,1,'TRF-250901360959',4,2,'P1735023118','P1735004105',31000,100000,5000,0,'',1,'2025-01-09 09:36:59'),
(10,1,'TRF-250901370936',4,1,'P1735023118','D1735003893',26000,59000,5000,0,'',1,'2025-01-09 09:37:36'),
(11,1,'TRF-250901400911',4,2,'P1735023118','P1735004105',21000,105000,5000,0,'',1,'2025-01-09 09:40:11'),
(12,1,'TRF-250901041029',2,3,'P1735004105','M1735004384',110000,50000,10000,0,'',1,'2025-01-10 09:04:29'),
(13,1,'TRF-252109301238',2,91,'P1735004105','P1757646121',28800,0,10000,0,'',1,'2025-09-12 21:30:38'),
(14,1,'TRF-261001440909',1,2,'D1735003893','P1735004105',138180,800,30000,0,'tes',1,'2026-01-09 10:44:09'),
(15,1,'TRF-261001450958',2,1,'P1735004105','D1735003893',30800,108180,10000,0,'oke',1,'2026-01-09 10:45:58');
/*!40000 ALTER TABLE `transaksi_saldo` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `voucher` varchar(20) NOT NULL,
  `tipe_voucher` char(1) NOT NULL,
  `untuk_fitur` int(11) NOT NULL,
  `tanggal_expired` date NOT NULL,
  `nilai` int(11) NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `count_to_use` int(11) NOT NULL,
  `is_valid` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES
(30,'DISKON','1',12,'2020-01-31',0,'Discount',0,'yes'),
(31,'DISKON','1',13,'2020-01-31',0,'Discount',0,'yes'),
(43,'DISKON','1',0,'2020-01-31',0,'Discount',0,'yes'),
(44,'DISKON','1',0,'2020-01-31',10,'Discount',0,'yes');
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` varchar(20) NOT NULL,
  `uuid` varchar(250) NOT NULL,
  `invoice` varchar(250) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `bank` varchar(250) NOT NULL,
  `nama_pemilik` varchar(500) NOT NULL,
  `rekening` varchar(250) NOT NULL,
  `tujuan` varchar(250) DEFAULT '-',
  `waktu` datetime NOT NULL DEFAULT current_timestamp(),
  `type` varchar(500) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1,
  `foto_bukti` varchar(200) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES
(1,'M1735004384','','',50000,'admin','demo','admin','-','2024-12-24 01:41:48','topup',1,''),
(2,'D1735003893','','',50000,'admin','demo','admin','-','2024-12-24 01:45:08','topup',1,''),
(3,'D1735003893','','',350,'OgotSend','demo','wallet','-','2024-12-24 03:29:41','Order-',1,''),
(4,'D1735003893','','',1650,'OgotSampah','demo','wallet','-','2024-12-24 06:45:25','Order-',1,''),
(5,'D1735025353','','',50000,'admin','ego','admin','-','2024-12-24 07:34:20','topup',1,''),
(6,'D1735025353','','',720,'OgotJek','ego','wallet','-','2024-12-24 07:35:35','Order-',1,''),
(7,'P1735004105','','',100000,'admin','demo','admin','-','2024-12-25 02:22:26','topup',1,''),
(8,'P1735092903','','',1000000,'admin','arfan123','admin','-','2024-12-25 02:23:28','topup',1,''),
(9,'M1735004384','','',1500,'OgotFood','demo','wallet','-','2025-01-10 06:45:22','Order-',1,''),
(10,'D1735025353','','',200,'OgotFood','ego','wallet','-','2025-01-10 06:45:28','Order-',1,''),
(11,'D1735003893','','',720,'OgotJek','demo','wallet','-','2025-03-06 07:05:31','Order-',1,''),
(12,'P1735004105','','',6700,'OgotJek','demo','wallet','-','2025-03-06 07:07:56','Order-',1,''),
(13,'D1735003893','','',720,'OgotJek','demo','wallet','-','2025-03-06 07:07:56','Order-',1,''),
(14,'D1735003893','','',7200,'OgotJek','demo','wallet','-','2025-03-06 07:07:56','Order+',1,''),
(15,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-06 14:51:35','Order-',1,''),
(16,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-06 15:07:34','Order-',1,''),
(17,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-06 15:11:28','Order-',1,''),
(18,'D1735003893','','',1000,'OgotJek','demo1','wallet','-','2025-03-06 15:24:19','Order-',1,''),
(19,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-06 15:25:24','Order-',1,''),
(20,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-07 03:16:48','Order-',1,''),
(21,'D1735003893','','',800,'OgotJek','demo1','wallet','-','2025-03-07 04:29:30','Order-',1,''),
(22,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-03-25 15:24:50','Order-',1,''),
(23,'P1735004105','','',1000,'sistem','demo','sistem','-','2025-03-25 15:25:24','tip',1,''),
(24,'D1735003893','','',1350,'OgotJek','demo1','wallet','-','2025-04-16 11:23:49','Order-',1,''),
(25,'D1735003893','','',12000,'demo','demo1','1233356','-','2025-04-16 15:17:33','withdraw',1,''),
(26,'M1735004384','','',10000,'BRI','sansan','2536854217758','-','2025-04-16 15:33:55','withdraw',1,''),
(27,'P1735004105','','',10000,'Saldo','demo','wallet','-','2025-07-11 06:44:27','donasi-',1,''),
(28,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-07-19 16:07:58','Order-',1,''),
(29,'P1735004105','','',1000,'sistem','demo','sistem','-','2025-07-19 16:10:57','tip',1,''),
(30,'D1735025353','','',50000,'admin','ego','admin','-','2025-07-19 16:42:46','topup',1,''),
(32,'D1752996584','','',50000,'admin','geo','admin','-','2025-07-20 07:36:31','topup',1,''),
(33,'D1735025353','','',720,'OgotJek','ego','wallet','-','2025-07-20 16:47:22','Order-',1,''),
(34,'D1735025353','','',720,'OgotJek','ego','wallet','-','2025-07-20 16:52:08','Order-',1,''),
(35,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-07-20 17:17:30','Order-',1,''),
(36,'P1735004105','','',1000,'sistem','demo','sistem','-','2025-07-20 17:17:44','tip',1,''),
(37,'P1735004105','','',16500,'OgotSend','demo','wallet','-','2025-07-20 17:23:16','Order-',1,''),
(38,'D1735003893','','',1700,'OgotSend','demo1','wallet','-','2025-07-20 17:23:16','Order-',1,''),
(39,'D1735003893','','',17000,'OgotSend','demo1','wallet','-','2025-07-20 17:23:16','Order+',1,''),
(40,'P1735004105','','',1000,'sistem','demo','sistem','-','2025-07-20 17:23:25','tip',1,''),
(42,'D1735003893','','',300,'OgotFood','demo1','wallet','-','2025-07-20 17:31:09','Order-',1,''),
(43,'P1735004105','','',1000,'sistem','demo','sistem','-','2025-07-20 17:31:20','tip',1,''),
(45,'D1735003893','','',300,'OgotFood','demo1','wallet','-','2025-07-20 17:36:08','Order-',1,''),
(46,'D1735003893','','',5000,'OgotCar','demo1','wallet','-','2025-07-20 17:40:16','Order-',1,''),
(47,'D1735003893','','',30000,'admin','Target','admin','-','2025-07-23 15:11:05','redeem',1,''),
(48,'P1735004105','','',12000,'Saldo','demo','wallet','-','2025-07-23 18:33:20','donasi-',1,''),
(49,'D1735003893','','',1000,'admin','ChassBack','admin','-','2025-07-23 19:06:39','redeem',1,''),
(51,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-07-24 06:03:51','Order-',1,''),
(52,'D1735025353','','',1350,'OgotSend','ego','wallet','-','2025-07-24 15:25:50','Order-',1,''),
(53,'D1735025353','','',750,'OgotJek','ego','wallet','-','2025-07-24 15:50:24','Order-',1,''),
(54,'D1735003893','','',1850,'OgotSend','demo1','wallet','-','2025-07-24 15:56:22','Order-',1,''),
(55,'D1735003893','','',720,'OgotJek','demo1','wallet','-','2025-07-24 16:02:54','Order-',1,''),
(56,'D1735025353','','',1350,'OgotSend','ego','wallet','-','2025-07-24 16:06:33','Order-',1,''),
(58,'P1735004105','','',1000,'Saldo','demo','wallet','-','2025-07-26 13:33:40','donasi-',1,''),
(59,'P1735004105','','',10000,'Saldo','demo','wallet','-','2025-07-26 13:34:54','donasi-',1,''),
(60,'D1735025353','','',0,'OgotJek','ego','wallet','-','2025-07-27 09:19:51','Order-',1,''),
(61,'D1735025353','','',0,'OgotJek','ego','wallet','-','2025-07-28 04:08:19','Order-',1,''),
(62,'P1753033513','','',25000,'admin','test','admin','-','2025-07-28 04:09:47','topup',1,''),
(63,'D1735025353','','',0,'OgotJek','ego','wallet','-','2025-07-28 04:16:28','Order-',1,''),
(64,'P1753033513','','',1000,'sistem','test','sistem','-','2025-07-28 04:16:40','tip',1,''),
(65,'D1754050053','','',50000,'admin','Ojek Endru ','admin','-','2025-08-19 07:32:17','topup',1,''),
(66,'M1754040626','','',50000,'admin','Endru','admin','-','2025-08-19 07:56:05','topup',1,''),
(67,'P1735004105','','',10000,'Saldo','demo','wallet','-','2025-08-19 11:16:54','donasi-',1,''),
(68,'M1735004384','','',20000,'test','test','123','-','2025-09-12 10:33:07','withdraw',0,''),
(69,'M1735004384','','',20000,'a','a','122','-','2025-09-12 10:35:04','withdraw',0,''),
(70,'M1735004384','','',600,'OgotFood','demo','wallet','-','2025-09-12 12:55:56','Order-',1,''),
(71,'D1735003893','','',100,'OgotFood','demo1','wallet','-','2025-09-12 12:56:01','Order-',1,''),
(72,'M1735004384','','',600,'OgotFood','demo','wallet','-','2025-09-12 13:34:07','Order-',1,''),
(73,'D1735003893','','',100,'OgotFood','demo1','wallet','-','2025-09-12 13:34:12','Order-',1,''),
(74,'M1735004384','','',500,'OgotFood','demo','wallet','-','2025-09-12 13:50:12','Order-',1,''),
(75,'D1735003893','','',100,'OgotFood','demo1','wallet','-','2025-09-12 13:50:17','Order-',1,''),
(76,'M1735004384','','',500,'OgotFood','demo','wallet','-','2025-09-12 14:00:52','Order-',1,''),
(77,'D1735003893','','',100,'OgotFood','demo1','wallet','-','2025-09-12 14:00:57','Order-',1,''),
(78,'M1735004384','','',1000,'OgotFood','demo','wallet','-','2025-09-13 07:10:06','Order-',1,''),
(79,'D1735003893','','',100000,'OgotFood','demo1','wallet','-','2025-09-13 07:10:11','Order-',1,''),
(80,'D1735003893','','',30000,'admin','Target','admin','-','2025-09-13 07:13:27','redeem',1,''),
(81,'D1735003893','','',1000,'admin','ChassBack','admin','-','2025-09-13 07:14:15','redeem',1,''),
(82,'M1735004384','','',1000,'OgotFood','demo','wallet','-','2025-09-13 07:20:44','Order-',1,''),
(83,'P1735004105','','',14500,'OgotFood','demo','wallet','-','2025-09-13 07:20:49','Order-',1,''),
(84,'D1735003893','','',100000,'OgotFood','demo1','wallet','-','2025-09-13 07:20:49','Order-',1,''),
(85,'D1735003893','','',15000,'OgotFood','demo1','wallet','-','2025-09-13 07:20:49','Order+',1,''),
(86,'M1757916707','','',20000,'admin','coba','admin','-','2025-09-15 06:14:46','topup',1,''),
(87,'M1735004384','','',20000,'dextrans','wallet','ID_DANA','-','2025-09-15 06:30:59','topup',1,''),
(88,'M1735004384','','',50000,'dextrans','wallet','ID_OVO','-','2025-09-15 06:31:36','topup',1,''),
(89,'D1735003893','','',20000,'dextrans','wallet','ID_OVO','-','2025-09-15 06:34:41','topup',1,''),
(90,'P1741272299','','',20000,'admin','tosin','admin','-','2025-12-14 02:42:32','topup',1,''),
(91,'D1735003893','','',80000,'admin','demo1','admin','-','2025-12-14 10:49:18','topup',1,''),
(92,'P1735004105','','',1000,'Saldo','demo','wallet','-','2026-01-08 13:53:51','donasi-',1,''),
(93,'P1735004105','','',1500,'Saldo','demo','wallet','-','2026-01-08 13:54:18','donasi-',1,''),
(94,'D1735003893','','',0,'OgotJek','demo1','wallet','-','2026-01-09 03:11:53','Order-',1,''),
(95,'D1735003893','','',0,'OgotJek','demo1','wallet','-','2026-01-09 03:38:16','Order-',1,''),
(96,'D1735003893','','',2100,'OgotSend','demo1','wallet','-','2026-01-09 03:41:35','Order-',1,''),
(97,'P1735004105','','',1000,'sistem','demo','sistem','-','2026-01-09 03:42:15','tip',1,''),
(98,'M1735004384','','',1000,'OgotFood','demo','wallet','-','2026-01-09 03:58:18','Order-',1,''),
(99,'D1735003893','','',360000,'OgotFood','demo1','wallet','-','2026-01-09 03:58:23','Order-',1,''),
(100,'P1735004105','','',20000,'Saldo','demo','wallet','-','2026-01-09 04:00:41','donasi-',1,''),
(101,'M1735004384','','',100000,'BNI','tes','123','-','2026-01-09 04:04:16','withdraw',1,''),
(102,'D1735003893','','',300000,'admin','demo1','admin','-','2026-01-09 04:08:12','topup',1,''),
(103,'M1735004384','','',1200,'OgotFood','demo','wallet','-','2026-01-09 04:12:03','Order-',1,''),
(104,'D1735003893','','',730000,'OgotFood','demo1','wallet','-','2026-01-09 04:12:08','Order-',1,''),
(105,'D1735003893','','',30000,'admin','Target','admin','-','2026-01-09 04:12:46','redeem',1,''),
(106,'P1735004105','','',100000,'admin','demo','admin','-','2026-01-09 04:14:47','topup',1,''),
(107,'P1735004105','','',3000,'sistem','demo','sistem','-','2026-01-09 04:15:24','tip',1,''),
(108,'D1735003893','','',2000000,'admin','demo1','admin','-','2026-01-09 06:33:53','topup',1,''),
(109,'D1775207413','','',1000000,'admin','yus','admin','-','2026-04-10 06:27:22','topup',1,''),
(110,'P1775197589','','',500000,'admin','yus','admin','-','2026-04-10 06:37:45','topup',1,''),
(111,'D1775207413','','',300000,'Bca','Moch yusuf','1070449311','-','2026-04-10 06:39:12','withdraw',1,''),
(112,'D1775207413','','',0,'OgotJek','yus','wallet','-','2026-04-14 12:28:08','Order-',1,''),
(113,'D1775207413','','',0,'OgotJek','yus','wallet','-','2026-04-14 22:40:14','Order-',1,''),
(114,'D1735003893','','',1400,'Travel','demo1','wallet','-','2026-06-02 00:02:08','Order-',1,''),
(115,'D1735003893','','',0,'OgotJek','demo1','wallet','-','2026-06-03 08:43:47','Order-',1,''),
(116,'P1735004105','','',1000,'sistem','demo','sistem','-','2026-06-03 10:16:12','tip',1,''),
(117,'M1735004384','','',2200,'OgotFood','demo','wallet','-','2026-06-03 14:47:19','Order-',1,''),
(118,'D1735003893','','',680000,'OgotFood','demo1','wallet','-','2026-06-03 14:47:22','Order-',1,''),
(119,'P1735004105','','',20000,'dextrans','wallet','ID_OVO','-','2026-06-04 07:08:06','topup',1,'');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Dumping routines for database 'u637179302_ojol'
--
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 DROP FUNCTION IF EXISTS `GetTransaksiHarga` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;

/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 DROP FUNCTION IF EXISTS `HitungTotalBelanja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;

/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2026-06-17 23:29:55
