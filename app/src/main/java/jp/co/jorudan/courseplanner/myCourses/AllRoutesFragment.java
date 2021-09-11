package jp.co.jorudan.courseplanner.myCourses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import jp.co.jorudan.courseplanner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllRoutesFragment extends Fragment {

    TextView allRouteItem;
    public AllRoutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_routes, container, false);
        allRouteItem = view.findViewById(R.id.all_routes_item);
        String message = getArguments().getString("message");

        allRouteItem.setText(message);

        return view;
    }

}
