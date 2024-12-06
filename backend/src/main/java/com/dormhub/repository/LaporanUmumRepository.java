package com.dormhub.repository;

import com.dormhub.model.LaporanUmum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaporanUmumRepository extends JpaRepository<LaporanUmum, Integer> {
    // Mengambil laporan berdasarkan mahasiswaId
    List<LaporanUmum> findByMahasiswaId(int mahasiswaId);  

    // Mengambil laporan berdasarkan status
    List<LaporanUmum> findByStatus(String status);  
}
