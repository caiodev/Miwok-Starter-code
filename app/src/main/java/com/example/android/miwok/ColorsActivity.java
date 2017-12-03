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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorsActivity extends AppCompatActivity {

    //Views
    @BindView(R.id.word_list)
    protected ListView listView;

    //Attributes
    private ArrayList<Word> mWords;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private Word mWord;
    private WordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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

        mCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        };

        // Create and setup the (@link AudioManager) to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mWords = new ArrayList<>();

        mWords.add(new Word(getString(R.string.red_in_miwok), getString(R.string.red), R.mipmap.color_red, R.raw.color_red));
        mWords.add(new Word(getString(R.string.green_in_miwok), getString(R.string.green), R.mipmap.color_green, R.raw.color_green));
        mWords.add(new Word(getString(R.string.brown_in_miwok), getString(R.string.brown), R.mipmap.color_brown, R.raw.color_brown));
        mWords.add(new Word(getString(R.string.gray_in_miwok), getString(R.string.gray), R.mipmap.color_gray, R.raw.color_gray));
        mWords.add(new Word(getString(R.string.black_in_miwok), getString(R.string.black), R.mipmap.color_black, R.raw.color_black));
        mWords.add(new Word(getString(R.string.white_in_miwok), getString(R.string.white), R.mipmap.color_white, R.raw.color_white));
        mWords.add(new Word(getString(R.string.dusty_yellow_in_miwok), getString(R.string.dusty_yellow), R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow));
        mWords.add(new Word(getString(R.string.mustard_yellow_in_miwok), getString(R.string.mustard_yellow), R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow));

        mAdapter = new WordAdapter(this, mWords, R.color.category_colors);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Release the media player if it currently exists because we are about to
                //play a idfferent sound file
                releaseMediaPlayer();
                mWord = mWords.get(position);

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //Release the media player if it currently exists because we are about to
                    //play a different sound file

                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), mWord.getmAudioResourceId());
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