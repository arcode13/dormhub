package com.dormhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dormhub.model.LaporanBarang;
import java.util.List;

@Repository
public interface LaporanBarangRepository extends JpaRepository<LaporanBarang, Integer> {

    // existing methods



    List<LaporanBarang> findByHelpdeskId(int helpdeskId);

}

