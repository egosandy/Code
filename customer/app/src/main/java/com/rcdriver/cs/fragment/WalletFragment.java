package com.rcdriver.cs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.item.WalletItem;
import com.rcdriver.cs.json.WalletRequestJson;
import com.rcdriver.cs.json.WalletResponseJson;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {
    private ShimmerFrameLayout shimmer;
    private RecyclerView recycle;
    private WalletItem walletItem;
    private RelativeLayout rlnodata;
    private ImageView backbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_wallet, container, false);

        shimmer = view.findViewById(R.id.shimmerwallet);
        recycle = view.findViewById(R.id.recycle);
        rlnodata = view.findViewById(R.id.rlnodata);
        backbtn = view.findViewById(R.id.back_btn);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        getdatawallet();

        return view;
    }

    private void shimmershow() {
        recycle.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();
    }

    private void shimmertutup() {
        recycle.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmerAnimation();
    }

    private void getdatawallet() {
        shimmershow();
        User loginUser = BaseApp.getInstance(requireContext()).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        WalletRequestJson param = new WalletRequestJson();
        param.setId(loginUser.getId());
        userService.wallet(param).enqueue(new Callback<WalletResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponseJson> call, @NonNull Response<WalletResponseJson> response) {
                if (response.isSuccessful()) {
                    shimmertutup();
                    walletItem = new WalletItem(getActivity(), response.body().getData(), R.layout.item_wallet);

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
}
