/*
 * Classname: CustomList
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
 * This is a custom ArrayAdapter: create a custom ListView of medicine objects
 * Create edit/delete icons on each row of listview
 * When clicked, medicines will be edited/deleted corresponding to the icon
 * Get and set each attribute's value
 */

package com.example.medbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Medicine> implements EditMedFragment.OnFragmentInteractionListener {
    private ArrayList<Medicine> medicines;
    private Context context;

    public CustomList(Context context, ArrayList<Medicine> medicines) {
        super(context, 0,medicines);
        this.medicines =  medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Medicine med = medicines.get(position);
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        // Get the view ids
        TextView medName = view.findViewById(R.id.med_text);
        TextView amount = view.findViewById(R.id.amount_text);
        TextView freq = view.findViewById(R.id.freq_text);
        ImageView edit = view.findViewById(R.id.edit);
        ImageView delete = view.findViewById(R.id.delete);

        // Set Tag for edit and delete button
        edit.setTag(position);
        delete.setTag(position);

        // Called when delete icon is clicked
        delete.setOnClickListener(view1 -> {
            medicines.remove(position);                     /* Remove item at position */
            notifyDataSetChanged();
            MainActivity.getInstance().totalDoses();        /* Update total doses */
        });

        // Called when edit icon is clicked
        edit.setOnClickListener(view12 ->
                /* Create a new fragment to edit */
                new EditMedFragment().newInstance(med).show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "EDIT_MEDICINE"));

        // Format and Save the value into corresponding editText
        medName.setText(med.getName());
        String m_amount = String.format("Dose amount: %d %s",med.getAmount(), med.getUnit());
        String m_freq = String.format("Frequency: %d ", med.getFrequency());
        amount.setText(m_amount);
        freq.setText(m_freq);

        return view;

    }

    @Override
    public void onDonePressed(Medicine medicine, Medicine med) {
    }
}
