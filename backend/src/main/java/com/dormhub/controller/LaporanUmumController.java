package com.dormhub.controller;

import com.dormhub.model.LaporanUmum;
import com.dormhub.service.LaporanUmumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LaporanUmumController {

    @Autowired
    private LaporanUmumService laporanUmumService;

    @GetMapping("/mahasiswa/daftarlaporanumum")
    public String daftarLaporan(Model model) {
        // Ambil semua laporan dari service
        List<LaporanUmum> laporanList = laporanUmumService.getAllLaporan();

        // Tambahkan data ke model
        model.addAttribute("laporanList", laporanList);

        // Return nama view (HTML)
        return "mahasiswa/daftarlaporanumum"; // Pastikan template sesuai dengan path
    }
}
