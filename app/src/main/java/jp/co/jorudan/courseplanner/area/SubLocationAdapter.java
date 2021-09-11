package jp.co.jorudan.courseplanner.area;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.co.jorudan.courseplanner.R;
import jp.co.jorudan.courseplanner.RoundedCornersTransformation;
import jp.co.jorudan.courseplanner.Spots;

public class SubLocationAdapter extends RecyclerView.Adapter<SubLocationAdapter.ViewHolder> {

    private List<SubLocationItem> subLocationItemList;
    private List<CourseList> courseLists;
    private Context context;
    private String spotsString = "";
    private String legsString = "";
    private String transitsString = "";
    private String tagsString = "";

    final int radius = 20;
    final int margin = 5;
    final Transformation transformation = new RoundedCornersTransformation(radius, margin);

    public SubLocationAdapter(List<CourseList> courseLists) {
        this.courseLists = courseLists;
    }

    public SubLocationAdapter(List<CourseList> courseLists, Context context) {
        this.courseLists = courseLists;
        this.context = context;
    }

    @NonNull
    @Override
    public SubLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_location_item, parent, false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull SubLocationAdapter.ViewHolder holder, int position) {

        final  CourseList courseList = courseLists.get(position);

        holder.courseTitle.setText(courseList.getTitle());
        holder.courseDescription.setText(courseList.getDescription());

        String courseTimeString = courseList.getCourse_time();
        int f = Integer.parseInt( courseTimeString );
        int hours, minutes;
        hours = f / 3600;
        minutes = (f % 3600) / 60;
        String courseTimeStringFinal = "course Time : " + hours + " hours " + minutes + " minutes ";

        holder.courseTime.setText( courseTimeStringFinal );


        List<Spots> spots = courseList.getSpots();
        spotsString = "Spots : \n\n";
        for ( int i = 0; i < spots.size(); i++ ) {
            Spots spot = spots.get( i );

            spotsString += spot.getName()  + "\n";
        }
        holder.courseSpots.setText(spotsString);


        holder.parentLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("course", courseList);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout2;
        TextView courseTitle;
        TextView courseDescription;
        TextView courseTime;
        TextView courseSpots;


        public ViewHolder(View itemView) {
            super(itemView);

            //subLocationImage = itemView.findViewById( R.id.sub_location_image );
            courseTitle = itemView.findViewById( R.id.course_title );
            courseDescription = itemView.findViewById( R.id.course_description );
            courseTime = itemView.findViewById( R.id.course_time );
            courseSpots = itemView.findViewById( R.id.course_spots );
            parentLayout2 = itemView.findViewById(R.id.parent_layout_2);
        }
    }
}
