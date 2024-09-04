package com.chef.api.chefapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.chef.api.chefapi.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for Recipes
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class RecipeFileDAO implements RecipeDAO {
    private static final Logger LOG = Logger.getLogger(RecipeFileDAO.class.getName());
    Map<Integer,Recipe> recipes;   // Provides a local cache of the recipe objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Recipe
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new recipe
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Recipe File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public RecipeFileDAO(@Value("${recipes.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the recipes from the file
    }

    /**
     * Generates the next id for a new {@linkplain Recipe recipe}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Recipe recipes} from the tree map
     * 
     * @return  The array of {@link Recipe recipes}, may be empty
     */
    private Recipe[] getRecipesArray() {
        return getRecipesArray(null);
    }

    /**
     * Generates an array of {@linkplain Recipe recipes} from the tree map for any
     * {@linkplain Recipe recipes} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Recipe recipes}
     * in the tree map
     * 
     * @return  The array of {@link Recipe recipes}, may be empty
     */
    private Recipe[] getRecipesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        for (Recipe recipe : recipes.values()) {
            if (containsText == null || recipe.getName().contains(containsText)) {
                recipeArrayList.add(recipe);
            }
        }

        Recipe[] recipeArray = new Recipe[recipeArrayList.size()];
        recipeArrayList.toArray(recipeArray);
        return recipeArray;
    }

    /**
     * Saves the {@linkplain Recipe recipes} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Recipe recipes} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Recipe[] recipeArray = getRecipesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),recipeArray);
        return true;
    }

    /**
     * Loads {@linkplain Recipe recipes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        recipes = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of recipes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Recipe[] recipeArray = objectMapper.readValue(new File(filename),Recipe[].class);

        // Add each recipe to the tree map and keep track of the greatest id
        for (Recipe recipe : recipeArray) {
            recipes.put(recipe.getId(),recipe);
            if (recipe.getId() > nextId)
                nextId = recipe.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Recipe[] getRecipes() {
        synchronized(recipes) {
            return getRecipesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Recipe[] findRecipes(String containsText) {
        synchronized(recipes) {
            return getRecipesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Recipe getRecipe(int id) {
        synchronized(recipes) {
            if (recipes.containsKey(id))
                return recipes.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Recipe createRecipe(Recipe recipe) throws IOException {
        synchronized(recipes) {
            // We create a new recipe object because the id field is immutable
            // and we need to assign the next unique id
            Recipe newRecipe = new Recipe(nextId(),recipe.getName(), recipe.getIngredients());
            recipes.put(newRecipe.getId(),newRecipe);
            save(); // may throw an IOException
            return newRecipe;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Recipe updateRecipe(Recipe recipe) throws IOException {
        synchronized(recipes) {
            if (recipes.containsKey(recipe.getId()) == false)
                return null;  // recipe does not exist

            recipes.put(recipe.getId(),recipe);
            save(); // may throw an IOException
            return recipe;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteRecipe(int id) throws IOException {
        synchronized(recipes) {
            if (recipes.containsKey(id)) {
                recipes.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
