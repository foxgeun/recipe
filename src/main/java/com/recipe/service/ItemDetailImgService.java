package com.recipe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.entity.ItemDetailImg;
import com.recipe.repository.ItemDetailImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemDetailImgService {

	private final ItemDetailImgRepository itemDetailImgRepository;
	
	public List<ItemDetailImg> getItemDetailImgByItemId(int id) {
		return itemDetailImgRepository.getItemDetailImgByItemId(id); 
	}
	
}
