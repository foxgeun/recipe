package com.recipe.dto;

public class SearchWrapper {
	private MemberSearchDto memberSearchDto;
	private RecipeSearchDto recipeSearchDto;
	private String searchQuery;
	
	public MemberSearchDto getMemberSearchDto() {
        return memberSearchDto;
    }

    public RecipeSearchDto getRecipeSearchDto() {
        return recipeSearchDto;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}
