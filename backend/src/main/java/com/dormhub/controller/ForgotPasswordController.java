package com.dormhub.controller;

import com.dormhub.model.User;
import com.dormhub.repository.UserRepository;
import com.dormhub.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        return "ForgotPassword"; // Pastikan file forgot-password.html ada di src/main/resources/templates
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email tidak ditemukan.");
            return "redirect:/forgot-password";
        }

        User user = userOptional.get();

        // Generate token
        String resetToken = UUID.randomUUID().toString();
        user.setToken(resetToken);
        userRepository.save(user);

        // URL Reset Password
        String resetUrl = "http://localhost:8080/reset-password?token=" + resetToken;

        try {
            emailService.sendResetPasswordEmail(user.getEmail(), resetUrl);
            redirectAttributes.addFlashAttribute("success", "Tautan reset password telah dikirim ke email Anda.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Gagal mengirim email reset password.");
        }

        return "redirect:/forgot-password";
    }
}
