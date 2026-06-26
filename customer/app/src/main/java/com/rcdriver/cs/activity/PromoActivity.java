package com.rcdriver.cs.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.accessibility.AccessibilityManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.PromosiAdapter;
import com.rcdriver.cs.json.PromoResponse;
import com.rcdriver.cs.models.VoucherModel;
import com.rcdriver.cs.utils.CommonUtils;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoActivity extends AppCompatActivity{
    private RecyclerView mRecycler;
    private PromosiAdapter mAdapter;
    private RelativeLayout LayoutRoot;
    private List<VoucherModel> mItems = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        mRecycler = findViewById(R.id.mRecyclerView);
        LayoutRoot = findViewById(R.id.LayoutRoot);
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> runningservice = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
            @Override
            public void onAccessibilityStateChanged(boolean b) {
                Toast.makeText(PromoActivity.this, "permission "+b, Toast.LENGTH_SHORT).show();
            }
        });
        prepareDemoContent();
    }

    private void prepareDemoContent(){
        CommonUtils.showLoading(this);
        new Handler().postDelayed(() -> {
            CommonUtils.hideLoading();
            UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
            service.getPromo().enqueue(new Callback<PromoResponse>() {
                @Override
                public void onResponse(@NonNull Call<PromoResponse> call, @NonNull Response<PromoResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("PromoData", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            mItems = response.body().getData();
                            for(int i = 0; i < mItems.size(); i++){
                                mAdapter = new PromosiAdapter(mItems,PromoActivity.this);
                                mRecycler.setLayoutManager(new LinearLayoutManager(PromoActivity.this));
                                mRecycler.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setClickListener(new PromosiAdapter.ClickListener() {
                                    @Override
                                    public void click(VoucherModel promomodel) {
                                        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
                                        snackbarText.append("Kode Promo Berhasil Disalin [");
                                        int boldStart = snackbarText.length();
                                        snackbarText.append(promomodel.getKode());
                                        snackbarText.setSpan(new ForegroundColorSpan(Color.GREEN), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        snackbarText.append("].");
                                        Snackbar snackbar = Snackbar
                                                .make(LayoutRoot, snackbarText, Snackbar.LENGTH_LONG)
                                                .setDuration(8000);
                                        snackbar.show();
                                        Toasty.info(getApplicationContext(),"Kode Promo Disalin");
                                        Log.d("PromoData",promomodel.getKode());
                                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("kodepromo", promomodel.getKode());
                                        if (clipboard == null || clip == null) return;
                                        clipboard.setPrimaryClip(clip);
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PromoResponse> call, @NonNull Throwable t) {
                    Log.d("PromoData", t.getMessage());
                    t.printStackTrace();
                }
            });
        }, 2000);
    }
}