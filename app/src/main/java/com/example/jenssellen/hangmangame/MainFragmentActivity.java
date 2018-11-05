package com.example.jenssellen.hangmangame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class MainFragmentActivity extends AppCompatActivity {

    HangmanGame game;
    Set<String> gameWords = new HashSet<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
        String theme = sharedPreferences.getString("THEME", "regular");
        if (theme.equals("regular")){
            setTheme(R.style.Pink);
        } else if (theme.equals("halloween")){
            setTheme(R.style.Halloween);
        }
        setContentView(R.layout.activity_main_fragment);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        gameWords.add(getString(R.string.hello_word));
        gameWords.add(getString(R.string.app_word));
        gameWords.add(getString(R.string.welcome_word));
        gameWords.add(getString(R.string.tiger_word));


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new MenuFragment()).commit();
        }
    }

    public HangmanGame getGame() {
        sharedPreferences = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("ACTIVE_GAME", false)){
            Set<String> allWordsSet = sharedPreferences.getStringSet("ALL_WORDS_SET",new HashSet<>());
            Set<String> guessesSet = sharedPreferences.getStringSet("GUESSES_SET",new HashSet<>());
            String theWord = sharedPreferences.getString("THE_WORD", "");
            game = new HangmanGame(allWordsSet , theWord , guessesSet);
        } else {
            Set<String> allWordsSet = sharedPreferences.getStringSet("ALL_WORDS_SET",new HashSet<>());
            if (allWordsSet.isEmpty()) {
                game = new HangmanGame(gameWords);
            } else {
                game = new HangmanGame(allWordsSet);
            }
        }
        return game;
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack("MENU", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
}
