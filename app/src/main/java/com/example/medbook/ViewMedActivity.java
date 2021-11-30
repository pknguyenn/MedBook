/*
 * Classname: ViewMedActivity
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
 * Create a new activity
 * After clicking an item in main activity, go to view item in details
 *
 */
package com.example.medbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import java.text.SimpleDateFormat;

public class ViewMedActivity extends AppCompatActivity {
    public static final String key_message2 = "com.example.MedBook.Edit_medicine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_med);

        // Get the Intent that started the activity and retrieve object
        Intent intent = getIntent();
        Medicine med = (Medicine) intent.getSerializableExtra(MainActivity.key_mess);

        // Get the id
        TextView medName = findViewById(R.id.name_text);
        TextView medAmount = findViewById(R.id.amount_text);
        TextView medUnit = findViewById(R.id.unit_text);
        TextView medFreq = findViewById(R.id.freq_text);
        TextView medDate = findViewById(R.id.date_text);

        // Format the entry
        String m_freq = String.format("Daily Frequency: %d ", med.getFrequency());
        String m_unit = String.format("Unit: %s", med.getUnit());
        String m_amount = String.format("Dose Amount: %d", med.getAmount());

        // Set the value of clicked item in detail
        medName.setText(med.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String m_date = String.format("Date Started: %s", sdf.format(med.getDate()));
        medDate.setText(m_date);
        medAmount.setText(m_amount);
        medFreq.setText(m_freq);
        medUnit.setText(m_unit);
    }
}

