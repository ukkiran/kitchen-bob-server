package de.robertdey.kitchenbob;

import de.robertdey.kitchenbob.domain.*;
import de.robertdey.kitchenbob.repositories.CategoryRepository;
import de.robertdey.kitchenbob.repositories.RecipeRepository;
import de.robertdey.kitchenbob.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        log.debug("starting data initialization...");

        Recipe recipe = new Recipe();
        recipe.setPrepTime(10);
        recipe.setCookTime(0);
        recipe.setServings(3);
        recipe.setDescription("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips. Watch how to make guacamole - it's easy!");

        Optional<UnitOfMeasure> piecesUOM = unitOfMeasureRepository.findByDescription("Pieces");
        piecesUOM.ifPresent(uom ->
                recipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), uom)));

        Optional<UnitOfMeasure> teaspoonUOM = unitOfMeasureRepository.findByDescription("Teaspoon");
        teaspoonUOM.ifPresent(uom ->
                recipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal("0.5"), uom)));

        recipe.setDirections("Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setSource("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setNotes(new Notes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados."));

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        categoryOptional.ifPresent(category -> recipe.getCategories().add(category));

        recipeRepository.save(recipe);

        log.debug("data initialization complete");
    }
}
