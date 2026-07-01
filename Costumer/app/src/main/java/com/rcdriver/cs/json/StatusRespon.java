package com.rcdriver.cs.json;

import java.util.List;

import com.rcdriver.cs.models.StatusModel;

public class StatusRespon {
    String kode, pesan;
    List<StatusModel> result;

    public List<StatusModel> getResult() {
        return result;
    }

    public void setResult(List<StatusModel> result) {
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
