package com.example.ivansv.softomatetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TextFragment extends Fragment implements DataResultReceiver.Receiver {
    public static final String TEXT = "text";
    private EditText userEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_text, container, false);

        userEditText = (EditText) rootView.findViewById(R.id.userEditText);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        return rootView;
    }

    private void request() {
        DataResultReceiver dataResultReceiver = new DataResultReceiver(new Handler());
        dataResultReceiver.setReceiver(this);
        String text = String.valueOf(userEditText.getText());
        Intent intent = new Intent(getContext(), RequestService.class);
        intent.putExtra(DataResultReceiver.RECEIVER, dataResultReceiver);
        intent.putExtra(TEXT, text);
        getActivity().startService(intent);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        String language = data.getString(RequestService.LANGUAGE);
        if (language != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Язык Вашего текста:")
                    .setMessage(language);
            AlertDialog successDialog = builder.create();
            successDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Ошибка!")
                    .setMessage("Проверьте соединение с интернетом");
            AlertDialog successDialog = builder.create();
            successDialog.show();
        }
    }
}
