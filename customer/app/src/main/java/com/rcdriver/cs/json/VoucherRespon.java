package com.rcdriver.cs.json;

import java.util.List;

import com.rcdriver.cs.models.VoucherModel;

public class VoucherRespon {
    String kode, pesan;
    List<VoucherModel> result;

    public List<VoucherModel> getResult() {
        return result;
    }

    public void setResult(List<VoucherModel> result) {
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
