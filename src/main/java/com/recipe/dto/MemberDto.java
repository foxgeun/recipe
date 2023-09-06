package com.recipe.dto;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.querydsl.core.annotations.QueryProjection;
import com.recipe.entity.Member;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	private Long id;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다")
	@Email(message = "이메일 형식으로 입력해주세요")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다")
	@Length(min = 9, max = 15, message = "비밀번호는 9 ~ 15자 사이로 입력해주세요")
	private String password;
	
	private String passwordConfirm;
	
	@Length(min = 2, max = 8, message = "닉네임은 2~8자 사이로 입력해주세요")
	@NotNull
	private String nickname; //사이트용 닉네임
	
	@NotEmpty
	@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력해주세요.")
	private String phoneNumber;

	
	private String emailConfirm; //이메일로 받은 인증번호
	
	private String emailConfirm2; ////이메일로 받은 인증번호 확인용
	
	private String postCode; //우편번호
	
	private String address; //주소
	
	private String detailAddress; //상세주소
	
	private String introduce; //자기소개 ,우선 기본값만 세팅 수정해서 쓰게끔
	
	private String imgUrl; //프로필이미지
	
	private String oriImgName; //프로필이지미 원본이름
	
	private String imgName; //바뀐 이미지 이름
		
	private String name;
	

	private Long allCommentCount;

	private Long allRecipeCount;

	private String commentContent;

	private LocalDateTime regTime; // 등록날짜

	private String formattedRegTime; // 보기 좋은 형식으로 변환된 등록날짜

	@QueryProjection
	public MemberDto(Long id, String nickname, String email, String password, String phoneNumber, Long allCommentCount,
			Long allRecipeCount, LocalDateTime regTime) {

		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.allCommentCount = allCommentCount;
		this.allRecipeCount = allRecipeCount;

		// 등록날짜를 보기 좋은 형식으로 변환하여 formattedRegTime에 저장
		if (regTime != null) {
			this.regTime = regTime;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			this.formattedRegTime = regTime.format(formatter);
		}

	}
	
	public MemberDto() {
	}



	public static MemberDto memberDto() {
		MemberDto memberDto = new MemberDto();
		
		if(memberDto.getName() != null) {
			memberDto.setNickname(memberDto.getName());
		} 
		
		if(memberDto.getNickname() == null) {
			memberDto.setNickname(memberDto.getEmail());
		}
		return memberDto;
	}



	// 상품 이미지 정보를 저장
	private List<RecipeDto> recipeDtoList = new ArrayList<>();

	private static ModelMapper modelMapper = new ModelMapper();

	// dto -> entity로 바꿈
	public Member createMember() {
		return modelMapper.map(this, Member.class);
	}

	// entity -> dto로 바꿈
	public static MemberDto of(Member member) {

		return modelMapper.map(member, MemberDto.class);
	}



	
	private String provider;
	
	private String providerId;
	
}
