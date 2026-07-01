package com.rcdriver.dr.utils.api.service;

// --- PERUBAHAN 1: Ganti import yang lama ---
import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.rcdriver.dr.utils.Log;

// --- PERUBAHAN 2: Ganti FirebaseInstanceIdService menjadi FirebaseMessagingService ---
public class MyFireBaseInstanceIdService extends FirebaseMessagingService {

    private static final String TAG = MyFireBaseInstanceIdService.class.getSimpleName();

    public MyFireBaseInstanceIdService() {
        super();
    }

    /**
     * Dipanggil ketika token FCM (Firebase Cloud Messaging) yang baru dibuat.
     * Ini terjadi saat:
     * 1. Aplikasi diinstal pertama kali.
     * 2. Pengguna menghapus data aplikasi.
     * 3. Aplikasi di-restore di perangkat baru.
     * 4. Token yang lama sudah tidak valid.
     *
     * @param token Token registrasi yang baru.
     */
    // --- PERUBAHAN 3: Ganti method onTokenRefresh() menjadi onNewToken(String token) ---
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // Panggil method Anda untuk mengirim token ini ke server aplikasi Anda.
        sendRegistrationToServer(token);

        // Jika Anda ingin berlangganan ke suatu topic, lakukan di sini.
        // FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    /**
     * Method untuk mengirim token ke server backend Anda.
     * @param token Token FCM yang baru.
     */
    // --- PERUBAHAN 4: Tambahkan parameter token agar lebih efisien ---
    private void sendRegistrationToServer(String token) {
        // Implementasikan logika Anda di sini untuk mengirim token ke server.
        // Contoh:
        // ApiClient.getApiService().registerToken(token).enqueue(...);
    }
}