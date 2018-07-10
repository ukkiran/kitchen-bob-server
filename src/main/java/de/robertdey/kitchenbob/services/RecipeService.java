package de.robertdey.kitchenbob.services;

import de.robertdey.kitchenbob.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAll();

    Recipe findById(Long id);
}
