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
public class PhrasesFragment extends Fragment {

    //Views
    @BindView(R.id.word_list)
    protected ListView listView;

    //Attribute
    private ArrayList<Word> mWords;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private WordAdapter mAdapter;
    private Word mWord;
    private Unbinder mUnbinder;

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        mWords.add(new Word(getString(R.string.where_are_you_going_in_miwok), getString(R.string.where_are_you_going), R.raw.phrase_where_are_you_going));
        mWords.add(new Word(getString(R.string.what_is_your_name_in_miwok), getString(R.string.what_is_your_name), R.raw.phrase_what_is_your_name));
        mWords.add(new Word(getString(R.string.my_name_is_in_miwok), getString(R.string.my_name_is), R.raw.phrase_my_name_is));
        mWords.add(new Word(getString(R.string.how_are_you_feeling_in_miwok), getString(R.string.how_are_you_feeling), R.raw.phrase_how_are_you_feeling));
        mWords.add(new Word(getString(R.string.i_am_feeling_good_in_miwok), getString(R.string.i_am_feeling_good), R.raw.phrase_im_feeling_good));
        mWords.add(new Word(getString(R.string.are_you_coming_in_miwok), getString(R.string.are_you_coming), R.raw.phrase_are_you_coming));
        mWords.add(new Word(getString(R.string.yes_i_am_coming_in_miwok), getString(R.string.yes_i_am_coming), R.raw.phrase_yes_im_coming));
        mWords.add(new Word(getString(R.string.i_am_coming_in_miwok), getString(R.string.i_am_coming), R.raw.phrase_im_coming));
        mWords.add(new Word(getString(R.string.lets_go_in_miwok), getString(R.string.lets_go), R.raw.phrase_lets_go));
        mWords.add(new Word(getString(R.string.come_here_in_miwok), getString(R.string.come_here), R.raw.phrase_come_here));

        mAdapter = new WordAdapter(getActivity(), mWords, R.color.category_phrases);

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