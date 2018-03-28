package com.example.administrator.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ValidFragment")
class ViewDialogFragment extends DialogFragment {

    public interface Callback {
        void onClick(String userName, String password);
    }

    private Callback callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "ViewDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            EditText et_userName = (EditText) view.findViewById(R.id.username);
                            EditText et_password = (EditText) view.findViewById(R.id.password);
                            callback.onClick(et_userName.getText().toString(), et_password.getText().toString());
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}
public class MainActivity extends AppCompatActivity implements ViewDialogFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showViewDialogFragment(view);
            }
        });
    }

    public void showViewDialogFragment(View view) {
        ViewDialogFragment viewDialogFragment = new ViewDialogFragment();
        viewDialogFragment.show(getFragmentManager());
    }

    public void onClick(String userName, String password) {
        Toast.makeText(MainActivity.this, "用户名: " + userName + " 密码: " + password, Toast.LENGTH_SHORT).show();
    }
}
