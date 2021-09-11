package jp.co.jorudan.courseplanner.myCourses;

import android.content.Context;
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

public class SelectRouteAdapter extends RecyclerView.Adapter<SelectRouteAdapter.ViewHolder> {

    private List<MyRoute> routeList = new ArrayList<>();
    private Context context;

    public interface OnRouteClickListener {
        void onRouteClick(String routeData, int position);
    }
    private OnRouteClickListener onRouteClickListener;

    public SelectRouteAdapter(List<MyRoute> routeList, Context context, OnRouteClickListener onRouteClickListener) {
        this.routeList = routeList;
        this.context = context;
        this.onRouteClickListener = onRouteClickListener;
    }

    public SelectRouteAdapter(List<MyRoute> routeList, Context context) {
        this.routeList = routeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final MyRoute myRoute = routeList.get( position );

        holder.originNodeName.setText(myRoute.getOriginNodeName());
        holder.destinationNodeName.setText(myRoute.getDestinationNodeName());

        holder.routeParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRouteClickListener.onRouteClick(myRoute.getOriginNodeName(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView originNodeName;
        TextView destinationNodeName;
        LinearLayout routeParentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            originNodeName = itemView.findViewById(R.id.origin_node_name);
            destinationNodeName = itemView.findViewById(R.id.destination_node_name);
            routeParentLayout = itemView.findViewById(R.id.route_parent_layout);
        }
    }
}
