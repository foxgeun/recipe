package com.recipe.dto;



import com.recipe.constant.CategoryEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSearchDto {

	

	private String subCategory;

	private String recipeMainCategory;

	private String type;
	

	private String searchBy;
	
	
	private String searchQuery = "";
	
	
	private String sort;
	

	
}
