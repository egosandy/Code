package com.rcdriver.dr.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import com.rcdriver.dr.models.PointModel;

public class PointResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<PointModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<PointModel> getData() {
        return data;
    }

    public void setData(List<PointModel> data) {
        this.data = data;
    }
}
