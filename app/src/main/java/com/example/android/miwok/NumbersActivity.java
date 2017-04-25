package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;
    private WordAdapter mAdapter;
    private ListView mListView;
    private MediaPlayer mMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mWords = new ArrayList<>() ;

        mWords.add(new Word(getString(R.string.numberOneInMiwok), getString(R.string.numberOne), R.mipmap.number_one));
        mWords.add(new Word(getString(R.string.numberTwoInMiwok), getString(R.string.numberTwo), R.mipmap.number_two));
        mWords.add(new Word(getString(R.string.numberThreeInMiwok), getString(R.string.numberThree), R.mipmap.number_three));
        mWords.add(new Word(getString(R.string.numberFourInMiwok), getString(R.string.numberFour), R.mipmap.number_four));
        mWords.add(new Word(getString(R.string.numberFiveInMiwok), getString(R.string.numberFive), R.mipmap.number_five));
        mWords.add(new Word(getString(R.string.numberSixInMiwok), getString(R.string.numberSix), R.mipmap.number_six));
        mWords.add(new Word(getString(R.string.numberSevenInMiwok), getString(R.string.numberSeven), R.mipmap.number_seven));
        mWords.add(new Word(getString(R.string.numberEightInMiwok), getString(R.string.numberEight), R.mipmap.number_eight));
        mWords.add(new Word(getString(R.string.numberNineInMiwok), getString(R.string.numberNine), R.mipmap.number_nine));
        mWords.add(new Word(getString(R.string.numberTenInMiwok), getString(R.string.numberTen), R.mipmap.number_ten));

        mAdapter = new WordAdapter(this, mWords, R.color.category_numbers);

        mListView = (ListView) findViewById(R.id.wordList);

        mListView.setAdapter(mAdapter);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mMp = MediaPlayer.create(getApplicationContext(), R.raw.number_one);
                mMp.start();
            }
        });
    }
}

//        Word w = new Word(getString(R.string.numberOne), getString(R.string.numberOne)) ;
//        words.add(w) ;
//        words.add(getString(R.string.numberOne));
//        words.add(getString(R.string.numberTwo));
//        words.add(getString(R.string.numberThree));
//        words.add(getString(R.string.numberFour));
//        words.add(getString(R.string.numberFive));
//        words.add(getString(R.string.numberSix));
//        words.add(getString(R.string.numberSeven));
//        words.add(getString(R.string.numberEight));
//        words.add(getString(R.string.numberNine));
//        words.add(getString(R.string.numberTen));

//LinearLayout rootView = (LinearLayout) findViewById(R.id.list) ;

//        int i = 0 ;
//        while (i < words.size()){
//
//            TextView wordView = new TextView(this) ;
//            wordView.setText(words.get(i));
//            rootView.addView(wordView);
//
//            i++ ;
//        }

//        for(int i = 0; i < words.size(); i++){
//
//            TextView wordView = new TextView(this) ;
//            wordView.setText(words.get(i));
//            rootView.addView(wordView);
//        }

//        TextView wordView = new TextView(this) ;
//        wordView.setText(words.get(0));
//        rootView.addView(wordView);
//
//        TextView wordView1 = new TextView(this) ;
//        wordView1.setText(words.get(1));
//        rootView.addView(wordView1);
//
//        TextView wordView2 = new TextView(this) ;
//        wordView2.setText(words.get(2));
//        rootView.addView(wordView2);

//        Log.v("NumbersActivity", "The first number in the array is: " + numbers.get(0)) ;
//        Log.v("NumbersActivity", "The second number in the array is: " + numbers.get(1)) ;
//        Log.v("NumbersActivity", "The third number in the array is: " + numbers.get(2)) ;
//        Log.v("NumbersActivity", "The fourth number in the array is: " + numbers.get(3)) ;
//        Log.v("NumbersActivity", "The fifth number in the array is: " + numbers.get(4)) ;
//        Log.v("NumbersActivity", "The sixth number in the array is: " + numbers.get(5)) ;
//        Log.v("NumbersActivity", "The seventh number in the array is: " + numbers.get(6)) ;
//        Log.v("NumbersActivity", "The eighth number in the array is: " + numbers.get(7)) ;
//        Log.v("NumbersActivity", "The ninth number in the array is: " + numbers.get(8)) ;
//        Log.v("NumbersActivity", "The tenth number in the array is: " + numbers.get(9)) ;

//        String[] words = new String[10];
//
//        words[0] = "One" ;
//        words[1] = "Two" ;
//        words[2] = "Three" ;
//        words[3] = "Four" ;
//        words[4] = "Five" ;
//        words[5] = "Six" ;
//        words[6] = "Seven" ;
//        words[7] = "Eight" ;
//        words[8] = "Nine" ;
//        words[9] = "Ten" ;