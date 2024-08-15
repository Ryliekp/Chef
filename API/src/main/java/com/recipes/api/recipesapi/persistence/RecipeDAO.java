package com.recipes.api.recipesapi.persistence;

import java.io.IOException;
import com.recipes.api.recipesapi.model.Recipe;

/**
 * Defines the interface for Recipe object persistence
 * 
 * @author SWEN Faculty
 */
public interface RecipeDAO {
    /**
     * Retrieves all {@linkplain Recipe recipes}
     * 
     * @return An array of {@link Recipe recipe} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe[] getRecipes() throws IOException;

    /**
     * Finds all {@linkplain Recipe recipes} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Recipe recipes} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe[] findRecipes(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Recipe recipe} with the given id
     * 
     * @param id The id of the {@link Recipe recipe} to get
     * 
     * @return a {@link Recipe recipe} object with the matching id
     * <br>
     * null if no {@link Recipe recipe} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe getRecipe(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Recipe recipe}
     * 
     * @param recipe {@linkplain Recipe recipe} object to be created and saved
     * <br>
     * The id of the recipe object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Recipe recipe} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe createRecipe(Recipe recipe) throws IOException;

    /**
     * Updates and saves a {@linkplain Recipe recipe}
     * 
     * @param {@link Recipe recipe} object to be updated and saved
     * 
     * @return updated {@link Recipe recipe} if successful, null if
     * {@link Recipe recipe} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Recipe updateRecipe(Recipe recipe) throws IOException;

    /**
     * Deletes a {@linkplain Recipe recipe} with the given id
     * 
     * @param id The id of the {@link Recipe recipe}
     * 
     * @return true if the {@link Recipe recipe} was deleted
     * <br>
     * false if recipe with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteRecipe(int id) throws IOException;
}
