package com.example.jenssellen.hangmangame;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.playGameButton).setOnClickListener(this::playButtonClicked);
        getActivity().findViewById(R.id.aboutButton).setOnClickListener(this::aboutButtonClicked);
        getActivity().findViewById(R.id.settingsButton).setOnClickListener(this::settingsButtonClicked);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_bar, menu);
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

    public void aboutButtonClicked(View view) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        AboutFragment aboutFragment = new AboutFragment();
        fragmentTransaction.replace(R.id.frameLayout, aboutFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void playButtonClicked(View view) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        GameFragment gameFragment = new GameFragment();
        fragmentTransaction.replace(R.id.frameLayout, gameFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void settingsButtonClicked(View view) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(R.id.frameLayout, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
