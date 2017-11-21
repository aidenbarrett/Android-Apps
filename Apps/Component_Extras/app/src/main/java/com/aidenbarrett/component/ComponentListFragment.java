package com.aidenbarrett.component;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.ListView;

public class ComponentListFragment extends ListFragment {

    static interface ComponentListListener {
        void itemClicked(long id);
    }
    private ComponentListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] names = new String[Component.components.length];
        for (int i = 0; i < names.length; i++) {
            // Creates a string array with the component names
            names[i] = Component.components[i].getName();
        }

        // Creates and Array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                // Gets context from the layout inflater
                inflater.getContext(), android.R.layout.simple_list_item_1,
                names);
        // Binds the array adapter to the list view
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ComponentListListener)activity;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
}

