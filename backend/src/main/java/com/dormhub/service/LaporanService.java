package com.dormhub.service;

import com.dormhub.repository.LaporanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaporanService {

    @Autowired
    private LaporanRepository laporanRepository;

    public int countLaporanByMahasiswaAndJenis(int mahasiswaId, String jenis) {
        return laporanRepository.countByMahasiswaIdAndJenis(mahasiswaId, jenis);
    }

    public int countTotalLaporan(int mahasiswaId) {
        return laporanRepository.countByMahasiswaId(mahasiswaId);
    }
}
