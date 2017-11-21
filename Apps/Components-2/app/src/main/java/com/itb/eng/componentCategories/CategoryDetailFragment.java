package com.itb.eng.componentCategories;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itb.eng.components.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    int categoryId;
    private static final String CATEGORY_ID = "categoryId";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            categoryId = savedInstanceState.getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textCategoryTitle);
            ComponentCategory componentCategory = ComponentCategory.componentCategories.get(categoryId);
            title.setText(componentCategory.getCategory());

            TextView description = (TextView) view.findViewById(R.id.textCategoryDetail);
            description.setText(componentCategory.getDetails());
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(CATEGORY_ID, categoryId);
    }

    public void setCategoryId(int id) {
        this.categoryId = id;
    }

}
