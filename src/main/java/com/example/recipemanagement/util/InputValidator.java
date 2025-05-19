package com.example.recipemanagement.util;

import com.example.recipemanagement.dto.RecipeInput;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    public void validateRecipeInput(RecipeInput input) {
        if (input.getTitle() == null || input.getTitle().trim().isEmpty()) {
            throw new ValidationException("Recipe title cannot be empty");
        }

        if (input.getTitle().length() > 100) {
            throw new ValidationException("Recipe title cannot exceed 100 characters");
        }

        if (input.getPrepTime() != null && input.getPrepTime() < 0) {
            throw new ValidationException("Prep time cannot be negative");
        }

        if (input.getCookTime() != null && input.getCookTime() < 0) {
            throw new ValidationException("Cook time cannot be negative");
        }

        if (input.getServings() != null && input.getServings() <= 0) {
            throw new ValidationException("Servings must be a positive number");
        }

        if (input.getCategory() == null || input.getCategory().trim().isEmpty()) {
            throw new ValidationException("Category cannot be empty");
        }
    }
}