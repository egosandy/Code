package com.rcdriver.mt.utils.api.service;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rcdriver.mt.R;
import com.rcdriver.mt.activity.ChatActivity;
import com.rcdriver.mt.activity.MainActivity;
import com.rcdriver.mt.activity.OrdervalidasiActivity;
import com.rcdriver.mt.activity.SplashActivity;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.models.fcm.DriverResponse;
import com.rcdriver.mt.utils.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.core.app.NotificationCompat;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    Intent intent;
    public static final String BROADCAST_ACTION = "com.ourdevelops.ourmerchant";
    public static final String BROADCAST_ORDER = "order";
    Intent intentOrder;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage == null) {
            Log.e("FCM", "RemoteMessage is null");
            return;
        }

        // Handle log notifikasi bawaan (jika ada)
        if (remoteMessage.getNotification() != null) {
            Log.e("FCM", "Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Map<String, String> data = remoteMessage.getData();
        User user = BaseApp.getInstance(this).getLoginUser();

        if (data != null && !data.isEmpty()) {
            Log.e("FCM", "Data payload: " + data.toString());

            // Khusus type == 1 dan user tidak null
            if ("1".equals(data.get("type")) && user != null) {
                parseAndSendMessage(data);
            }

            // Tangani semua type lain (chat, info, dll)
            messageHandler(remoteMessage);

            // Tampilkan notifikasi jika ada title/text/message
            ShowNotification(remoteMessage.getNotification(), data);
        } else {
            Log.e("FCM", "No data payload in message");
            // Masih bisa tampilkan notifikasi dari payload notification
            ShowNotification(remoteMessage.getNotification(), new HashMap<>());
        }
    }

    private void parseAndSendMessage(Map<String, String> mapResponse) {
        if (mapResponse == null) {
            Log.e("FCM", "parseAndSendMessage: mapResponse is null");
            return;
        }

        String type = mapResponse.get("type");
        if (type == null || !type.equals("1")) {
            Log.e("FCM", "parseAndSendMessage: type is null or not '1'");
            return;
        }

        String idDriver = mapResponse.get("id_driver");
        String idTransaksi = mapResponse.get("id_transaksi");
        String responseValue = mapResponse.get("response");

        if (idDriver == null || idTransaksi == null || responseValue == null) {
            Log.e("FCM", "parseAndSendMessage: Some required fields are null");
            return;
        }

        DriverResponse response = new DriverResponse();
        response.setIddriver(idDriver);
        response.setIdTransaksi(idTransaksi);
        response.setResponse(responseValue);

        EventBus.getDefault().postSticky(response);
    }


    private void messageHandler(RemoteMessage remoteMessage) {
        User user = BaseApp.getInstance(this).getLoginUser();

        Map<String, String> data = remoteMessage.getData();
        if (data == null || data.isEmpty()) {
            Log.e("FCM", "Data is null or empty in messageHandler");
            return;
        }

        String type = data.get("type");
        if (type == null) {
            Log.e("FCM", "Type is null, cannot handle message");
            return;
        }

        switch (type) {
            case "1":
                if (user != null) orderHandler(remoteMessage);
                break;
            case "2":
                if (user != null) chat(remoteMessage);
                break;
            case "3":
                if (user != null) otherHandler(remoteMessage);
                break;
            case "4":
                otherHandler2(remoteMessage);
                break;
            default:
                if (user != null) otherHandler(remoteMessage);
                break;
        }
    }

    private void otherHandler(RemoteMessage remoteMessage){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void otherHandler2(RemoteMessage remoteMessage){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void orderHandler(RemoteMessage remoteMessage) {
        Bundle data = new Bundle();
        intentToOrder(data);

        if (Objects.equals(remoteMessage.getData().get("response"), "5")) {
            notificationOrderBuilderCancel(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "2")) {
            playSound1();
            notificationOrderBuilderAccept(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "3")) {
            playSound1();
            notificationOrderBuilderStart(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "4")) {
            playSound1();
            notificationOrderBuilderFinish(remoteMessage);
        }
    }

    private void intentToOrder(Bundle bundle){
        intentOrder.putExtras(bundle);
        sendBroadcast(intentOrder);
    }


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
        if (message == null || message.isEmpty()) message = "Ada pesan* baru";

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


    private void notificationOrderBuilderCancel(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("id_driver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("id_pelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Cancel");
        mBuilder.setContentText(getString(R.string.notification_cancel));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderStart(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), OrdervalidasiActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Start");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderAccept(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), OrdervalidasiActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Accept");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderFinish(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("invoice", remoteMessage.getData().get("invoice"));
        intent1.putExtra("ordertime", remoteMessage.getData().get("ordertime"));
        intent1.putExtra("id", remoteMessage.getData().get("id_transaksi"));
        intent1.putExtra("iddriver",remoteMessage.getData().get("id_driver"));
        intent1.putExtra("idpelanggan",remoteMessage.getData().get("id_pelanggan"));
        intent1.putExtra("response",remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Finish");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "merchant";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel merchant",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());

    }

    private void chat(RemoteMessage remoteMessage){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("senderid", remoteMessage.getData().get("receiverid"));
        intent1.putExtra("receiverid", remoteMessage.getData().get("senderid"));
        intent1.putExtra("name", remoteMessage.getData().get("name"));
        intent1.putExtra("tokenku", remoteMessage.getData().get("tokendriver"));
        intent1.putExtra("tokendriver", remoteMessage.getData().get("tokenuser"));
        intent1.putExtra("pic", remoteMessage.getData().get("pic"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("name"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(remoteMessage.getData().get("name"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "mitra";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel mitra",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void playSound1(){
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.notification);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(2000);
    }

}
