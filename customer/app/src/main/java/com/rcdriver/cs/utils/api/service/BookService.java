package com.rcdriver.cs.utils.api.service;

import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.DetailRequestJson;
import com.rcdriver.cs.json.DetailTransResponseJson;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.ItemRequestJson;
import com.rcdriver.cs.json.LokasiDriverRequest;
import com.rcdriver.cs.json.LokasiDriverResponse;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.SendRequestJson;
import com.rcdriver.cs.json.SendResponseJson;
import com.rcdriver.cs.json.UpdateLocationRequestJson;
import com.rcdriver.cs.json.UpdateSaldoRequest;
import com.rcdriver.cs.json.UpdateStatusRequest;
import com.rcdriver.cs.json.fcm.CancelBookRequestJson;
import com.rcdriver.cs.json.fcm.CancelBookResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public interface BookService {

    @POST("pelanggan/list_ride")
    Call<GetNearRideCarResponseJson> getNearRide(@Body GetNearRideCarRequestJson param);

    @POST("pelanggan/request_transaksi")
    Call<RideCarResponseJson> requestTransaksi(@Body RideCarRequestJson param);

    @POST("pelanggan/inserttransaksimerchant")
    Call<RideCarResponseJson> requestTransaksiMerchant(@Body ItemRequestJson param);

    @POST("pelanggan/request_transaksi_send")
    Call<SendResponseJson> requestTransaksisend(@Body SendRequestJson param);

    @POST("pelanggan/check_status_transaksi")
    Call<CheckStatusTransaksiResponse> checkStatusTransaksi(@Body CheckStatusTransaksiRequest param);

    @POST("pelanggan/user_cancel")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("pelanggan/pending")
    Call<CancelBookResponseJson> pendingOrder(@Body CancelBookRequestJson param);

    @POST("pelanggan/liat_lokasi_driver")
    Call<LokasiDriverResponse> liatLokasiDriver(@Body LokasiDriverRequest param);

    @POST("pelanggan/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("pelanggan/update_lokasi")
    Call<ResponseJson> updatelocation(@Body UpdateLocationRequestJson param);

    @POST("pelanggan/update_saldo")
    Call<ResponseJson> updateSaldo(@Body UpdateSaldoRequest param);

    @POST("pelanggan/update_status")
    Call<ResponseJson> updateStatus(@Body UpdateStatusRequest param);

    @POST("pelanggan/update_tripay")
    Call<ResponseJson> updateTripay(@Body UpdateStatusRequest param);

    @POST("pelanggan/list_driver")
    Call<GetNearRideCarResponseJson> driverTerdekat(@Body GetNearRideCarRequestJson param);

}
