package com.nanoit.agent.controller;

// 필요한 클래스들을 임포트합니다.
import com.nanoit.agent.dto.MemberRegisterDto; // 회원가입 데이터 전송 객체
import com.nanoit.agent.service.MemberService;   // 회원 관련 비즈니스 로직 서비스
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;         // HTTP 세션 사용 (로그인 상태 유지 목적)
import lombok.RequiredArgsConstructor;             // Lombok: final 필드 생성자 자동 주입
import org.springframework.stereotype.Controller; // 스프링 MVC 컨트롤러 선언
import org.springframework.ui.Model;             // 뷰(HTML)에 데이터 전달
import org.springframework.web.bind.annotation.GetMapping;    // HTTP GET 요청 매핑
import org.springframework.web.bind.annotation.ModelAttribute; // 폼 데이터를 객체에 자동 바인딩
import org.springframework.web.bind.annotation.PostMapping;   // HTTP POST 요청 매핑
import org.springframework.web.bind.annotation.RequestParam;  // 요청 파라미터 개별적으로 받기
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 리다이렉트 시 데이터 전달

@Controller // 이 클래스가 웹 요청을 처리하는 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor // final로 선언된 필드의 생성자를 자동으로 만들어 의존성을 주입합니다 (DI).
public class AuthController {

    // MemberService를 주입받아 사용합니다. (final 키워드 + @RequiredArgsConstructor)
    private final MemberService memberService;

    /**
     * 로그인 페이지를 보여주는 요청을 처리합니다.
     * 회원가입 성공 또는 로그인 실패 시 메시지를 표시할 수 있습니다.
     * @param model 뷰에 데이터를 전달하기 위한 객체
     * @param registrationSuccess 회원가입 성공 여부 파라미터 (리다이렉트 시 전달됨)
     * @param error 로그인 실패 여부 파라미터 (리다이렉트 시 전달됨)
     * @return "login" 뷰 이름 (login.html)
     */
    @GetMapping("/login")
    public String loginForm(Model model,
                            @RequestParam(value = "registrationSuccess", required = false) String registrationSuccess,
                            @RequestParam(value = "error", required = false) String error) {
        // 회원가입 성공 파라미터가 있으면 성공 메시지를 모델에 추가
        if (registrationSuccess != null) {
            model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다. 로그인해주세요.");
        }
        // 로그인 실패 파라미터가 있으면 실패 메시지를 모델에 추가
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        return "login"; // src/main/resources/templates/login.html 파일을 반환
    }

