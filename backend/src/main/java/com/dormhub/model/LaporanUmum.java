package com.dormhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "laporan_umum")
public class LaporanUmum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "helpdesk_id", nullable = false)
    private int helpdeskId;

    @Column(name = "mahasiswa_id", nullable = false)
    private int mahasiswaId;

    @Column(name = "jenis", length = 255)
    private String jenis;

    @Column(name = "alasan", nullable = false, length = 255)
    private String alasan;

    @Column(name = "bukti_foto", nullable = false, length = 255)
    private String buktiFoto;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHelpdeskId() {
        return helpdeskId;
    }

    public void setHelpdeskId(int helpdeskId) {
        this.helpdeskId = helpdeskId;
    }

    public int getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(int mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getBuktiFoto() {
        return buktiFoto;
    }

    public void setBuktiFoto(String buktiFoto) {
        this.buktiFoto = buktiFoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
