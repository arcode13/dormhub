package com.dormhub.repository;

import com.dormhub.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Integer> {

    /**
     * Find the last room number or return 100 if no rooms exist.
     *
     * @return Last room number.
     */
    @Query("SELECT COALESCE(MAX(m.noKamar), 100) FROM Mahasiswa m")
    int findLastRoomNumber();

    /**
     * Count the number of occupants in a specific room.
     *
     * @param noKamar Room number.
     * @return Count of occupants.
     */
    @Query("SELECT COUNT(m) FROM Mahasiswa m WHERE m.noKamar = ?1")
    int countOccupantsInRoom(int noKamar);

    /**
     * Find Mahasiswa by User ID.
     *
     * @param userId User ID.
     * @return Mahasiswa object.
     */
    Mahasiswa findByUserId(int userId);
}
