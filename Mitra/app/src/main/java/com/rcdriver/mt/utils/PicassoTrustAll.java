package com.rcdriver.mt.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log; // DITAMBAHKAN Import Log

// DIHAPUS: import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class PicassoTrustAll {

    private static Picasso mInstance = null;

    // Metode ini mungkin masih digunakan di tempat lain, jadi biarkan saja.
    // Tapi ini TIDAK AKAN dipakai oleh Picasso lagi setelah perubahan di bawah.
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Gunakan builder baru setiap kali dipanggil untuk menghindari modifikasi state global
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true; // Trust all hostnames
                }
            });

            return builder.build();
        } catch (Exception e) {
            Log.e("PicassoTrustAll", "Error creating unsafe OkHttpClient", e); // Tambahkan Log
            throw new RuntimeException(e);
        }
    }

    // Constructor private untuk Singleton pattern
    private PicassoTrustAll(Context context) {
        // PERBAIKAN: Hapus .downloader()
        // Picasso akan menggunakan downloader default (UrlConnectionDownloader)
        // atau mencoba menggunakan OkHttp yang ada di classpath (tanpa konfigurasi unsafe SSL).
        mInstance = new Picasso.Builder(context)
                // .downloader(new OkHttp3Downloader(getUnsafeOkHttpClient())) // <-- BARIS INI DIHAPUS
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        // Gunakan TAG yang konsisten dan log URI juga
                        Log.e("PICASSO_FAIL", "Failed to load image: " + uri, exception);
                    }
                }).build();

        // Anda bisa set instance default Picasso jika mau, tapi ini akan mempengaruhi semua
        // pemanggilan Picasso.get() di aplikasi Anda, membuatnya "trust all".
        // Hati-hati dengan implikasi keamanannya.
        // try {
        //     Picasso.setSingletonInstance(mInstance);
        // } catch (IllegalStateException e) {
        //     // Instance mungkin sudah di set di tempat lain
        //     Log.w("PicassoTrustAll", "Picasso instance already set.");
        // }
    }

    // Metode public untuk mendapatkan instance Singleton
    public static Picasso getInstance(Context context) {
        // Gunakan context.getApplicationContext() untuk mencegah memory leak
        Context appContext = context.getApplicationContext();
        if (mInstance == null) {
            // Sinkronisasi untuk mencegah pembuatan instance ganda di multithreading
            synchronized (PicassoTrustAll.class) {
                if (mInstance == null) {
                    new PicassoTrustAll(appContext);
                }
            }
        }
        return mInstance;
    }
}