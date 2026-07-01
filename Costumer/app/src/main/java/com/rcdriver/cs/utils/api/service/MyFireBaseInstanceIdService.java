package com.rcdriver.cs.utils.api.service;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.rcdriver.cs.utils.Log;

public class MyFireBaseInstanceIdService extends FirebaseInstanceIdService {
    private String TAG = MyFireBaseInstanceIdService.class.getSimpleName();

    public MyFireBaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: token->"+refreshToken);
        sendRegistrationToServer();

    }

    private void sendRegistrationToServer() {

    }
}