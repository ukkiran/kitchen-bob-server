package de.robertdey.kitchenbob;

import de.robertdey.kitchenbob.domain.*;
import de.robertdey.kitchenbob.repositories.CategoryRepository;
import de.robertdey.kitchenbob.repositories.RecipeRepository;
import de.robertdey.kitchenbob.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        Recipe recipe = new Recipe();
        recipe.setPrepTime(10);
        recipe.setCookTime(0);
        recipe.setServings(3);

        Optional<UnitOfMeasure> piecesUOM = unitOfMeasureRepository.findByDescription("Pieces");
        if (piecesUOM.isPresent()) {
            Ingredient ingredient = new Ingredient("ripe avocados", new BigDecimal(2), piecesUOM.get(), recipe);
            recipe.getIngredients().add(ingredient);
        }

        Optional<UnitOfMeasure> teaspoonUOM = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (teaspoonUOM.isPresent()) {
            Ingredient ingredient = new Ingredient("Kosher salt", new BigDecimal("0.5"), teaspoonUOM.get(), recipe);
            recipe.getIngredients().add(ingredient);
        }

        recipe.setDirections("Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setSource("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        categoryOptional.ifPresent(category -> recipe.getCategories().add(category));

        recipeRepository.save(recipe);
    }
}
