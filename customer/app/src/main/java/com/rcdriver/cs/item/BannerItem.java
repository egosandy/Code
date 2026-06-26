package com.rcdriver.cs.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Transformation;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.SliderModel;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.RoundedCornersTransformation;


public class BannerItem extends PagerAdapter {

    private final List<SliderModel> models;
    private final Context context;

    public BannerItem(List<SliderModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.slide_mitra, container, false);
        ImageView imageView;
        CardView slider;
        imageView = view.findViewById(R.id.image);
        slider = view.findViewById(R.id.slider);
        final SliderModel propertyModels = models.get(position);
        final int radius = 0;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESSLIDER + propertyModels.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .transform(transformation)
                .into(imageView);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

