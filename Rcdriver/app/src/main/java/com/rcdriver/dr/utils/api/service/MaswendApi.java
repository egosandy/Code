package com.rcdriver.dr.utils.api.service;

import com.rcdriver.dr.json.WalletRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MaswendApi {

    @FormUrlEncoded
    @POST("wallet.php")
    Call<WalletRespon> sendwallet(@Field("id_user") String id_user,
                                   @Field("jumlah") String jumlah,
                                   @Field("bank") String bank,
                                   @Field("nama_pemilik") String nama_pemilik,
                                   @Field("rekening") String rekening,
                                   @Field("tujuan") String tujuan,
                                   @Field("type") String type,
                                   @Field("status") String status);

}
