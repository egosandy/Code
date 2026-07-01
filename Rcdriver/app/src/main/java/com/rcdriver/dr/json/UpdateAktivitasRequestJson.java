package com.rcdriver.dr.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UpdateAktivitasRequestJson {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("aktivitas")
    @Expose
    private String aktivitas;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAktivitas() {
        return aktivitas;
    }
    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }
}
