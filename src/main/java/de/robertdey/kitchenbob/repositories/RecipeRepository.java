package de.robertdey.kitchenbob.repositories;

import de.robertdey.kitchenbob.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
