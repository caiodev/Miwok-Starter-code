package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private ListView mListView;
    private MediaPlayer mMp;
    private ArrayList<Word> mWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mWords = new ArrayList<>() ;

        mWords.add(new Word(getString(R.string.whereAreYouGoingInMiwok), getString(R.string.whereAreYouGoing), R.raw.phrase_where_are_you_going));
        mWords.add(new Word(getString(R.string.whatIsYourNameInMiwok), getString(R.string.whatIsYourName), R.raw.phrase_what_is_your_name));
        mWords.add(new Word(getString(R.string.myNameIsInMiwok), getString(R.string.myNameIs), R.raw.phrase_my_name_is));
        mWords.add(new Word(getString(R.string.howAreYouFeelingInMiwok), getString(R.string.howAreYouFeeling), R.raw.phrase_how_are_you_feeling));
        mWords.add(new Word(getString(R.string.iAmFeelingGoodInMiwok), getString(R.string.iAmFeelingGood), R.raw.phrase_im_feeling_good));
        mWords.add(new Word(getString(R.string.areYouComingInMiwok), getString(R.string.areYouComing), R.raw.phrase_are_you_coming));
        mWords.add(new Word(getString(R.string.yesIAmComingInMiwok), getString(R.string.yesIAmComing), R.raw.phrase_yes_im_coming));
        mWords.add(new Word(getString(R.string.iAmComingInMiwok), getString(R.string.iAmComing), R.raw.phrase_im_coming));
        mWords.add(new Word(getString(R.string.letsGoInMiwok), getString(R.string.letsGo), R.raw.phrase_lets_go));
        mWords.add(new Word(getString(R.string.comeHereInMiwok), getString(R.string.comeHere), R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_phrases);

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