package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>() ;

        words.add(new Word(getString(R.string.fatherInMiwok), getString(R.string.father), R.mipmap.family_father));
        words.add(new Word(getString(R.string.motherInMiwok), getString(R.string.mother), R.mipmap.family_mother));
        words.add(new Word(getString(R.string.sonInMiwok), getString(R.string.son), R.mipmap.family_son));
        words.add(new Word(getString(R.string.daughterInMiwok), getString(R.string.daughter), R.mipmap.family_daughter));
        words.add(new Word(getString(R.string.youngerBrotherInMiwok), getString(R.string.youngerBrother), R.mipmap.family_younger_brother));
        words.add(new Word(getString(R.string.youngerSisterInMiwok), getString(R.string.youngerSister), R.mipmap.family_younger_sister));
        words.add(new Word(getString(R.string.olderBrotherInMiwok), getString(R.string.olderBrother), R.mipmap.family_older_brother));
        words.add(new Word(getString(R.string.olderSisterInMiwok), getString(R.string.olderSister), R.mipmap.family_older_sister));
        words.add(new Word(getString(R.string.grandFatherInMiwok), getString(R.string.grandFather), R.mipmap.family_grandfather));
        words.add(new Word(getString(R.string.grandMotherInMiwok), getString(R.string.grandMother), R.mipmap.family_grandmother));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.wordList);
        listView.setAdapter(adapter);
    }
}