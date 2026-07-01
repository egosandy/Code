package com.rcdriver.dr.utils.api.service;

import com.rcdriver.dr.json.AcceptRequestJson;
import com.rcdriver.dr.json.AcceptResponseJson;
import com.rcdriver.dr.json.AktivitasRequestJson;
import com.rcdriver.dr.json.AktivitasResponseJson;
import com.rcdriver.dr.json.AllTransResponseJson;
import com.rcdriver.dr.json.AreaRequestJson;
import com.rcdriver.dr.json.AreaResponseJson;
import com.rcdriver.dr.json.BankResponseJson;
import com.rcdriver.dr.json.ChangePassRequestJson;
import com.rcdriver.dr.json.DetailRequestJson;
import com.rcdriver.dr.json.DetailTransResponseJson;
import com.rcdriver.dr.json.DriverRequestJson;
import com.rcdriver.dr.json.EditHargaRequest;
import com.rcdriver.dr.json.EditHargaResponse;
import com.rcdriver.dr.json.EditKendaraanRequestJson;
import com.rcdriver.dr.json.EditprofileRequestJson;
import com.rcdriver.dr.json.FcmKeyResponse;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.GetHomeRequestJson;
import com.rcdriver.dr.json.GetHomeResponseJson;
import com.rcdriver.dr.json.GetKendaraan;
import com.rcdriver.dr.json.GetKendaraanList;
import com.rcdriver.dr.json.GetOnRequestJson;
import com.rcdriver.dr.json.GetOtpResponse;
import com.rcdriver.dr.json.HapusMenuRequest;
import com.rcdriver.dr.json.HapusMenuRespon;
import com.rcdriver.dr.json.ItemRespon;
import com.rcdriver.dr.json.JobResponse;
import com.rcdriver.dr.json.JobResponseJson;
import com.rcdriver.dr.json.KomisiResponse;
import com.rcdriver.dr.json.LayananRequest;
import com.rcdriver.dr.json.LoginRequest;
import com.rcdriver.dr.json.LoginRequestJson;
import com.rcdriver.dr.json.LoginResponse;
import com.rcdriver.dr.json.LoginResponseJson;
import com.rcdriver.dr.json.LokasiDriverRequest;
import com.rcdriver.dr.json.LokasiDriverResponse;
import com.rcdriver.dr.json.MainBGResponse;
import com.rcdriver.dr.json.MapKeyResponse;
import com.rcdriver.dr.json.PointRequest;
import com.rcdriver.dr.json.PointRequestJson;
import com.rcdriver.dr.json.PointResponse;
import com.rcdriver.dr.json.PointResponseJson;
import com.rcdriver.dr.json.PrivacyRequestJson;
import com.rcdriver.dr.json.PrivacyResponseJson;
import com.rcdriver.dr.json.RedeemRequestJson;
import com.rcdriver.dr.json.RedeemResponseJson;
import com.rcdriver.dr.json.RegisterRequestJson;
import com.rcdriver.dr.json.RegisterResponseJson;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.SaveStatusRequest;
import com.rcdriver.dr.json.SaveStatusResponse;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.json.SettingResponse;
import com.rcdriver.dr.json.SliderRequest;
import com.rcdriver.dr.json.SliderResponse;
import com.rcdriver.dr.json.StatusRequest;
import com.rcdriver.dr.json.StatusResponse;
import com.rcdriver.dr.json.TopupRequestJson;
import com.rcdriver.dr.json.TopupResponseJson;
import com.rcdriver.dr.json.UpdateAktivitasRequestJson;
import com.rcdriver.dr.json.UpdateFcmRequest;
import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.json.UpdateMenuRequestJson;
import com.rcdriver.dr.json.UpdateMenuResponseJson;
import com.rcdriver.dr.json.UpdateStatusRequest;
import com.rcdriver.dr.json.UpdateTokenRequestJson;
import com.rcdriver.dr.json.UpdateTotalRequest;
import com.rcdriver.dr.json.UpdateTotalResponse;
import com.rcdriver.dr.json.VerifyCodeRequest;
import com.rcdriver.dr.json.VerifyCodeResponse;
import com.rcdriver.dr.json.VerifyRequestJson;
import com.rcdriver.dr.json.WalletRequestJson;
import com.rcdriver.dr.json.WalletResponseJson;
import com.rcdriver.dr.json.WithdrawRequestJson;
import com.rcdriver.dr.json.WithdrawResponseJson;
import com.rcdriver.dr.json.fcm.CancelBookRequestJson;
import com.rcdriver.dr.json.fcm.CancelBookResponseJson;
import com.rcdriver.dr.json.transfer.GetReceiverRequestJson;
import com.rcdriver.dr.json.transfer.GetReceiverResponJson;
import com.rcdriver.dr.json.transfer.GetSaldoResponJson;
import com.rcdriver.dr.json.transfer.TransferRequestJson;
import com.rcdriver.dr.json.transfer.TransferResponJson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface DriverService {

    @POST("driver/loginew")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("driver/perbaruilokasi")
    Call<UpdateLocationResponseJson> updatelocation(@Body UpdateLocationRequestJson param);

    @POST("driver/update_lokasi")
    Call<ResponseJson> updatelokasi(@Body UpdateLocationRequestJson param);

    @POST("driver/update_aktivitas")
    Call<ResponseJson> updateAktivitas(@Body UpdateAktivitasRequestJson param);

    @POST("driver/update_status")
    Call<SaveStatusResponse> updateStatus(@Body SaveStatusRequest param);

    @POST("driver/syncronizing_account")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("driver/logout")
    Call<GetHomeResponseJson> logout(@Body GetHomeRequestJson param);

    @POST("driver/turning_on")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("driver/accept")
    Call<AcceptResponseJson> accept(@Body AcceptRequestJson param);

    @POST("driver/start")
    Call<AcceptResponseJson> startrequest(@Body AcceptRequestJson param);

    @POST("driver/finish")
    Call<AcceptResponseJson> finishrequest(@Body AcceptRequestJson param);

    @POST("driver/edit_profile")
    Call<LoginResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("driver/edit_kendaraan")
    Call<LoginResponseJson> editKendaraan(@Body EditKendaraanRequestJson param);

    @POST("driver/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("driver/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("driver/order_pending")
    Call<AllTransResponseJson> pending(@Body DetailRequestJson param);


    @POST("driver/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("driver/register_driver")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("driver/register")
    Call<RegisterResponseJson> signup(@Body RegisterRequestJson param);

    @POST("driver/upload_data")
    Call<RegisterResponseJson> upload(@Body RegisterRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);


    @POST("driver/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("driver/job")
    Call<JobResponseJson> job();

    @POST("driver/user_cancel")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("driver/mwapikey")
    Call<MapKeyResponse> mwapikey();

    @POST("driver/mwapikey")
    Call<FcmKeyResponse> fcmapikey();

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("pelanggan/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("driver/withdraw")
    Call<WithdrawResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("driver/midtrans")
    Call<WithdrawResponseJson> midtranspay(@Body WithdrawRequestJson param);

    @POST("driver/midtransresult")
    Call<WithdrawResponseJson> midtransres(@Body WithdrawRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);


    @POST("driver/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("driver/verifycode")
    Call<ResponseJson> verifycode(@Body VerifyRequestJson param);

    @POST("driver/liat_lokasi_driver")
    Call<LokasiDriverResponse> liatLokasiDriver(@Body LokasiDriverRequest param);

    @POST("driver/list_saldo")
    Call<WalletResponseJson> listWallet(@Body WalletRequestJson param);

    @POST("pelanggan/MainBG")
    Call<MainBGResponse> mainbackground();

    @POST("driver/update_token")
    Call<Void> updateToken(@Body UpdateTokenRequestJson param);


    @POST("driver/liat_area_driver")
    Call<AreaResponseJson> listarea(@Body AreaRequestJson param);

    @POST("driver/aktivitas_driver")
    Call<AktivitasResponseJson> cekAktivitas(@Body AktivitasRequestJson param);

    @POST("pelanggan/update_status")
    Call<ResponseJson> updateStatus(@Body UpdateStatusRequest param);

    @POST("pelanggan/setting")
    Call<SettingResponse> setting();

    @POST("driver/cekpoin")
    Call<PointResponseJson> CekPoint(@Body PointRequestJson param);

    @POST("driver/banner_app")
    Call<SliderResponse> SliderApp(@Body SliderRequest param);

    @POST("driver/point_app")
    Call<PointResponse> PointApp(@Body PointRequest param);

    @POST("driver/redeem_poin")
    Call<RedeemResponseJson> Redeempoin(@Body RedeemRequestJson param);

    @POST("driver/ikonmap")
    Call<JobResponse> CekIkon(@Body DriverRequestJson param);

    @POST("driver/komisi")
    Call<KomisiResponse> CekKomisi(@Body DriverRequestJson param);

    @POST("driver/loadmenu")
    Call<ItemRespon> LoadMenu(@Body DriverRequestJson param);

    @POST("driver/update_menu")
    Call<UpdateMenuResponseJson> updateMenu(@Body UpdateMenuRequestJson param);

    @POST("driver/update_harga")
    Call<UpdateMenuResponseJson> updateHarga(@Body LayananRequest param);

    @POST("driver/update_harga_total")
    @Headers("X-Requested-With:XMLHttpRequest")
    Call<UpdateTotalResponse> updateTotalHarga(@Body UpdateTotalRequest param);

    @POST("driver/delete_menu")
    Call<HapusMenuRespon> hapusMenu(@Body HapusMenuRequest param);

    @POST("driver/update_status_home")
    Call<StatusResponse> reloadstatus(@Body StatusRequest param);

    @POST("driver/update_login")
    Call<ResponseJson> updatelogin(@Body UpdateLoginRequest param);

    @POST("driver/cek_login")
    Call<LoginResponse> CekLogin(@Body LoginRequest param);

    @POST("pelanggan/device_notif")
    Call<FcmResponse> fcmnotif(@Body SendFcmRequest param);

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

    @POST("driver/edit_pesanan")
    Call<EditHargaResponse> editharga(@Body EditHargaRequest param);

    @GET("driver/vehicle")
    Call<GetKendaraanList> listvehicle(@Query("id_user") String idUser);

    @GET("driver/vehicleid")
    Call<GetKendaraan> vehicle(@Query("idk") String idUser);


    @POST("driver/vehicle_add")
    Call<RegisterResponseJson> vehicleadd(@Body RegisterRequestJson param);

    @POST("driver/update_token")
    Call<Void> updateFcm(UpdateFcmRequest request);



}
