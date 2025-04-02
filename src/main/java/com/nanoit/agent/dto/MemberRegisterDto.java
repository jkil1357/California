package com.nanoit.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterDto {
    private String email;
    private String name;
    private String loginId; // 회원가입 폼의 아이디 필드와 매핑
    private String password; // 회원가입 폼의 비밀번호 필드와 매핑
    // confirmPassword, agree 필드는 DTO에 포함하지 않고 컨트롤러에서 별도 처리
}