package com.dormhub.repository;

import com.dormhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // Gunakan tipe data yang sesuai dengan entitas User ID

    boolean existsByEmail(String email);  // Mengecek apakah email sudah terdaftar

    Optional<User> findByEmail(String email);  // Mencari user berdasarkan email

    // Tambahkan query method jika diperlukan
    Optional<User> findByEmailAndLevel_Id(String email, Integer levelId); // Mencari user berdasarkan email dan level

    Optional<User> findByToken(String token);

    // Contoh query dengan kustom SQL jika dibutuhkan
    // @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.level.id = ?2")
    // Optional<User> findUserByEmailAndLevel(String email, Integer levelId);
}
