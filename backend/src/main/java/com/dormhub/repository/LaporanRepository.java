package com.dormhub.repository;

import com.dormhub.model.Laporan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaporanRepository extends JpaRepository<Laporan, Integer> {

    int countByMahasiswaIdAndJenis(int mahasiswaId, String jenis);  // Hitung laporan berdasarkan jenis
    int countByMahasiswaId(int mahasiswaId);  // Hitung total laporan
}
