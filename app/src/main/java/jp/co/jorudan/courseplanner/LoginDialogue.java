package jp.co.jorudan.courseplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginDialogue extends AppCompatDialogFragment {

    private EditText etUserName;
    private EditText etPassword;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogue, null);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(etUserName.getText().toString().equals("admin") && etPassword.getText().toString().equals("123")){

                            startActivity(new Intent(getActivity(), SettingsActivity.class));

                        }

                        else if (!"admin".equals(etUserName.getText().toString()) && etPassword.getText().toString().equals("123")) {
                            //Toast.makeText(this, "Wrong User ID",Toast.LENGTH_SHORT).show();
                        }

                        else if (!"123".equals(etPassword.getText().toString()) && etUserName.getText().toString().equals("admin")) {
                            //Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                        }
                        //LoginDialogue.this.getActivity().finish();

                    }
                });

        etUserName = view.findViewById(R.id.id2);
        etPassword = view.findViewById(R.id.password2);

        return builder.create();
    }


}
