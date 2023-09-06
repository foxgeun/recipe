package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
