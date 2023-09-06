package com.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recipe.entity.ItemDetailImg;

public interface ItemDetailImgRepository extends JpaRepository<ItemDetailImg, Long> {
	
	
	@Query(value = "select * from item_detail_img where item_count = ?1", nativeQuery = true)
	List<ItemDetailImg> getItemDetailImgByItemId(int id);
}
