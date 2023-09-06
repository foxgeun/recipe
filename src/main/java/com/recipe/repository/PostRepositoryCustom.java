package com.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recipe.dto.PostDto;
import com.recipe.dto.RecipeSearchDto;

public interface PostRepositoryCustom {

	Page<PostDto> getAdminPostListPage(RecipeSearchDto recipeSearchDto, Pageable pageable);

	Page<PostDto> getPostListPage(RecipeSearchDto recipeSearchDto, Pageable pageable);
}
