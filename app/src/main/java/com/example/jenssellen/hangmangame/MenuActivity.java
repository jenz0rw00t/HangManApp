package com.example.jenssellen.hangmangame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
        theme = sharedPreferences.getString("THEME", "regular");
        if (theme.equals("regular")){
            setTheme(R.style.ReversePink);
        } else if (theme.equals("halloween")){
            setTheme(R.style.ReverseHalloween);
        }
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

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
                aboutButtonClicked(item.getActionView());
                return(true);
            case R.id.playGame:
                playButtonClicked(item.getActionView());
                return(true);
            case R.id.settings:
                settingsButtonClicked(item.getActionView());
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);

        String themeNow = sharedPreferences.getString("THEME", "regualar");

        if(!themeNow.equals(theme)){
            recreate();
        }

        Button playGamebutton = findViewById(R.id.playGameButton);

        if(sharedPreferences.getBoolean("ACTIVE_GAME", false)){
            playGamebutton.setText(getString(R.string.continue_button));
        } else {
            playGamebutton.setText(R.string.play_game_button);
        }


    }

    public void aboutButtonClicked(View view) {
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

    public void playButtonClicked(View view) {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    public void settingsButtonClicked(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

}
