package com.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recipe.dto.RecipeDto;
import com.recipe.dto.RecipeSearchDto;

public interface RecipeListRepositoryCustom {

	Page<RecipeDto> getAdminRecipePage(RecipeSearchDto recipeSearchDto, Pageable pageable);

}
