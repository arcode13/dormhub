package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.repository.MahasiswaRepository;
import com.dormhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class InformasiKamarController {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/mahasiswa/informasi-kamar")
    public String informasiKamar(Model model) {
        
        // Mendapatkan email pengguna yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        // Mencari user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Mencari data mahasiswa berdasarkan user_id
            Optional<Mahasiswa> mahasiswaOptional = mahasiswaRepository.findByUserId(user.getId());
            if (mahasiswaOptional.isPresent()) {
                Mahasiswa mahasiswa = mahasiswaOptional.get();
                
                // Mendapatkan nomor kamar yang dihuni mahasiswa tersebut
                int noKamar = mahasiswa.getNoKamar();
                
                // Mengambil daftar mahasiswa yang satu kamar
                List<Mahasiswa> mahasiswaSekamar = mahasiswaRepository.findByNoKamarWithUser(noKamar);
                
                // Mengirimkan data ke view (HTML)
                model.addAttribute("isCheckin", mahasiswa.getIsCheckin() == 1);
                model.addAttribute("isCheckout", mahasiswa.getIsCheckout() == 1);
                model.addAttribute("user", user);
                model.addAttribute("mahasiswaSekamar", mahasiswaSekamar);
                model.addAttribute("noKamar", mahasiswa.getNoKamar());
            } else {
                model.addAttribute("error", "Mahasiswa tidak ditemukan.");
            }
        } else {
            model.addAttribute("error", "User tidak ditemukan.");
        }

        return "mahasiswa/InformasiKamar";
    }
}
