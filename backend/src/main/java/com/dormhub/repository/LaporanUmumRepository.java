package com.dormhub.repository;

import com.dormhub.model.LaporanUmum;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LaporanUmumRepository extends JpaRepository<LaporanUmum, Integer> {

    /**
     * Menghitung jumlah laporan izin bulan ini berdasarkan mahasiswa_id.
     *
     * @param mahasiswaId ID mahasiswa
     * @return Jumlah laporan izin bulan ini
     */
    @Query(value = "SELECT COUNT(*) FROM laporan_umum WHERE jenis = 'izin' AND mahasiswa_id = :mahasiswaId AND MONTH(created_at) = MONTH(CURRENT_DATE()) AND YEAR(created_at) = YEAR(CURRENT_DATE())", nativeQuery = true)
    int countLaporanIzin(int mahasiswaId);

    /**
     * Menghitung jumlah laporan keluhan bulan ini berdasarkan mahasiswa_id.
     *
     * @param mahasiswaId ID mahasiswa
     * @return Jumlah laporan keluhan bulan ini
     */
    @Query(value = "SELECT COUNT(*) FROM laporan_umum WHERE jenis = 'keluhan' AND mahasiswa_id = :mahasiswaId AND MONTH(created_at) = MONTH(CURRENT_DATE()) AND YEAR(created_at) = YEAR(CURRENT_DATE())", nativeQuery = true)
    int countLaporanKeluhan(int mahasiswaId);
=======
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaporanUmumRepository extends JpaRepository<LaporanUmum, Integer> {
    // Mengambil laporan berdasarkan mahasiswaId
    List<LaporanUmum> findByMahasiswaId(int mahasiswaId);  

    // Mengambil laporan berdasarkan status
    List<LaporanUmum> findByStatus(String status);  
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
}
