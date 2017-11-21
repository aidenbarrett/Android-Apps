package com.itb.eng.componentCategories;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryListFragment extends ListFragment {

    interface CategoryListListener {
        void categoryItemClicked(long id);
    };

    private CategoryListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] categories = new String[ComponentCategory.componentCategories.size()];

        for (int i = 0; i < categories.length; i++) {
            categories[i] = ComponentCategory.componentCategories.get(i).getCategory();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                categories);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (CategoryListListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.categoryItemClicked(id);
        }
    }
}
