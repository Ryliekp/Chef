package com.recipes.api.recipesapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.recipes.api.recipesapi.persistence.RecipeDAO;
import com.recipes.api.recipesapi.model.Recipe;

/**
 * Handles the REST API requests for the Recipe resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 * @author Rylie Platt, rkp6174
 */

@RestController
@RequestMapping("recipes")
public class RecipeController {
    private static final Logger LOG = Logger.getLogger(RecipeController.class.getName());
    private RecipeDAO recipeDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param recipeDao The {@link RecipeDAO Recipe Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public RecipeController(RecipeDAO recipeDao) {
        this.recipeDao = recipeDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Recipe recipe} for the given id
     * 
     * @param id The id used to locate the {@link Recipe recipe}
     * 
     * @return ResponseEntity with {@link Recipe recipe} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        LOG.info("GET /recipes/" + id);
        try {
            Recipe recipe = recipeDao.getRecipe(id);
            if (recipe != null)
                return new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Recipe recipes}
     * 
     * @return ResponseEntity with array of {@link Recipe recipe} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Recipe[]> getRecipes() {
        LOG.info("GET /recipes");
        //Replaced with my implementation - Rylie
        try {
            Recipe[] recipes = recipeDao.getRecipes();
            if(recipes != null)
                return new ResponseEntity<Recipe[]>(recipes,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Recipe recipes} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Recipe recipes}
     * 
     * @return ResponseEntity with array of {@link Recipe recipe} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all recipes that contain the text "ma"
     * GET http://localhost:8080/recipes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Recipe[]> searchRecipes(@RequestParam String name) {
        LOG.info("GET /recipes/?name="+name);
        //Replaced with my implementation - Rylie
        try {
            Recipe[] recipes = recipeDao.findRecipes(name);
            if(recipes != null)
                return new ResponseEntity<Recipe[]>(recipes,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Recipe recipe} with the provided recipe object
     * 
     * @param recipe - The {@link Recipe recipe} to create
     * 
     * @return ResponseEntity with created {@link Recipe recipe} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Recipe recipe} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        LOG.info("POST /recipes " + recipe);

        //Replaced with my implementation - Rylie
        try {
            Recipe []recipes = recipeDao.getRecipes();
            for(Recipe h : recipes){
                if(h == recipe)
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Recipe newRecipe = recipeDao.createRecipe(recipe);
            return new ResponseEntity<Recipe>(newRecipe,HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Recipe recipe} with the provided {@linkplain Recipe recipe} object, if it exists
     * 
     * @param recipe The {@link Recipe recipe} to update
     * 
     * @return ResponseEntity with updated {@link Recipe recipe} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        LOG.info("PUT /recipes " + recipe);

        //Replaced with my implementation - Rylie
        try {
            Recipe h = recipeDao.updateRecipe(recipe);
            if(h != null)
                return new ResponseEntity<Recipe>(h,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Recipe recipe} with the given id
     * 
     * @param id The id of the {@link Recipe recipe} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable int id) {
        LOG.info("DELETE /recipes/" + id);

        //Replaced with my implementation - Rylie
        try {
            boolean h = recipeDao.deleteRecipe(id);
            if(h)
                return new ResponseEntity<Recipe>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}