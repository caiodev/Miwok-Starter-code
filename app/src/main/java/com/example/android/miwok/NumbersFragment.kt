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

class NumbersFragment : Fragment() {

    //Attributes
    private var words: ArrayList<Word>? = null
    private var adapter: WordAdapter? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var onAudioFocusChangeListener: AudioManager.OnAudioFocusChangeListener? = null
    private var completionListener: MediaPlayer.OnCompletionListener? = null
    private var word: Word? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate and return the layout for this fragment
        return inflater.inflate(R.layout.word_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.volumeControlStream = AudioManager.STREAM_MUSIC

        // Create and setup the (@link AudioManager) to request audio focus
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        onAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mediaPlayer?.start()

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer()
            }
        }

        completionListener = MediaPlayer.OnCompletionListener { releaseMediaPlayer() }

        words = ArrayList()
        words!!.add(Word(getString(R.string.number_one_in_miwok), getString(R.string.number_one), R.mipmap.number_one, R.raw.number_one))
        words!!.add(Word(getString(R.string.number_two_in_miwok), getString(R.string.number_two), R.mipmap.number_two, R.raw.number_two))
        words!!.add(Word(getString(R.string.number_three_in_miwok), getString(R.string.number_three), R.mipmap.number_three, R.raw.number_three))
        words!!.add(Word(getString(R.string.number_four_in_miwok), getString(R.string.number_four), R.mipmap.number_four, R.raw.number_four))
        words!!.add(Word(getString(R.string.number_five_in_miwok), getString(R.string.number_five), R.mipmap.number_five, R.raw.number_five))
        words!!.add(Word(getString(R.string.number_six_in_miwok), getString(R.string.number_six), R.mipmap.number_six, R.raw.number_six))
        words!!.add(Word(getString(R.string.number_seven_in_miwok), getString(R.string.number_seven), R.mipmap.number_seven, R.raw.number_seven))
        words!!.add(Word(getString(R.string.number_eight_in_miwok), getString(R.string.number_eight), R.mipmap.number_eight, R.raw.number_eight))
        words!!.add(Word(getString(R.string.number_nine_in_miwok), getString(R.string.number_nine), R.mipmap.number_nine, R.raw.number_nine))
        words!!.add(Word(getString(R.string.number_ten_in_miwok), getString(R.string.number_ten), R.mipmap.number_ten, R.raw.number_ten))

        adapter = WordAdapter(activity!!, words!!, R.color.category_numbers)

        wordListListView?.adapter = adapter

        wordListListView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            releaseMediaPlayer()
            word = words!![position]

            //Request audio focus for playback
            val result = audioManager?.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                //Release the media player if it currently exists because we are about to
                //play a different sound file
                mediaPlayer = MediaPlayer.create(activity, word!!.audioResourceId)
                mediaPlayer?.start()

                //Setup a listener on the media player, so that we can stop and release the
                //media player once the sounds has finished playing
                mediaPlayer?.setOnCompletionListener(completionListener)
            }
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer?.release()

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null
            audioManager?.abandonAudioFocus(onAudioFocusChangeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }
}