package com.recipe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe.entity.ItemImg;
import com.recipe.repository.ItemImgRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

	
	private final ItemImgRepository itemImgRepository;
	
	public List<ItemImg> getItemImgByItemId(int id){
		return itemImgRepository.getItemImgByItemId(id);
	}
	
	
}
