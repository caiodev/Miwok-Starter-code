package com.example.android.miwok

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.word_list.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FamilyFragment : Fragment() {

    private var mWords: ArrayList<Word>? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mAudioManager: AudioManager? = null
    private var mOnAudioFocusChangeListener: AudioManager.OnAudioFocusChangeListener? = null
    private var mCompletionListener: MediaPlayer.OnCompletionListener? = null
    private var mAdapter: WordAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val parentView = inflater.inflate(R.layout.word_list, container, false)

        activity!!.volumeControlStream = AudioManager.STREAM_MUSIC

        // Create and setup the (@link AudioManager) to request audio focus
        mAudioManager = activity!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mMediaPlayer!!.pause()
                mMediaPlayer!!.seekTo(0)

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mMediaPlayer!!.start()

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer()
            }
        }
        mCompletionListener = MediaPlayer.OnCompletionListener { releaseMediaPlayer() }

        mWords = ArrayList()

        mWords!!.add(Word(getString(R.string.father_in_miwok), getString(R.string.father), R.mipmap.family_father, R.raw.family_father))
        mWords!!.add(Word(getString(R.string.mother_in_miwok), getString(R.string.mother), R.mipmap.family_mother, R.raw.family_mother))
        mWords!!.add(Word(getString(R.string.son_in_miwok), getString(R.string.son), R.mipmap.family_son, R.raw.family_son))
        mWords!!.add(Word(getString(R.string.daughter_in_miwok), getString(R.string.daughter), R.mipmap.family_daughter, R.raw.family_daughter))
        mWords!!.add(Word(getString(R.string.younger_brother_in_miwok), getString(R.string.younger_brother), R.mipmap.family_younger_brother, R.raw.family_younger_brother))
        mWords!!.add(Word(getString(R.string.younger_sister_in_miwok), getString(R.string.younger_sister), R.mipmap.family_younger_sister, R.raw.family_younger_sister))
        mWords!!.add(Word(getString(R.string.older_brother_in_miwok), getString(R.string.older_brother), R.mipmap.family_older_brother, R.raw.family_older_brother))
        mWords!!.add(Word(getString(R.string.older_sister_in_miwok), getString(R.string.older_sister), R.mipmap.family_older_sister, R.raw.family_older_sister))
        mWords!!.add(Word(getString(R.string.grand_father_in_miwok), getString(R.string.grand_father), R.mipmap.family_grandfather, R.raw.family_grandfather))
        mWords!!.add(Word(getString(R.string.grand_mother_in_miwok), getString(R.string.grand_mother), R.mipmap.family_grandmother, R.raw.family_grandmother))

        mAdapter = WordAdapter(activity!!, mWords!!, R.color.category_family)

        wordListListView!!.adapter = mAdapter
        wordListListView!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //Release the media player if it currently exists because we are about to
            //play a different sound file
            releaseMediaPlayer()
            val word = mWords!![position]

            //Request audio focus for playback
            val result = mAudioManager!!.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                //Release the media player if it currently exists because we are about to
                //play a different sound file

                mMediaPlayer = MediaPlayer.create(activity, word.audioResourceId)
                mMediaPlayer!!.start()

                //Setup a listener on the media player, so that we can stop and release the
                //media player once the sounds has finished playing
                mMediaPlayer!!.setOnCompletionListener(mCompletionListener)
            }
        }

        return parentView
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer!!.release()

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null

            mAudioManager!!.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }
}