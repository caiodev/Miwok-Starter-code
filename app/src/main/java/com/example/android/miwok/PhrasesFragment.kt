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

class PhrasesFragment : Fragment() {

    //Attribute
    private var words: ArrayList<Word>? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var onAudioFocusChangeListener: AudioManager.OnAudioFocusChangeListener? = null
    private var completionListener: MediaPlayer.OnCompletionListener? = null
    private var adapter: WordAdapter? = null
    private var word: Word? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate and return the layout for this fragment
        return inflater.inflate(R.layout.word_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.volumeControlStream = AudioManager.STREAM_MUSIC

        // Create and setup the (@link AudioManager) to request audio focus
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        onAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
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

        words!!.add(Word(getString(R.string.where_are_you_going_in_miwok), getString(R.string.where_are_you_going), R.raw.phrase_where_are_you_going))
        words!!.add(Word(getString(R.string.what_is_your_name_in_miwok), getString(R.string.what_is_your_name), R.raw.phrase_what_is_your_name))
        words!!.add(Word(getString(R.string.my_name_is_in_miwok), getString(R.string.my_name_is), R.raw.phrase_my_name_is))
        words!!.add(Word(getString(R.string.how_are_you_feeling_in_miwok), getString(R.string.how_are_you_feeling), R.raw.phrase_how_are_you_feeling))
        words!!.add(Word(getString(R.string.i_am_feeling_good_in_miwok), getString(R.string.i_am_feeling_good), R.raw.phrase_im_feeling_good))
        words!!.add(Word(getString(R.string.are_you_coming_in_miwok), getString(R.string.are_you_coming), R.raw.phrase_are_you_coming))
        words!!.add(Word(getString(R.string.yes_i_am_coming_in_miwok), getString(R.string.yes_i_am_coming), R.raw.phrase_yes_im_coming))
        words!!.add(Word(getString(R.string.i_am_coming_in_miwok), getString(R.string.i_am_coming), R.raw.phrase_im_coming))
        words!!.add(Word(getString(R.string.lets_go_in_miwok), getString(R.string.lets_go), R.raw.phrase_lets_go))
        words!!.add(Word(getString(R.string.come_here_in_miwok), getString(R.string.come_here), R.raw.phrase_come_here))

        adapter = WordAdapter(activity!!, words!!, R.color.category_phrases)

        wordListListView?.adapter = adapter
        wordListListView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //Release the media player if it currently exists because we are about to
            //play a different sound file
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