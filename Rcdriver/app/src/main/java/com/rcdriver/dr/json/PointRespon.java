package com.rcdriver.dr.json;

import java.util.List;

import com.rcdriver.dr.models.PointModel;

public class PointRespon {
    String kode, pesan;
    List<PointModel> result;

    public List<PointModel> getResult() {
        return result;
    }

    public void setResult(List<PointModel> result) {
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
