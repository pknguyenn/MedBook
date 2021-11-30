/*
 * Classname: MainActivity
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
 * This is main activity (main screen) of the app
 * Support add/view/edit/delete function and show the total of doses
 *
 */

package com.example.medbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddMedFragment.OnFragmentInteractionListener,
             EditMedFragment.OnFragmentInteractionListener {

    public static final String key_mess = "com.example.MedBook.med";
    public ListView medList;
    public ArrayAdapter<Medicine> medAdapter;
    public ArrayList<Medicine> medDataList;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        // Create a new arraylist and custom adapter
        medList = findViewById(R.id.med_list);
        medDataList = new ArrayList<>();
        medAdapter = new CustomList(this, medDataList);
        medList.setAdapter(medAdapter);

        // Pop up fragment when add button is clicked
        final FloatingActionButton addMedButton = findViewById(R.id.add_med_button);
        addMedButton.setOnClickListener(view ->
                new AddMedFragment().show(getSupportFragmentManager(), "ADD_MEDICINE"));

        // Called when user clicks a medicine
        medList.setOnItemClickListener((adapterView, view, i, l) -> viewMedicine(i));
    }

    /* When an item's clicked, uses intent to go to new activity to new activity
     * Pass key_message and the clicked object through intent
     */
    public void viewMedicine(int i) {
        Intent intent = new Intent(this, ViewMedActivity.class);
        Medicine selected_med = (Medicine) medList.getItemAtPosition(i);
        intent.putExtra(key_mess, selected_med);
        startActivity(intent);
    }

    // Return this instance of main activity
    public static MainActivity getInstance() {
        return instance;
    }

    // When user press save, add new medicine to listview and update total doses
    @Override
    public void onSavePressed(Medicine newMed) {
        medAdapter.add(newMed);
        medList.setAdapter(medAdapter);
        totalDoses();
    }

    // When user press Done, updating new input
    @Override
    public void onDonePressed(Medicine medicine, Medicine selected) {
        selected.setName(medicine.getName());
        selected.setDate(medicine.getDate());
        selected.setAmount(medicine.getAmount());
        selected.setUnit(medicine.getUnit());
        selected.setFrequency(medicine.getFrequency());
        medAdapter.notifyDataSetChanged();
        totalDoses();
    }

    // Calculate total number of daily doses
    public void totalDoses() {
        TextView total_dose = findViewById(R.id.total);
        Medicine current;
        int total = 0;
        for (int i = 0; i < medDataList.size(); i++) {
            current = (Medicine) medList.getItemAtPosition(i);
            total += current.getFrequency();
        }
        String total_number = "Total Number of Doses: " + total;
        total_dose.setText(total_number);
    }
}


