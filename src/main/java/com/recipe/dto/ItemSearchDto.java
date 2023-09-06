package com.recipe.dto;

import com.recipe.constant.ItemCategoryEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
	
	private String itemCategoryEnum;
	
	private String type;
	
	private int dataNum;
	
	private String searchQuery = "";

}

