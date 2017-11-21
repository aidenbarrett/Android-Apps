package com.aidenbarrett.beeradvisor;

/**
 * Created by AidenBarrett on 19/09/16.
 */

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands(String color) {
        List<String> brands = new ArrayList<String>();
        if (color.equals("amber")) {
            brands.add("Heineken");
            brands.add("Carlsberg");
            brands.add("Tennants");
        } else if (color.equals("dark")) {
            brands.add("Guinness");
            brands.add("Murphy's Irish Stout");
        } else if (color.equals("brown")) {
            brands.add("Smithwicks");
            brands.add("Murphys Irish Red");
            brands.add("O'Hara's Red Ale");
        } else if (color.equals("craft")) {
            brands.add("Sierra Nevada IPA");
            brands.add("8 Degree Hurricane");
        } else {
            brands.add("Light Beer?! \n\nGet back in the Sea.");
        }
        return brands;
    }
}

