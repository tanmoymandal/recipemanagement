package com.example.recipemanagement.service;

import com.example.recipemanagement.controller.RecipeSubscriptionController;
import com.example.recipemanagement.dto.RecipeInput;
import com.example.recipemanagement.model.Recipe;
import com.example.recipemanagement.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeSubscriptionController subscriptionController;

    public RecipeService(RecipeRepository recipeRepository,
                         RecipeSubscriptionController subscriptionController) {
        this.recipeRepository = recipeRepository;
        this.subscriptionController = subscriptionController;
    }

    public Recipe createRecipe(RecipeInput input) {
        Recipe recipe = new Recipe();
        updateRecipeFromInput(recipe, input);

        Recipe savedRecipe = recipeRepository.save(recipe);
        subscriptionController.onRecipeAdded(savedRecipe);
        return savedRecipe;
    }

    public Recipe updateRecipe(Long id, RecipeInput input) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        updateRecipeFromInput(recipe, input);

        Recipe updatedRecipe = recipeRepository.save(recipe);
        subscriptionController.onRecipeUpdated(updatedRecipe);
        return updatedRecipe;
    }

    private void updateRecipeFromInput(Recipe recipe, RecipeInput input) {
        recipe.setTitle(input.getTitle());
        recipe.setDescription(input.getDescription());
        recipe.setPrepTime(input.getPrepTime());
        recipe.setCookTime(input.getCookTime());
        recipe.setServings(input.getServings());
        recipe.setCategory(input.getCategory());
        recipe.setDifficulty(input.getDifficulty());
    }
}