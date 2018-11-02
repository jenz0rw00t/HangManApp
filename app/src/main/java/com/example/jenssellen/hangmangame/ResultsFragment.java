package com.example.jenssellen.hangmangame;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    private HangmanGame game;

    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.results_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.menuButton).setOnClickListener(this::menuButtonClicked);
        TextView wonOrLostView = getActivity().findViewById(R.id.wonOrLostText);
        TextView theWordView = getActivity().findViewById(R.id.theWordText);
        TextView triesLeftView = getActivity().findViewById(R.id.triesLeft);

        game = ((MainFragmentActivity)getActivity()).getGame();

        if (game.getTriesLeft()>0){
            wonOrLostView.setText(getString(R.string.won_text));
        } else {
            wonOrLostView.setText(getString(R.string.lost_text));
        }

        theWordView.setText(game.getTheWord());

        triesLeftView.setText(String.valueOf(game.getTriesLeft()));

        game.setActiveGame(false);
    }


    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences setting = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean("ACTIVE_GAME", game.isActiveGame()).apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_bar, menu);
        ((MainFragmentActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack("MENU", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return (true);
            case R.id.about:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
                return(true);
            case R.id.playGame:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new GameFragment())
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

    private void menuButtonClicked(View view) {
        getActivity().getSupportFragmentManager().popBackStack("MENU", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
