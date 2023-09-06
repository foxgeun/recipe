package com.recipe.dto;

import java.time.LocalDateTime;
import com.querydsl.core.annotations.QueryProjection;
import com.recipe.constant.CategoryEnum;
import com.recipe.constant.ImgMainOk;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeCategoryDto {


	private Long id;
	private int count;
	private String durTime;
	private String imageUrl;
	private String level;
	private String subTitle;
	private String title;
	private Long memberId;
	private LocalDateTime regTime;
	private String intro;
	private String nickname;
	private String imgUrl;
	private ImgMainOk imgMainOk;
	private CategoryEnum categoryEnum;
	private Long reviewCount;
	private Double retingAvg;
	private Long bookmarkCount;
	
	@QueryProjection
	public RecipeCategoryDto(Long id, int count, String durTime, String imageUrl, String level, String subTitle,
			String title, Long memberId, LocalDateTime regTime, String intro, String nickname, String imgUrl,
			ImgMainOk imgMainOk, CategoryEnum categoryEnum , Long reviewCount, Double retingAvg) {
		
		
		this.id = id;
		this.count = count;
		this.durTime = durTime;
		this.imageUrl = imageUrl;
		this.level = level;
		this.subTitle = subTitle;
		this.title = title;
		this.memberId = memberId;
		this.regTime = regTime;
		this.intro = intro;
		this.nickname = nickname;
		this.imgUrl = imgUrl;
		this.imgMainOk = imgMainOk;
		this.categoryEnum = categoryEnum;
		this.reviewCount = reviewCount;
		this.retingAvg = retingAvg;
	
	}
	

	
}

