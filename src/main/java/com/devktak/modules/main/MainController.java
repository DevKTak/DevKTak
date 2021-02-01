package com.devktak.modules.main;

import com.devktak.modules.member.CurrentMember;
import com.devktak.modules.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(@CurrentMember Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
