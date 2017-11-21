package com.itb.eng.componentCategories;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by btoland on 18/11/2016.
 */

public class ComponentCategory {

    private String category;
    private String details;

    public static ArrayList<ComponentCategory> componentCategories = new ArrayList<ComponentCategory>(Arrays.asList(
            new ComponentCategory("SBC", "Beaglebone\nRaspberry Pi 3\nGalileo"),
            new ComponentCategory("Micro Dev Board", "Arduino"),
            new ComponentCategory("IC", "HTC14\nMAX232"),
            new ComponentCategory("Discrete", "LED\nResistor")));


    public String getCategory() {
        return category;
    }

    public String getDetails() {
        return details;
    }

    private ComponentCategory(String name, String details)
    {
        this.category = name;
        this.details = details;
    }

    public static void addPart(String category, String details) {
        componentCategories.add(new ComponentCategory(category, details));
    }

}
