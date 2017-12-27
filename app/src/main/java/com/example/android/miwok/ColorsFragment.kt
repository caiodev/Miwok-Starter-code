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

class ColorsFragment : Fragment() {

    //Attributes
    private var mWords: ArrayList<Word>? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mAudioManager: AudioManager? = null
    private var mOnAudioFocusChangeListener: AudioManager.OnAudioFocusChangeListener? = null
    private var mCompletionListener: MediaPlayer.OnCompletionListener? = null
    private var mWord: Word? = null
    private var mAdapter: WordAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate and return the layout for this fragment
        return inflater.inflate(R.layout.word_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.volumeControlStream = AudioManager.STREAM_MUSIC

        // Create and setup the (@link AudioManager) to request audio focus
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mMediaPlayer?.pause()
                mMediaPlayer?.seekTo(0)

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mMediaPlayer?.start()

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer()
            }
        }

        mCompletionListener = MediaPlayer.OnCompletionListener { releaseMediaPlayer() }

        mWords = ArrayList()

        mWords!!.add(Word(getString(R.string.red_in_miwok), getString(R.string.red), R.mipmap.color_red, R.raw.color_red))
        mWords!!.add(Word(getString(R.string.green_in_miwok), getString(R.string.green), R.mipmap.color_green, R.raw.color_green))
        mWords!!.add(Word(getString(R.string.brown_in_miwok), getString(R.string.brown), R.mipmap.color_brown, R.raw.color_brown))
        mWords!!.add(Word(getString(R.string.gray_in_miwok), getString(R.string.gray), R.mipmap.color_gray, R.raw.color_gray))
        mWords!!.add(Word(getString(R.string.black_in_miwok), getString(R.string.black), R.mipmap.color_black, R.raw.color_black))
        mWords!!.add(Word(getString(R.string.white_in_miwok), getString(R.string.white), R.mipmap.color_white, R.raw.color_white))
        mWords!!.add(Word(getString(R.string.dusty_yellow_in_miwok), getString(R.string.dusty_yellow), R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow))
        mWords!!.add(Word(getString(R.string.mustard_yellow_in_miwok), getString(R.string.mustard_yellow), R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow))

        mAdapter = WordAdapter(activity!!, mWords!!, R.color.category_colors)

        wordListListView?.adapter = mAdapter

        wordListListView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //Release the media player if it currently exists because we are about to
            //play a different sound file
            releaseMediaPlayer()

            mWord = mWords!![position]

            //Request audio focus for playback
            val result = mAudioManager!!.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                //Release the media player if it currently exists because we are about to
                //play a different sound file
                mMediaPlayer = MediaPlayer.create(activity, mWord!!.audioResourceId)
                mMediaPlayer?.start()

                //Setup a listener on the media player, so that we can stop and release the
                //media player once the sounds has finished playing
                mMediaPlayer?.setOnCompletionListener(mCompletionListener)
            }
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer?.release()

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null

            mAudioManager?.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }
}