package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;
    private MediaPlayer mMp;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mWords = new ArrayList<>() ;

        mWords.add(new Word(getString(R.string.fatherInMiwok), getString(R.string.father), R.mipmap.family_father, R.raw.family_father));
        mWords.add(new Word(getString(R.string.motherInMiwok), getString(R.string.mother), R.mipmap.family_mother, R.raw.family_mother));
        mWords.add(new Word(getString(R.string.sonInMiwok), getString(R.string.son), R.mipmap.family_son, R.raw.family_son));
        mWords.add(new Word(getString(R.string.daughterInMiwok), getString(R.string.daughter), R.mipmap.family_daughter, R.raw.family_daughter));
        mWords.add(new Word(getString(R.string.youngerBrotherInMiwok), getString(R.string.youngerBrother), R.mipmap.family_younger_brother, R.raw.family_younger_brother));
        mWords.add(new Word(getString(R.string.youngerSisterInMiwok), getString(R.string.youngerSister), R.mipmap.family_younger_sister, R.raw.family_younger_sister));
        mWords.add(new Word(getString(R.string.olderBrotherInMiwok), getString(R.string.olderBrother), R.mipmap.family_older_brother, R.raw.family_older_brother));
        mWords.add(new Word(getString(R.string.olderSisterInMiwok), getString(R.string.olderSister), R.mipmap.family_older_sister, R.raw.family_older_sister));
        mWords.add(new Word(getString(R.string.grandFatherInMiwok), getString(R.string.grandFather), R.mipmap.family_grandfather, R.raw.family_grandfather));
        mWords.add(new Word(getString(R.string.grandMotherInMiwok), getString(R.string.grandMother), R.mipmap.family_grandmother, R.raw.family_grandmother));

        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_family);

        mListView = (ListView) findViewById(R.id.wordList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = mWords.get(position);
                mMp = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                mMp.start();
            }
        });
    }
}