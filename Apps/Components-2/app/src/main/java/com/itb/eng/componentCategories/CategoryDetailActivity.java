package com.itb.eng.componentCategories;

import android.app.Activity;
import android.os.Bundle;

import com.itb.eng.components.R;

public class CategoryDetailActivity extends Activity {

    public static final String EXTRA_CATEGORY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CategoryDetailFragment workoutDetailFragment = (CategoryDetailFragment)
                getFragmentManager().findFragmentById(R.id.category_detail_frag);

        int workoutId = (int) getIntent().getExtras().get(EXTRA_CATEGORY_ID);

        workoutDetailFragment.setCategoryId(workoutId);
    }
}
