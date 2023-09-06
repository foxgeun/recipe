package com.recipe.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;

import jakarta.persistence.*;
import lombok.*;

import com.recipe.constant.CategoryEnum;
import com.recipe.constant.WritingStatus;
import com.recipe.dto.RecipeNewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "recipe") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Recipe extends BaseTimeEntity {
	
	
    @Id
    @Column(name="recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String title; //제목
    
    private String subTitle; //부제목
    

    private String intro; //레시피소개

    
    private String durTime; //소요시간
    
    private String level; //난이도

    
    @Column(length = 1000)
    private String description;
    
    private int count = 0; //조회수
    
    private String imageUrl; // 메인이미지 (이미지 URL필드 추가)
    
    private String imgName; //이미지 이름
    
    private int bookmarkCount; //북마크
    
    @Enumerated(EnumType.STRING) //레시피타입별 카테고리
	private CategoryEnum categoryType;
    
    @Enumerated(EnumType.STRING) //레시피 등록,임시저장
    private WritingStatus writingStatus;
    
    private String category;

    
    @Enumerated(EnumType.STRING)
	private CategoryEnum categoryEnum;
    

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	public void updateRecipe(RecipeNewDto recipeNewDto) {
		this.title = recipeNewDto.getTitle();
		this.subTitle = recipeNewDto.getSubTitle();
		this.intro = recipeNewDto.getIntro();
		this.durTime = recipeNewDto.getDurTime();
		this.level = recipeNewDto.getLevel();
		this.imageUrl = recipeNewDto.getImageUrl();
		this.categoryType = recipeNewDto.getCategoryType();
		this.writingStatus = recipeNewDto.getWritingStatus();
	}
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
	private List<RecipeOrder> recipeOrders = new ArrayList<>();


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngre> ingredients = new ArrayList<>();

    // Other getters and setters...

    public List<RecipeIngre> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngre> ingredients) {
        this.ingredients = ingredients;
    }


	public void updateRecipeImg(String imageUrl , String imgName) {
		this.imageUrl = imageUrl;
		this.imgName = imgName;
	}
	

}
