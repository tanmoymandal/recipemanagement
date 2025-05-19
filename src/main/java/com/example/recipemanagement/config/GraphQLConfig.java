package com.example.recipemanagement.config;

import com.example.recipemanagement.model.Ingredient;
import com.example.recipemanagement.repository.IngredientRepository;
import org.dataloader.BatchLoaderEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class GraphQLConfig {

    private final BatchLoaderRegistry registry;
    private final IngredientRepository ingredientRepository;

    public GraphQLConfig(BatchLoaderRegistry registry, IngredientRepository ingredientRepository) {
        this.registry = registry;
        this.ingredientRepository = ingredientRepository;
    }

    @PostConstruct
    public void registerBatchLoaders() {
        registry.forName("ingredients")
                .registerMappedBatchLoader((recipeIds, env) -> {
                    List<Long> recipeIdList = recipeIds.stream()
                            .map(id -> (Long) id)
                            .collect(Collectors.toList());

                    List<Ingredient> allIngredients = ingredientRepository.findByRecipeIdIn(recipeIdList);

                    // Group ingredients by recipe ID
                    Map<Long, List<Ingredient>> ingredientsByRecipeId = allIngredients.stream()
                            .collect(Collectors.groupingBy(
                                    ingredient -> ingredient.getRecipe().getId()));

                    // Ensure every requested recipe ID has an entry, even if empty
                    Map<Object, Object> result = new HashMap<>();
                    recipeIds.forEach(id -> result.put(id, ingredientsByRecipeId.getOrDefault((Long) id, Collections.emptyList())));


                    return Mono.just(result);
                });

    }
}