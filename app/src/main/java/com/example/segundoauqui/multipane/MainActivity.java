package com.example.segundoauqui.multipane;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener{


    private static final String DETAILS_FRAGMENT_TAG = "Details";
    private static final String LIST_FRAGMENT_TAG =  "List";
    private static final String TAG = "Main";

    FrameLayout flFrag1;
    FrameLayout flFrag2;

    ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        flFrag1 = (FrameLayout) findViewById(R.id.flFrag1);
        flFrag2 = (FrameLayout) findViewById(R.id.flFrag2);





        DetailsFragment detailsFragment = DetailsFragment.newInstance ("-1","");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFrag1, detailsFragment, DETAILS_FRAGMENT_TAG)
                .addToBackStack(DETAILS_FRAGMENT_TAG)
                .commit();

        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFrag2, listFragment, LIST_FRAGMENT_TAG)
                .addToBackStack(LIST_FRAGMENT_TAG)
                .commit();




//Inflate the fragment
       // getFragmentManager().beginTransaction().add(R.id.).commit();

    }

    @Override
    public void onFragmentInteraction(String string) {

        DetailsFragment detailFragment = DetailsFragment.newInstance(string,"");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFrag1,detailFragment,DETAILS_FRAGMENT_TAG)
                .addToBackStack(DETAILS_FRAGMENT_TAG)
                .commit();
        Log.d(TAG, "onFragmentInteraction: " + string);

    }

    @Override
    public void onFragmentInteraction(int inter) {
        Log.d(TAG, "onFragmentInteraction: "+ inter);
        listFragment.NotificationChanged();
    }

    public void addFragments(View view){



    }
}
