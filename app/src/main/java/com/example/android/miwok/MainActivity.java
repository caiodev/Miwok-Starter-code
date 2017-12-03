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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.numbers)
    protected TextView numbers;

    @BindView(R.id.family)
    protected TextView family;

    @BindView(R.id.colors)
    protected TextView colors;

    @BindView(R.id.phrases)
    protected TextView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        populateView(numbers, NumbersActivity.class);
        populateView(family, FamilyActivity.class);
        populateView(colors, ColorsActivity.class);
        populateView(phrases, PhrasesActivity.class);
    }

    void populateView(TextView textView, final Class<? extends Activity> ActivityToOpen) {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                startActivity(new Intent(getApplicationContext(), ActivityToOpen));
            }
        });
    }
}