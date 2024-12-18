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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String tampilkanFormBuatLaporan(Model model) {
        return "mahasiswa/BuatLaporanUmum";
    }

    @PostMapping("/mahasiswa/buat-laporan-umum")
    public String buatLaporanUmum(
            @RequestParam("kategori") String kategori,
            @RequestParam("keterangan") String keterangan,
            @RequestParam(value = "buktiFoto", required = false) MultipartFile buktiFoto, // Ubah ke MultipartFile
            Model model) {

        try {
            // 1. Ambil email user yang login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            // 2. Dapatkan user berdasarkan email
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                model.addAttribute("error", "User tidak ditemukan.");
                return "redirect:/mahasiswa/buat-laporan-umum?error=user-not-found";
            }

            User user = userOptional.get();
            int userId = user.getId();

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);
            if (!mahasiswaOptional.isPresent()) {
                model.addAttribute("error", "Mahasiswa tidak ditemukan.");
                return "redirect:/mahasiswa/buat-laporan-umum?error=mahasiswa-not-found";
            }

            Mahasiswa mahasiswa = mahasiswaOptional.get();
            int mahasiswaId = mahasiswa.getId(); // Mahasiswa ID sinkron dari database mahasiswa

            // 4. Simpan laporan ke dalam tabel laporan_umum
            laporanService.buatLaporanUmum(mahasiswaId, kategori, keterangan, buktiFoto);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Terjadi kesalahan saat membuat laporan.");
            return "redirect:/mahasiswa/buat-laporan-umum?error=exception";
        }

        // Redirect ke daftar laporan setelah berhasil membuat laporan
        return "redirect:/mahasiswa/daftar-laporan";
    }

    @GetMapping("/mahasiswa/daftar-laporan")
    public String daftarLaporanUmum(Model model) {
        try {
            // 1. Ambil email user yang login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            // 2. Dapatkan user berdasarkan email
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                model.addAttribute("error", "User tidak ditemukan.");
                return "redirect:/mahasiswa/daftar-laporan?error=user-not-found";
            }

            User user = userOptional.get();
            int userId = user.getId();

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);
            if (!mahasiswaOptional.isPresent()) {
                model.addAttribute("error", "Mahasiswa tidak ditemukan.");
                return "redirect:/mahasiswa/daftar-laporan?error=mahasiswa-not-found";
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
            model.addAttribute("daftarLaporan", daftarLaporan);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Terjadi kesalahan saat mengambil daftar laporan.");
            return "redirect:/mahasiswa/daftar-laporan?error=exception";
        }

        return "mahasiswa/DaftarLaporanUmum"; // File view: templates/mahasiswa/DaftarLaporanUmum.html
    }
}
