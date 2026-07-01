package com.rcdriver.dr.activity.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.payment.CheckResponseJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.PaymentService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OvoWaitingActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout lwaiting, lresponse;
    private TextView timer, responseTitle, responseMessage, close;
    private ImageView imgStatus, ivPulse1, ivPulse2;
    private static final String FORMAT = "%02d:%02d";
    private String invoice;
    private User user;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ovo_waiting);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        lwaiting = findViewById(R.id.lwaiting);
        lresponse = findViewById(R.id.lresponse);
        timer = findViewById(R.id.timer2);
        ivPulse1 = findViewById(R.id.imageViewPulse1);
        ivPulse2 = findViewById(R.id.imageViewPulse2);
        imgStatus = findViewById(R.id.imageView3);
        responseTitle = findViewById(R.id.textView6);
        responseMessage = findViewById(R.id.textView7);
        close = findViewById(R.id.textView8);

        invoice = getIntent().getStringExtra(Constants.INTENT_ID);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                check(invoice);
            }
        }, 20000);

        startCountDown();
        Animation pulse1 = AnimationUtils.loadAnimation(context, R.anim.animate_pulse_1000);
        ivPulse1.startAnimation(pulse1);
        Animation pulse2 = AnimationUtils.loadAnimation(context, R.anim.animate_pulse_1500);
        pulse2.setStartOffset(500);
        ivPulse2.startAnimation(pulse2);
    }

    private void startCountDown(){
        new CountDownTimer(70000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            @Override
            public void onFinish() {
                timer.setText("00:00");
                check(invoice);
//                finish();
            }
        }.start();
    }

    private void check(String invoice){
        PaymentService service = ServiceGenerator.createService(PaymentService.class, user.getId(), user.getPhone());
        service.check(invoice).enqueue(new Callback<CheckResponseJson>() {
            @Override
            public void onResponse(Call<CheckResponseJson> call, Response<CheckResponseJson> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode().equalsIgnoreCase("200")){
                        if(response.body().getTransaksi().getStatus() == 2){
                            lresponse.setVisibility(View.VISIBLE);
                            lwaiting.setVisibility(View.GONE);
                            responseTitle.setText("Pembayaran berhasil");
                            responseMessage.setText("Topup anda berhasil, saldo anda sudah bertambah");
                            imgStatus.setImageResource(R.drawable.ic_success);
                        }else if(response.body().getTransaksi().getStatus() == 3){
                            lresponse.setVisibility(View.VISIBLE);
                            lwaiting.setVisibility(View.GONE);
                            responseTitle.setText("Pembayaran gagal");
                            responseMessage.setText(response.body().getTransaksi().getNote());
                            imgStatus.setImageResource(R.drawable.ic_action_cancel);
                        }

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                }
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onFailure(Call<CheckResponseJson> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}