package com.recipe.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.recipe.constant.CategoryEnum;
import com.recipe.dto.RecipeMainDto;
import com.recipe.entity.Recipe;

import jakarta.transaction.Transactional;



public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	

	@Query(value = "SELECT * FROM recipe WHERE member_id = ?1  ORDER BY recipe_id DESC" , nativeQuery = true)
	List<Recipe> findRecipeByid(Long id);
	
	@Query(value = "SELECT * FROM recipe WHERE member_id = ?1 AND writing_status = 'PUBLISHED' ORDER BY recipe_id DESC ", nativeQuery = true)
	List<Recipe> findAllRecipe(Long id);
	
	
	@Query(value = "SELECT r.* FROM recipe r " +
            "JOIN (SELECT recipe_id, COUNT(*) AS bookmark_count " +
            "      FROM book_mark " +
            "      GROUP BY recipe_id) b ON r.recipe_id = b.recipe_id " +
            "WHERE r.member_id = ?1 AND writing_status = 'PUBLISHED' " +
            "ORDER BY b.bookmark_count DESC " +
            "LIMIT 5", nativeQuery = true)
List<Recipe> getPopularRecipe(Long id);
	

	
	boolean existsByTitle(String title);
	
	@Query(value = "SELECT * FROM recipe ORDER BY RAND() limit 3;", nativeQuery = true)
	List<Recipe> getRecipeMainContent();

	@Query(value = "select * from recipe where recipe_id = ?1", nativeQuery = true)
	Recipe getRecipeDetailByid(Long id);
	
	@Transactional
	@Query(value = "select * from recipe order by count desc limit 5", nativeQuery = true)
	List<Recipe> getRecipeListOrderByView();	
	
	
	@Transactional
	@Query(value = "select * from recipe", nativeQuery = true)
	List<Recipe> getAllRecipeList();	
	
	@Transactional
	@Modifying
	@Query(value = "update recipe set count = count + 1 where recipe_id = ?1", nativeQuery = true)
	int setaddview(Long id);	
	
	
	@Query(value = "SELECT * FROM recipe WHERE member_id = ?1", nativeQuery = true)
	List<Recipe> findRecipe(Long id);

	
//	조회수 가장 높은순으로 모든레시피 가져옴 (4개)
//	메인
	@Query ( value = "select * from recipe ORDER BY RAND() desc limit 4", nativeQuery = true)
	List<Recipe> getRecipeHeaderBestList();

//	최근 등록순으로 모든레시피 가져옴 (제한 15개)
//	메인
	@Query (value = "select * from recipe order by reg_time desc limit 15 ", nativeQuery = true)
	List<Recipe> getRecipeNewList();
	
	
//	최근 등록순으로 모든레시피 가져옴 (제한 10개)
//	메인
	@Query ( value = "select * from recipe order by count desc limit 15 ", nativeQuery = true)
	List<Recipe> getRecipeBestList();

	Page<Recipe> findByTitleContaining(String searchQuery, Pageable pageable);

	Page<Recipe> findByCategory(String searchQuery, Pageable pageable);

	Page<Recipe> findByTitleContainingAndCategory(String searchQuery, String MainCategory, Pageable pageable);
}
