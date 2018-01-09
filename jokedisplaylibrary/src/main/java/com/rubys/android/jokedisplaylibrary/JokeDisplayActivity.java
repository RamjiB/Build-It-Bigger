package com.rubys.android.jokedisplaylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    private static final String TAG = "JokeDisplayActivity";

    public final static String INTENT_JOKE = "INTENT_JOKE";

    String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        if (getIntent().hasExtra(INTENT_JOKE)) {
            joke = getIntent().getStringExtra(INTENT_JOKE);
            Log.i(TAG, "Joke: " + joke);
        }

        TextView jokeTextView = (TextView) findViewById(R.id.jokeText);
        jokeTextView.setText(joke);

    }
}
