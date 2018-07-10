package de.robertdey.kitchenbob.controllers;

import de.robertdey.kitchenbob.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        log.debug("recipe/show page requested");

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }
}
