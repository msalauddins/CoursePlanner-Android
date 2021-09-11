package jp.co.jorudan.courseplanner.myCourses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> allRouteList = new ArrayList<>();
    String clickedRoute;

    public interface OnAllRouteClickListener {
        void onAllRouteClick(String routeInfo, int rp);
    }

    private OnAllRouteClickListener onAllRouteClickListener;

    public ViewPagerAdapter(Context context, List<String> allRouteList, OnAllRouteClickListener onAllRouteClickListener) {
        this.context = context;
        this.allRouteList = allRouteList;
        this.onAllRouteClickListener = onAllRouteClickListener;
    }

    @Override
    public int getCount() {
        return allRouteList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_all_routes, null);
        Button btn = view.findViewById(R.id.select_from_all_route);
        TextView textView = view.findViewById(R.id.all_routes_item);
        textView.setText(allRouteList.get(position));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedRoute = allRouteList.get(position);
                onAllRouteClickListener.onAllRouteClick(clickedRoute, position);
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
