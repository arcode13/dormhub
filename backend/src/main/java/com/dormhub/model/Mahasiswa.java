package com.dormhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "jurusan_id", nullable = false)
    private int jurusanId;

    @Column(name = "no_kamar", nullable = false)
    private int noKamar;

    @Column(name = "no_kasur", nullable = false)
    private int noKasur;

    @Column(name = "is_checkin", nullable = false)
    private int isCheckin;

    @Column(name = "is_checkout", nullable = false)
    private int isCheckout;

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJurusanId() {
        return jurusanId;
    }

    public void setJurusanId(int jurusanId) {
        this.jurusanId = jurusanId;
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

    public int getIsCheckin() {
        return isCheckin;
    }

    public void setIsCheckin(int isCheckin) {
        this.isCheckin = isCheckin;
    }

    public int getIsCheckout() {
        return isCheckout;
    }

    public void setIsCheckout(int isCheckout) {
        this.isCheckout = isCheckout;
    }
}
