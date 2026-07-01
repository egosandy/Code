package com.rcdriver.mt.utils.api.service;

import com.rcdriver.mt.json.ActivekatRequestJson;
import com.rcdriver.mt.json.AddEditItemRequestJson;
import com.rcdriver.mt.json.AddEditKategoriRequestJson;
import com.rcdriver.mt.json.BankResponseJson;
import com.rcdriver.mt.json.ChangePassRequestJson;
import com.rcdriver.mt.json.DetailRequestJson;
import com.rcdriver.mt.json.DetailTransResponseJson;
import com.rcdriver.mt.json.EditMerchantRequestJson;
import com.rcdriver.mt.json.EditProfileRequestJson;
import com.rcdriver.mt.json.FcmKeyResponse;
import com.rcdriver.mt.json.FcmResponse;
import com.rcdriver.mt.json.GetFiturResponseJson;
import com.rcdriver.mt.json.GetOnRequestJson;
import com.rcdriver.mt.json.GetOtpResponse;
import com.rcdriver.mt.json.HistoryRequestJson;
import com.rcdriver.mt.json.HistoryResponseJson;
import com.rcdriver.mt.json.HomeRequestJson;
import com.rcdriver.mt.json.HomeResponseJson;
import com.rcdriver.mt.json.ItemRequestJson;
import com.rcdriver.mt.json.ItemResponseJson;
import com.rcdriver.mt.json.KategoriRequestJson;
import com.rcdriver.mt.json.KategoriResponseJson;
import com.rcdriver.mt.json.LoginRequestJson;
import com.rcdriver.mt.json.LoginResponseJson;
import com.rcdriver.mt.json.MapKeyResponse;
import com.rcdriver.mt.json.PrivacyRequestJson;
import com.rcdriver.mt.json.PrivacyResponseJson;
import com.rcdriver.mt.json.RegisterRequestJson;
import com.rcdriver.mt.json.RegisterResponseJson;
import com.rcdriver.mt.json.ResponseJson;
import com.rcdriver.mt.json.SendFcmRequest;
import com.rcdriver.mt.json.TopupRequestJson;
import com.rcdriver.mt.json.TopupResponseJson;
import com.rcdriver.mt.json.VerifyCodeRequest;
import com.rcdriver.mt.json.VerifyCodeResponse;
import com.rcdriver.mt.json.WalletRequestJson;
import com.rcdriver.mt.json.WalletResponseJson;
import com.rcdriver.mt.json.WithdrawRequestJson;

import com.rcdriver.mt.json.transfer.GetHomeRequestJson;
import com.rcdriver.mt.json.transfer.GetReceiverRequestJson;
import com.rcdriver.mt.json.transfer.GetReceiverResponJson;
import com.rcdriver.mt.json.transfer.GetSaldoResponJson;
import com.rcdriver.mt.json.transfer.TransferRequestJson;
import com.rcdriver.mt.json.transfer.TransferResponJson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface MerchantService {

    @GET("merchant/kategorimerchant")
    Call<GetFiturResponseJson> getFitur();

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("merchant/kategorimerchantbyfitur")
    Call<GetFiturResponseJson> getKategori(@Body HistoryRequestJson param);

    @POST("merchant/onoff")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("merchant/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("merchant/register_merchant")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("merchant/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("merchant/edit_profile")
    Call<LoginResponseJson> editprofile(@Body EditProfileRequestJson param);

    @POST("merchant/edit_merchant")
    Call<LoginResponseJson> editmerchant(@Body EditMerchantRequestJson param);

    @POST("merchant/home")
    Call<HomeResponseJson> home(@Body HomeRequestJson param);

    @POST("merchant/history")
    Call<HistoryResponseJson> history(@Body HistoryRequestJson param);

    @POST("merchant/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("merchant/kategori")
    Call<KategoriResponseJson> kategori(@Body KategoriRequestJson param);


    @POST("merchant/item")
    Call<ItemResponseJson> itemlist(@Body ItemRequestJson param);

    @POST("merchant/active_kategori")
    Call<ResponseJson> activekategori(@Body ActivekatRequestJson param);

    @POST("merchant/active_item")
    Call<ResponseJson> activeitem(@Body ActivekatRequestJson param);

    @POST("merchant/add_kategori")
    Call<ResponseJson> addkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/edit_kategori")
    Call<ResponseJson> editkategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/delete_kategori")
    Call<ResponseJson> deletekategori(@Body AddEditKategoriRequestJson param);

    @POST("merchant/add_item")
    Call<ResponseJson> additem(@Body AddEditItemRequestJson param);

    @POST("merchant/edit_item")
    Call<ResponseJson> edititem(@Body AddEditItemRequestJson param);

    @POST("merchant/delete_item")
    Call<ResponseJson> deleteitem(@Body AddEditItemRequestJson param);

    @POST("pelanggan/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("merchant/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("merchant/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("merchant/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("driver/mwapikey")
    Call<MapKeyResponse> mwapikey();

    @POST("driver/mwapikey")
    Call<FcmKeyResponse> fcmapikey();

    @POST("pelanggan/device_notif")
    Call<FcmResponse> fcmnotif(@Body SendFcmRequest param);

    /*transfer*/
    @POST("payment/check_saldo")
    Call<GetSaldoResponJson> checksaldo(@Body GetHomeRequestJson param);

    @POST("payment/check_valid_trf")
    Call<GetReceiverResponJson> validation(@Body GetReceiverRequestJson param);

    @POST("payment/transfer_saldo")
    Call<TransferResponJson> transfer(@Body TransferRequestJson param);

    @GET("otp")
    Call<GetOtpResponse> getOtp(@HeaderMap Map<String, String> headers);

    @POST("otp")
    Call<VerifyCodeResponse> verifyOtp(@HeaderMap Map<String,
            String> headers, @Body VerifyCodeRequest param);
}
