package jp.co.jorudan.courseplanner.area;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;
import jp.co.jorudan.courseplanner.RoundedCornersTransformation;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder> {

    private List<Area> areaList = new ArrayList<>();
    private Context context;
    private String url;
    private String id;

    final int radius = 25;
    final int margin = 5;
    final Transformation transformation = new RoundedCornersTransformation(radius, margin);
    //final Transformation transformation = new CropSquareTransformation();
    //final Transformation transformation = new CircleTransform();
    /*int color = Color.parseColor("#00000000");
    final Transformation transformation = new ColorFilterTransformation();*/

    public MyAdapter(List<Area> areaList, Context context) {
        this.areaList = areaList;
        this.context = context;
    }

    @NonNull
    @Override
    /*this method will bw called whenever instance of 'ViewHolder' class is created*/
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Area area = areaList.get( position );

        holder.textLocTitle.setText(area.getName());

        Picasso.get()
                .load(area.getImage())
                .resize(1000,650)
                .into(holder.locationImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("TAG", "Clicked on : " + course.getLocTitle());
                Intent intent = new Intent(context, SubLocationActivity.class);
                intent.putExtra("areaId", area.getAreaId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout parentLayout;
        public ImageView locationImage;
        public TextView textLocTitle;

        public ViewHolder(View itemView) {

            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            locationImage = itemView.findViewById( R.id.location_image );
            textLocTitle = itemView.findViewById( R.id.loc_title );
        }
    }
}


