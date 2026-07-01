package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSaldoResponJson {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("saldoReward")
    @Expose
    private String saldoReward;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldoReward() {
        return saldoReward;
    }

    public void setSaldoReward(String saldoReward) {
        this.saldoReward = saldoReward;
    }
}
