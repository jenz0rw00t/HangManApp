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

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_bar, menu);
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
        }
        return super.onOptionsItemSelected(item);
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
