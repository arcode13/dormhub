package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.repository.MahasiswaRepository;
import com.dormhub.repository.UserRepository;
import com.dormhub.service.LaporanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Optional;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private LaporanService laporanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @GetMapping("/mahasiswa/dashboard")
    public String mahasiswaDashboard(Model model) {
        // Log awal masuk ke metode
        logger.info("Masuk ke metode mahasiswaDashboard.");

        // Ambil email dari user yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.debug("Email pengguna yang login: {}", email);

        // Cari user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int userId = user.getId();
            logger.debug("User ditemukan dengan ID: {}", userId);

            // Ambil jumlah laporan berdasarkan mahasiswa_id
            int jumlahLaporanIzin = laporanService.getJumlahLaporanIzinBulanIni(userId);
            int jumlahLaporanKeluhan = laporanService.getJumlahLaporanKeluhanBulanIni(userId);
            int totalLaporan = jumlahLaporanIzin + jumlahLaporanKeluhan;

            logger.debug("Jumlah laporan izin: {}", jumlahLaporanIzin);
            logger.debug("Jumlah laporan keluhan: {}", jumlahLaporanKeluhan);
            logger.debug("Total laporan: {}", totalLaporan);

            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
                model.addAttribute("isCheckin", mahasiswa.getIsCheckin() == 1);
                model.addAttribute("isCheckout", mahasiswa.getIsCheckout() == 1);
            }

            String ucapan = getUcapan();
            model.addAttribute("namaMahasiswa", user.getNamaLengkap());
            model.addAttribute("ucapan", ucapan);
            model.addAttribute("laporanIzinBulanIni", jumlahLaporanIzin);
            model.addAttribute("laporanKeluhanBulanIni", jumlahLaporanKeluhan);
            model.addAttribute("totalLaporan", totalLaporan);
        } else {
            logger.warn("User dengan email {} tidak ditemukan.", email);
            model.addAttribute("error", "User tidak ditemukan.");
        }

        // Log akhir metode
        logger.info("Selesai menjalankan metode mahasiswaDashboard.");
        return "mahasiswa/dashboard";
    }

    private String getUcapan() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.NOON)) {
            return "Selamat Pagi";
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            return "Selamat Siang";
        } else {
            return "Selamat Malam";
        }
    }

    @GetMapping("/mahasiswa/checkin")
    public String handleCheckin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(user.getId());
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
                mahasiswa.setIsCheckin(1); // Update check-in status
                mahasiswaRepository.save(mahasiswa);
            }
        }

        return "redirect:/mahasiswa/dashboard";
    }

    @GetMapping("/mahasiswa/checkout")
    public String handleCheckout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(user.getId());
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
                mahasiswa.setIsCheckout(1); // Update check-out status
                mahasiswaRepository.save(mahasiswa);
            }
        }

        return "redirect:/mahasiswa/dashboard";
    }

    @GetMapping("/help-desk/dashboard")
    public String helpDeskDashboard(Model model) {
        // Log awal masuk ke metode
        logger.info("Masuk ke metode mahasiswaDashboard.");

        // Ambil email dari user yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        logger.debug("Email pengguna yang login: {}", email);

        // Cari user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int userId = user.getId();
            logger.debug("User ditemukan dengan ID: {}", userId);

            String ucapan = getUcapan();
            model.addAttribute("namaHelpDesk", user.getNamaLengkap());
            model.addAttribute("ucapan", ucapan);
        } else {
            logger.warn("User dengan email {} tidak ditemukan.", email);
            model.addAttribute("error", "User tidak ditemukan.");
        }

        return "help-desk/dashboard";
    }

}
