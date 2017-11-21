package com.aidenbarrett.component;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//The class extends the Android Fragment class
public class ComponentDetailFragment extends Fragment {
    // This is the ID of the component the user chooses
    private int componentId;
    private static final String COMPONENT_ID = "componentId";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            componentId = savedInstanceState.getInt(COMPONENT_ID);
        }
    }

    // onCreateView() method is called when Android needs the fragment’s layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Set the value of the componentId
            componentId = savedInstanceState.getInt(COMPONENT_ID);
        }
        // This tells Android which layout the fragment uses (in this case, it’s fragment_component_detail).
        return inflater.inflate(R.layout.fragment_component_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // The getView() method gets the fragment's root View.
        // This can be used to get references to the  component title and description text views
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Component component = Component.components.get(componentId);
            title.setText(component.getName());

            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(component.getDescription());
        }
    }

    // Save the value of the workoutId in the savedInstanceState Bundle before the
    // fragment gets destroyed. Value is retrieved in the onCreateView() method.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(COMPONENT_ID, componentId);
    }

    // This is a setter method for the workout ID. The activity will use this method to set the value of the workout ID.
    public void setComponent(int id) {
        this.componentId = id;
    }

}
