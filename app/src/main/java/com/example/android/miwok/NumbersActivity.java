package com.example.android.miwok;

import android.content.Context;
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
    private ListView mListView;
    private WordAdapter mAdapter;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mMediaPlayer.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Create and setup the (@link AudioManager) to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mWords = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.wordList);

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

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();
                Word word = mWords.get(position);

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //Release the media player if it currently exists because we are about to
                    //play a different sound file

                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), word.getmAudioResourceId());
                    mMediaPlayer.start();

                    //Setup a listener on the media player, so that we can stop and release the
                    //media player once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}