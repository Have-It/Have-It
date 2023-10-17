package com.meta.member.controller;



import com.meta.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class TestController {
    private final MemberService memberService;

    @GetMapping("/do")
    public String loginPage()
    {
        return "login";
    }

}
