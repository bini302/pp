package com.plat.plantsplanet.controller;

import com.plat.plantsplanet.dto.MemberDTO;
import com.plat.plantsplanet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지 요청 출력
    @GetMapping("/PlantsPlanet/save")
    public String saveForm(){
        return "save";
    }
    @PostMapping("/PlantsPlanet/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "login";
    }

    //로그인페이지
    @GetMapping("/PlantsPlanet/login")
    public String loginForm(){
        return "login";
    }
    @PostMapping("/PlantsPlanet/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult=memberService.login(memberDTO);
        if (loginResult!=null){
            //login success
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            //세션에 로그인 정보를 담는다
            return "main";
        } else {
            //login fail
            return "login";
        }
    }

    //회원목록
    @GetMapping("/PlantsPlanet/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList=memberService.findAll();
        //여러개 조회
        // html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    //이메일 중복 확인(ajax)
    @PostMapping("/PlantsPlanet/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String emailCheckResult=memberService.emailCheck(memberEmail);
        return emailCheckResult;
    }
    //닉네임 중복 확인(ajax)
    @PostMapping("/PlantsPlanet/name-check")
    public @ResponseBody String nameCheck(@RequestParam("memberName") String memberName) {
        String nameCheckResult = memberService.nameCheck(memberName);
        return nameCheckResult;
    }

    //회원조회
    @GetMapping("/PlantsPlanet/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO=memberService.findById(id);
        //하나만 조회
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    //회원정보 수정
    @GetMapping("/PlantsPlanet/update")
    public String updateForm(HttpSession session, Model model){
        //오브젝트를 스트링에 담기엔 넘 크다, 강제 형변환
        String myEmail= (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO=memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }
    @PostMapping("/PlantsPlanet/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        //수정된 정보를 업데이트해서 리다이렉트
        return "redirect:/PlantsPlanet/"+memberDTO.getId();
    }

    //삭제
    @GetMapping("/PlantsPlanet/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/PlantsPlanet/";
    }

    //로그아웃, 세션에서 지운다
    @GetMapping("/PlantsPlanet/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
