package com.rcdriver.cs.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.SettingResponse;
import com.rcdriver.cs.models.SettingModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.AppIntroPagerAdapter;
import com.rcdriver.cs.utils.SharedPrefrence;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    Button buttonSign;
    int[] mResources = {R.drawable.slide_one, R.drawable.slide_two, R.drawable.slide_three};
    private AppIntroPagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    private Context mContext;
    public SharedPrefrence preference;
    List<SettingModel> SettingList;
    private String cekotp = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int MainBG = Color.parseColor("#4c84ff");
        mContext = IntroActivity.this;
        preference = SharedPrefrence.getInstance(mContext);
        buttonSign = findViewById(R.id.tombolstar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonSign.setBackgroundTintList(ColorStateList.valueOf(MainBG));
        }
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(i);*/
                GetSetting();
            }
        });

        removeNotif();
        mViewPager = findViewById(R.id.viewpager);
        viewPagerCountDots = findViewById(R.id.viewPagerCountDots);
        mAdapter = new AppIntroPagerAdapter(IntroActivity.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();
    }

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setColorFilter(getResources().getColor(R.color.gray));
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    25,
                    25
            );

            params.setMargins(4, 20, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }
        int MainBG = Color.parseColor("#4c84ff");
        dots[0].setColorFilter(MainBG);
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setColorFilter(getResources().getColor(R.color.gray));
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        int MainBG = Color.parseColor("#4c84ff");
        dots[position].setColorFilter(MainBG);
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        clickDone();
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }
    private void GetSetting() {
        try {
            final User user = BaseApp.getInstance(this).getLoginUser();
            UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
            service.setting().enqueue(new Callback<SettingResponse>() {
                @Override
                public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                    if (response.isSuccessful()) {
                        com.rcdriver.cs.utils.Log.d("AppSetting", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            SettingList = response.body().getData();
                            cekotp = SettingList.get(0).getIsotp();
                            if(cekotp.equals("1")){
                                Intent intent = new Intent(IntroActivity.this, ActivityLogin.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SettingResponse> call, @NonNull Throwable t) {
                    com.rcdriver.cs.utils.Log.d("AppSetting", t.getMessage());
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
