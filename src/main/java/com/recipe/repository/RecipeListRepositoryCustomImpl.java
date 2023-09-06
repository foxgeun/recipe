package com.recipe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recipe.dto.MemberSearchDto;
import com.recipe.dto.RecipeDto;
import com.recipe.dto.RecipeSearchDto;
import com.recipe.dto.SearchWrapper;
import com.recipe.entity.QComment;
import com.recipe.entity.QMember;
import com.recipe.entity.QRecipe;
import com.recipe.entity.QReview;
import com.recipe.entity.Recipe;

import jakarta.persistence.EntityManager;

public class RecipeListRepositoryCustomImpl implements RecipeListRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public RecipeListRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy , String searchQuery) {
		QMember m = QMember.member;
		QRecipe r = QRecipe.recipe;

		if (StringUtils.equals("nickname", searchBy)) {
			// 등록자로 검색시
			return m.nickname.like("%" + searchQuery + "%"); // nickname like %검색어%
		} else if (StringUtils.equals("description", searchBy)) {
			return r.description.like("%" + searchQuery + "%"); // description like %검색어%
		}

		return null; // 쿼리문을 실행하지 않는다.
	}

	@Override
	public Page<RecipeDto> getAdminRecipePage(RecipeSearchDto recipeSearchDto, Pageable pageable) {
		QRecipe r = QRecipe.recipe;
		QComment c = QComment.comment;
		QMember m = QMember.member;
		QReview rv = QReview.review;

		List<RecipeDto> content = queryFactory
				.select(
						Projections.constructor(
								RecipeDto.class, 
								r.id,
								r.title,
								r.description,
								r.member.nickname,
								c.count().intValue().as("CommentCount"),
								r.imageUrl
								))
				.from(r)
				.join(r.member , m)
				.leftJoin(c).on(r.id.eq(c.recipe.id))
				.leftJoin(rv).on(r.id.eq(rv.recipe.id))
				.where(searchByLike(recipeSearchDto.getSearchBy(),
						recipeSearchDto.getSearchQuery()))
				.groupBy(r.id, r.title, r.description, r.member.nickname, r.imageUrl)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		Long total = queryFactory
				.select(Wildcard.count)
				.from(r)
				.join(r.member , m)
				.leftJoin(c).on(r.id.eq(c.recipe.id))
				.where(searchByLike(recipeSearchDto.getSearchBy(),
						recipeSearchDto.getSearchQuery()))
				.fetchOne();
		
		if (total == null) {
		    total = 0L; // 검색 결과가 없을 때 0으로 초기화
		}

		return new PageImpl<>(content, pageable, total);

	}
}
