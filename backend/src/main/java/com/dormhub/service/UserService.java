package com.dormhub.service;

import com.dormhub.model.Jurusan;
import com.dormhub.model.Level;
import com.dormhub.model.Mahasiswa;
import com.dormhub.model.User;
import com.dormhub.repository.MahasiswaRepository;
import com.dormhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private RoomService roomService; 

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; 

    public String registerUser(User user, Jurusan jurusan) {
        if (!user.getNamaLengkap().matches("^[a-zA-Z\\s]+$")) {
            return "Nama lengkap hanya boleh berisi huruf dan spasi";
        }
    
        if (!user.getNomorHp().matches("^[0-9]{10,13}$")) {
            return "Nomor HP harus berisi 10 hingga 13 digit angka tanpa simbol atau karakter khusus";
        }
    
        if (user.getPassword().length() < 5) {
            return "Password minimal 5 karakter";
        }

        if (user.getPassword().length() > 12) {
            return "Password maksimal 12 karakter";
        }
    
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email sudah terdaftar";
        }

        try {
            LocalDateTime now = LocalDateTime.now();

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Optional<Level> levelOpt = Optional.ofNullable(user.getLevel());
            if (levelOpt.isPresent()) {
                user.setLevel(levelOpt.get());
            } else {
                Level level = new Level();
                level.setId(1L);
                user.setLevel(level);
            }

            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            User savedUser = userRepository.save(user);

            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setUserId(savedUser.getId());
            mahasiswa.setJurusanId(jurusan.getId()); 
            
            int[] roomAndBed = roomService.assignRoom();
            mahasiswa.setNoKamar(roomAndBed[0]);
            mahasiswa.setNoKasur(roomAndBed[1]);
            
            mahasiswa.setIsCheckin(0);
            mahasiswa.setIsCheckout(0);
            mahasiswaRepository.save(mahasiswa);

            return "Berhasil mendaftar";
        } catch (Exception e) {
            logger.error("Terjadi kesalahan saat mendaftar user: ", e);
            return "Terjadi kesalahan saat mendaftar: " + e.getMessage();
        }
    }

    public User authenticate(String email, String password) {
        logger.debug("Mencoba autentikasi untuk email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.debug("User ditemukan: {}", user.getEmail());
            logger.debug("Stored hash: {}", user.getPassword());

            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Login berhasil untuk user: {}", email);
                return user;
            } else {
                logger.warn("Password tidak cocok untuk user: {}", email);
            }
        } else {
            logger.warn("User dengan email {} tidak ditemukan", email);
        }

        return null; 
    }
}
