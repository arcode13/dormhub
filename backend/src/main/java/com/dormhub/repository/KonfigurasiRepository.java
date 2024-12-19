package com.dormhub.repository;

import com.dormhub.model.Konfigurasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface KonfigurasiRepository extends JpaRepository<Konfigurasi, Integer> {

    @Query("SELECT k.kValue FROM Konfigurasi k WHERE k.kKey = ?1")
    Optional<String> findKValueByKKey(String kKey);
}
