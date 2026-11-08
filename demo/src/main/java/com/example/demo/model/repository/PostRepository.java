package com.example.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository // 게시글 리포지토리 : save, findAll, findById, delete 는 이미 있다
public interface PostRepository extends JpaRepository<Post, Long> {
  // 10주차 연습문제1: 조회수
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
  void incrementViewCount(@Param("id") Long id);

  // 10주차 연습문제2: 작성자별 글 모아보기
  List<Post> findByAuthor_UsernameOrderByIdDesc(String username);
}
