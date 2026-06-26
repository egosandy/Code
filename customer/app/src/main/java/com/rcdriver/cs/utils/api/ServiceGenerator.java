package com.rcdriver.cs.utils.api;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.utils.BooleanSerializerDeserializer;
import com.rcdriver.cs.utils.Log;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Maswend Team on 10/13/2019.
 *
 * Reworked: a SINGLE OkHttpClient is built once and shared. Per-request clients are
 * derived with newBuilder(), which reuses the shared client's connection pool,
 * dispatcher and thread pools. Previously a brand-new OkHttpClient was built on every
 * createService() call from a shared, mutable Builder, so there was no HTTP keep-alive
 * (a fresh TCP/TLS handshake per request -> slow loads) and concurrent calls raced on
 * the shared Builder's interceptor list (auth header sometimes missing -> failed loads).
 */

public class ServiceGenerator {
    private static final BooleanSerializerDeserializer booleanSerializerDeserializer = new BooleanSerializerDeserializer();

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .setLenient()
            .registerTypeAdapter(Boolean.class, booleanSerializerDeserializer)
            .registerTypeAdapter(boolean.class, booleanSerializerDeserializer)
            .create();

    /** Built once; shared by every request so connections are pooled/kept alive. */
    private static final OkHttpClient SHARED_CLIENT = buildSharedClient();

    private static OkHttpClient buildSharedClient() {
        try {
            // Trust manager that does not validate certificate chains (unchanged behavior).
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

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder b = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .retryOnConnectionFailure(true)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);

            if (Log.LOG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                b.addInterceptor(logging);
            }

            return b.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Kept for backward compatibility; returns the shared client. */
    public static OkHttpClient getUnsafeOkHttpClient() {
        return SHARED_CLIENT;
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        OkHttpClient client;
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            // newBuilder() shares the connection pool / dispatcher / thread pools.
            client = SHARED_CLIENT.newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("Authorization", basic)
                                    .header("Accept", "application/json")
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
        } else {
            client = SHARED_CLIENT;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.CONNECTION)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }
}
