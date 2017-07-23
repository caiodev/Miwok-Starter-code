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

public class FamilyActivity extends AppCompatActivity {

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