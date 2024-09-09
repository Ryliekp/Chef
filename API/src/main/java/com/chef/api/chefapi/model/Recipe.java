package com.chef.api.chefapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
/**
 * Represents a Recipe entity
 * 
 * @author SWEN Faculty
 */
public class Recipe {
    private static final Logger LOG = Logger.getLogger(Recipe.class.getName());

    // Package private for tests
    // static final String STRING_FORMAT = "Recipe [id=%d, name=%s, ingredients=]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("ingredients") private List<Ingredient> ingredients;

    /**
     * Create a recipe with the given id and name
     * @param id The id of the recipe
     * @param name The name of the recipe
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Recipe(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("ingredients") 
            List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    /**
     * Retrieves the id of the recipe
     * @return The id of the recipe
     */
    public int getId() {return id;}

    /**
     * Sets the name of the recipe - necessary for JSON object to Java object deserialization
     * @param name The name of the recipe
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the recipe
     * @return The name of the recipe
     */
    public String getName() {return name;}

    /**
     * Retrieves the list of ingredients
     * @return The list of ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;}

    /**
     * Retrieves the list of ingredients
     * @return The list of ingredients
     */
    public List<Ingredient> getIngredients() {return ingredients;}
}