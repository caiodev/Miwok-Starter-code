package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;
    private MediaPlayer mMp;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mWords = new ArrayList<>();

        mWords.add(new Word(getString(R.string.redInMiwok), getString(R.string.red), R.mipmap.color_red, R.raw.color_red));
        mWords.add(new Word(getString(R.string.greenInMiwok), getString(R.string.green), R.mipmap.color_green, R.raw.color_green));
        mWords.add(new Word(getString(R.string.brownInMiwok), getString(R.string.brown), R.mipmap.color_brown, R.raw.color_brown));
        mWords.add(new Word(getString(R.string.grayInMiwok), getString(R.string.gray), R.mipmap.color_gray, R.raw.color_gray));
        mWords.add(new Word(getString(R.string.blackInMiwok), getString(R.string.black), R.mipmap.color_black, R.raw.color_black));
        mWords.add(new Word(getString(R.string.whiteInMiwok), getString(R.string.white), R.mipmap.color_white, R.raw.color_white));
        mWords.add(new Word(getString(R.string.dustyYellowInMiwok), getString(R.string.dustyYellow), R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow));
        mWords.add(new Word(getString(R.string.mustardYellowInMiwok), getString(R.string.mustardYellow), R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_colors);

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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}