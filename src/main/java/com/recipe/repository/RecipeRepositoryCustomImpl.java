package com.recipe.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recipe.constant.CategoryEnum;
import com.recipe.constant.ImgMainOk;
import com.recipe.dto.RecipeCategoryDto;
import com.recipe.dto.RecipeSearchDto;
import com.recipe.entity.QBookMark;
import com.recipe.entity.QCategory;
import com.recipe.entity.QMember;
import com.recipe.entity.QMemberImg;
import com.recipe.entity.QRecipe;
import com.recipe.entity.QReview;

import jakarta.persistence.EntityManager;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
	
	private JPAQueryFactory queryFactory;

	public RecipeRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<RecipeCategoryDto> getRecipeCategoryReviewBestList(Pageable pageable, RecipeSearchDto recipeSearchDto) {
		QRecipe r = QRecipe.recipe;
		QBookMark bm = QBookMark.bookMark;
		QReview rv = QReview.review;
		QMember m = QMember.member;
		QMemberImg mi = QMemberImg.memberImg;
		QCategory c = QCategory.category;

		List<Tuple> bookmarkResults = queryFactory
		    .select(r.id, bm.recipe.id.count())
		    .from(r)
		    .leftJoin(bm).on(r.id.eq(bm.recipe.id))
		    .groupBy(r.id)
		    .fetch();
		
//		List<Tuple> reviewResults = queryFactory
//			    .select(r.id, rv.reting.avg().coalesce(0.0), rv.recipe.count())
//			    .from(r)
//			    .leftJoin(rv).on(r.id.eq(rv.recipe.id))
//			    .groupBy(r.id)
//			    .fetch();
//		
		
		
		
		List<RecipeCategoryDto> content = queryFactory
		        .select(
		        		Projections.constructor(
		        				RecipeCategoryDto.class,
		                r.id,
		                r.count,
		                r.durTime,
		                r.imageUrl,
		                r.level,
		                r.subTitle,
		                r.title,
		                r.member.id,
		                r.regTime,
		                r.intro,
		                m.nickname,
		                mi.imgUrl,
		                mi.imgMainOk,
		                c.categoryEnum,
		                rv.recipe.count().as("reviewCount"),
		                rv.rating.avg().coalesce(0.0).as("retingAvg")

		        ))
		        .from(r)
		        .join(r.member , m)
		        .join(c).on(r.id.eq(c.recipe.id))
		        .leftJoin(mi).on(m.id.eq(mi.member.id).and(mi.imgMainOk.eq(ImgMainOk.Y)))
		        .leftJoin(rv).on(r.id.eq(rv.recipe.id))
		        .where( mainCategoryEq(recipeSearchDto.getRecipeMainCategory()),
		        		searchByLike(recipeSearchDto.getSearchBy() , recipeSearchDto.getSearchQuery()))
		        .groupBy(r.id, r.count, r.durTime, r.imageUrl, r.level, r.subTitle, r.title,
		                r.member.id, r.regTime, r.intro, m.nickname, mi.imgUrl, mi.imgMainOk,
		                c.categoryEnum)
		        .orderBy(orderByType(recipeSearchDto.getType()))
		        .offset(pageable.getOffset())
				.limit(pageable.getPageSize())
		        .fetch();
		
		for (RecipeCategoryDto dto : content) {
		    Tuple bookmarkTuple = findBookmarkTuple(dto.getId(), bookmarkResults); // 해당 레시피 ID에 해당하는 Tuple을 찾음
		    
		    if (bookmarkTuple != null) {
		        Long bookmarkCount = bookmarkTuple.get(1, Long.class);
		        dto.setBookmarkCount(bookmarkCount);
		    }
		}
		
		long total = queryFactory
		        .select(Wildcard.count)
		        .from(r)
		        .join(r.member , m)
		        .leftJoin(mi).on(m.id.eq(mi.member.id).and(mi.imgMainOk.eq(ImgMainOk.Y)))
		        .join(c).on(r.id.eq(c.recipe.id))
		        .where( mainCategoryEq(recipeSearchDto.getRecipeMainCategory()),
		        		searchByLike(recipeSearchDto.getSearchBy() , recipeSearchDto.getSearchQuery()))
		        .fetchOne();

		return new PageImpl<>(content, pageable, total);
		}

	
	

	

	private Predicate mainCategoryEq(String mainCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	private OrderSpecifier<?> orderByType(String type) {
	    QReview rv = QReview.review;
	    QRecipe r = QRecipe.recipe;
	    QBookMark bm = QBookMark.bookMark;
	    
	    
	    if ("order".equals(type)) {
	        return r.regTime.desc();
	    }
	    else if ("reviewAvg".equals(type)) {
	    	System.out.println("avg" + rv.rating.avg().coalesce(0.0).desc());
	    	return rv.rating.avg().coalesce(0.0).desc();
	    }
	    else if ("reviewCount".equals(type)) {
	    	System.out.println("rvc" +  rv.recipe.count().desc());
	    	return rv.count().desc();
	    }
	    else if ("countBest".equals(type)) {
	    	return r.count.desc();
	    }
	    else {
	        return r.regTime.desc();
	    }
	    
	}
	
private BooleanExpression searchByLike(String searchBy , String searchQuery) {
	 QRecipe r = QRecipe.recipe;
	 QMember m = QMember.member;
	
		if(StringUtils.equals("title", searchBy)) { //제목명 선택시
			return r.title.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("nickname", searchBy)) { // 쉐프명 선택시
			return m.nickname.like("%" + searchQuery + "%");
		} 
		return null;
}


private Tuple findBookmarkTuple(Long recipeId, List<Tuple> bookmarkResults) {
    for (Tuple tuple : bookmarkResults) {
        Long id = tuple.get(0, Long.class);
        if (id.equals(recipeId)) {
            return tuple;
        }
    }
    return null;
}




//
//private Tuple findReviewTuple(Long recipeId, List<Tuple> reviewResults) {
//    for (Tuple tuple : reviewResults) {
//        Long id = tuple.get(0, Long.class);
//        if (id.equals(recipeId)) {
//            return tuple;
//        }
//    }
//    return null;
//}


}
