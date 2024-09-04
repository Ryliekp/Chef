package com.chef.api.chefapi.persistence;

import java.io.IOException;
import com.chef.api.chefapi.model.Ingredient;

/**
 * Defines the interface for Ingredient object persistence
 * 
 * @author SWEN Faculty
 */
public interface IngredientDAO {
    /**
     * Retrieves all {@linkplain Ingredient ingredients}
     * 
     * @return An array of {@link Ingredient ingredient} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient[] getIngredients() throws IOException;

    /**
     * Finds all {@linkplain Ingredient ingredients} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Ingredient ingredients} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient[] findIngredients(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Ingredient ingredient} with the given id
     * 
     * @param id The id of the {@link Ingredient ingredient} to get
     * 
     * @return a {@link Ingredient ingredient} object with the matching id
     * <br>
     * null if no {@link Ingredient ingredient} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient getIngredient(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Ingredient ingredient}
     * 
     * @param ingredient {@linkplain Ingredient ingredient} object to be created and saved
     * <br>
     * The id of the ingredient object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Ingredient ingredient} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient createIngredient(Ingredient ingredient) throws IOException;

    /**
     * Updates and saves a {@linkplain Ingredient ingredient}
     * 
     * @param {@link Ingredient ingredient} object to be updated and saved
     * 
     * @return updated {@link Ingredient ingredient} if successful, null if
     * {@link Ingredient ingredient} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Ingredient updateIngredient(Ingredient ingredient) throws IOException;

    /**
     * Deletes a {@linkplain Ingredient ingredient} with the given id
     * 
     * @param id The id of the {@link Ingredient ingredient}
     * 
     * @return true if the {@link Ingredient ingredient} was deleted
     * <br>
     * false if ingredient with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteIngredient(int id) throws IOException;
}

