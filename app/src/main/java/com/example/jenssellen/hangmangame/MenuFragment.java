package com.example.jenssellen.hangmangame;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.playGameButton).setOnClickListener(this::playButtonClicked);
        getActivity().findViewById(R.id.aboutButton).setOnClickListener(this::aboutButtonClicked);
        getActivity().findViewById(R.id.settingsButton).setOnClickListener(this::settingsButtonClicked);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_bar, menu);
        ((MainFragmentActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                aboutButtonClicked(getView());
                return(true);
            case R.id.playGame:
                playButtonClicked(getView());
                return(true);
            case R.id.settings:
                settingsButtonClicked(getView());
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void aboutButtonClicked(View view) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new AboutFragment()).addToBackStack("MENU").commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);

        Button playGamebutton = getActivity().findViewById(R.id.playGameButton);

        if(sharedPreferences.getBoolean("ACTIVE_GAME", false)){
            playGamebutton.setText(getString(R.string.continue_button));
        } else {
            playGamebutton.setText(R.string.play_game_button);
        }
    }

    public void playButtonClicked(View view) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new GameFragment()).addToBackStack("MENU").commit();
    }

    public void settingsButtonClicked(View view) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new SettingsFragment()).addToBackStack("MENU").commit();
    }
}
