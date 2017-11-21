package com.aidenbarrett.component;

/**
 * Created by AidenBarrett on 03/11/2016.
 */

public class Component {
    private String name;
    private String description;
    public static final Component[] components = {
            new Component("Capacitors",
                    "0.1uF\n1uF\n10uF\n100uF"),
            new Component("Diodes",
                    "1N4148\n1N4001\nZenner"),
            new Component("Transistors",
                    "2N3906 PNP Transistor\n2N3904 NPN Transistor"),
            new Component("LEDs",
                    "RGB\nRed\nGreen\nYellow")
    };
    //Each Component has a name and description
    private Component(String name, String description) {
        this.name = name;
        this.description = description;
    }
    //This is a getter for the private variable
    public String getDescription() {
        return description;
    }
    //This is a getter for the private variable
    public String getName() {
        return name;
    }
    //String representation of Component is its name
    public String toString() {
        return this.name;
    }
}
