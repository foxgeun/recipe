package com.recipe.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recipe.entity.Item;


import jakarta.transaction.Transactional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
	

	
	 Page<Item> findByItemNmContaining(String searchQuery, Pageable pageable);
	    Page<Item> findByCategory(String category, Pageable pageable);
	    Page<Item> findByItemNmContainingAndCategory(String searchQuery, String category, Pageable pageable);
	    
	@Transactional
	@Query(value = "SELECT * FROM item ORDER BY RAND() limit 12", nativeQuery = true)
	List<Item> getAllItemListLimit();
	
	@Transactional
	@Query(value = "SELECT * FROM item", nativeQuery = true)
	List<Item> getAllItemList();
	
	@Query(value = "select * from item where item_id = ?1", nativeQuery = true)
	Item getItemByItemId(int Id);

	 Optional<Item> findById(Long id);
	 


	
	@Query(value = "select * from item where category = ?1", nativeQuery = true)
	List<Item> getItemByCategoryEnum(String category);

	



}