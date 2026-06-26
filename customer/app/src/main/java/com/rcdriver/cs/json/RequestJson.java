package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestJson {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("amount")
    @Expose
    private String amount;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
