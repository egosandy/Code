// LOKASI: com.asia.pengemudi.utils.api.service.MessagingService.java
package com.rcdriver.dr.utils.api.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils; // Import TextUtils
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.ChatActivity;
import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.activity.NewOrderActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.json.UpdateFcmRequest;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFCM";
    private static final String CHANNEL_ID_ORDER = "order_channel_high_importance";
    private static final String CHANNEL_NAME_ORDER = "Notifikasi Order Baru";
    private static final String CHANNEL_ID_CHAT = "chat_channel";
    private static final String CHANNEL_NAME_CHAT = "Notifikasi Pesan";
    private static final String CHANNEL_ID_GENERAL = "general_channel";
    private static final String CHANNEL_NAME_GENERAL = "Notifikasi Umum";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "TOKEN FCM BARU: " + token);
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "------------------------------------------");
        Log.i(TAG, "onMessageReceived: PESAN BARU DITERIMA");
        Log.i(TAG, "------------------------------------------");
        Log.d(TAG, "   From: " + remoteMessage.getFrom());
        Log.d(TAG, "   Data Payload: " + remoteMessage.getData());

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String type = data.get("type");

            User user = BaseApp.getInstance(this).getLoginUser();
            if (user == null) {
                Log.w(TAG, "   User tidak login, notifikasi diabaikan.");
                return;
            }

            Log.d(TAG, "   Tipe Pesan: " + type);
            if (type != null) {
                switch (type) {
                    case "1": // Orderan Baru
                        Log.d(TAG, "   Memproses sebagai Notifikasi Order Baru (Tipe 1)...");
                        handleNewOrderNotification(data);
                        break;
                    case "2": // Pesan Chat
                        Log.d(TAG, "   Memproses sebagai Notifikasi Chat (Tipe 2)...");
                        handleChatNotification(data);
                        break;
                    default: // Notifikasi lain (cancel, info, dll)
                        Log.d(TAG, "   Memproses sebagai Notifikasi Umum (Tipe " + type + ")...");
                        handleGeneralNotification(data);
                        break;
                }
            } else {
                Log.w(TAG, "   Tipe pesan 'null', memproses sebagai notifikasi umum.");
                handleGeneralNotification(data); // Fallback jika 'type' tidak ada
            }
        } else {
            Log.w(TAG, "   Pesan tidak memiliki data payload, diabaikan.");
        }
    }

    private void handleNewOrderNotification(Map<String, String> data) {
        Log.i(TAG, "handleNewOrderNotification: Mempersiapkan notifikasi pop-up...");

        // --- VALIDASI DATA PENTING ---
        String idTransaksi = data.get("id_transaksi");
        String regIdCustomer = data.get("reg_id");

        Log.d(TAG, "   Mengecek data payload:");
        Log.d(TAG, "      id_transaksi: " + idTransaksi);
        Log.d(TAG, "      reg_id (customer): " + regIdCustomer); // Ini harus ada nilainya!

        if (TextUtils.isEmpty(idTransaksi)) {
            Log.e(TAG, "   GAGAL BUAT NOTIF: id_transaksi tidak ada di payload!");
            return;
        }
        // Peringatan jika regid kosong, karena balasan akan gagal
        if (TextUtils.isEmpty(regIdCustomer) || regIdCustomer.equalsIgnoreCase("null")) {
            Log.w(TAG, "   PERINGATAN: reg_id customer KOSONG atau 'null' di payload! Notifikasi balasan akan GAGAL.");
        }

        // 1. Buat Intent ke NewOrderActivity
        Intent intent = new Intent(this, NewOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        Log.d(TAG, "   Intent ke NewOrderActivity dibuat.");

        // 2. Buat PendingIntent
        int pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentFlags |= PendingIntent.FLAG_IMMUTABLE;
        }
        int requestCode = new Random().nextInt();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlags);
        Log.d(TAG, "   PendingIntent dibuat (Request Code: " + requestCode + ")");


        // 3. Siapkan Suara
        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.orderan);
        Log.d(TAG, "   Sound URI: " + soundUri.toString());

        // 4. Buat Notifikasi
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID_ORDER)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("🔔 Orderan Baru Masuk! 🔔")
                        .setContentText("ID Order: " + idTransaksi + ". Segera respon!")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setFullScreenIntent(pendingIntent, true); // KUNCI POP-UP
        Log.d(TAG, "   NotificationCompat.Builder dibuat dengan FullScreenIntent.");


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            Log.e(TAG, "   GAGAL: Tidak bisa mendapatkan NotificationManager.");
            return;
        }

        // 5. Buat atau Update Channel Notifikasi (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_ID_ORDER) == null) {
                Log.d(TAG, "   Membuat Notification Channel baru: " + CHANNEL_ID_ORDER);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                        .build();
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID_ORDER,
                        CHANNEL_NAME_ORDER,
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Notifikasi prioritas tinggi untuk orderan baru");
                channel.setSound(soundUri, audioAttributes);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{0, 400, 200, 400, 200, 400});
                channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                channel.setBypassDnd(true);

                notificationManager.createNotificationChannel(channel);
                Log.d(TAG, "   Notification Channel BERHASIL dibuat.");
            } else {
                Log.d(TAG, "   Notification Channel ("+CHANNEL_ID_ORDER+") sudah ada.");
            }
        }

        // 6. Tampilkan Notifikasi
        int notificationId = new Random().nextInt();
        try {
            notificationManager.notify(notificationId, notificationBuilder.build());
            Log.i(TAG, "   [✓✓ BERHASIL ✓✓] Menampilkan notifikasi pop-up untuk order ID: " + idTransaksi + " (Notif ID: " + notificationId + ")");
        } catch (SecurityException se) {
            Log.e(TAG, "   [!!! GAGAL !!!] SecurityException! Pastikan izin USE_FULL_SCREEN_INTENT ada di Manifest & diaktifkan di HP.", se);
            showBasicNotificationFallback(data, pendingIntent, soundUri, notificationId);
        } catch (Exception e) {
            Log.e(TAG, "   [!!! GAGAL !!!] Exception umum saat menampilkan notifikasi!", e);
        }
    }

    // Fallback jika FullScreenIntent gagal
    private void showBasicNotificationFallback(Map<String, String> data, PendingIntent pendingIntent, Uri soundUri, int notificationId) {
        Log.w(TAG, "   Menampilkan notifikasi biasa sebagai fallback...");
        NotificationCompat.Builder fallbackBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID_ORDER)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Orderan Baru Masuk! (Fallback)")
                        .setContentText("ID Order: " + data.get("id_transaksi"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, fallbackBuilder.build());
            Log.w(TAG, "   Notifikasi fallback ditampilkan.");
        }
    }

    // Metode untuk menampilkan notifikasi chat (Tipe 2)
    private void handleChatNotification(Map<String, String> data) {
        Log.d(TAG, "handleChatNotification: Menyiapkan notifikasi chat...");
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("senderid", data.get("receiverid"));
        intent.putExtra("receiverid", data.get("senderid"));
        intent.putExtra("name", data.get("name"));
        // intent.putExtra("pic", data.get("pic")); // Aktifkan jika diperlukan

        int pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentFlags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(), intent, pendingIntentFlags);

        String chatMessage = data.get("message");
        if (TextUtils.isEmpty(chatMessage)) {
            chatMessage = "Anda menerima pesan baru.";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_CHAT)
                .setSmallIcon(R.drawable.notif_chat) // Pastikan R.drawable.notif_chat ada
                .setContentTitle(data.get("name")) // Nama pengirim
                .setContentText(chatMessage) // Isi pesan
                .setStyle(new NotificationCompat.BigTextStyle().bigText(chatMessage))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL); // Suara & getar default

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID_CHAT) == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID_CHAT, CHANNEL_NAME_CHAT, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Notifikasi untuk pesan chat baru");
                manager.createNotificationChannel(channel);
            }
        }
        manager.notify(2, builder.build()); // ID notif 2 untuk chat
        Log.d(TAG, "   Notifikasi chat ditampilkan.");
    }

    // Metode untuk notifikasi umum (Tipe 3, 4, default)
    private void handleGeneralNotification(Map<String, String> data) {
        Log.d(TAG, "handleGeneralNotification: Menyiapkan notifikasi umum...");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentFlags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(), intent, pendingIntentFlags);

        String title = data.get("title");
        String body = data.get("body");
        if (TextUtils.isEmpty(body)) body = data.get("message");
        if (TextUtils.isEmpty(title)) title = "Notifikasi";
        if (TextUtils.isEmpty(body)) body = "Anda memiliki pesan baru.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_GENERAL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID_GENERAL) == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID_GENERAL, CHANNEL_NAME_GENERAL, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Notifikasi umum dari aplikasi");
                manager.createNotificationChannel(channel);
            }
        }
        manager.notify(3, builder.build()); // ID notif 3 untuk umum
        Log.d(TAG, "   Notifikasi umum ditampilkan.");
    }


    private void sendRegistrationToServer(String token) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser != null) {
            DriverService service = ServiceGenerator.createService(
                    DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());

            UpdateFcmRequest request = new UpdateFcmRequest();
            request.setId(loginUser.getId());
            request.setToken(token);

            Log.d(TAG, "sendRegistrationToServer: Mengirim token ke server...");
            service.updateFcm(request).enqueue(new Callback<Void>() { // Asumsi nama metodenya updateFcm
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "   Token FCM BERHASIL diupdate ke server.");
                    } else {
                        Log.w(TAG, "   Gagal update token ke server. Kode: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.e(TAG, "   Gagal update token (koneksi bermasalah)", t);
                }
            });
        } else {
            Log.w(TAG, "sendRegistrationToServer: User belum login, pengiriman token ditunda.");
        }
    }
}