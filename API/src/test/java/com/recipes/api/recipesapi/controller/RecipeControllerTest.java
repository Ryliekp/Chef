package com.recipes.api.recipesapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.recipes.api.recipesapi.model.Recipe;
import com.recipes.api.recipesapi.persistence.RecipeDAO;

/**
 * Test the Recipe Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class RecipeControllerTest {
    private RecipeController recipeController;
    private RecipeDAO mockRecipeDAO;

    /**
     * Before each test, create a new RecipeController object and inject
     * a mock Recipe DAO
     */
    @BeforeEach
    public void setupRecipeController() {
        mockRecipeDAO = mock(RecipeDAO.class);
        recipeController = new RecipeController(mockRecipeDAO);
    }

    @Test
    public void testGetRecipe() throws IOException {  // getRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Galactic Agent");
        // When the same id is passed in, our mock Recipe DAO will return the Recipe object
        when(mockRecipeDAO.getRecipe(recipe.getId())).thenReturn(recipe);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.getRecipe(recipe.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(recipe,response.getBody());
    }

    @Test
    public void testGetRecipeNotFound() throws Exception { // createRecipe may throw IOException
        // Setup
        int recipeId = 99;
        // When the same id is passed in, our mock Recipe DAO will return null, simulating
        // no recipe found
        when(mockRecipeDAO.getRecipe(recipeId)).thenReturn(null);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.getRecipe(recipeId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetRecipeHandleException() throws Exception { // createRecipe may throw IOException
        // Setup
        int recipeId = 99;
        // When getRecipe is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).getRecipe(recipeId);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.getRecipe(recipeId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all RecipeController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateRecipe() throws IOException {  // createRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Wi-Fire");
        // when createRecipe is called, return true simulating successful
        // creation and save
        when(mockRecipeDAO.createRecipe(recipe)).thenReturn(recipe);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.createRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(recipe,response.getBody());
    }

    @Test
    public void testCreateRecipeFailed() throws IOException {  // createRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Bolt");
        // when createRecipe is called, return false simulating failed
        // creation and save
        when(mockRecipeDAO.createRecipe(recipe)).thenReturn(null);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.createRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateRecipeHandleException() throws IOException {  // createRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Ice Gladiator");

        // When createRecipe is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).createRecipe(recipe);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.createRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateRecipe() throws IOException { // updateRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Wi-Fire");
        // when updateRecipe is called, return true simulating successful
        // update and save
        when(mockRecipeDAO.updateRecipe(recipe)).thenReturn(recipe);
        ResponseEntity<Recipe> response = recipeController.updateRecipe(recipe);
        recipe.setName("Bolt");

        // Invoke
        response = recipeController.updateRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(recipe,response.getBody());
    }

    @Test
    public void testUpdateRecipeFailed() throws IOException { // updateRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Galactic Agent");
        // when updateRecipe is called, return true simulating successful
        // update and save
        when(mockRecipeDAO.updateRecipe(recipe)).thenReturn(null);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.updateRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateRecipeHandleException() throws IOException { // updateRecipe may throw IOException
        // Setup
        Recipe recipe = new Recipe(99,"Galactic Agent");
        // When updateRecipe is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).updateRecipe(recipe);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.updateRecipe(recipe);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetRecipes() throws IOException { // getRecipes may throw IOException
        // Setup
        Recipe[] recipes = new Recipe[2];
        recipes[0] = new Recipe(99,"Bolt");
        recipes[1] = new Recipe(100,"The Great Iguana");
        // When getRecipes is called return the recipes created above
        when(mockRecipeDAO.getRecipes()).thenReturn(recipes);

        // Invoke
        ResponseEntity<Recipe[]> response = recipeController.getRecipes();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(recipes,response.getBody());
    }

    @Test
    public void testGetRecipesHandleException() throws IOException { // getRecipes may throw IOException
        // Setup
        // When getRecipes is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).getRecipes();

        // Invoke
        ResponseEntity<Recipe[]> response = recipeController.getRecipes();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchRecipes() throws IOException { // findRecipes may throw IOException
        // Setup
        String searchString = "la";
        Recipe[] recipes = new Recipe[2];
        recipes[0] = new Recipe(99,"Galactic Agent");
        recipes[1] = new Recipe(100,"Ice Gladiator");
        // When findRecipes is called with the search string, return the two
        /// recipes above
        when(mockRecipeDAO.findRecipes(searchString)).thenReturn(recipes);

        // Invoke
        ResponseEntity<Recipe[]> response = recipeController.searchRecipes(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(recipes,response.getBody());
    }

    @Test
    public void testSearchRecipesHandleException() throws IOException { // findRecipes may throw IOException
        // Setup
        String searchString = "an";
        // When createRecipe is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).findRecipes(searchString);

        // Invoke
        ResponseEntity<Recipe[]> response = recipeController.searchRecipes(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteRecipe() throws IOException { // deleteRecipe may throw IOException
        // Setup
        int recipeId = 99;
        // when deleteRecipe is called return true, simulating successful deletion
        when(mockRecipeDAO.deleteRecipe(recipeId)).thenReturn(true);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.deleteRecipe(recipeId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteRecipeNotFound() throws IOException { // deleteRecipe may throw IOException
        // Setup
        int recipeId = 99;
        // when deleteRecipe is called return false, simulating failed deletion
        when(mockRecipeDAO.deleteRecipe(recipeId)).thenReturn(false);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.deleteRecipe(recipeId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteRecipeHandleException() throws IOException { // deleteRecipe may throw IOException
        // Setup
        int recipeId = 99;
        // When deleteRecipe is called on the Mock Recipe DAO, throw an IOException
        doThrow(new IOException()).when(mockRecipeDAO).deleteRecipe(recipeId);

        // Invoke
        ResponseEntity<Recipe> response = recipeController.deleteRecipe(recipeId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
