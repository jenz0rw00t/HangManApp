package com.example.jenssellen.hangmangame;


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
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_bar, menu);
        menu.removeItem(R.id.about);
        ((MainFragmentActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

}
