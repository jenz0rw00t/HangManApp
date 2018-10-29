package com.example.jenssellen.hangmangame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
        String theme = sharedPreferences.getString("THEME", "regular");
        if (theme.equals("regular")){
            setTheme(R.style.Pink);
        } else if (theme.equals("halloween")){
            setTheme(R.style.Halloween);
        }
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String theWord = intent.getStringExtra("THE_WORD");
        int triesLeft = intent.getIntExtra("TRIES_LEFT", 10);

        TextView wonOrLostView = findViewById(R.id.wonOrLostText);
        TextView theWordView = findViewById(R.id.theWordText);
        TextView triesLeftView = findViewById(R.id.triesLeft);

        if (triesLeft>0){
            wonOrLostView.setText(getString(R.string.won_text));
        } else {
            wonOrLostView.setText(getString(R.string.lost_text));
        }

        theWordView.setText(theWord);

        triesLeftView.setText(String.valueOf(triesLeft));
    }

    public void menuButtonClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
