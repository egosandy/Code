package com.rcdriver.cs.utils.api.service;


import com.rcdriver.cs.json.payment.CheckResponseJson;
import com.rcdriver.cs.json.payment.EwalletResponseJson;
import com.rcdriver.cs.json.payment.GetHistoryResponse;
import com.rcdriver.cs.json.payment.GetMethodResponse;
import com.rcdriver.cs.json.payment.RequestPaymentJson;
import com.rcdriver.cs.json.payment.RetailResponseJson;
import com.rcdriver.cs.json.payment.VaResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentService {
    @GET("payment")
    Call<GetMethodResponse> method();

    @GET("payment/check_ewallet")
    Call<CheckResponseJson> check(@Query("invoice") String invoice);

    @GET("payment/transaksi")
    Call<GetHistoryResponse> riwayat(@Query("id_user") String idUser);

    @POST("payment/ewallet")
    Call<EwalletResponseJson> ewallet (@Body RequestPaymentJson param);

    @POST("payment/va")
    Call<VaResponseJson> va (@Body RequestPaymentJson param);

    @POST("payment/retail")
    Call<RetailResponseJson> retail (@Body RequestPaymentJson param);
}
