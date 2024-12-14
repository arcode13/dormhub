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
import org.springframework.transaction.annotation.Transactional;
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
    private RoomService roomService; // Ensure RoomService is implemented and available

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registers a new user along with associated mahasiswa data.
     *
     * @param user    The user data to be registered.
     * @param jurusan The selected major for the user.
     * @return Success or error message.
     */
    @Transactional
    public String registerUser(User user, Jurusan jurusan) {
        // Validate if email is already registered
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Email already registered: {}", user.getEmail());
            return "Email sudah terdaftar";
        }

        try {
            LocalDateTime now = LocalDateTime.now();

            // Hash password using BCrypt
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Set level for Mahasiswa (if not set, default to "Mahasiswa")
            if (user.getLevel() == null) {
                Level level = new Level();
                level.setId(1L); // Default to "Mahasiswa"
                user.setLevel(level);
            }

            // Set timestamps
            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            // Save user to the database
            User savedUser = userRepository.save(user);

            // Assign room for Mahasiswa
            int[] roomAssignment = roomService.assignRoom();

            // Save Mahasiswa data (associate with the user)
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setUser(savedUser);
            mahasiswa.setJurusan(jurusan);
            mahasiswa.setNoKamar(roomAssignment[0]);
            mahasiswa.setNoKasur(roomAssignment[1]);
            mahasiswaRepository.save(mahasiswa);

            logger.info("User registered successfully: {}", user.getEmail());
            return "Berhasil mendaftar";
        } catch (Exception e) {
            logger.error("Error during user registration: ", e);
            return "Terjadi kesalahan saat mendaftar: " + e.getMessage();
        }
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param email    User email.
     * @param password User password.
     * @return User object if authentication succeeds, null otherwise.
     */
    public User authenticate(String email, String password) {
        logger.debug("Authenticating user: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.debug("User found: {}", user.getEmail());

            // Compare hashed password with provided password
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Authentication successful for user: {}", email);
                return user;
            } else {
                logger.warn("Invalid password for user: {}", email);
            }
        } else {
            logger.warn("User not found for email: {}", email);
        }

        return null;
    }

    /**
     * Finds a user by their email.
     *
     * @param email User email.
     * @return User object or null if not found.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
