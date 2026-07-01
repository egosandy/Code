package com.rcdriver.cs.adapter;

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rcdriver.cs.databinding.ItemEmptyViewBinding;
import com.rcdriver.cs.databinding.ListPromoBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.VoucherModel;

// Import ButterKnife telah dihapus

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
        // Menggunakan ViewBinding untuk membuat ViewHolder
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ListPromoBinding listPromoBinding = ListPromoBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(listPromoBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemEmptyViewBinding itemEmptyViewBinding = ItemEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(itemEmptyViewBinding);
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

        // Deklarasi @BindView dihapus dan digantikan oleh objek binding
        private final ListPromoBinding binding;

        public ViewHolder(ListPromoBinding binding) {
            // Menggunakan binding.getRoot() sebagai itemView
            super(binding.getRoot());
            this.binding = binding;
            // ButterKnife.bind() dihapus
        }

        protected void clear() {
            binding.thumbnail.setImageDrawable(null);
            binding.title.setText("");
            binding.Kode.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final VoucherModel mPromo = mPromoList.get(position);

            // Mengakses view melalui objek binding
            if (mPromo.getImage() != null) {
                Glide.with(binding.getRoot().getContext())
                        .load(mPromo.getImage())
                        .into(binding.thumbnail);
            }

            if (mPromo.getNama() != null) {
                binding.title.setText(mPromo.getNama());
            }

            if (mPromo.getKode() != null) {
                binding.Kode.setText(mPromo.getKode());
            }

            binding.getRoot().setOnClickListener(v -> {
                if (mPromo.getImage() != null) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(mPromo.getImage()));
                        binding.getRoot().getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: Image url is not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        // Deklarasi @BindView dihapus dan digantikan oleh objek binding
        private final ItemEmptyViewBinding binding;

        EmptyViewHolder(ItemEmptyViewBinding binding) {
            // Menggunakan binding.getRoot() sebagai itemView
            super(binding.getRoot());
            this.binding = binding;
            // ButterKnife.bind() dihapus
            binding.buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {
            // Tidak ada yang perlu dibersihkan
        }
    }
}