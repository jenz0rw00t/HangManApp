package com.example.jenssellen.hangmangame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button playGamebutton = findViewById(R.id.playGameButton);

        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
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
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        recreate();
    }
}
