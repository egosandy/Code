package com.rcdriver.dr.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.json.WithdrawRequestJson;
import com.rcdriver.dr.json.WithdrawResponseJson;
import com.rcdriver.dr.models.Notif;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupFragment extends Fragment  {
    public static String Jumlah, Metode, NumberCard,Type;
    LinearLayout clikGopay, clickIndomaret, clickAkulaku, clickTransfer;
    TextView txtnominal;
    EditText saldo;
    RelativeLayout sepuluh, duapuluh, limapuluh, seratusribu;
    ImageView imgnoselect;
    ScrollView selectpay;
    private View getView;
    private Context context;

    //----------------------------------------------------------------------------------------
    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.activity_pembayaran, container, false);
        context = getContext();
        selectpay = (ScrollView) getView.findViewById(R.id.selectpay);
        imgnoselect = (ImageView) getView.findViewById(R.id.imgnoselect);
        txtnominal = (TextView) getView.findViewById(R.id.txtnominal);
        saldo = (EditText) getView.findViewById(R.id.saldo);
        sepuluh = (RelativeLayout) getView.findViewById(R.id.sepuluh);
        duapuluh = (RelativeLayout) getView.findViewById(R.id.duapuluh);
        limapuluh = (RelativeLayout) getView.findViewById(R.id.limapuluh);
        seratusribu = (RelativeLayout) getView.findViewById(R.id.seratusribu);
        clikGopay = (LinearLayout) getView.findViewById(R.id.gopay);
        clickIndomaret = (LinearLayout) getView.findViewById(R.id.indomaret);
        clickAkulaku = (LinearLayout) getView.findViewById(R.id.akulaku);
        clickTransfer = (LinearLayout) getView.findViewById(R.id.banktransfer);
        initMidtranSdk();
        // clickPay();
        if (txtnominal.equals("")) {
            txtnominal.setText("");
            saldo.setText("");
            imgnoselect.setVisibility(View.VISIBLE);
            selectpay.setVisibility(View.GONE);
        }
        sepuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtnominal.setText("10000");
                Double getprice = Double.valueOf(txtnominal.getText().toString());
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                saldo.setText(ValFormat);
                imgnoselect.setVisibility(View.GONE);
                selectpay.setVisibility(View.VISIBLE);
            }
        });
        duapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtnominal.setText("20000");
                Double getprice = Double.valueOf(txtnominal.getText().toString());
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                saldo.setText(ValFormat);
                imgnoselect.setVisibility(View.GONE);
                selectpay.setVisibility(View.VISIBLE);
            }
        });
        limapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtnominal.setText("50000");
                Double getprice = Double.valueOf(txtnominal.getText().toString());
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                saldo.setText(ValFormat);
                imgnoselect.setVisibility(View.GONE);
                selectpay.setVisibility(View.VISIBLE);
            }
        });
        seratusribu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtnominal.setText("100000");
                Double getprice = Double.valueOf(txtnominal.getText().toString());
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                saldo.setText(ValFormat);
                imgnoselect.setVisibility(View.GONE);
                selectpay.setVisibility(View.VISIBLE);
            }
        });

        clikGopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String iddata = String.valueOf(random);
                String Saldo = txtnominal.getText().toString();
                int price = Integer.parseInt(Saldo);
                int qty = 1;
                String product_name = "Topup Saldo";
                Jumlah = Saldo;
                Metode = "Gopay";
                NumberCard = Metode + iddata;
//                MidtransSDK.getInstance().setTransactionRequest(DataCustomer.transactionRequest(iddata, price, qty, product_name));
//                MidtransSDK.getInstance().startPaymentUiFlow(context, PaymentMethod.GO_PAY);
            }
        });
        clickIndomaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String iddata = String.valueOf(random);
                String Saldo = txtnominal.getText().toString();
                int price = Integer.parseInt(Saldo);
                int qty = 1;
                String product_name = "Topup Saldo";
                Jumlah = Saldo;
                Metode = "Indomart";
                NumberCard = Metode + iddata;
//                MidtransSDK.getInstance().setTransactionRequest(DataCustomer.transactionRequest(iddata, price, qty, product_name));
//                MidtransSDK.getInstance().startPaymentUiFlow(context, PaymentMethod.INDOMARET);
            }
        });
        clickAkulaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String iddata = String.valueOf(random);
                String Saldo = txtnominal.getText().toString();
                int price = Integer.parseInt(Saldo);
                int qty = 1;
                String product_name = "Topup Saldo";
                Jumlah = Saldo;
                Metode = "Akulaku";
                NumberCard = Metode + iddata;
//                MidtransSDK.getInstance().setTransactionRequest(DataCustomer.transactionRequest(iddata, price, qty, product_name));
//                MidtransSDK.getInstance().startPaymentUiFlow(context, PaymentMethod.AKULAKU);
            }
        });
        clickTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String iddata = String.valueOf(random);
                String Saldo = txtnominal.getText().toString();
                int price = Integer.parseInt(Saldo);
                int qty = 1;
                String product_name = "Topup Saldo";
                Jumlah = Saldo;
                Metode = "Transfer";
                NumberCard = Metode + iddata;
