package com.aidenbarrett.component;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class DetailActivity extends Activity {
    public static final String EXTRA_COMPONENT_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Gets a reference to the fragment
        ComponentDetailFragment componentDetailFragment = (ComponentDetailFragment)
                getFragmentManager().findFragmentById(R.id.detail_frag);
        // Gets the ID of the component the user clicks on from the intent
        int componentId = (int) getIntent().getExtras().get(EXTRA_COMPONENT_ID);
        // Pass the component ID to the fragment
        componentDetailFragment.setComponent(componentId);
    }
}