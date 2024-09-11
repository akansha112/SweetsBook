package com.saxenaakansha.sweetsbooks;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class RateUsDialog extends Dialog {
    public RateUsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__about);
        final AppCompatButton rateNowBtn=findViewById(R.id.rateNowBtn);
        final AppCompatButton laterBtn=findViewById(R.id.laterBtn);
        final RatingBar ratingBar=findViewById(R.id.ratingBar);
        final ImageView ratingImage=findViewById(R.id.ratingImage);
        rateNowBtn.setOnClickListener(v -> {
//                      code
            showCustomToast();
            dismiss();
        });
        laterBtn.setOnClickListener(v -> dismiss());
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating<=1){
                ratingImage.setImageResource(R.drawable.onestarrating);
            }
            else if (rating<=2){
                ratingImage.setImageResource(R.drawable.twostarrating);
            } else if (rating<=3) {
                ratingImage.setImageResource(R.drawable.threestarrating);}
            else if (rating<=4) {
                ratingImage.setImageResource(R.drawable.fourstarrating);
            }
            else if (rating<=5){
                ratingImage.setImageResource(R.drawable.fivestarrating);
            }
            //animate emoji
            animateImage(ratingImage);
        });
    }
    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation=new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f );
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.setAnimation(scaleAnimation);
    }
    private void showCustomToast() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast));
        TextView textView = layout.findViewById(R.id.toast_message);
        textView.setText("Thank you for rating!");

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
