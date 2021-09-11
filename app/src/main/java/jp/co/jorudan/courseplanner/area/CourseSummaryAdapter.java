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

public class CourseSummaryAdapter extends RecyclerView.Adapter<CourseSummaryAdapter.ViewHolder>{

    private ArrayList<String> spotImages = new ArrayList<>();
    private ArrayList<String> legTypes = new ArrayList<>();
    private ArrayList<String> spotTitles = new ArrayList<>();
    private Context context;

    public CourseSummaryAdapter(ArrayList<String> spotImages, ArrayList<String> legTypes, ArrayList<String> spotTitles, Context context) {
        this.spotImages = spotImages;
        this.legTypes = legTypes;
        this.spotTitles = spotTitles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_summary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(spotImages.get(position))
                .resize(1000,600)
                .into(holder.spotImage);
        holder.spotTitle.setText(spotTitles.get(position));
        holder.legType.setText(legTypes.get(position));

    }

    @Override
    public int getItemCount() {
        return spotImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView spotImage;
        TextView spotTitle;
        TextView legType;

        public ViewHolder(View itemView) {

            super(itemView);

            spotImage = itemView.findViewById(R.id.course_summary_spot_image);
            spotTitle = itemView.findViewById(R.id.spot_title);
            legType = itemView.findViewById(R.id.leg_type);
        }
    }
}
