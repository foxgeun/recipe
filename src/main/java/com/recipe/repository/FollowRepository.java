package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.recipe.entity.Follow;
import com.recipe.entity.Member;

public interface FollowRepository extends JpaRepository<Follow, Long>{
	
	@Modifying 
	@Query(value= "insert into Follow(member_id, from_member_id) VALUES(?1,?2)", nativeQuery = true)
	void saveFollow(Member memberId,String fromMember);
	
	 void deleteByToMemberAndFromMember(Member toMember, Member fromMember);
	 
	 boolean existsByToMemberAndFromMember(Member toMember, Member fromMember);
	 
	    // 특정 사용자를 팔로우한 사용자 수 조회
	 	@Query(value="SELECT COUNT(*) FROM Follow WHERE member_id = ?1" , nativeQuery = true)
	    int countToMember(Long memberId);
	 	
	 	// 특정 사용자가 팔로우한 사용자 수 조회
	 	@Query(value="SELECT COUNT(*) FROM Follow WHERE from_member_id = ?1" , nativeQuery = true)
	 	int countFromMember(Long fromMember);


	 
}
