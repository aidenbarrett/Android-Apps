package com.aidenbarrett.component;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AidenBarrett on 03/11/2016.
 */

public class Component {

    private String name;
    private String description;

    public static ArrayList<Component> components = new ArrayList<Component>(Arrays.asList(
            new Component("Capacitors", "0.1uF\n1uF\n10uF\n100uF"),
            new Component("Diodes", "1N4148\n1N4001\nZenner"),
            new Component("Transistors", "2N3906 PNP Transistor\n2N3904 NPN Transistor"),
            new Component("LEDs", "RGB\nRed\nGreen\nYellow")));


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private Component(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public static void addPart(String name, String description) {
        components.add(new Component(name, description));
    }

}

