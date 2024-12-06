package com.dormhub.service;

import com.dormhub.model.LaporanUmum;
import com.dormhub.repository.LaporanUmumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaporanUmumService {

    @Autowired
    private LaporanUmumRepository laporanUmumRepository;

    // Mendapatkan semua laporan umum
    public List<LaporanUmum> getAllLaporan() {
        return laporanUmumRepository.findAll();
    }

    // Mendapatkan laporan berdasarkan mahasiswaId
    public List<LaporanUmum> getLaporanByMahasiswaId(int mahasiswaId) {
        return laporanUmumRepository.findByMahasiswaId(mahasiswaId);
    }
}
