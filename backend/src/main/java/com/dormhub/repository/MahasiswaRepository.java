package com.dormhub.repository;

import com.dormhub.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Integer> {

    @Query("SELECT COALESCE(MAX(m.noKamar), 100) FROM Mahasiswa m")
    int findLastRoomNumber();

    @Query("SELECT COUNT(m) FROM Mahasiswa m WHERE m.noKamar = ?1")
    int countOccupantsInRoom(int noKamar);

    // Metode baru untuk mencari mahasiswa berdasarkan user_id
    Mahasiswa findByUserId(int userId);
}
