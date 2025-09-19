package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private City city;
    private int cPosition;

    interface EditCityDialogListener {
        void editCity(City city, int position);
    }
    private EditCityDialogListener listener;


    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putString("city_name", city.getName());
        args.putString("province_name", city.getProvince());
        args.putInt("position", position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
        if (getArguments() != null){
            String cName = getArguments().getString("city_name");
            String pName = getArguments().getString("province_name");
            cPosition = getArguments().getInt("position");
            city = new City(cName, pName);
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null){
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cName = editCityName.getText().toString();
                    String pName = editProvinceName.getText().toString();

                    // Can only create edit if NOT blank input
                    if (!cName.isEmpty() && !pName.isEmpty()) {
                        listener.editCity(new City(cName, pName), cPosition);
                    }
                })
                .create();
    }
}
