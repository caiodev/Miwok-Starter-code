/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView numbers;
    private TextView family;
    private TextView colors;
    private TextView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateView(numbers, R.id.numbers, NumbersActivity.class);
        populateView(family, R.id.family, FamilyActivity.class);
        populateView(colors, R.id.colors, ColorsActivity.class);
        populateView(phrases, R.id.phrases, PhrasesActivity.class);
    }

    void populateView(TextView textView, int viewId, final Class<? extends Activity> ActivityToOpen){

        textView = (TextView) findViewById(viewId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the current activity
                Intent intent = new Intent(getApplicationContext(), ActivityToOpen);
                // Start the new activity
                startActivity(intent);
            }
        });
    }
}