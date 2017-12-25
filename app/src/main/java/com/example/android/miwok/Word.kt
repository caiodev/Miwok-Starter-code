package com.example.android.miwok

/**
 * Created by unknown on 2/4/17
 */

/**
 * [Word] represents a vocabulary word that the user wants to learn.
 * It contains a default translation, a Miwok translation, and an image for that word.
 */
internal class Word {

    private val noImageProvided = -1

    /**
     * Default translation for the word
     */
    /**
     * Get the default translation of the word.
     */
    var defaultTranslation: String? = null
        private set
    /**
     * Miwok translation for the word
     */

    /**
     * Get the Miwok translation of the word.
     */
    var miwokTranslation: String? = null
        private set
    /**
     * Image resource ID for the word
     */

    /**
     * Return the image resource ID of the word.
     */
    var imageResourceId = noImageProvided
        private set
    /**
     * Audio resource ID for the audio
     */
    var audioResourceId: Int = 0
        private set

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     * (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     */
    constructor(defaultTranslation: String, miwokTranslation: String, mAudioResourceId: Int) {
        this.defaultTranslation = defaultTranslation
        this.miwokTranslation = miwokTranslation
        this.audioResourceId = mAudioResourceId
    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     * (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param imageResourceId    is the drawable resource ID for the image associated with the word
     */
    constructor(defaultTranslation: String, miwokTranslation: String, imageResourceId: Int, mAudioResourceId: Int) {
        this.defaultTranslation = defaultTranslation
        this.miwokTranslation = miwokTranslation
        this.imageResourceId = imageResourceId
        this.audioResourceId = mAudioResourceId
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    fun hasImage(): Boolean {
        return imageResourceId != noImageProvided
    }
}