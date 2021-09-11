package jp.co.jorudan.courseplanner.area;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    private String imgURLs[];

    public ViewPagerAdapter(Context context, String[] imgURLs) {
        this.context = context;
        this.imgURLs = imgURLs;
    }

    @Override
    public int getCount() {
        return imgURLs.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(imgURLs[position])
                .resize(700,300)
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
