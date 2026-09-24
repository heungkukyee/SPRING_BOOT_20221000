package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동 추가

@Controller // 컨트롤러 어노테이션 명시
public class DemoController {
  @Autowired
  TestService testService; // DemoController 클래스 아래 객체 주입

  @GetMapping("/hello") // 전송 방식 GET
  public String hello(Model model) {
    model.addAttribute("data", "반갑습니다."); // model 설정
    return "hello"; // hello.html 연결
  }

  @GetMapping("/hello2")
  public String hello2(Model model) {
    model.addAttribute("name", "유장건님");
    model.addAttribute("hi", "반갑습니다.");
    model.addAttribute("today", "오늘");
    model.addAttribute("weather", "날씨는");
    model.addAttribute("good", "매우 좋습니다.");
    return "hello2";
  }

  @GetMapping("/testdb")
public String getAllTestDBs(Model model) {
    // 1명(홍길동)만 조회하던 것을 전체 목록(List) 조회로 변경
    List<TestDB> testList = testService.getAllTestDBs(); // 또는 testService.findAll();
    
    // HTML로 전달하는 변수명을 data4로 설정
    model.addAttribute("data4", testList);
    
    System.out.println("데이터 출력 디버그 : " + testList);
    return "testdb";
}
}