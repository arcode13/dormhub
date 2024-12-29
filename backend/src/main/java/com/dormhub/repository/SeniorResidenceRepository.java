package com.dormhub.repository;

import com.dormhub.model.SeniorResidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeniorResidenceRepository extends JpaRepository<SeniorResidence, Integer> {
    // Mencari senior residence berdasarkan mahasiswa_id
    Optional<SeniorResidence> findByMahasiswaId(int mahasiswaId);

    // Mencari senior residence berdasarkan id
    Optional<SeniorResidence> findById(int id);
}
