package com.dormhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "laporan_umum")
public class LaporanUmum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