//                MidtransSDK.getInstance().setTransactionRequest(DataCustomer.transactionRequest(iddata, price, qty, product_name));
//                MidtransSDK.getInstance().startPaymentUiFlow(context, PaymentMethod.BANK_TRANSFER);
            }
        });
        return getView;
    }

    private void initMidtranSdk() {
//        User loginUser = BaseApp.getInstance(context).getLoginUser();
//        UIKitCustomSetting uisetting = new
//                UIKitCustomSetting();
//        uisetting.setShowPaymentStatus(true);
//        uisetting.setSaveCardChecked(true);
//        uisetting.setSkipCustomerDetailsPages(true);
//        uisetting.setEnabledAnimation(true);
//        SdkUIFlowBuilder.init()
//                .setContext(context)
//                .setMerchantBaseUrl(URL_MIDTRANS)
//                .setClientKey(KEY_MIDTRANS)
//                .setTransactionFinishedCallback(this)
//                .enableLog(true)
//                .setUIkitCustomSetting(uisetting)
//                .setColorTheme(new CustomColorTheme("#FC585C", "#F5B55F", "#EFF0F2"))
//                .buildSDK();
//        // Set user details
//        /*UserDetail userDetail = new UserDetail();
//        userDetail.setUserFullName(loginUser.getFullnama());
//        userDetail.setEmail(loginUser.getEmail());
//        userDetail.setPhoneNumber(loginUser.getNoTelepon());
//        userDetail.setUserId(loginUser.getId());*/
//        ArrayList<UserAddress> userAddresses = new ArrayList<>();
//        UserAddress shippingUserAddress = new UserAddress();
//        shippingUserAddress.setAddress(loginUser.getAlamat());
//        shippingUserAddress.setCity(null);
//        shippingUserAddress.setCountry(null);
//        shippingUserAddress.setZipcode("0000");
//        shippingUserAddress.setAddressType(Constants.ADDRESS_TYPE_SHIPPING);
//        userAddresses.add(shippingUserAddress);
    }

//    @Override
//    public void onTransactionFinished(TransactionResult result) {
//        if (result.getResponse() != null) {
//            switch (result.getStatus()) {
//                case TransactionResult.STATUS_SUCCESS:
//                  //  Toast.makeText(context, "Transaction Sukses " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                   // SendSaldo();
//                    input_saldo(Jumlah, Metode, NumberCard);
//                 //   addwallet("1","1","test","test","1234","midtrans");
//                    break;
//                case TransactionResult.STATUS_PENDING:
//                    Toast.makeText(context, "Transaction Pending " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    submit(Jumlah, Metode, NumberCard);
//                    break;
//                case TransactionResult.STATUS_FAILED:
//                    Toast.makeText(context, "Transaction Failed" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    break;
//            }
//            result.getResponse().getValidationMessages();
//        } else if (result.isTransactionCanceled()) {
//            Toast.makeText(context, "Transaction Failed", Toast.LENGTH_LONG).show();
//            txtnominal.setText("");
//            saldo.setText("");
//            imgnoselect.setVisibility(View.VISIBLE);
//            selectpay.setVisibility(View.GONE);
//        } else {
//            if (result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))) {
//                Toast.makeText(context, "Transaction Invalid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, "Something Wrong", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void submit(String jumlah, String bank, String Rek) {
        final User user = BaseApp.getInstance(context).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(bank);
        request.setName(user.getFullnama());
        request.setAmount(jumlah);
        request.setCard(Rek);
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType("topup");
        DriverService service = ServiceGenerator.createService(DriverService.class, user.getNoTelepon(), user.getPassword());
        service.withdraw(request).enqueue(new Callback<WithdrawResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WithdrawResponseJson> call, @NonNull Response<WithdrawResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "Topup requests have been successful, we will send a notification after we confirm";
                        sendNotif(user.getToken(), notif);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "error, please check your account data!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WithdrawResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendNotif(final String regIDTujuan, final Notif notif) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(regIDTujuan);
            param.setData(notif);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful()) {
                        android.util.Log.d("ResultFCM", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("sukses")) {
                            android.util.Log.d("ResultFCM", response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<FcmResponse> call, @NonNull Throwable t) {
                    android.util.Log.e("TestFCM", t.getMessage());
                }
            });
        }
    }
    private void input_saldo(String jumlah, String bank, String Rek) {
        final User user = BaseApp.getInstance(context).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(bank);
        request.setName(user.getFullnama());
        request.setAmount(jumlah);
        request.setCard(Rek);
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType("topup");
        DriverService service = ServiceGenerator.createService(DriverService.class, user.getNoTelepon(), user.getPassword());
        service.midtranspay(request).enqueue(new Callback<WithdrawResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WithdrawResponseJson> call, @NonNull Response<WithdrawResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "Topup requests have been successful, we will send a notification after we confirm";
                        sendNotif(user.getToken(), notif);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Log.d("TOPUP", "Falure : " + "Gagal Mengirim Request");
                        //Toast.makeText(context, "error, please check your account data!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("TOPUP", "Falure : " + "Gagal Mengirim Request");
                   // Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WithdrawResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
