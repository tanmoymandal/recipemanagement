package com.example.recipemanagement.repository;

import com.example.recipemanagement.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByRecipeIdIn(List<Long> recipeIds);
}
