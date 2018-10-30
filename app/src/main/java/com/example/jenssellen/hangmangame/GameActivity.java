package com.example.jenssellen.hangmangame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GameActivity extends AppCompatActivity {

    // Användarens gissning
    private EditText guessInput;
    // Nummret av försök kvar
    private TextView triesLeftNumberView;
    // Bokstäver som användaren gissat
    private TextView guessedCharsView;
    // Ordet som visas
    private TextView wordTextView;
    // Hangman bilden som visas
    private ImageView hangImg;
    //ArrayList med ord
    Set<String> gameWords = new HashSet<>();
    HangmanGame game;

    private String theme;

    private Context context;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("gameDetails", MODE_PRIVATE);
        theme = sharedPreferences.getString("THEME", "regular");
        if (theme.equals("regular")){
            setTheme(R.style.Pink);
        } else if (theme.equals("halloween")){
            setTheme(R.style.Halloween);
        }
        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        guessInput = findViewById(R.id.guessInput);
        triesLeftNumberView = findViewById(R.id.triesLeftNumber);
        guessedCharsView = findViewById(R.id.guessedChars);
        wordTextView = findViewById(R.id.wordText);
        hangImg = findViewById(R.id.hangmanImage);
        context = getApplicationContext();

        //laddar in ord på listan
        gameWords.add(getString(R.string.hello_word));
        gameWords.add(getString(R.string.app_word));
        gameWords.add(getString(R.string.welcome_word));
        gameWords.add(getString(R.string.tiger_word));

        if(sharedPreferences.getBoolean("ACTIVE_GAME", false)){
            Set<String> allWordsSet = sharedPreferences.getStringSet("ALL_WORDS_SET",new HashSet<String>());
            Set<String> guessesSet = sharedPreferences.getStringSet("GUESSES_SET",new HashSet<String>());
            String theWord = sharedPreferences.getString("THE_WORD", "");
            game = new HangmanGame(allWordsSet , theWord , guessesSet);
        } else {
            Set<String> allWordsSet = sharedPreferences.getStringSet("ALL_WORDS_SET",new HashSet<String>());
            if (allWordsSet.isEmpty()) {
                game = new HangmanGame(gameWords);
            } else {
                game = new HangmanGame(allWordsSet);
            }
        }

        wordTextView.setText(game.getHiddenWord());
        triesLeftNumberView.setText(String.valueOf(game.getTriesLeft()));
        guessedCharsView.setText(game.getWrongGuesses());

        setHangImg();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                return(true);
            case R.id.newWord:
                game.clearGuesses();
                if (!game.getAllWordsSet().isEmpty()){
                    game.newWord();
                } else {
                    game = new HangmanGame(gameWords);
                }
                recreate();
                return(true);
            case R.id.settings:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent3, 1);
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String themeNow = sharedPreferences.getString("THEME", "regualar");

        if(!themeNow.equals(theme)){
            recreate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ACTIVE_GAME", game.isActiveGame());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("THE_WORD", game.getTheWord());
        editor.putBoolean("ACTIVE_GAME", game.isActiveGame());
        editor.putStringSet("GUESSES_SET", game.getGuessesList());
        editor.putStringSet("ALL_WORDS_SET", game.getAllWordsSet());
        editor.apply();
    }

    public void guessButtonClicked(View view) {
        if(guessInput.getText().toString().length()<= 0){
            noCharToast();
        } else {
            char guess = guessInput.getText().toString().toUpperCase().charAt(0);
            if(guessInput.getText().toString().length()>1) {
                moreCharToast();
            } else if (!Character.isAlphabetic(guess)){
                notAlphabeticToast();
            } else {
                if (!game.hasUsedLetter(guess)) {
                    game.guess(guess);
                    hasWonOrLost();
                    triesLeftNumberView.setText(String.valueOf(game.getTriesLeft()));
                    guessedCharsView.setText(game.getWrongGuesses());
                    wordTextView.setText(game.getHiddenWord());
                    setHangImg();
                    guessInput.setText("");
                } else {
                    guessedToast();
                    guessInput.setText("");
                }
            }
        }
    }

    private void guessedToast() {
        Toast toast = Toast.makeText(context, R.string.guessed_toast, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

    private void notAlphabeticToast() {
        Toast toast = Toast.makeText(context, getString(R.string.only_alph_toast), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

    private void moreCharToast() {
        Toast toast = Toast.makeText(context, getString(R.string.one_char_toast), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

    private void noCharToast() {
        Toast toast = Toast.makeText(context, getString(R.string.no_guess_toast), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

    private void setHangImg(){
        Picasso.get()
                .load("https://jenz0rw00t.github.io/HangManApp/app/src/main/res/"+theme+"/hang"+game.getTriesLeft()+".png")
                .placeholder(R.drawable.hang10)
                .fit()
                .centerInside()
                .error(R.mipmap.ic_launcher_round)
                .into(hangImg);
    }

    private void hasWonOrLost() {
        if (game.hasWon() || game.hasLost()) {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("TRIES_LEFT", game.getTriesLeft());
            intent.putExtra("THE_WORD", game.getTheWord());
            startActivity(intent);
            finish();
        }
    }
}
