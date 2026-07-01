package com.rcdriver.dr.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

// --- PERUBAHAN DI SINI ---
// Hapus import yang lama dari jakewharton
// import com.jakewharton.picasso.OkHttp3Downloader;
// Ganti dengan import yang benar dari squareup
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class PicassoTrustAll {

    private static Picasso mInstance = null;

    private PicassoTrustAll(Context context) {
        OkHttpClient client = getUnsafeOkHttpClient();
        mInstance = new Picasso.Builder(context)
                // --- PERUBAHAN DI SINI ---
                // Kode ini sekarang valid karena import di atas sudah benar
                .downloader(new OkHttp3Downloader(client))
                .listener((picasso, uri, exception) -> Log.e("PICASSO", "Failed to load image: " + uri, exception))
                .build();
    }

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            // Menggunakan synchronized untuk thread safety saat inisialisasi pertama kali
            synchronized (PicassoTrustAll.class) {
                if (mInstance == null) {
                    new PicassoTrustAll(context);
                }
            }
        }
        return mInstance;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Membuat trust manager yang mempercayai semua sertifikat
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Menginstal trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Membuat SSL socket factory
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}