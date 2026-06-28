package com.rcdriver.cs.utils.api.service;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.ActivityProgress;
import com.rcdriver.cs.activity.ChatActivity;
import com.rcdriver.cs.activity.MainActivity;
import com.rcdriver.cs.activity.SplashActivity;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.fcm.DriverResponse;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.SettingPreference;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.google.firebase.messaging.RemoteMessage.PRIORITY_HIGH;

/**
 * Created by Maswend Team on 10/13/2019.
 */

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    public static final String BROADCAST_ACTION = "com.iborjek.cs";
    public static final String BROADCAST_ORDER = "order";
    Intent intent;
    Intent intentOrder;

    private SettingPreference sp;
    NotificationManager notificationManager;
    NotificationCompat.Builder mbuilder;
    private static final String CHANNEL_ID = "Gojasa_Id";
    private static final String CHANNEL_NAME = "Gojasa_Channel";


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
        sp = new SettingPreference(this);
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences("token", MODE_PRIVATE).edit().putString("token", s).apply();
    }
    public static String getToken(Context context) {
        return context.getSharedPreferences("token", MODE_PRIVATE).getString("token", "empty");
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        User user = BaseApp.getInstance(this).getLoginUser();
        if (!remoteMessage.getData().isEmpty() && user != null) {
            parseAndSendMessage(remoteMessage.getData());
        }
        sp.updateTitle(remoteMessage.getData().get("title"));
        sp.updateNotif(remoteMessage.getData().get("message"));
        messageHandler(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        ShowNotification(notification, data);
    }

    private void parseAndSendMessage(Map<String, String> mapResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(mapResponse.isEmpty()){
                Log.d("ParseFcm","Empty");
            }else{
                if(mapResponse != null){
                    for (int i = 0; i < mapResponse.size(); i++) {
                        String type = mapResponse.get("type");

                        if ("1".equals(type)) {
                            DriverResponse response = new DriverResponse();
                            response.setId(mapResponse.get("id_driver"));
                            response.setIdTransaksi(mapResponse.get("id_transaksi"));
                            response.setResponse(mapResponse.get("response"));
                            EventBus.getDefault().postSticky(response);
                        }
                    }
                }
            }
        }
    }
    private void messageHandler(RemoteMessage remoteMessage) {
        try {
            User user = BaseApp.getInstance(this).getLoginUser();

            Map<String, String> data = remoteMessage.getData();
            String type = data.get("type");

            if (type == null) {
                Log.e("FCM", "Key 'type' tidak ditemukan dalam data FCM.");
                return; // Menghentikan eksekusi lebih lanjut jika key "type" tidak ada
            }

            // Periksa data title dan message
            String title = data.get("title");
            String message = data.get("message");

            if (title == null || title.isEmpty()) {
                title = "Notifikasi Masuk";  // Fallback
            }

            if (message == null || message.isEmpty()) {
                message = "Ada pesan baru";  // Fallback
            }

            // Log untuk memastikan data masuk dengan benar
            Log.d("FCM", "Received notification data: title = " + title + ", message = " + message);

            // Lanjutkan dengan pengecekan nilai "type"
            switch (type) {
                case "1":
                    if (user != null) {
                        orderHandler(remoteMessage);
                    }
                    break;
                case "3":
                    if (user != null) {
                        NotifikasiHandle(remoteMessage);
                    }
                    break;
                case "4":
                    NotifikasiHandle2(remoteMessage);
                    break;
                case "2":
                    if (user != null) {
                        chat(remoteMessage);
                    }
                    break;
                case "0":
                    if (user != null) {
                        testrespon(remoteMessage);
                    }
                    break;
                default:
                    // Tampilkan notifikasi dengan title dan message dari data
                    showNotification(title, message);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void NotifikasiHandle(RemoteMessage remoteMessage){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = "Gojasa";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mchannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mchannel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mchannel);
            //------------------------------------------------
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
            mbuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")))
                    .setAutoCancel(true)
                    .setPriority(PRIORITY_HIGH)
                    .setSound(customSoundUri)
                    .setContentIntent(pendingIntent);
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mbuilder.setChannelId(CHANNEL_ID);
            Objects.requireNonNull(notificationManager).notify(0, mbuilder.build());




        }
    }
    private void NotifikasiHandle2(RemoteMessage remoteMessage){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
            intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
            mbuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")))
                    .setAutoCancel(true)
                    .setSound(customSoundUri)
                    .setContentIntent(pendingIntent);
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mbuilder.setChannelId(CHANNEL_ID);
            Objects.requireNonNull(notificationManager).notify(0, mbuilder.build());
        }

    }
    private void orderHandler(RemoteMessage remoteMessage) {
        Bundle data = new Bundle();
        intentToOrder(data);
        if (Objects.equals(remoteMessage.getData().get("response"), "5")) {
            NotifikasiBatal(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "2")) {
            playSound1();
            NotifikasiTerima(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "3")) {
            playSound1();
            NotifikasiMulai(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "4")) {
            playSound1();
            NotifikasiSelesai(remoteMessage);
        }else if (Objects.equals(remoteMessage.getData().get("response"), "9")) {
            playSound1();
            NotifikasiPending(remoteMessage);
        }else if (Objects.equals(remoteMessage.getData().get("response"), "7")) {
            playSound1();
            NotifikasiGanti(remoteMessage);
        }

        NotifikasiHandle(remoteMessage);
    }

    private void intentToOrder(Bundle bundle) {
        intentOrder.putExtras(bundle);
        sendBroadcast(intentOrder);
    }

    private void chat(RemoteMessage remoteMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
            Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
            intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra("senderid", remoteMessage.getData().get("receiverid"));
            intent1.putExtra("receiverid", remoteMessage.getData().get("senderid"));
            intent1.putExtra("name", remoteMessage.getData().get("name"));
            intent1.putExtra("tokenku", remoteMessage.getData().get("tokendriver"));
            intent1.putExtra("tokendriver", remoteMessage.getData().get("tokenuser"));
            intent1.putExtra("pic", remoteMessage.getData().get("pic"));
            PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_MUTABLE);
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(remoteMessage.getData().get("name"));
            bigTextStyle.bigText(remoteMessage.getData().get("message"));
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
            mBuilder.setContentIntent(pIntent1);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(remoteMessage.getData().get("name"));
            mBuilder.setContentText(remoteMessage.getData().get("message"));
            mBuilder.setStyle(bigTextStyle);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setVibrate(new long[]{500, 500});
            mBuilder.setLights(Color.RED, 3000, 3000);
            mBuilder.setSound(soundUri);
            mBuilder.setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_ID);
            Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
        }
    }
    private void NotifikasiPending(RemoteMessage remoteMessage){
        Intent intent1 = new Intent(getApplicationContext(), ActivityProgress.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id_driver"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_MUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Menunggu")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void NotifikasiTerima(RemoteMessage remoteMessage){
        Intent intent1 = new Intent(getApplicationContext(), ActivityProgress.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id_driver"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_MUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Diterima")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void NotifikasiMulai(RemoteMessage remoteMessage){
        Intent intent1 = new Intent(getApplicationContext(), ActivityProgress.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id_driver"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Diproses")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void NotifikasiGanti(RemoteMessage remoteMessage){
        Intent intent1 = new Intent(getApplicationContext(), ActivityProgress.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Harga disesuaikan")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void NotifikasiSelesai(RemoteMessage remoteMessage){
        Intent intent1 = new Intent(getApplicationContext(), ActivityProgress.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id_driver"));
        intent1.putExtra("complete", "true");
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_MUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Selesai")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void NotifikasiBatal(RemoteMessage remoteMessage){
        android.util.Log.d("TrackRespon", "Berhasil");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver", remoteMessage.getData().get("id_driver"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        intent1.putExtra("Status", "1");
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_MUTABLE);
        startActivity(intent1);
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pesan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Dibatalkan")
                .setContentText(remoteMessage.getData().get("desc"))
                .setAutoCancel(true)
                .setSound(customSoundUri)
                .setContentIntent(pIntent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void playSound1() {
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.pesan);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(2000);
    }
    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_notif);
        return remoteViews;
    }
    private void showNotification(String title, String message) {
        try {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            // Buat intent untuk membuka MainActivity saat notifikasi diklik
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/raw/notification");

            String channelId = "default_channel_id";  // Channel ID untuk Android 8+

            // Membuat notifikasi dengan title dan message dari data
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent)
                    .setContentInfo("ANY")
                    .setLargeIcon(icon)
                    .setColor(Color.RED)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            notificationBuilder.setLights(Color.YELLOW, 1000, 300);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Buat notification channel untuk Android 8+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Notifikasi Umum", NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(true);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }

            // Kirim notifikasi
            notificationManager.notify(0, notificationBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //------------------------------- test message ---------------------------
    private void ShowNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        String title = data.get("title");
        String message = data.get("text");

        // Fallback untuk key "message" lama
        if ((message == null || message.isEmpty()) && data.get("message") != null) {
            message = data.get("message");
        }

        // Fallback lagi ke notification payload jika kosong
        if ((title == null || title.isEmpty()) && notification != null) {
            title = notification.getTitle();
        }
        if ((message == null || message.isEmpty()) && notification != null) {
            message = notification.getBody();
        }

        if (title == null || title.isEmpty()) title = "Notifikasi Masuk";
        if (message == null || message.isEmpty()) message = "Ada pesan baru";

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/raw/notification");

        String channelId = "default_channel_id";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setColor(Color.RED)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE);

        notificationBuilder.setLights(Color.YELLOW, 1000, 300);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Channel Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Notifikasi Umum", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setDescription("Channel untuk notifikasi umum");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
    private void testrespon(RemoteMessage remoteMessage) {
        Log.d("ResponFCM", "Topic Diterima");

        // Cek apakah notification ada dalam pesan
        if (remoteMessage.getNotification() != null) {
            // Log untuk memastikan title dan body ada
            Log.d("FCM-Notification", "Title: " + remoteMessage.getNotification().getTitle());
            Log.d("FCM-Notification", "Body: " + remoteMessage.getNotification().getBody());

            // Membuat notification channel untuk Android 8+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "default_channel_id";
                String channelName = "Notifikasi Umum";
                NotificationChannel channel = new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("description");

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Set custom sound URI
            Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

            // Membuat notification
            Notification notification = new NotificationCompat.Builder(this, "default_channel_id")
                    .setSmallIcon(R.drawable.ic_notif)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_ALL)  // Default notification settings (sound, vibration, etc.)
                    .setSound(customSoundUri)               // Custom sound
                    .setPriority(NotificationCompat.PRIORITY_HIGH)  // Heads-up notification
                    .build();

            // Menampilkan notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);
        }
    }
}
