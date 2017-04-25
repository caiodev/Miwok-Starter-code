package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>() ;

        words.add(new Word(getString(R.string.whereAreYouGoingInMiwok), getString(R.string.whereAreYouGoing)));
        words.add(new Word(getString(R.string.whatIsYourNameInMiwok), getString(R.string.whatIsYourName)));
        words.add(new Word(getString(R.string.myNameIsInMiwok), getString(R.string.myNameIs)));
        words.add(new Word(getString(R.string.howAreYouFeelingInMiwok), getString(R.string.howAreYouFeeling)));
        words.add(new Word(getString(R.string.iAmFeelingGoodInMiwok), getString(R.string.iAmFeelingGood)));
        words.add(new Word(getString(R.string.areYouComingInMiwok), getString(R.string.areYouComing)));
        words.add(new Word(getString(R.string.yesIAmComingInMiwok), getString(R.string.yesIAmComing)));
        words.add(new Word(getString(R.string.iAmComingInMiwok), getString(R.string.iAmComing)));
        words.add(new Word(getString(R.string.letsGoInMiwok), getString(R.string.letsGo)));
        words.add(new Word(getString(R.string.comeHereInMiwok), getString(R.string.comeHere)));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.wordList);
        listView.setAdapter(adapter);
    }
}