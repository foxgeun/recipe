package com.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recipe.dto.CommentDto;
import com.recipe.dto.RecipeSearchDto;

public interface CommentRepositoryCustom {
	Page<CommentDto> getAdminCommentPage(RecipeSearchDto recipeSearchDto, Pageable pageable);
}
