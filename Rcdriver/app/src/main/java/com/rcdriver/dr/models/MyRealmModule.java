package com.rcdriver.dr.models;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {User.class, FirebaseToken.class})
public class MyRealmModule {
}
