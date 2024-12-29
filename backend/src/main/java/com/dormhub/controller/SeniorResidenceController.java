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

@Controller
public class SeniorResidenceController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeniorResidenceService seniorResidenceService;

    @GetMapping("/senior-residence/dashboard")
    public String seniorResidenceDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Cari user berdasarkan email
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Pengguna tidak ditemukan.");
            return "error"; // Pastikan template error tersedia
        }

        // Ambil data Senior Residence
        SeniorResidence seniorResidence = seniorResidenceService.getSeniorResidenceByMahasiswaId(user.getId()).orElse(null);
        if (seniorResidence == null) {
            model.addAttribute("error", "Data Senior Residence tidak ditemukan.");
            return "error";
        }

        // Tambahkan data ke model
        model.addAttribute("seniorResidence", seniorResidence);
        model.addAttribute("user", user);

        return "senior-residence/Dashboard"; // Pastikan template tersedia
    }
}
