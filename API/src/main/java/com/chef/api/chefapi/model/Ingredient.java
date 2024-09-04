package com.chef.api.chefapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private static final Logger LOG = Logger.getLogger(Ingredient.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Ingredient [id=%d, name=%s, quantity=%f, unit=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("quantity") private double quantity;
    @JsonProperty("unit") private String unit;

    /**
     * Create a recipe with the given id and name
     * @param id The id of the ingredient
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit the ingredient is measured in
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Ingredient(@JsonProperty("id") int id, @JsonProperty("name") String name, 
            @JsonProperty("quantity") double quantity, @JsonProperty("unit") String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Retrieves the id of the recipe
     * @return The id of the recipe
     */
    public int getId() {return id;}

    /**
     * Sets the name of the ingredient - necessary for JSON object to Java object deserialization
     * @param name The name of the ingredient
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the ingredient of the recipe
     * @return The ingredient of the recipe
     */
    public String getName() {return name;}

    public void setQuantity(double quantity) {this.quantity = quantity;}

    public double getQuantity() {return quantity;}

    public void setUnit(String unit) {this.unit = unit;}

    public String getUnits() {return unit;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name, quantity, unit);
    }
}
