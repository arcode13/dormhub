package com.dormhub.repository;

import com.dormhub.model.LaporanBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaporanBarangRepository extends JpaRepository<LaporanBarang, Integer> {

}
