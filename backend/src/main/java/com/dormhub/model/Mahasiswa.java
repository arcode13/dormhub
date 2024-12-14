package com.dormhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)  // Lazy loading for performance
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User table
    private User user;

<<<<<<< HEAD
    @Column(name = "jurusan_id", nullable = false)
    private int jurusanId;
=======
    @ManyToOne(fetch = FetchType.LAZY)  // Lazy loading for performance
    @JoinColumn(name = "jurusan_id", nullable = false) // Foreign key to the Jurusan table
    private Jurusan jurusan;
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802

    @Column(name = "no_kamar", nullable = false)
    private int noKamar;

    @Column(name = "no_kasur", nullable = false)
    private int noKasur;

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }

    public int getNoKamar() {
        return noKamar;
    }

    public void setNoKamar(int noKamar) {
        this.noKamar = noKamar;
    }

    public int getNoKasur() {
        return noKasur;
    }

    public void setNoKasur(int noKasur) {
        this.noKasur = noKasur;
    }
}
