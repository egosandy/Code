package com.rcdriver.cs.constants;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rcdriver.cs.models.FirebaseToken;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.LocalStore;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class BaseApp extends Application {

    private User loginUser;

    private String deviceToken = null;
    public static BaseApp getInstance(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Local persistence (replaces the former Realm layer).
        LocalStore.init(this);

        FirebaseToken token = new FirebaseToken(FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("gojasa");
        FirebaseMessaging.getInstance().subscribeToTopic("pelanggan");
        LocalStore.get().saveToken(token);

        start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    /** Backing local store (replaces the former getRealmInstance()). */
    public final LocalStore getStore() {
        return LocalStore.get();
    }

    private void start() {
        User user = LocalStore.get().getUser();
        if (user != null) {
            setLoginUser(user);
        }
    }

}
