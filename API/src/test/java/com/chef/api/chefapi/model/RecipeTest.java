package com.chef.api.chefapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Recipe class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class RecipeTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        List<Ingredient> ings = new ArrayList<Ingredient>();

        // Invoke
        Recipe recipe = new Recipe(expected_id,expected_name, ings);

        // Analyze
        assertEquals(expected_id,recipe.getId());
        assertEquals(expected_name,recipe.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        List<Ingredient> ings = new ArrayList<Ingredient>();
        Recipe recipe = new Recipe(id,name,ings);

        String expected_name = "Galactic Agent";

        // Invoke
        recipe.setName(expected_name);

        // Analyze
        assertEquals(expected_name,recipe.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Recipe.STRING_FORMAT,id,name);
        List<Ingredient> ings = new ArrayList<Ingredient>();
        Recipe recipe = new Recipe(id,name,ings);

        // Invoke
        String actual_string = recipe.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}