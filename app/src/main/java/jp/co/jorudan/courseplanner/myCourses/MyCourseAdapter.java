package jp.co.jorudan.courseplanner.myCourses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.ViewHolder> {

    private List<MyCourse> myCourseList = new ArrayList<>();
    private Context context;

    public MyCourseAdapter(List<MyCourse> myCourseList, Context context) {
        this.myCourseList = myCourseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_course, parent, false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final MyCourse myCourse = myCourseList.get( position );

        holder.myCourseTitle.setText(myCourse.getMyCourseTitle());
        holder.myCourseStartDate.setText(myCourse.getMyCourseStartDate());
        holder.myCourseEndDate.setText(myCourse.getMyCourseEndDate());

        holder.myCourseParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectRouteActivity.class);
                intent.putExtra("cp", position);
                intent.putExtra("courseName", myCourse.getMyCourseTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCourseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView myCourseTitle;
        TextView myCourseStartDate;
        TextView myCourseEndDate;
        LinearLayout myCourseParentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            myCourseTitle = itemView.findViewById(R.id.my_course_title);
            myCourseStartDate = itemView.findViewById(R.id.my_course_start_date);
            myCourseEndDate = itemView.findViewById(R.id.my_course_end_date);
            myCourseParentLayout = itemView.findViewById(R.id.my_course_parent_layout);
        }
    }
}
