package com.rcdriver.cs.json;

import java.util.List;

import com.rcdriver.cs.models.ProsesModel;

public class ProsesResponse {
    String kode, pesan;
    List<ProsesModel> result;

    public List<ProsesModel> getResult() {
        return result;
    }

    public void setResult(List<ProsesModel> result) {
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
        this.pesan = pesan;
    }
}
