package com.dormhub.model;

import jakarta.persistence.*;
<<<<<<< HEAD
import java.time.LocalDateTime;
=======
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802

@Entity
@Table(name = "laporan_umum")
public class LaporanUmum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

<<<<<<< HEAD
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
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helpdesk_id", nullable = false)
    private HelpDesk helpDesk;

    @Column(name = "jenis")
    private String jenis;

    @Column(name = "alasan")
    private String alasan;

    @Column(name = "bukti_foto")
    private String buktiFoto;

    @Column(name = "status")
    private String status;

    // Getters and setters
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

<<<<<<< HEAD
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
=======
    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public HelpDesk getHelpDesk() {
        return helpDesk;
    }

    public void setHelpDesk(HelpDesk helpDesk) {
        this.helpDesk = helpDesk;
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
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
<<<<<<< HEAD

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
=======
>>>>>>> 051d97f0d7862a3decd7b4a47589eae18684a802
}
