package com.recipes.api.recipesapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.api.recipesapi.model.Recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Recipe File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class RecipeFileDAOTest {
    RecipeFileDAO recipeFileDAO;
    Recipe[] testRecipes;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupRecipeFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testRecipes = new Recipe[3];
        testRecipes[0] = new Recipe(99,"Wi-Fire");
        testRecipes[1] = new Recipe(100,"Galactic Agent");
        testRecipes[2] = new Recipe(101,"Ice Gladiator");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the recipe array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Recipe[].class))
                .thenReturn(testRecipes);
        recipeFileDAO = new RecipeFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetRecipes() {
        // Invoke
        Recipe[] recipes = recipeFileDAO.getRecipes();

        // Analyze
        assertEquals(recipes.length,testRecipes.length);
        for (int i = 0; i < testRecipes.length;++i)
            assertEquals(recipes[i],testRecipes[i]);
    }

    @Test
    public void testFindRecipes() {
        // Invoke
        Recipe[] recipes = recipeFileDAO.findRecipes("la");

        // Analyze
        assertEquals(recipes.length,2);
        assertEquals(recipes[0],testRecipes[1]);
        assertEquals(recipes[1],testRecipes[2]);
    }

    @Test
    public void testGetRecipe() {
        // Invoke
        Recipe recipe = recipeFileDAO.getRecipe(99);

        // Analzye
        assertEquals(recipe,testRecipes[0]);
    }

    @Test
    public void testDeleteRecipe() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> recipeFileDAO.deleteRecipe(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test recipes array - 1 (because of the delete)
        // Because recipes attribute of RecipeFileDAO is package private
        // we can access it directly
        assertEquals(recipeFileDAO.recipes.size(),testRecipes.length-1);
    }

    @Test
    public void testCreateRecipe() {
        // Setup
        Recipe recipe = new Recipe(102,"Wonder-Person");

        // Invoke
        Recipe result = assertDoesNotThrow(() -> recipeFileDAO.createRecipe(recipe),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Recipe actual = recipeFileDAO.getRecipe(recipe.getId());
        assertEquals(actual.getId(),recipe.getId());
        assertEquals(actual.getName(),recipe.getName());
    }

    @Test
    public void testUpdateRecipe() {
        // Setup
        Recipe recipe = new Recipe(99,"Galactic Agent");

        // Invoke
        Recipe result = assertDoesNotThrow(() -> recipeFileDAO.updateRecipe(recipe),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Recipe actual = recipeFileDAO.getRecipe(recipe.getId());
        assertEquals(actual,recipe);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Recipe[].class));

        Recipe recipe = new Recipe(102,"Wi-Fire");

        assertThrows(IOException.class,
                        () -> recipeFileDAO.createRecipe(recipe),
                        "IOException not thrown");
    }

    @Test
    public void testGetRecipeNotFound() {
        // Invoke
        Recipe recipe = recipeFileDAO.getRecipe(98);

        // Analyze
        assertEquals(recipe,null);
    }

    @Test
    public void testDeleteRecipeNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> recipeFileDAO.deleteRecipe(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(recipeFileDAO.recipes.size(),testRecipes.length);
    }

    @Test
    public void testUpdateRecipeNotFound() {
        // Setup
        Recipe recipe = new Recipe(98,"Bolt");

        // Invoke
        Recipe result = assertDoesNotThrow(() -> recipeFileDAO.updateRecipe(recipe),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the RecipeFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Recipe[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new RecipeFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
