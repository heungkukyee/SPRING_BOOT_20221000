package com.example.demo.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.domain.Member;
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
  // List<Post> findByAuthor_UsernameOrderByIdDesc(String username);

  // 11주차 연습문제1: 내가 쓴 글 페이징
  Page<Post> findByAuthor_Username(String username, Pageable pageable);

  // [11주차] 단계 2 : 제목 또는 내용에 검색어가 들어간 글 (JPQL)
  @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
  Page<Post> search(@Param("keyword") String keyword, Pageable pageable);

  // 11주차 연습문제2: 작성자로 검색
  @Query("SELECT p FROM Post p WHERE p.author.name LIKE %:name%")
  Page<Post> searchByAuthorName(@Param("name") String name, Pageable pageable);

  boolean existsByAuthor(Member author); // 이 회원이 쓴 글이 있는지
}
