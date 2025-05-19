package com.example.recipemanagement.controller;

import com.example.recipemanagement.model.Ingredient;
import com.example.recipemanagement.model.Recipe;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class RecipeFieldResolver {

    @SchemaMapping(typeName = "Recipe", field = "ingredients")
    public CompletableFuture<List<Ingredient>> ingredients(Recipe recipe, DataFetchingEnvironment env) {
        DataLoader<Long, List<Ingredient>> dataLoader = env.getDataLoader("ingredients");
        return dataLoader.load(recipe.getId());
    }
}
