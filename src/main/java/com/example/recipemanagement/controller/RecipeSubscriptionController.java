package com.example.recipemanagement.controller;

import com.example.recipemanagement.model.Recipe;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Controller
public class RecipeSubscriptionController {

    private final Sinks.Many<Recipe> recipeAddedSink;
    private final Sinks.Many<Recipe> recipeUpdatedSink;

    public RecipeSubscriptionController() {
        this.recipeAddedSink = Sinks.many().multicast().onBackpressureBuffer();
        this.recipeUpdatedSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @SubscriptionMapping
    public Flux<Recipe> recipeAdded() {
        return recipeAddedSink.asFlux();
    }

    @SubscriptionMapping
    public Flux<Recipe> recipeUpdated() {
        return recipeUpdatedSink.asFlux();
    }

    public void onRecipeAdded(Recipe recipe) {
        recipeAddedSink.tryEmitNext(recipe);
    }

    public void onRecipeUpdated(Recipe recipe) {
        recipeUpdatedSink.tryEmitNext(recipe);
    }
}