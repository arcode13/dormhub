package com.dormhub.controller;

import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.service.LaporanService;
import com.dormhub.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MahasiswaController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private LaporanService laporanService;

    private static final Logger logger = LoggerFactory.getLogger(MahasiswaController.class);

    @GetMapping("/mahasiswa/dashboard")
    public String mahasiswaDashboard(Model model) {
        // Get logged-in user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            logger.error("User is not authenticated");
            return "redirect:/login"; // Redirect to login page
        }

        // Retrieve mahasiswa data for the user
        Mahasiswa mahasiswa = mahasiswaService.findByUserId(user.getId());
        if (mahasiswa == null) {
            logger.error("Mahasiswa data not found for user with id: " + user.getId());
            model.addAttribute("error", "Mahasiswa data not found");
            return "error"; // Redirect to error page
        }

        // Log mahasiswa data for debugging
        logger.info("Found mahasiswa: " + mahasiswa);

        // Count the reports
        int laporanIzinBulanIni = laporanService.countLaporanByMahasiswaAndJenis(mahasiswa.getId(), "izin");
        int laporanKeluhanBulanIni = laporanService.countLaporanByMahasiswaAndJenis(mahasiswa.getId(), "keluhan");
        int totalLaporan = laporanService.countTotalLaporan(mahasiswa.getId());

        // Log the counts for debugging
        logger.info("Laporan Izin Bulan Ini: " + laporanIzinBulanIni);
        logger.info("Laporan Keluhan Bulan Ini: " + laporanKeluhanBulanIni);
        logger.info("Total Laporan: " + totalLaporan);

        // Add data to the model
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("laporanIzinBulanIni", laporanIzinBulanIni);
        model.addAttribute("laporanKeluhanBulanIni", laporanKeluhanBulanIni);
        model.addAttribute("totalLaporan", totalLaporan);

        return "mahasiswa/dashboard"; // Return the dashboard view
    }
}
