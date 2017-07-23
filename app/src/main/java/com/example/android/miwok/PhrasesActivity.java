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

public class PhrasesActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;
    private ListView mListView;
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

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Release the media player if it currently exists because we are about to
                //play a idfferent sound file
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