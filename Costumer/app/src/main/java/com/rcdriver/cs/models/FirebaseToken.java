package com.rcdriver.cs.models;

import io.realm.RealmObject;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class FirebaseToken extends RealmObject {
    private String tokenId;

    public FirebaseToken(String tokenId) {
        this.tokenId = tokenId;
    }

    public FirebaseToken() {
    }

}
