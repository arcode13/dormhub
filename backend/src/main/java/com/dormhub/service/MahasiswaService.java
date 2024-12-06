package com.dormhub.service;

import com.dormhub.model.Mahasiswa;
import com.dormhub.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public Mahasiswa findByUserId(int userId) {
        return mahasiswaRepository.findByUserId(userId);
    }
}
