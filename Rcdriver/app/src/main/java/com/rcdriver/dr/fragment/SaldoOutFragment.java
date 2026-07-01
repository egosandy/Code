package com.rcdriver.dr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.item.WalletItem;
import com.rcdriver.dr.json.WalletRequestJson;
import com.rcdriver.dr.json.WalletResponseJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.Log;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoOutFragment extends Fragment {
    private View getView;
    private Context context;
    ImageView backbtn;
    ShimmerFrameLayout shimmer;
    RecyclerView recycle;
    WalletItem walletItem;
    RelativeLayout rlnodata;
    TextView Total,TotalKeluar;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.activity_wallet, container, false);
        context = getContext();
        shimmer = getView.findViewById(R.id.shimmerwallet);
        recycle = getView.findViewById(R.id.recycle);
        rlnodata = getView.findViewById(R.id.rlnodata);
        backbtn = getView.findViewById(R.id.back_btn);
        Total = getView.findViewById(R.id.Total);
        TotalKeluar = getView.findViewById(R.id.TotalKeluar);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new GridLayoutManager(context, 1));
        getdatawallet();
        kalkulasiwallet();
        return getView;
    }
    private void shimmershow() {
        recycle.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    private void shimmertutup() {

        recycle.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmer();
    }

    private void getdatawallet() {
        shimmershow();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService driverService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        WalletRequestJson param = new WalletRequestJson();
        param.setId(loginUser.getId());
        driverService.wallet(param).enqueue(new Callback<WalletResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponseJson> call, @NonNull Response<WalletResponseJson> response) {
                if (response.isSuccessful()) {
                    shimmertutup();
                    walletItem = new WalletItem(context, Objects.requireNonNull(response.body()).getData(), R.layout.item_wallet);
                    recycle.setAdapter(walletItem);
                    if (response.body().getData().isEmpty()) {
                        recycle.setVisibility(View.GONE);
                        rlnodata.setVisibility(View.VISIBLE);
                    } else {
                        recycle.setVisibility(View.VISIBLE);
                        rlnodata.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
    private void kalkulasiwallet() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService driverService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        WalletRequestJson param = new WalletRequestJson();
        param.setId(loginUser.getId());
        driverService.wallet(param).enqueue(new Callback<WalletResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponseJson> call, @NonNull Response<WalletResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().isEmpty()) {
                       //tidak ada data
                    } else {
                        int sumplus = 0;
                        int summin = 0;
                        int Bulanini = 0;
                        String startdate = "0";
                        String enddate = "0";
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            try {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date date = dt.parse(response.body().getData().get(i).getWaktu());
                                SimpleDateFormat dt1 = new SimpleDateFormat("MM");
                                startdate = dt1.format(date);
                                enddate = new SimpleDateFormat("MM").format(cal.getTime());
                                if(response.body().getData().get(i).getType().equals("Order+") || response.body().getData().get(i).getType().equals("topup") || response.body().getData().get(i).getType().equals("redeem")){
                                    if(isTodayBetween(Integer.parseInt(dt1.format(date)), Integer.parseInt(new SimpleDateFormat("MM").format(cal.getTime())))){
                                        int jumlah = Integer.parseInt(response.body().getData().get(i).getJumlah());
                                        int[] array = {jumlah};
                                        for( int num : array) {
                                            Bulanini = Bulanini+num;
                                        }
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(response.body().getData().get(i).getType().equals("Order+") || response.body().getData().get(i).getType().equals("topup") || response.body().getData().get(i).getType().equals("redeem")){
                                int jumlah = Integer.parseInt(response.body().getData().get(i).getJumlah());
                                int[] array = {jumlah};
                                for( int num : array) {
                                    sumplus = sumplus+num;
                                }
                            }else{
                                int jumlah = Integer.parseInt(response.body().getData().get(i).getJumlah());
                                int[] array = {jumlah};
                                for( int num : array) {
                                    summin = summin+num;
                                }
                            }

                        }
                        Log.e("Bulanini", startdate + "," + enddate + "," + Bulanini);
                        Utility.currencyTXT(Total, String.valueOf(sumplus), context);
                        Utility.currencyTXT(TotalKeluar, String.valueOf(summin), context);
                    }
                }
            }
            private boolean isTodayBetween(int from, int to) {
               try{
                   if (from < 0 || to < 0 || from > Calendar.DECEMBER || to > Calendar.DECEMBER) {
                       throw new IllegalArgumentException("Invalid month provided: from = " + from + " to = " + to);
                   }
                   Date now = new Date();
                   GregorianCalendar cal = new GregorianCalendar();
                   cal.setTime(now);
                   int thisMonth = cal.get(Calendar.MONTH);
                   if (from > to) {
                       to = to + Calendar.DECEMBER;
                       thisMonth = thisMonth + Calendar.DECEMBER;
                   }
                   if (thisMonth >= from && thisMonth <= to) {
                       return true;
                   }
               } catch (IllegalArgumentException e) {
                   e.printStackTrace();
               }
               return false;
            }
            @Override
            public void onFailure(@NonNull Call<WalletResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
}
