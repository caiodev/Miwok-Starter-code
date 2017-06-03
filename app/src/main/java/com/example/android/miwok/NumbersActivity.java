package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;
    private WordAdapter mAdapter;
    private ListView mListView;
    private MediaPlayer mMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mWords = new ArrayList<>() ;

        mWords.add(new Word(getString(R.string.numberOneInMiwok), getString(R.string.numberOne), R.mipmap.number_one, R.raw.number_one));
        mWords.add(new Word(getString(R.string.numberTwoInMiwok), getString(R.string.numberTwo), R.mipmap.number_two, R.raw.number_two));
        mWords.add(new Word(getString(R.string.numberThreeInMiwok), getString(R.string.numberThree), R.mipmap.number_three, R.raw.number_three));
        mWords.add(new Word(getString(R.string.numberFourInMiwok), getString(R.string.numberFour), R.mipmap.number_four, R.raw.number_four));
        mWords.add(new Word(getString(R.string.numberFiveInMiwok), getString(R.string.numberFive), R.mipmap.number_five, R.raw.number_five));
        mWords.add(new Word(getString(R.string.numberSixInMiwok), getString(R.string.numberSix), R.mipmap.number_six, R.raw.number_six));
        mWords.add(new Word(getString(R.string.numberSevenInMiwok), getString(R.string.numberSeven), R.mipmap.number_seven, R.raw.number_seven));
        mWords.add(new Word(getString(R.string.numberEightInMiwok), getString(R.string.numberEight), R.mipmap.number_eight, R.raw.number_eight));
        mWords.add(new Word(getString(R.string.numberNineInMiwok), getString(R.string.numberNine), R.mipmap.number_nine, R.raw.number_nine));
        mWords.add(new Word(getString(R.string.numberTenInMiwok), getString(R.string.numberTen), R.mipmap.number_ten, R.raw.number_ten));

        mAdapter = new WordAdapter(this, mWords, R.color.category_numbers);

        mListView = (ListView) findViewById(R.id.wordList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = mWords.get(position);
                mMp = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                mMp.start();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}