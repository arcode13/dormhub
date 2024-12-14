package com.dormhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "laporan")
public class Laporan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @Column(name = "jenis", nullable = false)
    private String jenis;

    @Column(name = "status", nullable = false)
    private String status;

    // Getters and Setters
}
