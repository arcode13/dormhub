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
    private RoomService roomService; // Tambahan RoomService untuk logika lantai, kamar, dan kasur

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Memanfaatkan dependency injection untuk BCryptPasswordEncoder

    /**
     * Mendaftarkan pengguna baru
     *
     * @param user Data user dari form pendaftaran
     * @param jurusan Jurusan yang dipilih oleh pengguna
     * @return Pesan sukses atau error
     */
    public String registerUser(User user, Jurusan jurusan) {
        // Validasi email sudah digunakan
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email sudah terdaftar";
        }

        try {
            // Set waktu saat ini
            LocalDateTime now = LocalDateTime.now();

            // Hash password menggunakan BCrypt
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Set level untuk Mahasiswa (ambil dari DB jika perlu)
            Optional<Level> levelOpt = Optional.ofNullable(user.getLevel());
            if (levelOpt.isPresent()) {
                // Set level default jika belum diatur
                user.setLevel(levelOpt.get());
            } else {
                Level level = new Level();
                level.setId(1L); // Ganti dengan level "Mahasiswa" sesuai ID yang ada di DB
                user.setLevel(level);
            }

            // Set waktu pembuatan dan pembaruan
            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            // Simpan user ke database
            User savedUser = userRepository.save(user);

            // Simpan data mahasiswa dengan jurusan
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setUserId(savedUser.getId());
            mahasiswa.setJurusanId(jurusan.getId()); // Hubungkan dengan jurusan
            mahasiswa.setNoKamar(roomService.assignRoom()[0]);
            mahasiswa.setNoKasur(roomService.assignRoom()[1]);
            mahasiswaRepository.save(mahasiswa);

            return "Berhasil mendaftar";
        } catch (Exception e) {
            logger.error("Terjadi kesalahan saat mendaftar user: ", e);
            return "Terjadi kesalahan saat mendaftar: " + e.getMessage();
        }
    }

    /**
     * Autentikasi pengguna berdasarkan email dan password
     *
     * @param email Email pengguna
     * @param password Password pengguna
     * @return User object jika autentikasi berhasil, null jika gagal
     */
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

        return null; // Login gagal
    }

}