package com.example.recipemanagement.controller;

import com.example.recipemanagement.dto.IngredientInput;
import com.example.recipemanagement.dto.RecipeInput;
import com.example.recipemanagement.model.Ingredient;
import com.example.recipemanagement.model.Recipe;
import com.example.recipemanagement.model.Recipe;
import com.example.recipemanagement.repository.IngredientRepository;
import com.example.recipemanagement.repository.RecipeRepository;
import com.example.recipemanagement.service.RecipePermissionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.security.AccessControlException;
import java.util.List;
import java.util.Map;

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipePermissionService permissionService;

    public RecipeController(RecipeRepository recipeRepository,
                            IngredientRepository ingredientRepository,
                            RecipePermissionService permissionService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.permissionService = permissionService;
    }

    @QueryMapping
    public Recipe recipeById(@Argument Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    @QueryMapping
    public List<Recipe> allRecipes() {
        return recipeRepository.findAll();
    }

    @QueryMapping
    public List<Recipe> recipesByCategory(@Argument String category) {
        return recipeRepository.findByCategory(category);
    }

    @MutationMapping
    public Recipe createRecipe(@Argument RecipeInput input) {
        Recipe recipe = new Recipe();
        recipe.setTitle(input.getTitle());
        recipe.setDescription(input.getDescription());
        recipe.setPrepTime(input.getPrepTime());
        recipe.setCookTime(input.getCookTime());
        recipe.setServings(input.getServings());
        recipe.setCategory(input.getCategory());
        recipe.setDifficulty(input.getDifficulty());

        return recipeRepository.save(recipe);
    }

    @MutationMapping
    public Recipe updateRecipe(@Argument Long id, @Argument RecipeInput input) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        recipe.setTitle(input.getTitle());
        if (input.getDescription() != null) recipe.setDescription(input.getDescription());
        if (input.getPrepTime() != null) recipe.setPrepTime(input.getPrepTime());
        if (input.getCookTime() != null) recipe.setCookTime(input.getCookTime());
        if (input.getServings() != null) recipe.setServings(input.getServings());
        recipe.setCategory(input.getCategory());
        if (input.getDifficulty() != null) recipe.setDifficulty(input.getDifficulty());

        return recipeRepository.save(recipe);
    }

    @MutationMapping
    public boolean deleteRecipe(@Argument Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Recipe addIngredientToRecipe(@Argument Long recipeId, @Argument IngredientInput ingredientInput) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + recipeId));

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientInput.getName());
        ingredient.setAmount(ingredientInput.getAmount());
        ingredient.setUnit(ingredientInput.getUnit());

        recipe.addIngredient(ingredient);
        return recipeRepository.save(recipe);
    }
}
