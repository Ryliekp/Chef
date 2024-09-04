package com.chef.api.chefapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chef.api.chefapi.model.Ingredient;

/**
 * Implements the functionality for JSON file-based peristance for Ingredients
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class IngredientFileDAO implements IngredientDAO {
    private static final Logger LOG = Logger.getLogger(IngredientFileDAO.class.getName());
    Map<Integer,Ingredient> ingredients;   // Provides a local cache of the ingredient objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Ingredient
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new ingredient
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Ingredient File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public IngredientFileDAO(@Value("${ingredients.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the ingredients from the file
    }

    /**
     * Generates the next id for a new {@linkplain Ingredient ingredient}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Ingredient ingredients} from the tree map
     * 
     * @return  The array of {@link Ingredient ingredients}, may be empty
     */
    private Ingredient[] getIngredientsArray() {
        return getIngredientsArray(null);
    }

    /**
     * Generates an array of {@linkplain Ingredient ingredients} from the tree map for any
     * {@linkplain Ingredient ingredients} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Ingredient ingredients}
     * in the tree map
     * 
     * @return  The array of {@link Ingredient ingredients}, may be empty
     */
    private Ingredient[] getIngredientsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();

        for (Ingredient ingredient : ingredients.values()) {
            if (containsText == null || ingredient.getName().contains(containsText)) {
                ingredientArrayList.add(ingredient);
            }
        }

        Ingredient[] ingredientArray = new Ingredient[ingredientArrayList.size()];
        ingredientArrayList.toArray(ingredientArray);
        return ingredientArray;
    }

    /**
     * Saves the {@linkplain Ingredient ingredients} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Ingredient ingredients} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Ingredient[] ingredientArray = getIngredientsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),ingredientArray);
        return true;
    }

    /**
     * Loads {@linkplain Ingredient ingredients} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        ingredients = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of ingredients
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Ingredient[] ingredientArray = objectMapper.readValue(new File(filename),Ingredient[].class);

        // Add each ingredient to the tree map and keep track of the greatest id
        for (Ingredient ingredient : ingredientArray) {
            ingredients.put(ingredient.getId(),ingredient);
            if (ingredient.getId() > nextId)
                nextId = ingredient.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Ingredient[] getIngredients() {
        synchronized(ingredients) {
            return getIngredientsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Ingredient[] findIngredients(String containsText) {
        synchronized(ingredients) {
            return getIngredientsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Ingredient getIngredient(int id) {
        synchronized(ingredients) {
            if (ingredients.containsKey(id))
                return ingredients.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Ingredient createIngredient(Ingredient ingredient) throws IOException {
        synchronized(ingredients) {
            // We create a new ingredient object because the id field is immutable
            // and we need to assign the next unique id
            Ingredient newIngredient = new Ingredient(nextId(),ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit());
            ingredients.put(newIngredient.getId(),newIngredient);
            save(); // may throw an IOException
            return newIngredient;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Ingredient updateIngredient(Ingredient ingredient) throws IOException {
        synchronized(ingredients) {
            if (ingredients.containsKey(ingredient.getId()) == false)
                return null;  // ingredient does not exist

            ingredients.put(ingredient.getId(),ingredient);
            save(); // may throw an IOException
            return ingredient;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteIngredient(int id) throws IOException {
        synchronized(ingredients) {
            if (ingredients.containsKey(id)) {
                ingredients.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
