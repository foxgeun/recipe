package com.recipe.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.recipe.dto.RecipeSearchDto;
import com.recipe.entity.Recipe;
import com.recipe.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryController {
	
	private final RecipeService recipeService;
	
	@GetMapping(value = {"/recipe/total" , "/recipe/total/{page}"})
	public String categoryOrder(RecipeSearchDto recipeSearchDto ,
			@PathVariable("page") Optional<Integer> page , Model model) {
		
		
		System.out.println(recipeSearchDto.getRecipeMainCategory());
		System.out.println(recipeSearchDto.getSearchQuery());
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 12);
		Page<Recipe> recipes = recipeService.getRecipePage(pageable, recipeSearchDto);
		model.addAttribute("RecipeList",recipes);
		model.addAttribute("maxPage" , 5);
		model.addAttribute("page" , page);
		

		return "category/category";
	}
	
	


}
	
	

