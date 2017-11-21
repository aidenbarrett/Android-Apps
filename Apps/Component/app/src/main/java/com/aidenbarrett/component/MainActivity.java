package com.aidenbarrett.component;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;



public class MainActivity extends Activity
                          implements ComponentListFragment.ComponentListListener{

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(long id) {
        // Get a reference to the frame layout that contains ComponentDetailFragment
        View fragmentContainer = findViewById(R.id.fragment_container);

        // if FrameLayout exists, run:
        if (fragmentContainer != null) {
            ComponentDetailFragment details = new ComponentDetailFragment();
            // This starts the fragment transaction.
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setComponent(id);
            // This replaces the fragment and adds it to the back stack.
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            // This gets the new and old fragments to fade in and out.
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // Commits the transaction.
            ft.commit();
        }

        else {
            Intent intent = new Intent(this, DetailActivity.class);
            // If FrameLayout doesn't exist, must be using a small screen.
            // Starts DetailActivity, passing it the ID of the component.
            intent.putExtra(DetailActivity.EXTRA_COMPONENT_ID, (int)id);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                //Code to run when the Create Order item is clicked
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
