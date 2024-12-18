package com.dormhub.service;

import com.dormhub.model.LaporanUmum;
import com.dormhub.model.Mahasiswa;
import com.dormhub.repository.LaporanUmumRepository;
import com.dormhub.repository.MahasiswaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LaporanService {

    private static final String IMAGE_DIR = "src/main/resources/static/assets/images/laporan-umum/";

    @Autowired
    private LaporanUmumRepository laporanUmumRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    // Mengambil jumlah laporan izin berdasarkan mahasiswa_id
    public int getJumlahLaporanIzinBulanIni(int userId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Mahasiswa tidak ditemukan untuk userId: " + userId));
        
        return laporanUmumRepository.countLaporanIzin(mahasiswa.getId());
    }

    // Mengambil jumlah laporan keluhan berdasarkan mahasiswa_id
    public int getJumlahLaporanKeluhanBulanIni(int userId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Mahasiswa tidak ditemukan untuk userId: " + userId));

        return laporanUmumRepository.countLaporanKeluhan(mahasiswa.getId());
    }

    // Membuat laporan umum baru dengan penyimpanan file gambar
    public LaporanUmum buatLaporanUmum(int mahasiswaId, String jenis, String alasan, MultipartFile buktiFoto) throws IOException {
        // Proses penyimpanan file gambar
        String fileName = null;
        if (buktiFoto != null && !buktiFoto.isEmpty()) {
            // Buat direktori jika belum ada
            File directory = new File(IMAGE_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate nama file unik
            fileName = System.currentTimeMillis() + "_" + buktiFoto.getOriginalFilename().replaceAll("\\s+", "_");
            Path filePath = Paths.get(IMAGE_DIR + fileName);
            Files.write(filePath, buktiFoto.getBytes());
        }

        // Simpan data ke database
        LaporanUmum laporan = new LaporanUmum();
        laporan.setMahasiswaId(mahasiswaId);
        laporan.setJenis(jenis);
        laporan.setAlasan(alasan);
        laporan.setBuktiFoto(fileName); // Simpan nama file ke database
        laporan.setStatus("menunggu");
        laporan.setCreatedAt(LocalDateTime.now());
        laporan.setUpdatedAt(LocalDateTime.now());
        return laporanUmumRepository.save(laporan);
    }
}