    /**
     * 로그인 폼에서 제출된 데이터를 처리합니다.
     * @param loginId 사용자가 입력한 아이디
     * @param password 사용자가 입력한 비밀번호
     * @param session 로그인 성공 시 사용자 정보를 저장하기 위한 HttpSession 객체
     * @param redirectAttributes 리다이렉트 시 메시지(주로 에러)를 전달하기 위한 객체
     * @return 로그인 성공 시 메인 페이지로 리다이렉트, 실패 시 로그인 페이지로 리다이렉트
     */
    @PostMapping("/login")
    public String loginProcess(@RequestParam String loginId, @RequestParam String password,
                               HttpSession session, // 세션 객체 주입
                               RedirectAttributes redirectAttributes) { // 리다이렉트 속성 객체 주입

        // MemberService를 통해 아이디와 비밀번호가 유효한지 확인
        boolean isAuthenticated = memberService.authenticate(loginId, password);

        if (isAuthenticated) {
            // --- 로그인 성공 시 임시로 세션에 사용자 아이디 저장 ---
            // 실제 운영 환경에서는 사용자 정보 전체나 권한 등을 더 안전하게 관리해야 합니다.
            session.setAttribute("loggedInUserId", loginId);
            session.setMaxInactiveInterval(1800); // 세션 유효 시간 설정 (예: 30분)
            // --- END 임시 세션 저장 ---

            System.out.println("Login successful for: " + loginId);
            return "redirect:/main"; // 로그인 성공 시 메인 페이지(/main)로 이동
        } else {
            // 로그인 실패 시, "/login" 경로로 리다이렉트하면서 "error=true" 파라미터를 추가합니다.
            // loginForm 메소드에서 이 파라미터를 받아 에러 메시지를 표시합니다.
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }

    /**
     * 회원가입 페이지를 보여주는 요청을 처리합니다.
     * @param model 뷰에 빈 MemberRegisterDto 객체를 전달하여 폼 데이터 바인딩 준비
     * @return "register" 뷰 이름 (register.html)
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        // Thymeleaf의 th:object와 th:field를 사용하기 위해
        // 비어있는 DTO 객체를 "memberRegisterDto"라는 이름으로 모델에 담아 전달합니다.
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "register"; // src/main/resources/templates/register.html 파일을 반환
    }

    /**
     * 회원가입 폼에서 제출된 데이터를 처리합니다.
     * @param memberRegisterDto 폼 데이터가 자동으로 바인딩된 DTO 객체 (@ModelAttribute 사용)
     * @param confirmPassword 비밀번호 확인 필드 값 (DTO에 포함되지 않음)
     * @param agree 약관 동의 체크박스 값 (DTO에 포함되지 않음)
     * @param model 에러 발생 시 뷰에 에러 메시지와 입력값을 다시 전달하기 위한 객체
     * @return 회원가입 성공 시 로그인 페이지로 리다이렉트, 실패 시 회원가입 페이지 다시 표시
     */
    @PostMapping("/register")
    public String registerProcess(
            @ModelAttribute MemberRegisterDto memberRegisterDto, // 폼 데이터 자동 바인딩
            @RequestParam String confirmPassword,
            @RequestParam(defaultValue = "false") boolean agree,
            Model model) {

        // 1. 약관 동의 여부 검증
        if (!agree) {
            model.addAttribute("error", "이용약관 및 개인정보처리방침에 동의해야 합니다.");
            model.addAttribute("memberRegisterDto", memberRegisterDto); // 입력값 유지를 위해 DTO 전달
            return "register"; // 에러 메시지와 함께 register.html 반환
        }

        // 2. 비밀번호 길이 검증
        if (memberRegisterDto.getPassword() == null || memberRegisterDto.getPassword().length() < 6) {
            model.addAttribute("error", "비밀번호는 6자 이상이어야 합니다.");
            model.addAttribute("memberRegisterDto", memberRegisterDto);
            return "register";
        }

        // 3. 비밀번호 일치 여부 검증
        if (!memberRegisterDto.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("memberRegisterDto", memberRegisterDto);
            return "register";
        }

        // 4. MemberService를 통해 실제 회원가입 로직(DB 저장) 처리
        try {
            memberService.register(memberRegisterDto);
            // 회원가입 성공 시, "/login" 경로로 리다이렉트하며 "registrationSuccess=true" 파라미터 전달
            return "redirect:/login?registrationSuccess=true";
        } catch (IllegalArgumentException e) { // MemberService에서 아이디 중복 등 특정 예외 발생 시
            model.addAttribute("error", e.getMessage()); // 서비스에서 발생한 에러 메시지 전달
            model.addAttribute("memberRegisterDto", memberRegisterDto);
            return "register"; // 에러 메시지와 함께 register.html 반환
        } catch (Exception e) { // 그 외 예상치 못한 예외 발생 시
            // 실제 운영 시에는 로그를 기록하는 것이 좋습니다.
            e.printStackTrace(); // 개발 중에는 스택 트레이스 출력
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("memberRegisterDto", memberRegisterDto);
            return "register";
        }
    }

    /**
     * 메인 페이지를 보여주는 요청을 처리합니다. (로그인 후 접근 가능해야 함)
     * @return "main" 뷰 이름 (main.html)
     */
    @GetMapping("/main")
    public String mainPage() {
        // --- TODO: 실제로는 로그인된 사용자만 접근 가능하도록 보안 처리 필요 ---
        // 예: 인터셉터나 Spring Security를 사용하여 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
        return "main"; // src/main/resources/templates/main.html 파일을 반환한다고 가정
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 현재 세션을 가져와서 무효화합니다.
        HttpSession session = request.getSession(false); // false: 세션이 없으면 새로 생성하지 않음
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        // 로그아웃 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }
}