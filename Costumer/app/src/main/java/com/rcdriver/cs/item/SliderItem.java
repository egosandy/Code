package com.rcdriver.cs.item;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.rcdriver.cs.activity.OjekNewActivity;
import com.rcdriver.cs.activity.SendNewActivity;
import com.squareup.picasso.Transformation;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.AllMerchantActivity;
import com.rcdriver.cs.activity.RentCarActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.dialog.DialogInbok;
import com.rcdriver.cs.models.PromoModel;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.RoundedCornersTransformation;


public class SliderItem extends PagerAdapter {

    private final List<PromoModel> models;
    private final Context context;
    private SliderItem.ClickListener clickListener;
    public SliderItem(List<PromoModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    public interface ClickListener extends View.OnClickListener {
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
        View view = layoutInflater.inflate(R.layout.item_slide, container, false);
        ImageView imageView;
        LinearLayout slider;
        imageView = view.findViewById(R.id.image);
        slider = view.findViewById(R.id.slider);
        final PromoModel propertyModels = models.get(position);
        container.setOnClickListener(clickListener);
        final int radius = 0;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESSLIDER + propertyModels.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .transform(transformation)
                .into(imageView);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInbok viewinbok = new DialogInbok();
                viewinbok.showDialog(context,"Test","Contoh");
            }
        });

        slider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (propertyModels.getTypepromosi().equals("link")) {
                    try{
                        String url = (propertyModels.getLinkpromosi());
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } catch (Exception e) {
                        android.util.Log.e("Link", e.getMessage());
                        e.printStackTrace();
                    }
                }else if (propertyModels.getFiturpromosi() == 1 || propertyModels.getFiturpromosi() == 2){
                    Intent i = new Intent(context, OjekNewActivity.class);
                    i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                    i.putExtra("icon", propertyModels.getIcon());
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }else if (propertyModels.getFiturpromosi() == 5){
                    Intent i = new Intent(context, SendNewActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                    i.putExtra("icon", propertyModels.getIcon());
                    context.startActivity(i);
                }else if (propertyModels.getFiturpromosi() == 6){
                    Intent i = new Intent(context, RentCarActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                    i.putExtra("icon", propertyModels.getIcon());
                    context.startActivity(i);
                }else if (propertyModels.getFiturpromosi() == 10 || propertyModels.getFiturpromosi() == 11 || propertyModels.getFiturpromosi() == 12 || propertyModels.getFiturpromosi() == 13){
                    Intent i = new Intent(context, AllMerchantActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                    context.startActivity(i);
                }
            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

