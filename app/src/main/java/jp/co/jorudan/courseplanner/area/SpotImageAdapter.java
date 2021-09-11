package jp.co.jorudan.courseplanner.area;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.co.jorudan.courseplanner.R;

public class SpotImageAdapter extends RecyclerView.Adapter<SpotImageAdapter.ViewHolder>{

    private ArrayList<String> spotImages = new ArrayList<>();
    private ArrayList<String> spotNames = new ArrayList<>();
    private Context context;

    public SpotImageAdapter(ArrayList<String> spotImages, ArrayList<String> spotNames, Context context) {
        this.spotImages = spotImages;
        this.spotNames = spotNames;
        this.context = context;
    }

    @NonNull
    @Override
    public SpotImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotImageAdapter.ViewHolder holder, int position) {

        Picasso.get()
                .load(spotImages.get(position))
                .resize(700,300)
                .into(holder.spotImage);
        holder.spotName.setText(spotNames.get(position));

    }

    @Override
    public int getItemCount() {
        return spotNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView spotImage;
        TextView spotName;

        public ViewHolder(View itemView) {
            super(itemView);

            spotImage = itemView.findViewById(R.id.spot_image);
            spotName = itemView.findViewById(R.id.spot_name);
        }
    }
}
