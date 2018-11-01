package com.example.jenssellen.hangmangame;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }

    private EditText guessInput;
    private TextView triesLeftNumberView;
    private TextView guessedCharsView;
    private TextView wordTextView;
    private ImageView hangImg;
    private HangmanGame game;
    private SharedPreferences sharedPreferences;
    private Context context;
    private String theme = "regular";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_fragment, container, false);
        guessInput = v.findViewById(R.id.guessInput);
        triesLeftNumberView = v.findViewById(R.id.triesLeftNumber);
        guessedCharsView = v.findViewById(R.id.guessedChars);
        wordTextView = v.findViewById(R.id.wordText);
        hangImg = v.findViewById(R.id.hangmanImage);

        context = getContext();

        sharedPreferences = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        theme = sharedPreferences.getString("THEME", "regular");

        game = ((MainFragmentActivity)getActivity()).getGame();

        wordTextView.setText(game.getHiddenWord());
        triesLeftNumberView.setText(String.valueOf(game.getTriesLeft()));
        guessedCharsView.setText(game.getWrongGuesses());

        setHangImg();

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.guessButton).setOnClickListener(this::guessButtonClicked);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.game_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newWord:
                game.clearGuesses();
                if (!game.getAllWordsSet().isEmpty()){
                    game.newWord();
                } else {
                    game.setActiveGame(false);
                    SharedPreferences setting = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.clear();
                    editor.putBoolean("ACTIVE_GAME", game.isActiveGame());
                    editor.putString("THEME", theme).apply();
                    game = ((MainFragmentActivity)getActivity()).getGame();
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .detach(GameFragment.this)
                        .attach(GameFragment.this)
                        .commit();
                return(true);
            case R.id.about:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
                return(true);
            case R.id.settings:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences setting = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("THE_WORD", game.getTheWord());
        editor.putBoolean("ACTIVE_GAME", game.isActiveGame());
        editor.putStringSet("GUESSES_SET", game.getGuessesList());
        editor.putStringSet("ALL_WORDS_SET", game.getAllWordsSet());
        editor.putString("THEME", theme);
        editor.apply();
    }

    public void guessButtonClicked(View view) {
        if(guessInput.getText().toString().length()<= 0){
            showToast(getString(R.string.one_char_toast));
        } else {
            char guess = guessInput.getText().toString().toUpperCase().charAt(0);
            if(guessInput.getText().toString().length()>1) {
                showToast(getString(R.string.one_char_toast));
            } else if (!Character.isAlphabetic(guess)){
                showToast(getString(R.string.only_alph_toast));
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
                    showToast(getString(R.string.guessed_toast));
                    guessInput.setText("");
                }
            }
        }
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
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new ResultsFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showToast(String toastText){
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

}
