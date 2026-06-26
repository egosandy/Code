package com.rcdriver.cs.adapter;

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.VoucherModel;
public class PromoAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "PromoAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<VoucherModel> mPromoList;

    public PromoAdapter(List<VoucherModel> promoList) {
        mPromoList = promoList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_promo, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPromoList != null && mPromoList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mPromoList != null && mPromoList.size() > 0) {
            return mPromoList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<VoucherModel> promoList) {
        mPromoList.addAll(promoList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView coverImageView;

        TextView titleTextView;

        TextView newsTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.thumbnail);
            titleTextView = itemView.findViewById(R.id.title);
            newsTextView = itemView.findViewById(R.id.Kode);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
            newsTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final VoucherModel mPromo = mPromoList.get(position);

            if (mPromo.getImage() != null) {
                Glide.with(itemView.getContext())
                        .load(mPromo.getImage())
                        .into(coverImageView);
            }

            if (mPromo.getNama() != null) {
                titleTextView.setText(mPromo.getNama());
            }

            if (mPromo.getKode() != null) {
                newsTextView.setText(mPromo.getKode());
            }


            itemView.setOnClickListener(v -> {
                if (mPromo.getImage() != null) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(mPromo.getImage()));
                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: Image url is not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        TextView messageTextView;
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.tv_message);
            buttonRetry = itemView.findViewById(R.id.buttonRetry);
            buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {

        }

    }
}
