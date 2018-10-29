package com.example.jenssellen.hangmangame;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
        String theme = sharedPreferences.getString("THEME", "regular");
        if (theme.equals("regular")){
            setTheme(R.style.ReversePink);
        } else if (theme.equals("halloween")){
            setTheme(R.style.ReverseHalloween);
        }
        setContentView(R.layout.activity_settings);
    }

    public void normalThemeButtonClicked(View view) {
        sharedPreferences.edit().putString("THEME", "regular").apply();
        recreate();
    }

    public void halloweenThemeButtonClicked(View view) {
        sharedPreferences.edit().putString("THEME", "halloween").apply();
        recreate();
    }
}
