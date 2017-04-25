package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>() ;

        words.add(new Word(getString(R.string.redInMiwok), getString(R.string.red), R.mipmap.color_red));
        words.add(new Word(getString(R.string.greenInMiwok), getString(R.string.green), R.mipmap.color_green));
        words.add(new Word(getString(R.string.brownInMiwok), getString(R.string.brown), R.mipmap.color_brown));
        words.add(new Word(getString(R.string.grayInMiwok), getString(R.string.gray), R.mipmap.color_gray));
        words.add(new Word(getString(R.string.blackInMiwok), getString(R.string.black), R.mipmap.color_black));
        words.add(new Word(getString(R.string.whiteInMiwok), getString(R.string.white), R.mipmap.color_white));
        words.add(new Word(getString(R.string.dustyYellowInMiwok), getString(R.string.dustyYellow), R.mipmap.color_dusty_yellow));
        words.add(new Word(getString(R.string.mustardYellowInMiwok), getString(R.string.mustardYellow), R.mipmap.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.wordList);
        listView.setAdapter(adapter);
    }
}