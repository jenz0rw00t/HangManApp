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

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.normalThemeButton).setOnClickListener(this::normalThemeButtonClicked);
        getActivity().findViewById(R.id.halloweenThemeButton).setOnClickListener(this::halloweenThemeButtonClicked);
    }

    private void halloweenThemeButtonClicked(View view) {
        SharedPreferences setting = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("THEME", "halloween").apply();
        getActivity().recreate();
    }

    private void normalThemeButtonClicked(View view) {
        SharedPreferences setting = this.getActivity().getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("THEME", "regular").apply();
        getActivity().recreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new AboutFragment()).addToBackStack(null).commit();
                return(true);
            case R.id.playGame:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new GameFragment()).addToBackStack(null).commit();
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_bar, menu);
        menu.removeItem(R.id.settings);
        ((MainFragmentActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
