package com.rcdriver.mt.activity.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.rcdriver.mt.R;
import com.rcdriver.mt.adapter.JenisPaymentAdapter;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.json.payment.EwalletResponseJson;
import com.rcdriver.mt.json.payment.GetMethodResponse;
import com.rcdriver.mt.json.payment.RequestPaymentJson;
import com.rcdriver.mt.json.payment.RetailResponseJson;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.models.payment.Ewallet;
import com.rcdriver.mt.models.payment.PaymentMethod;
import com.rcdriver.mt.models.payment.Retail;
import com.rcdriver.mt.utils.Log;
import com.rcdriver.mt.utils.OnItemPaymentSelected;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.PaymentService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupSaldoActivity extends AppCompatActivity implements OnItemPaymentSelected {
    private Context context;
    EditText nominal;
    ImageView text1, text2, text3, text4, backBtn;
    JenisPaymentAdapter adapter;
    private RecyclerView recyclerView;
    private String inputNominal = "";
    private User user;
    private RelativeLayout rlProgress;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private ImageView backButton, btnRiwayat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldo);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        nominal = findViewById(R.id.saldo);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.rec_metode);
        rlProgress = findViewById(R.id.rlprogress);
        View bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        btnRiwayat = findViewById(R.id.btn_history);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryTopupActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        text1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("10000");
                inputNominal = "10000";
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("20000");
                inputNominal = "20000";
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("50000");
                inputNominal = "50000";
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("100000");
                inputNominal = "100000";
            }
        });

        getData();
    }

    private void getData(){
        rlProgress.setVisibility(View.VISIBLE);
        PaymentService service = ServiceGenerator.createService(PaymentService.class, user.getId(), user.getPhone());
        service.method().enqueue(new Callback<GetMethodResponse>() {
            @Override
            public void onResponse(Call<GetMethodResponse> call, Response<GetMethodResponse> response) {
                rlProgress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        adapter = new JenisPaymentAdapter(context, response.body().getJenisPaymentList(), TopupSaldoActivity.this::onItemSelected);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMethodResponse> call, Throwable t) {
                rlProgress.setVisibility(View.GONE);
                t.printStackTrace();


            }
        });
    }

    private void showBottomSheet(PaymentMethod methods){
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        @SuppressLint("InflateParams") final View mDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_payment, null);
        Button submit = mDialog.findViewById(R.id.buttonlogin2);
        LinearLayout layout = mDialog.findViewById(R.id.lphone);
        TextView textView = mDialog.findViewById(R.id.textView4);
        EditText editText = mDialog.findViewById(R.id.phonenumber);

        textView.setText(methods.getNama());

        if(methods.getJenis() == 1){
            layout.setVisibility(View.VISIBLE);
        }else {
            layout.setVisibility(View.GONE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(methods.getJenis() == 1){
                    if(editText.getText().toString().isEmpty()){
                        editText.setError("This fielad cannot be blank");
                        editText.requestFocus();
                        return;
                    }
                    postEwallet(String.valueOf(methods.getId()), editText.getText().toString());
                }else if(methods.getJenis() == 2){
                    Intent intent = new Intent(context, KonfirmasiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constants.INTENT_AMOUNT, inputNominal);
                    intent.putExtra(Constants.INTENT_METHOD, new Gson().toJson(methods));
                    startActivity(intent);
                }else if(methods.getJenis() == 3){
                    postRetail(String.valueOf(methods.getId()));
                }else if(methods.getJenis() == 4){
                    Intent intent = new Intent(context, TransferBankActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constants.INTENT_AMOUNT, inputNominal);
                    intent.putExtra(Constants.INTENT_CODE, new Gson().toJson(methods));
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "Fitur belum bisa digunakan", Toast.LENGTH_LONG).show();
                }

                Log.e("Tag.Log", methods.getCode());
                mBottomSheetDialog.cancel();

            }
        });

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(mDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }

    private void postEwallet(String method, String phone){
        rlProgress.setVisibility(View.VISIBLE);
        RequestPaymentJson requestPaymentJson = new RequestPaymentJson();
        requestPaymentJson.setId(method);
        requestPaymentJson.setIdUser(user.getId());
        requestPaymentJson.setPhone("+62" + phone);
        requestPaymentJson.setAmount(inputNominal);
        PaymentService service = ServiceGenerator.createService(PaymentService.class, user.getId(), user.getPhone());
        service.ewallet(requestPaymentJson).enqueue(new Callback<EwalletResponseJson>() {
            @Override
            public void onResponse(Call<EwalletResponseJson> call, Response<EwalletResponseJson> response) {
                rlProgress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        Ewallet ewallet = response.body().getModel();
                        if(ewallet.isRedirect()){
                            String url = ewallet.getRedirectUrl().getMweburl();
                            String urlShopee = ewallet.getRedirectUrl().getDeeplink();
                            if(ewallet.getMethod().equalsIgnoreCase("ID_SHOPEEPAY")){
                                if (url != null){
                                    Intent intent = new Intent (Intent.ACTION_VIEW);
                                    intent.setData (Uri.parse(urlShopee));
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(context, "Url Redirect not setup, please try again", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                if (url != null){
                                    Intent intent = new Intent (Intent.ACTION_VIEW);
                                    intent.setData (Uri.parse(url));
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(context, "Url Redirect not setup, please try again", Toast.LENGTH_LONG).show();
                                }
                            }

                        }else {
                            Intent intent = new Intent(context, OvoWaitingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(Constants.INTENT_ID, ewallet.getTrxid());
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<EwalletResponseJson> call, Throwable t) {
                rlProgress.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postRetail(String method){
        rlProgress.setVisibility(View.VISIBLE);
        RequestPaymentJson requestPaymentJson = new RequestPaymentJson();
        requestPaymentJson.setId(method);
        requestPaymentJson.setIdUser(user.getId());
        requestPaymentJson.setNamaDepan(user.getNamamitra());
        requestPaymentJson.setNamaBelakang(user.getNamamerchant());
        requestPaymentJson.setAmount(inputNominal);
        PaymentService service = ServiceGenerator.createService(PaymentService.class, user.getId(), user.getPhone());
        service.retail(requestPaymentJson).enqueue(new Callback<RetailResponseJson>() {
            @Override
            public void onResponse(Call<RetailResponseJson> call, Response<RetailResponseJson> response) {
                rlProgress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        Retail retail = response.body().getRetailModel();
                        Intent intent = new Intent(context, PaymentFixedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(Constants.INTENT_ID, retail.getExternalId());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RetailResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(PaymentMethod method) {
        nominal.setError(null);
        if(nominal.getText().toString().isEmpty()){
            nominal.setError("This field cannot be blank");
            nominal.requestFocus();
            return;
        }

        showBottomSheet(method);
    }
}