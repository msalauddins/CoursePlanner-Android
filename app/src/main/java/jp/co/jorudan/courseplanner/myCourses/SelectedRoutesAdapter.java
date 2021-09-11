package jp.co.jorudan.courseplanner.myCourses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.jorudan.courseplanner.R;

public class SelectedRoutesAdapter extends RecyclerView.Adapter<SelectedRoutesAdapter.ViewHolder>{

    private List<SelectedRouteItems> selectedRouteList = new ArrayList<>();
    private Context context;

    public SelectedRoutesAdapter(List<SelectedRouteItems> selectedRouteList, Context context) {
        this.selectedRouteList = selectedRouteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_route_items, parent, false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SelectedRouteItems selectedRouteItems = selectedRouteList.get(position);

        holder.selectedRoute.setText(selectedRouteItems.getSelectedRoute());
    }

    @Override
    public int getItemCount() {
        return selectedRouteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView selectedRoute;

        public ViewHolder(View itemView) {
            super(itemView);

            selectedRoute = itemView.findViewById(R.id.selected);
        }
    }
}
