package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    //Views
    @BindView(R.id.word_list)
    protected ListView listView;

    //Attributes
    private ArrayList<Word> mWords;
    private WordAdapter mAdapter;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private Word mWord;
    private Unbinder mUnbinder;

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.word_list, container, false);

        ButterKnife.bind(this, parentView);

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Create and setup the (@link AudioManager) to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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

        mWords = new ArrayList<>();
        mWords.add(new Word(getString(R.string.number_one_in_miwok), getString(R.string.number_one), R.mipmap.number_one, R.raw.number_one));
        mWords.add(new Word(getString(R.string.number_two_in_miwok), getString(R.string.number_two), R.mipmap.number_two, R.raw.number_two));
        mWords.add(new Word(getString(R.string.number_three_in_miwok), getString(R.string.number_three), R.mipmap.number_three, R.raw.number_three));
        mWords.add(new Word(getString(R.string.number_four_in_miwok), getString(R.string.number_four), R.mipmap.number_four, R.raw.number_four));
        mWords.add(new Word(getString(R.string.number_five_in_miwok), getString(R.string.number_five), R.mipmap.number_five, R.raw.number_five));
        mWords.add(new Word(getString(R.string.number_six_in_miwok), getString(R.string.number_six), R.mipmap.number_six, R.raw.number_six));
        mWords.add(new Word(getString(R.string.number_seven_in_miwok), getString(R.string.number_seven), R.mipmap.number_seven, R.raw.number_seven));
        mWords.add(new Word(getString(R.string.number_eight_in_miwok), getString(R.string.number_eight), R.mipmap.number_eight, R.raw.number_eight));
        mWords.add(new Word(getString(R.string.number_nine_in_miwok), getString(R.string.number_nine), R.mipmap.number_nine, R.raw.number_nine));
        mWords.add(new Word(getString(R.string.number_ten_in_miwok), getString(R.string.number_ten), R.mipmap.number_ten, R.raw.number_ten));

        mAdapter = new WordAdapter(getActivity(), mWords, R.color.category_numbers);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();
                mWord = mWords.get(position);

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //Release the media player if it currently exists because we are about to
                    //play a different sound file
                    mMediaPlayer = MediaPlayer.create(getActivity(), mWord.getmAudioResourceId());
                    mMediaPlayer.start();

                    //Setup a listener on the media player, so that we can stop and release the
                    //media player once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return parentView;
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
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }
}