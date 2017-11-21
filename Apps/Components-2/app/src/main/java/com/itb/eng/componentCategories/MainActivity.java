package com.itb.eng.componentCategories;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.itb.eng.components.R;

public class MainActivity extends Activity implements CategoryListFragment.CategoryListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void categoryItemClicked(long id) {

        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            CategoryDetailFragment details = new CategoryDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setCategoryId((int)id);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else {
            Intent intent = new Intent(this, CategoryDetailActivity.class);
            intent.putExtra(CategoryDetailActivity.EXTRA_CATEGORY_ID, (int) id);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                Intent intent = new Intent(this, AddCategory.class); startActivity(intent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
