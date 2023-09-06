package com.recipe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.recipe.dto.MemberDto;
import com.recipe.dto.MemberSearchDto;
import com.recipe.dto.RecipeSearchDto;
import com.recipe.entity.Member;
import java.util.List;

import com.recipe.dto.MemberBestDto;
import com.recipe.dto.MemberDto;
import com.recipe.dto.MemberMainDto;


import com.recipe.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;





@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	
	//-----------------용규형님------------------------
	
	private final MemberRepository memberRepository;
	
	//회원가입 데이터를 DB에 저장한다
	public Member saveMember(Member member) {
		
		validateDuplicateMember(member); //중복체크
		
		
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}
	
	//이메일 중복체크
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다");
		}
	}

	@Override //시큐리티 ,DB에서 사용자의 정보를 확인해서 로그인
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) { //DB에 사용자가 없으면
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
					.username(member.getEmail())
					.password(member.getPassword())
					.roles(member.getRole().toString())
					.build();
	}

	
	//휴대폰 번호로 가입 이메일 찾기
	public String findEmail(String phoneNumber) {
		
		String memberEmail = memberRepository.findEmailByPhoneNumber(phoneNumber);
		
		return memberEmail;
	}
	
	
	//----------------용규형님-----------------
	
	
	
	//----------------민규형님------------------------
	
	@Transactional(readOnly = true)
	public List<MemberMainDto> getMemberBestList() {
		List<MemberMainDto> getMemberBestList = memberRepository.getMemberBestList();
		return getMemberBestList;
		
	}

	//
	@Transactional(readOnly = true)
	public Page<MemberDto> getAdminMemberPage(RecipeSearchDto recipeSearchDto, Pageable pageable) {
		Page<MemberDto> memberPage = memberRepository.getAdminMemberPage(recipeSearchDto, pageable);
		return memberPage;


	}
	
	@Transactional(readOnly = true)
	public List<MemberBestDto> getRankMemberList() {
		List<MemberBestDto> getRankMemberList = memberRepository.getRankMemberList();
		return getRankMemberList;
	}
	
	//----------------민규형님------------------------
	
	
	//----------------민기형-------------------------
	






	// 회원 삭제
	public void deleteMember(Long memberId) {

		// ★delete하기 전에 select를 한번 해준다
		// ->영속성 컨텍스트에 엔티티를 저장한 후 변경 감지를 하도록 하기 위해
		Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);

		// delete
		memberRepository.delete(member);
	}
	//----------------민기형-------------------------
	
	
}