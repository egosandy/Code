package com.rcdriver.dr.json;

import java.util.List;

import com.rcdriver.dr.models.AktivitasModel;

public class AktivitasResponseJson {
    String kode, pesan;
    List<AktivitasModel> result;

    public List<AktivitasModel> getResult() {
        return result;
    }

    public void setResult(List<AktivitasModel> result) {
        this.result = result;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {

    }
}
