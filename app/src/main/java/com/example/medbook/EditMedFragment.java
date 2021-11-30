/*
 * Classname: EditMedFragment
 * Version information
 * Date
 * Copyright [2021] [Phuong Nguyen]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 *
 * Pop up a fragment when user clicks edit button
 * Fragment also shows the unedited data
 */
package com.example.medbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditMedFragment extends DialogFragment {
    private EditText medName;
    private EditText medAmount;
    private Spinner medUnit;
    private EditText medFreq;
    private EditText medDate;
    private OnFragmentInteractionListener listener;
    Calendar cal;
    DatePickerDialog dpd;

    // Store object into bundle
    static EditMedFragment newInstance(Medicine med) {
        Bundle args = new Bundle();
        args.putSerializable("medicine", med);
        EditMedFragment fragment = new EditMedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Interface of edit fragment
    public interface OnFragmentInteractionListener{
        void onDonePressed(Medicine medicine, Medicine med);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_med_fragment_layout, null);

        medName = view.findViewById(R.id.med_name_editText);
        medAmount = view.findViewById(R.id.dose_amount_editText);
        medFreq = view.findViewById(R.id.daily_freq_editText);
        medUnit = (Spinner) view.findViewById(R.id.spinner);
        medDate = view.findViewById(R.id.date_editText);

        // Create a spinner for drop down items
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.unit));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        medUnit.setAdapter(myAdapter);

        // Use DatePickerDialog to get date from user
        medDate.setOnClickListener(view1 -> {
            cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            dpd = new DatePickerDialog(getActivity(), (datePicker, n_year, n_month, n_day) -> {
                String selected_date = n_year + "-" + (n_month + 1) + "-" + n_day;
                medDate.setText(selected_date);
            }, year, month, day);
            dpd.show();
        });

        // Set the text of fragment to be unedited data
        // Retrieve objects from bundle
        Medicine selected = (Medicine) getArguments().getSerializable("medicine");
        medName.setText(selected.getName());
        medAmount.setText(selected.getAmount().toString());
        ArrayAdapter adapt = (ArrayAdapter) medUnit.getAdapter();
        int pos = adapt.getPosition(selected.getUnit());
        medUnit.setSelection(pos);
        medFreq.setText(selected.getFrequency().toString());
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String m_date = d.format(selected.getDate());
        medDate.setText(m_date);

        // Create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit Medicine")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Done", (dialogInterface, i) -> {
                    int amount = 0;
                    int freq = 0;
                    Date date = null;
                    SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");

                    // Get user new input and validate
                    String name = medName.getText().toString();
                    if (!medAmount.getText().toString().isEmpty()){
                        amount = Integer.parseInt(medAmount.getText().toString());
                    }
                    String unit = medUnit.getSelectedItem().toString();
                    if (!medFreq.getText().toString().isEmpty()) {
                        freq = Integer.parseInt(medFreq.getText().toString());
                    }
                    try {
                        date = d1.parse((medDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Check if user input validate and proceed
                    if (!name.equals("") && amount != 0 && freq != 0 && date != null) {
                        // Save the data just entered
                        listener.onDonePressed(new Medicine(name, amount, unit, freq, date), selected);
                    }
                }).create();
    }
}