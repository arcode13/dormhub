package com.dormhub.controller;

import com.dormhub.model.SeniorResidence;
import com.dormhub.model.User;
import com.dormhub.repository.UserRepository;
import com.dormhub.service.SeniorResidenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class InfoMahasiswaController {

    @Autowired
    private SeniorResidenceService seniorResidenceService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/senior-residence/info-mahasiswa")
    public String infoMahasiswa(Model model, RedirectAttributes redirectAttributes) {
        
        // Mendapatkan email pengguna yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        // Mencari user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int userId = user.getId();
            
            // Ambil data Senior Residence
            SeniorResidence seniorResidence = seniorResidenceService.getSeniorResidenceByMahasiswaId(userId).orElse(null);
            if (seniorResidence == null) {
                redirectAttributes.addFlashAttribute("error", "Senior Residence tidak ditemukan.");
                return "redirect:/logout";
            }

            model.addAttribute("user", user);
        } else {
            redirectAttributes.addFlashAttribute("error", "User tidak ditemukan.");
        }

        return "senior-residence/InfoMahasiswa";
    }
}
