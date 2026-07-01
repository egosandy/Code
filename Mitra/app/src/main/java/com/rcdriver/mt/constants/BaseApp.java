package com.rcdriver.mt.constants;

import android.app.Application;
import android.content.Context;
import android.util.Log;

// Import yang benar untuk Firebase Messaging
import com.google.firebase.messaging.FirebaseMessaging;
import com.rcdriver.mt.models.FirebaseToken;
import com.rcdriver.mt.models.User;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class BaseApp extends Application {

    private static final int SCHEMA_VERSION = 0;
    private User loginUser;
    private Realm realmInstance;

    public static BaseApp getInstance(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        // Subscribe to topics
        FirebaseMessaging.getInstance().subscribeToTopic("ouride");
        FirebaseMessaging.getInstance().subscribeToTopic("mitra");

        // Get the token asynchronously
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // When the token is successfully retrieved, save it to Realm
                String fcmToken = task.getResult();
                FirebaseToken token = new FirebaseToken(fcmToken);

                // Perform Realm transaction
                realmInstance = Realm.getDefaultInstance();
                realmInstance.beginTransaction();
                realmInstance.delete(FirebaseToken.class);
                realmInstance.copyToRealm(token);
                realmInstance.commitTransaction();
                Log.d("FCM", "FCM Token saved to Realm.");
            }
        });

        start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        // The fix is here, changing the hyphen to camelCase
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public final Realm getRealmInstance() {
        // Ensure realm instance is available if called before the async task completes
        if (realmInstance == null) {
            realmInstance = Realm.getDefaultInstance();
        }
        return realmInstance;
    }

    private void start() {
        Realm realm = getRealmInstance();
        User user = realm.where(User.class).findFirst();
        if (user != null) {
            setLoginUser(user);
        }
    }
}