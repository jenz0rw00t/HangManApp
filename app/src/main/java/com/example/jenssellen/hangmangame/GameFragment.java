package com.example.jenssellen.hangmangame;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }

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
    SharedPreferences sharedPreferences;
    String theme = "regular";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.activity_game, container, false);
        guessInput = v.findViewById(R.id.guessInput);
        triesLeftNumberView = v.findViewById(R.id.triesLeftNumber);
        guessedCharsView = v.findViewById(R.id.guessedChars);
        wordTextView = v.findViewById(R.id.wordText);
        hangImg = v.findViewById(R.id.hangmanImage);

        gameWords.add(getString(R.string.hello_word));
        gameWords.add(getString(R.string.app_word));
        gameWords.add(getString(R.string.welcome_word));
        gameWords.add(getString(R.string.tiger_word));

        sharedPreferences = this.getActivity().getSharedPreferences("gameDetails", getContext().MODE_PRIVATE);

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

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.game_bar, menu);
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
}
