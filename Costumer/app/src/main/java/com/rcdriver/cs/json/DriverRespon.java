package com.rcdriver.cs.json;

import java.util.List;

import com.rcdriver.cs.models.MDriverModel;

public class DriverRespon {
    String kode, pesan;
    List<MDriverModel> result;

    public List<MDriverModel> getResult() {
        return result;
    }

    public void setResult(List<MDriverModel> result) {
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
