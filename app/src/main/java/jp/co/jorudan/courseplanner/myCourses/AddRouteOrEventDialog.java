package jp.co.jorudan.courseplanner.myCourses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import jp.co.jorudan.courseplanner.R;

public class AddRouteOrEventDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_route_or_event_dialog, null);
        builder.setView(view);

        Button addItem = view.findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                openRouteListDialogue();
            }
        });

        return builder.create();
    }

    private void openRouteListDialogue() {

        RouteListDialog routeListDialog = new RouteListDialog();
        routeListDialog.show(getActivity().getSupportFragmentManager(), "Route List Dialog");
    }
}
