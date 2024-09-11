package com.saxenaakansha.sweetsbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;



public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int[] images = {

            R.drawable.chef2,
            R.drawable.chef,
            R.drawable.searchimage
    };

    String[] headings = {

            "Indian Sweet Recipes",
            "Learn To Cook",
            "Search here"    };

    String[] description={
            "Made in India with love",
            "Get instant Recipes here\n  Go and Checkout this app",
            "Search for your favourite Sweets and Enjoy !"
    };

    public ViewPagerAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return  headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slidetitleimage = view.findViewById(R.id.titleImage);
        TextView slideHeading = view.findViewById(R.id.texttitle);
        TextView Slidedescription= view.findViewById(R.id.textdescription);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        Slidedescription.setText(description[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }
}
