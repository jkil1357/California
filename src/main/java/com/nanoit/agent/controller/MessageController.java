package com.nanoit.agent.controller;

import com.nanoit.agent.dto.SendMessageDto;
import com.nanoit.agent.service.MessageService;
import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.Authentication; // Spring Security 사용 시 주석 해제
// import org.springframework.security.core.context.SecurityContextHolder; // Spring Security 사용 시 주석 해제
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession; // 임시 세션 사용 예시

@Controller
@RequestMapping("/messages") // 메시지 관련 경로는 /messages 로 시작하도록 그룹화
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 메시지 작성 폼 보여주기
    @GetMapping("/new")
    public String showMessageForm(Model model, HttpSession session) { // HttpSession 임시 사용

        // --- TODO: 실제 로그인 여부 확인 및 권한 체크 로직 필요 ---
        // 예시: Spring Security 사용 시 @PreAuthorize("isAuthenticated()") 등 사용
        String loggedInUserId = (String) session.getAttribute("loggedInUserId"); // 세션에서 임시로 ID 가져오기
        if (loggedInUserId == null) {
            // 로그인되지 않았으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        // --- END TODO ---

        // 폼 바인딩을 위한 빈 DTO 객체 전달
        model.addAttribute("sendMessageDto", new SendMessageDto());
        return "new-message"; // templates/new-message.html 반환
    }

    // 메시지 전송 처리
    @PostMapping
    public String sendMessage(@ModelAttribute SendMessageDto sendMessageDto,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) { // HttpSession 임시 사용

        // --- TODO: 실제 로그인된 사용자 정보 가져오기 ---
        // 예시 1: Spring Security 사용 시
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String currentPrincipalName = authentication.getName(); // 보통 여기에 loginId 저장
        // 예시 2: HttpSession 사용 (AuthController 에서 로그인 성공 시 세션에 저장했다고 가정)
        String senderLoginId = (String) session.getAttribute("loggedInUserId"); // 세션에서 임시로 ID 가져오기

        if (senderLoginId == null) {
            // 로그인되지 않았으면 로그인 페이지로 리다이렉트 (오류 처리)
            redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        }
        // --- END TODO ---


        try {
            messageService.saveMessage(sendMessageDto, senderLoginId);
            redirectAttributes.addFlashAttribute("message", "메시지가 성공적으로 전송(저장)되었습니다.");
            return "redirect:/messages/new"; // 성공 시 다시 메시지 작성 폼으로
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "메시지 저장 중 오류 발생: " + e.getMessage());
            // 실패 시에도 입력값 유지를 위해 DTO 전달 (FlashAttribute 사용)
            redirectAttributes.addFlashAttribute("sendMessageDto", sendMessageDto);
            return "redirect:/messages/new"; // 실패 시 다시 메시지 작성 폼으로
        }
    }
}