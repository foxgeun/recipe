package com.recipe.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recipe.entity.ItemImg;


public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{

	
	@Query(value = "select * from item_img where item_count = ?1", nativeQuery = true)
	List<ItemImg> getItemImgByItemId(int id);
	
	
	List<ItemImg> findByItemId(Long itemId);

}
