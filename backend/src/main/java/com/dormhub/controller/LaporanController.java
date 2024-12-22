package com.dormhub.controller;

import com.dormhub.model.LaporanUmum;
import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.service.LaporanService;
import com.dormhub.repository.UserRepository;
import com.dormhub.repository.LaporanUmumRepository;
import com.dormhub.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

import java.util.Optional;
import java.util.List;

@Controller
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private LaporanUmumRepository laporanUmumRepository;

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }    

    @GetMapping("/mahasiswa/buat-laporan-umum")
    public String tampilkanFormBuatLaporan(Principal principal, Model model) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(user.getId()).orElse(null);
    
        if (mahasiswa != null) {
            model.addAttribute("isCheckin", mahasiswa.getIsCheckin() == 1);
            model.addAttribute("isCheckout", mahasiswa.getIsCheckout() == 1);
            model.addAttribute("mahasiswa", mahasiswa);
        } else {
            model.addAttribute("isCheckin", false);
            model.addAttribute("isCheckout", false);
            model.addAttribute("mahasiswa", false);
        }
    
        model.addAttribute("user", user);
        return "mahasiswa/BuatLaporanUmum";
    }

    @PostMapping("/mahasiswa/buat-laporan-umum")
    public String buatLaporanUmum(
            @RequestParam("kategori") String kategori,
            @RequestParam("keterangan") String keterangan,
            @RequestParam(value = "buktiFoto", required = false) MultipartFile buktiFoto, // Ubah ke MultipartFile
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            // 1. Ambil email user yang login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            // 2. Dapatkan user berdasarkan email
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "User tidak ditemukan.");
                return "redirect:/mahasiswa/buat-laporan-umum";
            }

            User user = userOptional.get();
            int userId = user.getId();

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);
            if (!mahasiswaOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Mahasiswa tidak ditemukan.");
                return "redirect:/mahasiswa/buat-laporan-umum";
            }

            Mahasiswa mahasiswa = mahasiswaOptional.get();
            int mahasiswaId = mahasiswa.getId();

            // 4. Simpan laporan ke dalam tabel laporan_umum
            String buktiPath = null;

            if (buktiFoto != null && !buktiFoto.isEmpty()) {
                // Validasi file
                String contentType = buktiFoto.getContentType();
                long maxFileSize = 2 * 1024 * 1024; // 2 MB

                if (buktiFoto.getSize() > maxFileSize) {
                    redirectAttributes.addFlashAttribute("error", "Ukuran file maksimal 2 MB");
                    return "redirect:/mahasiswa/buat-laporan-umum";
                }

                if (!contentType.equalsIgnoreCase("image/jpeg") &&
                    !contentType.equalsIgnoreCase("image/jpg") &&
                    !contentType.equalsIgnoreCase("image/png")) {
                    redirectAttributes.addFlashAttribute("error", "Format file hanya boleh JPG, JPEG, atau PNG");
                    return "redirect:/mahasiswa/buat-laporan-umum";
                }

                // Simpan file
                String fileName = System.currentTimeMillis() + "_" + buktiFoto.getOriginalFilename().replaceAll("\\s+", "_");
                Path uploadPath = Path.of("src/main/resources/static/assets/images/laporan-umum/" + fileName);
                Files.write(uploadPath, buktiFoto.getBytes());
                buktiPath = fileName;
            }

            // 5. Panggil service untuk menyimpan laporan
            laporanService.buatLaporanUmum(mahasiswaId, kategori, keterangan, buktiPath);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan saat membuat laporan.");
            return "redirect:/mahasiswa/buat-laporan-umum";
        }

        redirectAttributes.addFlashAttribute("success", "Laporan berhasil dibuat");
        return "redirect:/mahasiswa/daftar-laporan";
    }

    @GetMapping("/mahasiswa/daftar-laporan")
    public String daftarLaporanUmum(Model model, RedirectAttributes redirectAttributes) {
        try {
            // 1. Ambil email user yang login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            // 2. Dapatkan user berdasarkan email
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "User tidak ditemukan.");
                return "redirect:/mahasiswa/daftar-laporan";
            }

            User user = userOptional.get();
            int userId = user.getId();

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);
            if (!mahasiswaOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Mahasiswa tidak ditemukan.");
                return "redirect:/mahasiswa/daftar-laporan";
            }

            Mahasiswa mahasiswa = mahasiswaOptional.get();
            int mahasiswaId = mahasiswa.getId();

            // 4. Dapatkan daftar laporan umum dari database berdasarkan mahasiswa_id
            List<LaporanUmum> daftarLaporan = laporanUmumRepository.findAllByMahasiswaId(mahasiswaId);

            // **Format tanggal ke format Indonesia**
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy - HH:mm", new java.util.Locale("id", "ID"));
            for (LaporanUmum laporan : daftarLaporan) {
                String formattedDate = laporan.getCreatedAt().format(formatter);
                laporan.setStatus(capitalize(laporan.getStatus()));
                laporan.setFormattedCreatedAt(formattedDate);
            }
        
            // 5. Tambahkan daftar laporan ke model agar bisa digunakan di view
            model.addAttribute("isCheckin", mahasiswa.getIsCheckin() == 1);
            model.addAttribute("isCheckout", mahasiswa.getIsCheckout() == 1);
            model.addAttribute("user", user);
            model.addAttribute("daftarLaporan", daftarLaporan);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan saat mengambil daftar laporan.");
            return "redirect:/mahasiswa/daftar-laporan";
        }

        return "mahasiswa/DaftarLaporanUmum";
    }
}
