package com.dormhub.service;

import com.dormhub.model.Mahasiswa;
import com.dormhub.repository.LaporanUmumRepository;
import com.dormhub.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaporanService {

    @Autowired
    private LaporanUmumRepository laporanUmumRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public int getJumlahLaporanIzinBulanIni(int userId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(userId);
        if (mahasiswa == null) {
            throw new IllegalArgumentException("Mahasiswa tidak ditemukan untuk userId: " + userId);
        }
        return laporanUmumRepository.countLaporanIzin(mahasiswa.getId());
    }

    public int getJumlahLaporanKeluhanBulanIni(int userId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(userId);
        if (mahasiswa == null) {
            throw new IllegalArgumentException("Mahasiswa tidak ditemukan untuk userId: " + userId);
        }
        return laporanUmumRepository.countLaporanKeluhan(mahasiswa.getId());
    }
}
