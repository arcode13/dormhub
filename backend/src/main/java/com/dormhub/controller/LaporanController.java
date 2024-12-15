package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.service.LaporanService;
import com.dormhub.repository.UserRepository;
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

import java.util.Optional;

@Controller
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

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

        // 1. Ambil email user yang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // 2. Dapatkan user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int userId = user.getId();

            // 3. Dapatkan mahasiswa_id berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(userId);

            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
                int mahasiswaId = mahasiswa.getId();

                try {
                    // 4. Simpan laporan ke dalam tabel laporan_umum
                    laporanService.buatLaporanUmum(1, mahasiswaId, kategori, keterangan, buktiFoto);
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("error", "Gagal mengunggah file gambar.");
                    return "redirect:/mahasiswa/buat-laporan-umum?error=file-upload-failed";
                }
            } else {
                model.addAttribute("error", "Mahasiswa tidak ditemukan.");
                return "redirect:/mahasiswa/buat-laporan-umum?error=mahasiswa-not-found";
            }
        } else {
            model.addAttribute("error", "User tidak ditemukan.");
            return "redirect:/mahasiswa/buat-laporan-umum?error=user-not-found";
        }

        // Redirect ke daftar laporan setelah berhasil membuat laporan
        return "redirect:/mahasiswa/daftar-laporan";
    }
}
