package com.example.jenssellen.hangmangame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                return(true);
            case R.id.playGame:
                Intent intent2 = new Intent(this, GameActivity.class);
                startActivity(intent2);
                return(true);
            case R.id.settings:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuButtonClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
