package com.example.jenssellen.hangmangame;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_about);
    }
}
