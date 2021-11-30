/*
 * Classname: Medicine
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
 * Create medicine objects with attributes: name, date, amount, unit, and frequency
 * Get and set each attribute's value
 */
package com.example.medbook;

import java.io.Serializable;
import java.util.Date;

public class Medicine implements Serializable {
    private String name;
    private Date date;
    private String unit;
    private int amount;
    private int frequency;

    public Medicine(String name, int amount, String unit, int frequency,  Date date) {
        this.name = name;
        this.date = date;
        this.unit = unit;
        this.amount = amount;
        this.frequency = frequency;
    }

    // Get and set medicine name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Get and set medicine date
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Get and set medicine unit
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Get and set medicine amount
    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Get and set medicine daily frequency
    public Integer getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
