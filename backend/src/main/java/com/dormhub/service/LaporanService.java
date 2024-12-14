package com.dormhub.service;

<<<<<<< HEAD
import com.dormhub.model.Mahasiswa;
import com.dormhub.repository.LaporanUmumRepository;
import com.dormhub.repository.MahasiswaRepository;
=======
import com.dormhub.repository.LaporanRepository;
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaporanService {

    @Autowired
<<<<<<< HEAD
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
=======
    private LaporanRepository laporanRepository;

    public int countLaporanByMahasiswaAndJenis(int mahasiswaId, String jenis) {
        return laporanRepository.countByMahasiswaIdAndJenis(mahasiswaId, jenis);
    }

    public int countTotalLaporan(int mahasiswaId) {
        return laporanRepository.countByMahasiswaId(mahasiswaId);
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
    }
}
