package com.arts.sanofi.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.arts.sanofi.app.R;

public class PasswordDialogFragment extends DialogFragment {

    private static PasswordDialogListener mListener;

    private EditText mEditText;

    public String getPassword() {
        return mEditText.getText().toString();
    }

    public static PasswordDialogFragment newInstance(
            PasswordDialogListener listener) {
        PasswordDialogFragment f = new PasswordDialogFragment();
        mListener = listener;

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.password_dialog_layout, null);
        builder.setView(view);
        mEditText = (EditText) view.findViewById(R.id.password_edit_text);
        mEditText.requestFocus();

        mEditText.setSelection(mEditText.length());
        Dialog dialog = builder
                .setTitle(
                        getResources().getString(
                                R.string.password_dialog_title_text))
                .setPositiveButton(
                        getResources().getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {

                                mListener.onFinishPassword(getPassword());

                            }
                        })
                .setNegativeButton(
                        getResources().getString(android.R.string.cancel), null)
                .create();

        return dialog;
    }

    public interface PasswordDialogListener {
        void onFinishPassword(String inputText);
    }
}
