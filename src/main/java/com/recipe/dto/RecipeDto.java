package com.recipe.dto;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.recipe.entity.Recipe;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RecipeDto {

	
	

	private Long id;

	private String title;

	private String subTitle;

	private String intro;

	private String durTime;

	private int level;

	private int count;

	private int commentCount;

	private String nickname;


    
    @Column(length = 1000) // Adjust the length as needed
    private String description;

    private String imageUrl; // 이미지 URL 필드 추가

	private double rating;
    
	private static ModelMapper modelMapper = new ModelMapper();
    
    public Recipe createRecipe() {
    	return modelMapper.map(this, Recipe.class);
    }
    
    public static RecipeDto of(Recipe recipe) {
    	return modelMapper.map(recipe, RecipeDto.class);
    }


	@QueryProjection
	public RecipeDto(Long id, String title, String description, String nickname, int commentCount, String imageUrl) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.nickname = nickname;
		this.commentCount = commentCount;
		this.imageUrl = imageUrl;
	}

}
