package com.example.android.miwok

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

/**
 * Created by unknown on 2/4/17
 */

internal class WordAdapter(context: Activity, words: ArrayList<Word>, private var colorResourceId: Int) : ArrayAdapter<Word>(context, 0, words) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.list_item, parent, false)
        }

        val currentWord = getItem(position)

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        val iconView = listItemView!!.listViewImageViewAvatar
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        if (currentWord!!.hasImage()) {
            iconView.setImageResource(currentWord.imageResourceId)
            iconView.visibility = View.VISIBLE
        } else {
            iconView.visibility = View.GONE
        }

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        val miwokTextView = listItemView.notMiwokTranslationTextView
        // Get the miwok name from the current Word object and
        // set this text on the name TextView
        miwokTextView.text = currentWord.miwokTranslation

        // Find the TextView in the list_item.xml layout with the ID version_number
        val englishTextView = listItemView.miwokTranslationTextView
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        englishTextView.text = currentWord.defaultTranslation

        // Set the theme color for the list item
        val textContainer = listItemView.listViewLayoutContainer
        // Find the color that the resource ID maps to
        val color = ContextCompat.getColor(context, colorResourceId)
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color)

        return listItemView
    }
}