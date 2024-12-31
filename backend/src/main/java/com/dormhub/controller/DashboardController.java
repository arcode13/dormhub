package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.repository.MahasiswaRepository;
import com.dormhub.repository.HelpDeskRepository;
import com.dormhub.repository.UserRepository;
import com.dormhub.repository.LaporanBarangRepository;
import com.dormhub.service.LaporanService;
import com.dormhub.model.HelpDesk;
import com.dormhub.model.LaporanBarang;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private HelpDeskRepository helpDeskRepository;

    @Autowired
    private LaporanBarangRepository laporanBarangRepository;

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }    

    @GetMapping("/mahasiswa/dashboard")
    public String mahasiswaDashboard(Model model, RedirectAttributes redirectAttributes) {
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
                model.addAttribute("mahasiswa", mahasiswa);
            } else {
                model.addAttribute("isCheckin", false);
                model.addAttribute("isCheckout", false);
                model.addAttribute("mahasiswa", false);
            }

            // Ambil laporan barang dengan status 'menunggu'
            List<LaporanBarang> laporanBarangList = laporanBarangRepository.findByMahasiswaIdAndStatus(userId, "menunggu");

            for (LaporanBarang laporan : laporanBarangList) {
                // Format tanggal
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy - HH:mm", new java.util.Locale("id", "ID"));
                laporan.setFormattedCreatedAt(laporan.getCreatedAt().format(formatter));

                // Ambil nama Help Desk
                Optional<HelpDesk> helpDeskOptional = helpDeskRepository.findById(laporan.getHelpdeskId());
                if (helpDeskOptional.isPresent()) {
                    HelpDesk helpDesk = helpDeskOptional.get();
                    laporan.setNamaLengkap(helpDesk.getUser().getNamaLengkap());
                }
            }

            String ucapan = getUcapan();
            model.addAttribute("user", user);
            model.addAttribute("ucapan", ucapan);
            model.addAttribute("laporanIzinBulanIni", jumlahLaporanIzin);
            model.addAttribute("laporanKeluhanBulanIni", jumlahLaporanKeluhan);
            model.addAttribute("totalLaporan", totalLaporan);
            model.addAttribute("laporanBarangList", laporanBarangList);
        } else {
            logger.warn("User dengan email {} tidak ditemukan.", email);
            redirectAttributes.addFlashAttribute("error", "User tidak ditemukan.");
        }

        // Log akhir metode
        logger.info("Selesai menjalankan metode mahasiswaDashboard.");
        return "mahasiswa/Dashboard";
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
    public String handleCheckin(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
    
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
    
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(user.getId());
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
    
                // Validasi jika sudah check-in
                if (mahasiswa.getIsCheckin() == 1) {
                    redirectAttributes.addFlashAttribute("error", "Anda sudah melakukan check-in sebelumnya.");
                    return "redirect:/mahasiswa/dashboard";
                }
    
                mahasiswa.setIsCheckin(1); // Update status check-in
                user.setUpdatedAt(LocalDateTime.now());
                mahasiswaRepository.save(mahasiswa);
                userRepository.save(user);
    
                redirectAttributes.addFlashAttribute("success", "Berhasil melakukan check-in");
                return "redirect:/mahasiswa/dashboard";
            }
        }
    
        redirectAttributes.addFlashAttribute("error", "Data mahasiswa tidak ditemukan.");
        return "redirect:/mahasiswa/dashboard";
    }
    
    @GetMapping("/mahasiswa/checkout")
    public String handleCheckout(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
    
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
    
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(user.getId());
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
    
                // Validasi jika sudah check-out
                if (mahasiswa.getIsCheckout() == 1) {
                    redirectAttributes.addFlashAttribute("error", "Anda sudah melakukan check-out sebelumnya.");
                    return "redirect:/mahasiswa/dashboard";
                }
    
                mahasiswa.setIsCheckout(1); // Update status check-out
                user.setUpdatedAt(LocalDateTime.now());
                mahasiswaRepository.save(mahasiswa);
                userRepository.save(user);
    
                redirectAttributes.addFlashAttribute("success", "Berhasil melakukan check-out");
                return "redirect:/mahasiswa/dashboard";
            }
        }
    
        redirectAttributes.addFlashAttribute("error", "Data mahasiswa tidak ditemukan.");
        return "redirect:/mahasiswa/dashboard";
    }

    @GetMapping("/mahasiswa/daftar-barang/{id}")
    public String updateStatusLaporanBarang(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<LaporanBarang> laporanBarangOptional = laporanBarangRepository.findById(id);
        if (laporanBarangOptional.isPresent()) {
            LaporanBarang laporanBarang = laporanBarangOptional.get();
            laporanBarang.setStatus("Diterima");
            laporanBarang.setUpdatedAt(LocalDateTime.now());
            laporanBarangRepository.save(laporanBarang);

            // Pesan dinamis berdasarkan jenis barang
            String jenis = laporanBarang.getJenis();
            redirectAttributes.addFlashAttribute("success", "" + jenis + " berhasil diterima.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Barang tidak ditemukan.");
        }
        return "redirect:/mahasiswa/dashboard";
    }

    @GetMapping("/help-desk/dashboard")
    public String helpDeskDashboard(Model model, RedirectAttributes redirectAttributes) {
        // Log awal masuk ke metode
        logger.info("Masuk ke metode helpDeskDashboard.");
    
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
            model.addAttribute("user", user);
            model.addAttribute("ucapan", ucapan);
    
            // Ambil daftar mahasiswa dari repository
            List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
            model.addAttribute("mahasiswaList", mahasiswaList);

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<HelpDesk> helpDeskOptional = helpDeskRepository.findByUserId(userId);
            if (!helpDeskOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Help Desk tidak ditemukan.");
                return "redirect:/logout";
            }

            HelpDesk helpDesk = helpDeskOptional.get();
            int helpDeskId = helpDesk.getId();
            
            List<LaporanBarang> laporanBarangList = laporanBarangRepository.findByHelpdeskId(helpDeskId);

            // **Format tanggal ke format Indonesia**
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy - HH:mm", new java.util.Locale("id", "ID"));
            for (LaporanBarang laporan : laporanBarangList) {
                String formattedDate = laporan.getCreatedAt().format(formatter);
                laporan.setStatus(capitalize(laporan.getStatus()));
                laporan.setFormattedCreatedAt(formattedDate);

                Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findById(laporan.getMahasiswaId());
                if (mahasiswaOptional.isPresent()) {
                    Mahasiswa mahasiswa = mahasiswaOptional.get();
                    laporan.setNamaLengkap(mahasiswa.getUser().getNamaLengkap());
                    laporan.setNoKamar(mahasiswa.getNoKamar());
                }
            }

            model.addAttribute("laporanBarangList", laporanBarangList);
        } else {
            logger.warn("User dengan email {} tidak ditemukan.", email);
            redirectAttributes.addFlashAttribute("error", "User tidak ditemukan.");
        }
    
        return "help-desk/Dashboard"; // Pastikan ini mengarah ke view yang benar
    }

}
