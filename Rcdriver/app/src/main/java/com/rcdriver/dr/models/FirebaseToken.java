package com.rcdriver.dr.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FirebaseToken extends RealmObject {
    @PrimaryKey
    private String token;

    public FirebaseToken() {}

    public FirebaseToken(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
