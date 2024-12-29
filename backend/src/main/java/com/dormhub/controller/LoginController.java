package com.dormhub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dormhub.model.User;
import com.dormhub.service.UserService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * Menampilkan halaman loginnnn.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Email atau password salah.");
        }
        return "Login"; // Menampilkan halaman login.html
    }

    /**
     * Proses login pengguna dan pengalihan ke dashboard sesuai level.
     *
     * @param username Email pengguna (atau username jika diubah sesuai Spring Security)
     * @param password Password pengguna
     * @param model    Model untuk menambahkan atribut jika login gagal
     * @return Tampilan yang sesuai (Login atau halaman dashboard)
     */
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes, 
            Model model) {

        logger.debug("Login attempt dengan email: {}", email);

        System.out.println("Email: " + email); // Debug
        System.out.println("Password: " + password); // Debug

        User user = userService.authenticate(email, password);

        if (user != null) {
            logger.info("Login sukses untuk user dengan email: {}", email);
            String level = user.getLevel().getNama();
            logger.debug("User level: {}", level);

            switch (level.toLowerCase()) {
                case "mahasiswa":
                case "senior residence":
                    return "redirect:/mahasiswa/dashboard";
                case "admin":
                    return "redirect:/admin/dashboard";
                case "help desk":
                    return "redirect:/help-desk/dashboard";
                default:
                    redirectAttributes.addFlashAttribute("error", "Level tidak valid.");
                    return "login";
            }
        } else {
            logger.warn("Login gagal untuk email: {}", email);
            redirectAttributes.addFlashAttribute("error", "Email atau password salah.");
            return "login";
        }
    }

}
