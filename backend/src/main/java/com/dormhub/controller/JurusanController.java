package com.dormhub.controller;

import com.dormhub.model.Jurusan;
import com.dormhub.service.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/jurusan")
public class JurusanController {

    @Autowired
    private JurusanService jurusanService;

    // Menampilkan halaman daftar jurusan
    @GetMapping
    public String getAllJurusan(Model model) {
        List<Jurusan> jurusanList = jurusanService.getAllJurusan();
        model.addAttribute("jurusanList", jurusanList); // Mengirim data ke template
        return "admin/Jurusan/index"; // Path ke templates/admin/Jurusan/index.html
    }

    // Menampilkan halaman edit jurusan
    @GetMapping("/edit/{id}")
    public String editJurusanPage(@PathVariable int id, Model model) {
        Jurusan jurusan = jurusanService.findById(id);
        if (jurusan == null) {
            return "redirect:/admin/jurusan"; // Redirect jika data tidak ditemukan
        }
        model.addAttribute("jurusan", jurusan);
        return "admin/Jurusan/edit"; // Path ke templates/admin/Jurusan/edit.html
    }

    // Menyimpan hasil edit jurusan
    @PostMapping("/edit/{id}")
    public String updateJurusan(@PathVariable int id, @ModelAttribute Jurusan updatedJurusan) {
        Jurusan jurusan = jurusanService.findById(id);
        if (jurusan != null) {
            jurusan.setNama(updatedJurusan.getNama());
            jurusanService.saveJurusan(jurusan);
        }
        return "redirect:/admin/jurusan"; // Kembali ke daftar jurusan
    }

    // Menampilkan halaman tambah jurusan
    @GetMapping("/tambah")
    public String tambahJurusanPage(Model model) {
        model.addAttribute("jurusan", new Jurusan());
        return "admin/Jurusan/tambah"; // Path ke templates/admin/Jurusan/tambah.html
    }

    // Menyimpan jurusan baru
    @PostMapping("/tambah")
    public String saveJurusan(@ModelAttribute Jurusan jurusan) {
        jurusanService.saveJurusan(jurusan);
        return "redirect:/admin/jurusan"; // Kembali ke daftar jurusan
    }

    // Menghapus jurusan
    @GetMapping("/delete/{id}")
    public String deleteJurusan(@PathVariable int id) {
        jurusanService.deleteJurusan(id);
        return "redirect:/admin/jurusan"; // Kembali ke daftar jurusan
    }
}
