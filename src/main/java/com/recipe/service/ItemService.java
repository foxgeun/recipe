package com.recipe.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.entity.Item;
import com.recipe.repository.ItemRepository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.recipe.constant.AnswerOk;
import com.recipe.constant.ItemInqBoardEnum;
import com.recipe.constant.ItemInqEnum;
import com.recipe.dto.ItemCategoryDto;
import com.recipe.dto.ItemDetailDto;
import com.recipe.dto.ItemInqDto;
import com.recipe.dto.ItemReviewDto;
import com.recipe.dto.ItemReviewImgDto;
import com.recipe.dto.ItemSearchDto;
import com.recipe.dto.ItemReviewAnswerDto;
import com.recipe.entity.Item;
import com.recipe.entity.ItemInq;
import com.recipe.entity.ItemInqAnwser;
import com.recipe.entity.ItemReview;
import com.recipe.entity.ItemReviewAnswer;
import com.recipe.repository.ItemInqAnswerRepository;
import com.recipe.repository.ItemInqRepository;
import com.recipe.repository.ItemRepository;
import com.recipe.repository.ItemReviewAnswerRepository;
import com.recipe.repository.ItemReviewImgRepositroy;
import com.recipe.repository.ItemReviewRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {


	private final ItemRepository itemRepository;


	private final ItemReviewRepository itemReviewRepository;

	private final ItemReviewAnswerRepository itemReviewAnswerRepository;

	private final ItemInqRepository itemInqRepository;
	
	private final ItemInqAnswerRepository itemInqAnswerRepository;
	
	private final ItemReviewImgRepositroy itemReviewImgRepositroy;
	
//	public Page<Item> getItemPage(Pageable pageable, ItemSearchDto itemSearchDto){
//	    if (itemSearchDto.getItemCategoryEnum() == null || itemSearchDto.getItemCategoryEnum().isEmpty()) {
//	 
//	        return itemRepository.findAll(pageable);
//	    } else {
//	        // 필터링된 결과를 저장할 List 생성
//	    	System.out.println(itemSearchDto.getItemCategoryEnum()+"momomommoo");
//	        List<Item> filteredItems = new ArrayList<>();
//	        List<Item> allItems = itemRepository.getAllItemList();
//	        
//	        for(Item item : allItems) {
//	        	System.out.println(item.getCategory()+"111111");
//	        	System.out.println(itemSearchDto.getItemCategoryEnum()+"222222");
//	            if(item.getCategory().equals(itemSearchDto.getItemCategoryEnum())) {
//	            	System.out.println("성공");
//	                filteredItems.add(item);
//	                
//	                
//	            }
//	        }
//	        System.out.println(filteredItems);
//	        // 필터링된 결과를 Page 객체로 반환
//	        return new PageImpl<>(filteredItems, pageable, filteredItems.size());
//	    }
//	}
	
//	public Page<Item> getItemPage(Pageable pageable, ItemSearchDto itemSearchDto){
//	    if (itemSearchDto.getItemCategoryEnum() == null || itemSearchDto.getItemCategoryEnum().isEmpty()) {
//	        return itemRepository.findAll(pageable);
//	    } else {
//	        return itemRepository.findByCategory(itemSearchDto.getItemCategoryEnum(), pageable);
//	    }
//	}
	public Page<Item> getItemPage(Pageable pageable, ItemSearchDto itemSearchDto) {
	    // 1. 검색 쿼리와 카테고리 값 모두가 비어있는 경우 전체 데이터를 반환합니다.
	    if ((itemSearchDto.getSearchQuery() == null || itemSearchDto.getSearchQuery().isEmpty()) && 
	        (itemSearchDto.getItemCategoryEnum() == null || itemSearchDto.getItemCategoryEnum().isEmpty())) {
	        return itemRepository.findAll(pageable);
	    }

	    // 2. 검색 쿼리가 존재하고 카테고리 값이 비어있는 경우 상품 이름을 기준으로 검색합니다.
	    if (itemSearchDto.getSearchQuery() != null && !itemSearchDto.getSearchQuery().isEmpty() && 
	        (itemSearchDto.getItemCategoryEnum() == null || itemSearchDto.getItemCategoryEnum().isEmpty())) {
	        return itemRepository.findByItemNmContaining(itemSearchDto.getSearchQuery(), pageable);
	    }

	    // 3. 검색 쿼리가 비어있고 카테고리 값이 존재하는 경우 카테고리를 기준으로 검색합니다.
	    if ((itemSearchDto.getSearchQuery() == null || itemSearchDto.getSearchQuery().isEmpty()) && 
	        itemSearchDto.getItemCategoryEnum() != null && !itemSearchDto.getItemCategoryEnum().isEmpty()) {
	        return itemRepository.findByCategory(itemSearchDto.getItemCategoryEnum(), pageable);
	    }

	    // 4. 검색 쿼리와 카테고리 값 모두가 존재하는 경우 두 조건 모두를 만족하는 결과를 반환합니다.
	    return itemRepository.findByItemNmContainingAndCategory(itemSearchDto.getSearchQuery(), itemSearchDto.getItemCategoryEnum(), pageable);
	}

	
	public List<Item> getAllItemList(){
		return itemRepository.getAllItemList();
	}
	
	public Item getItemByItemId(int Id) {
		return itemRepository.getItemByItemId(Id);
	}
	
	public List<Item> getItemByCategoryEnum(String category){
		return itemRepository.getItemByCategoryEnum(category);
	}





	
	@Transactional(readOnly = true)
	public ItemDetailDto getItemDetailList(Long itemId) {
		ItemDetailDto getItemDetailList = itemRepository.getItemDetailList(itemId);
		return getItemDetailList;
	}

//	모든 상품 가져오기
	@Transactional(readOnly = true)
	public Page<ItemCategoryDto> getItemCategoryList(Pageable pageable, ItemSearchDto itemSearchDto) {
		Page<ItemCategoryDto> getItemCategoryList = itemRepository.getItemCategoryList(pageable, itemSearchDto);
		return getItemCategoryList;
	}

//	상품의 리뷰글 가져오기
	@Transactional(readOnly = true)
	public Page<ItemReviewDto> getItemReviewList(Pageable pageable, Long itemId) {
		Page<ItemReviewDto> getItemReviewList = itemRepository.getItemReviewList(pageable, itemId);
		return getItemReviewList;
	}
	
//	상품의 리뷰의 이미지 가져오기
	@Transactional(readOnly = true)
	public List<ItemReviewImgDto> getItemReviewImgList(Long id) {
		List<ItemReviewImgDto> getItemReviewImgList = itemReviewImgRepositroy.getItemReviewImgList(id) ;
		return getItemReviewImgList;
	}
	
//	상품의 문의글 가져오기
	@Transactional(readOnly = true)
	public Page<ItemInqDto> getItemInqList(Pageable pageable , Long itemId){
		Page<ItemInqDto> getItemInqList =  itemRepository.getItemInqList(pageable ,itemId);
		return getItemInqList;
	}

//	리뷰 답변 등록
	public void itemReviewAnswerReg(@RequestBody Map<String, Object> requestBody) {
		Long id = Long.parseLong(requestBody.get("id").toString());
		String content = requestBody.get("content").toString();
		
		ItemReview itemReview = itemReviewRepository.findById(id).orElseThrow();

		ItemReviewAnswer itemReviewAnswer = new ItemReviewAnswer();

		itemReviewAnswer.setItemReview(itemReview);
		itemReviewAnswer.setContent(content);
		itemReviewAnswerRepository.save(itemReviewAnswer);
	}

//	리뷰 답변 수정
	public void itemReviewAnswerUpdate(@RequestBody Map<String, Object> requestBody) {
		Long id = Long.parseLong(requestBody.get("id").toString());
		String content = requestBody.get("content").toString();
		
		ItemReviewAnswer itemReviewAnswer = itemReviewAnswerRepository.findById(id)
				.orElseThrow();

		itemReviewAnswer.setId(id);
		itemReviewAnswer.setContent(content);

		itemReviewAnswerRepository.save(itemReviewAnswer);
	}

//	리뷰 답변 삭제
	public void itemReviewAnswerDelete(Long id) {
		
		ItemReviewAnswer itemReviewAnswer = itemReviewAnswerRepository.findById(id).orElseThrow();

		itemReviewAnswerRepository.delete(itemReviewAnswer);
	}

//	문의글 등록
	public void itemInqReg(Map<String, Object> requestBody) {

		Long id = Long.parseLong(requestBody.get("id").toString());
		String title = requestBody.get("title").toString();
		String content = requestBody.get("content").toString();
		int itemInqBoardEnum = Integer.parseInt(requestBody.get("itemInqBoardEnum").toString());
		int itemInqEnum = Integer.parseInt(requestBody.get("itemInqEnum").toString());
		
		Item item = itemRepository.findById(id).orElseThrow();
		
		ItemInq itemInq = new ItemInq();
		itemInq.setItem(item);
		itemInq.setTitle(title);
		itemInq.setContent(content);
		
		switch (itemInqBoardEnum) {
		case 2:
			itemInq.setItemInqBoardEnum(ItemInqBoardEnum.비밀글);
			break;
		}
		
		switch (itemInqEnum) {
		case 1:
			itemInq.setItemInqEnum(ItemInqEnum.배송문의);
			break;
		case 2:
			itemInq.setItemInqEnum(ItemInqEnum.재입고문의);
			break;
		case 3:
			itemInq.setItemInqEnum(ItemInqEnum.상품상세문의);
			break;
		case 4:
			itemInq.setItemInqEnum(ItemInqEnum.기타문의);
			break;
		}
		
		
		itemInqRepository.save(itemInq);
	}
	
	
//	리뷰 답변 등록
	public void itemInqAnswerReg(Map<String, Object> requestBody) {
		
		Long id = Long.parseLong(requestBody.get("id").toString());
		String content = requestBody.get("content").toString();
		
		ItemInq itemInq = itemInqRepository.findById(id).orElseThrow();
		itemInq.setAnswerOk(AnswerOk.답변완료);
		itemInqRepository.save(itemInq);
		
		ItemInqAnwser itemInqAnwser = new ItemInqAnwser();
		itemInqAnwser.setItemInq(itemInq);
		itemInqAnwser.setContent(content);
		
		itemInqAnswerRepository.save(itemInqAnwser);

	}
	
//	리뷰 답변 수정
	public void itemInqAnswerUpdate(Map<String, Object> requestBody) {
		
		Long id = Long.parseLong(requestBody.get("id").toString());
		String content = requestBody.get("content").toString();
		
		ItemInqAnwser itemInqAnwser = itemInqAnswerRepository.findById(id).orElseThrow();
		itemInqAnwser.setId(id);
		itemInqAnwser.setContent(content);
		
		itemInqAnswerRepository.save(itemInqAnwser);

	}
	
	
//	리뷰 답변 삭제
	public void itemInqAnswerDelete(Map<String, Object> requestBody) {
		
		Long id = Long.parseLong(requestBody.get("id").toString());
		
		ItemInqAnwser itemInqAnwser = itemInqAnswerRepository.findById(id).orElseThrow();
		
		ItemInq itemInq = itemInqRepository.findById(itemInqAnwser.getItemInq().getId()).orElseThrow();
		itemInq.setAnswerOk(AnswerOk.답변대기);
		itemInqRepository.save(itemInq);
		
		itemInqAnswerRepository.delete(itemInqAnwser);
	}
}

