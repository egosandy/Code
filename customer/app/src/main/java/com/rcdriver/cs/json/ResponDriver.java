package com.rcdriver.cs.json;


import java.util.List;

import com.rcdriver.cs.models.ResultDriver;

public class ResponDriver {
    String kode, pesan;
    List<ResultDriver> result;

    public List<ResultDriver> getResult() {
        return result;
    }

    public void setResult(List<ResultDriver> result) {
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