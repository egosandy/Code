package com.rcdriver.cs.json;

import java.util.List;

import com.rcdriver.cs.models.JobModels;

public class JobResponse {
    String kode, pesan;
    List<JobModels> result;

    public List<JobModels> getResult() {
        return result;
    }

    public void setResult(List<JobModels> result) {
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