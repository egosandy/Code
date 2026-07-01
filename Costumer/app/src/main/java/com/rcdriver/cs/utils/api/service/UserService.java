package com.rcdriver.cs.utils.api.service;

import com.rcdriver.cs.json.AllMerchantByNearResponseJson;
import com.rcdriver.cs.json.AllMerchantBySection;
import com.rcdriver.cs.json.AllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.AllTransResponseJson;
import com.rcdriver.cs.json.BankResponseJson;
import com.rcdriver.cs.json.BeritaDetailRequestJson;
import com.rcdriver.cs.json.BeritaDetailResponseJson;
import com.rcdriver.cs.json.ChangePassRequestJson;
import com.rcdriver.cs.json.DetailRequestJson;
import com.rcdriver.cs.json.DigiTopupRequest;
import com.rcdriver.cs.json.DigiTopupResponse;
import com.rcdriver.cs.json.DonasiRequestJson;
import com.rcdriver.cs.json.DonasiResponseJson;
import com.rcdriver.cs.json.EditprofileRequestJson;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.FiturPromoRequest;
import com.rcdriver.cs.json.GetAllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.GetDonasiWdResponse;
import com.rcdriver.cs.json.GetDonaturJson;
import com.rcdriver.cs.json.GetFiturResponseJson;
import com.rcdriver.cs.json.GetHomeRequestJson;
import com.rcdriver.cs.json.GetHomeResponseJson;
import com.rcdriver.cs.json.GetMerchantbyCatRequestJson;
import com.rcdriver.cs.json.GetOtpResponse;
import com.rcdriver.cs.json.GetProgressRequest;
import com.rcdriver.cs.json.GetProgressResponse;
import com.rcdriver.cs.json.GetReceiverRequestJson;
import com.rcdriver.cs.json.GetReceiverResponJson;
import com.rcdriver.cs.json.GetRiwayatDigiResponse;
import com.rcdriver.cs.json.GetSaldoResponJson;
import com.rcdriver.cs.json.InbokRequest;
import com.rcdriver.cs.json.InbokResponse;
import com.rcdriver.cs.json.InquiryResponseJson;
import com.rcdriver.cs.json.InquiryTokenRequest;
import com.rcdriver.cs.json.InquiryTokenResponse;
import com.rcdriver.cs.json.ListLokasiRequest;
import com.rcdriver.cs.json.ListLokasiResponse;
import com.rcdriver.cs.json.LoginGmailRequest;
import com.rcdriver.cs.json.LoginRequest;
import com.rcdriver.cs.json.LoginRequestJson;
import com.rcdriver.cs.json.LoginResponse;
import com.rcdriver.cs.json.LoginResponseJson;
import com.rcdriver.cs.json.MPulsaResponse;
import com.rcdriver.cs.json.MerchantByCatResponseJson;
import com.rcdriver.cs.json.MerchantByIdResponseJson;
import com.rcdriver.cs.json.MerchantByNearResponseJson;
import com.rcdriver.cs.json.MerchantbyIdRequestJson;
import com.rcdriver.cs.json.MpulsaRequest;
import com.rcdriver.cs.json.PoinRequest;
import com.rcdriver.cs.json.PointRespon;
import com.rcdriver.cs.json.PrivacyRequestJson;
import com.rcdriver.cs.json.PrivacyResponseJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponse;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.RateRequestJson;
import com.rcdriver.cs.json.RateResponseJson;
import com.rcdriver.cs.json.RegisterRequestJson;
import com.rcdriver.cs.json.RegisterResponseJson;
import com.rcdriver.cs.json.RequestJson;
import com.rcdriver.cs.json.ResponseDigiKategori;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.ResponseOperatorDigi;
import com.rcdriver.cs.json.ResponsePasca;
import com.rcdriver.cs.json.ResponseProdukDigi;
import com.rcdriver.cs.json.SaldoResponse;
import com.rcdriver.cs.json.SaveLokasiRequest;
import com.rcdriver.cs.json.SaveLokasiResponse;
import com.rcdriver.cs.json.SaveStatusRequest;
import com.rcdriver.cs.json.SaveStatusResponse;
import com.rcdriver.cs.json.SearchMerchantbyCatRequestJson;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.SettingResponse;
import com.rcdriver.cs.json.SliderRequest;
import com.rcdriver.cs.json.SliderResponse;
import com.rcdriver.cs.json.TipRequestJson;
import com.rcdriver.cs.json.TransferRequestJson;
import com.rcdriver.cs.json.TransferResponJson;
import com.rcdriver.cs.json.UpdateLoginRequest;
import com.rcdriver.cs.json.UpdatePasswordRequestJson;
import com.rcdriver.cs.json.UpdateTokenRequestJson;
import com.rcdriver.cs.json.VerifyCodeRequest;
import com.rcdriver.cs.json.VerifyCodeResponse;
import com.rcdriver.cs.json.WalletRequestJson;
import com.rcdriver.cs.json.WalletResponseJson;
import com.rcdriver.cs.json.WithdrawRequestJson;
import com.rcdriver.cs.json.WithdrawResponseJson;
import com.rcdriver.cs.midtrans.MidtrxRequest;
import com.rcdriver.cs.midtrans.MidtrxResponse;
import com.rcdriver.cs.ppob.json.CekHistoriResponse;
import com.rcdriver.cs.ppob.json.CekRequest;
import com.rcdriver.cs.ppob.json.CekResponse;
import com.rcdriver.cs.ppob.json.DataHistoriResponse;
import com.rcdriver.cs.ppob.json.HistoriRequest;
import com.rcdriver.cs.ppob.json.HistoriResponse;
import com.rcdriver.cs.ppob.json.OperatorRequest;
import com.rcdriver.cs.ppob.json.OperatorResponse;
import com.rcdriver.cs.ppob.json.TopupRequest;
import com.rcdriver.cs.ppob.json.TopupResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public interface UserService {

    @POST("pelanggan/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("pelanggan/logingmail")
    Call<LoginResponseJson> logingmail(@Body LoginGmailRequest param);

    @POST("pelanggan/kodepromo")
    Call<PromoResponseJson> promocode(@Body PromoRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("pelanggan/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("pelanggan/register_user")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @GET("pelanggan/detail_fitur")
    Call<GetFiturResponseJson> getFitur();

    @POST("pelanggan/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("pelanggan/home")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("pelanggan/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("pelanggan/rate_driver")
    Call<RateResponseJson> rateDriver(@Body RateRequestJson param);

    @POST("pelanggan/edit_profile")
    Call<RegisterResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("pelanggan/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("pelanggan/detail_berita")
    Call<BeritaDetailResponseJson> beritadetail(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/all_berita")
    Call<BeritaDetailResponseJson> allberita(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/merchantbykategoripromo")
    Call<MerchantByCatResponseJson> getmerchanbycat(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbykategori")
    Call<MerchantByNearResponseJson> getmerchanbynear(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchantbykategori")
    Call<AllMerchantByNearResponseJson> getallmerchanbynear(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchantbykategoripage")
    Call<AllMerchantByNearResponseJson> getallmerchanbynearpage(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/itembykategori")
    Call<MerchantByIdResponseJson> getitembycat(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/searchmerchant")
    Call<AllMerchantByNearResponseJson> searchmerchant(@Body SearchMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchant")
    Call<AllMerchantByNearResponseJson> allmerchant(@Body AllMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchantbysection")
    Call<AllMerchantBySection> allmerchantbysection(@Body AllMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbyid")
    Call<MerchantByIdResponseJson> merchantbyid(@Body MerchantbyIdRequestJson param);

    @POST("pelanggan/update_token")
    Call<ResponseJson> updateToken(@Body UpdateTokenRequestJson param);

    @POST("pelanggan/device_notif")
    Call<FcmResponse> fcmnotif(@Body SendFcmRequest param);

    @POST("pelanggan/update_password")
    Call<ResponseJson> updatePassword(@Body UpdatePasswordRequestJson param);

    @POST("pelanggan/setting")
    Call<SettingResponse> setting();

    @POST("pelanggan/midtrans")
    Call<WithdrawResponseJson> midtranspay(@Body WithdrawRequestJson param);

    @POST("pelanggan/tripay")
    Call<ResponseJson> tripaytrx(@Body WithdrawRequestJson param);

    @POST("pelanggan/onProgress")
    Call<GetProgressResponse> progress(@Body GetProgressRequest param);

    @POST("pelanggan/save_lokasihome")
    Call<SaveLokasiResponse> SaveHome(@Body SaveLokasiRequest param);

    @POST("pelanggan/save_lokasi")
    Call<SaveLokasiResponse> SaveLokasi(@Body SaveLokasiRequest param);

    @POST("pelanggan/liat_lokasi_tersimpan")
    Call<ListLokasiResponse> GetLokasi(@Body ListLokasiRequest param);

    @POST("pelanggan/lokasi_pelanggan")
    Call<ListLokasiResponse> ListLokasi(@Body ListLokasiRequest param);

    @POST("pelanggan/banner_app")
    Call<SliderResponse> SliderApp(@Body SliderRequest param);

    @POST("pelanggan/update_login")
    Call<ResponseJson> updatelogin(@Body UpdateLoginRequest param);

    @POST("pelanggan/cek_login")
    Call<LoginResponse> CekLogin(@Body LoginRequest param);

    @POST("pelanggan/inbox")
    Call<InbokResponse> inbox(@Body InbokRequest param);

    @GET("pelanggan/list_promo")
    Call<PromoResponse> getPromo();

    @POST("pelanggan/fitur_promo")
    Call<PromoResponse> FiturPromo(@Body FiturPromoRequest param);

    @POST("pelanggan/getpoin")
    Call<PointRespon> GetPoin(@Body PoinRequest param);

    @POST("pelanggan/getsaldo")
    Call<SaldoResponse> SaldoDriver(@Body RequestJson param);

    @POST("pelanggan/sendpoin")
    Call<PointRespon> KirimPoin(@Body RequestJson param);

    @POST("pelanggan/kirimtips")
    Call<SaldoResponse> KirimSaldo(@Body TipRequestJson param);
    @POST("driver/update_status")
    Call<SaveStatusResponse> updateStatus(@Body SaveStatusRequest param);

    //------------------ MPulsa -----------------------
    @POST("pelanggan/mpulsa_list")
    Call<MPulsaResponse> mpulsa_list(@Body MpulsaRequest param);
    @POST("pelanggan/ppob_list")
    Call<OperatorResponse> ppoblist(@Body OperatorRequest param);
    @POST("pelanggan/mpulsa_topup")
    Call<TopupResponse> ppobtopup(@Body TopupRequest param);
    @POST("pelanggan/ppob_histori")
    Call<HistoriResponse> ppobhistori(@Body HistoriRequest param);
    @POST("pelanggan/mpulsa_cek")
    Call<CekResponse> cektopup(@Body CekRequest param);
    @POST("pelanggan/cekdata_histori")
    Call<CekHistoriResponse> cekHistori(@Body CekRequest param);
    @POST("pelanggan/update_ppobhistori")
    Call<ResponseJson> updatehistori(@Body CekRequest param);
    @POST("pelanggan/data_histori")
    Call<DataHistoriResponse> datahistori(@Body CekRequest param);
    //------------------ Whatsapp ----------------------
    @GET("pelanggan/xendit")
    Call<FcmResponse> getXendit();
    //------------------ New Midtrans ------------------
    @POST("pelanggan/midtranscektrs")
    Call<MidtrxResponse> midorder(@Body MidtrxRequest param);

    /*--------------------------digiflazz----------------*/
    @GET("digital/kategori")
    Call<ResponseDigiKategori> getKategori();

    @GET("digital/operator")
    Call<ResponseOperatorDigi> getOperator(@Query("id") String id);

    @GET("digital/produk")
    Call<ResponseProdukDigi> getProduk(@Query("id") String id);

    @GET("digital/pasca")
    Call<ResponsePasca> getPasca(@Query("id") String id);

    @GET("digital/detail")
    Call<GetRiwayatDigiResponse> detailTransaksi(@Query("invoice") String invoice);

    @GET("digital/riwayat")
    Call<GetRiwayatDigiResponse> riwayatTransaksi(@Query("id") String id);

    @POST("digital/inquiry_token")
    Call<InquiryTokenResponse> inqToken(@Body InquiryTokenRequest param);

    @POST("digital/topup")
    Call<DigiTopupResponse> topup(@Body DigiTopupRequest param);

    @POST("digital/inquiry")
    Call<InquiryResponseJson> inqPasca(@Body InquiryTokenRequest param);

    @POST("digital/payment")
    Call<DigiTopupResponse> payPasca(@Body InquiryTokenRequest param);

    @POST("digital/check_status")
    Call<DigiTopupResponse> checkTopup(@Body DigiTopupRequest param);

    @POST("digital/check_status_pasca")
    Call<DigiTopupResponse> checkTPasca(@Body DigiTopupRequest param);

    @POST("payment/check_saldo")
    Call<GetSaldoResponJson> checksaldo(@Body GetHomeRequestJson param);

    @POST("payment/check_valid_trf")
    Call<GetReceiverResponJson> validation(@Body GetReceiverRequestJson param);

    @POST("payment/transfer_saldo")
    Call<TransferResponJson> transfer(@Body TransferRequestJson param);

    @GET("payment/donatur")
    Call<GetDonaturJson> getDonatur(@Query("id") String id);

    @GET("payment/donasiwd")
    Call<GetDonasiWdResponse> getDonasiWd(@Query("id") String id);

    @POST("payment/donasi")
    Call<DonasiResponseJson> donasi(@Body DonasiRequestJson param);

    @GET("otp")
    Call<GetOtpResponse> getOtp(@HeaderMap Map<String, String> headers);

    @POST("otp")
    Call<VerifyCodeResponse> verifyOtp(@HeaderMap Map<String,
            String> headers, @Body VerifyCodeRequest param);

}
