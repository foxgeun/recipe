package com.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recipe.dto.MemberDto;
import com.recipe.dto.MemberSearchDto;
import com.recipe.dto.RecipeSearchDto;
import com.recipe.entity.Member;

public interface MemberRepositoryCustom {

	Page<MemberDto> getAdminMemberPage(RecipeSearchDto recipeSearchDto, Pageable pageable);

//
//	Page<MemberDto> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable);
}
