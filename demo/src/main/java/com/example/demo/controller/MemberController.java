package com.example.demo.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.service.MemberService;

@Controller // 컨트롤러 어노테이션 명시
public class MemberController {
  @Autowired
  MemberService memberService; // 클래스 상단에 객체 주입

  @GetMapping("/login") // 로그인 화면 (POST /login 은 시큐리티가 처리)
  public String login() {
    return "login"; // login.html 연결
  }

  @GetMapping("/signup") // 회원가입 화면
  public String signupForm() {
    return "signup"; // signup.html 연결
  }

  @PostMapping("/signup") // 회원가입 처리
  public String signup(MemberForm memberForm, Model model) {
    // 비밀번호 확인 검증 : 다르면 가입 처리 없이 다시 가입 화면
    if (!Objects.equals(memberForm.getPassword(), memberForm.getPasswordConfirm())) {
      model.addAttribute("error", "비밀번호가 일치하지 않습니다."); // ${error}
      return "signup";
    }
    try {
      memberService.signup(memberForm);
    } catch (IllegalArgumentException e) { // 중복 아이디 등
      model.addAttribute("error", e.getMessage()); // ${error}
      return "signup"; // 입력값 유지한 채 다시 가입 화면
    }
    return "redirect:/login?signup"; // 성공 → 로그인 화면
  }

  // MemberController (import java.security.Principal, Member)
  @GetMapping("/mypage") // 내 정보 : 로그인한 사람만
  public String mypage(Principal principal, Model model) { // 현재 로그인 사용자
    Member member = memberService.findByUsername(principal.getName());
    model.addAttribute("member", member);
    return "mypage"; // mypage.html 연결
  }
}