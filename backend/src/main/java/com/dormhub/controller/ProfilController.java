package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.repository.MahasiswaRepository;
import com.dormhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/mahasiswa")
public class ProfilController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String IMAGE_DIR = "src/main/resources/static/assets/images/users/";

    @GetMapping("/profil")
    public String profilPage(Principal principal, Model model) {
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
        model.addAttribute("currentTimeMillis", System.currentTimeMillis());
        return "mahasiswa/Profil";
    }

    @PostMapping("/profil/ubah-foto")
    public String ubahFotoProfil(@RequestParam("fotoProfil") MultipartFile fotoProfil, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        try {
            if (!fotoProfil.isEmpty()) {
                // Validasi ukuran file (maksimal 2 MB)
                long maxFileSize = 2 * 1024 * 1024; // 2 MB
                if (fotoProfil.getSize() > maxFileSize) {
                    redirectAttributes.addFlashAttribute("error", "Ukuran file maksimal 2 MB.");
                    return "redirect:/mahasiswa/profil";
                }
    
                // Validasi format file
                String contentType = fotoProfil.getContentType();
                if (!contentType.equalsIgnoreCase("image/jpeg") &&
                    !contentType.equalsIgnoreCase("image/jpg") &&
                    !contentType.equalsIgnoreCase("image/png")) {
                    redirectAttributes.addFlashAttribute("error", "Format file hanya boleh JPG, JPEG, atau PNG.");
                    return "redirect:/mahasiswa/profil";
                }
    
                // Buat direktori jika belum ada
                File directory = new File(IMAGE_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
    
                // Generate nama file unik
                String fileName = System.currentTimeMillis() + "_" + fotoProfil.getOriginalFilename().replaceAll("\\s+", "_");
    
                // Simpan file ke direktori
                Path filePath = Paths.get(IMAGE_DIR + fileName);
                Files.write(filePath, fotoProfil.getBytes());
    
                // Hapus foto lama jika ada
                String oldFotoProfil = user.getFotoProfil();
                if (oldFotoProfil != null) {
                    File oldFile = new File(IMAGE_DIR + oldFotoProfil);
                    oldFile.delete();
                }
    
                // Update foto profil di database
                user.setFotoProfil(fileName);
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
            }
    
            redirectAttributes.addFlashAttribute("success", "Foto Profil berhasil diubah");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan saat mengunggah foto profil.");
        }
    
        return "redirect:/mahasiswa/profil";
    }    

    @PostMapping("/profil/ubah-data")
    public String ubahDataProfil(
            @RequestParam("namaLengkap") String namaLengkap,
            @RequestParam("nomorHp") String nomorHp,
            @RequestParam("jenisKelamin") String jenisKelamin,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        String currentEmail = principal.getName();
        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        // Validasi nama lengkap
        if (!namaLengkap.matches("^[a-zA-Z\\s]+$")) {
            redirectAttributes.addFlashAttribute("error", "Nama lengkap hanya boleh berisi huruf dan spasi.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Validasi nomor HP
        if (!nomorHp.matches("^[0-9]{10,13}$")) {
            redirectAttributes.addFlashAttribute("error", "Nomor HP harus berisi 10 hingga 13 angka.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Validasi jenis kelamin
        if (!jenisKelamin.equalsIgnoreCase("Laki-Laki") && !jenisKelamin.equalsIgnoreCase("Perempuan")) {
            redirectAttributes.addFlashAttribute("error", "Jenis kelamin tidak valid.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Update data user
        user.setNamaLengkap(namaLengkap);
        user.setNomorHp(nomorHp);
        user.setJenisKelamin(jenisKelamin);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Profil berhasil diubah");
        return "redirect:/mahasiswa/profil";
    }

    @PostMapping("/profil/ubah-password")
    public String ubahPassword(
            @RequestParam("passwordLama") String passwordLama,
            @RequestParam("passwordBaru") String passwordBaru,
            @RequestParam("konfirmasiPasswordBaru") String konfirmasiPasswordBaru,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        // Validasi password lama
        if (!passwordEncoder.matches(passwordLama, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Password saat ini salah.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Validasi panjang password baru
        if (passwordBaru.length() < 5) {
            redirectAttributes.addFlashAttribute("error", "Password baru minimal 5 karakter.");
            return "redirect:/mahasiswa/profil";
        }

        // Validasi panjang password baru
        if (passwordBaru.length() > 12) {
            redirectAttributes.addFlashAttribute("error", "Password baru maksimal 12 karakter.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Validasi konfirmasi password baru
        if (!passwordBaru.equals(konfirmasiPasswordBaru)) {
            redirectAttributes.addFlashAttribute("error", "Konfirmasi password baru tidak sesuai.");
            return "redirect:/mahasiswa/profil";
        }
    
        // Simpan password baru
        user.setPassword(passwordEncoder.encode(passwordBaru));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    
        redirectAttributes.addFlashAttribute("success", "Password berhasil diubah");
        return "redirect:/mahasiswa/profil";
    }    
}
