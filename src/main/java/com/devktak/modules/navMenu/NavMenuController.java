package com.devktak.modules.navMenu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NavMenuController {

    @GetMapping("/kyungtak")
    public String kyungtakForm() {
        return "navMenu/kyungtak";
    }

    @GetMapping("/experience")
    public String experienceForm() {
        return "navMenu/experience";
    }

    @GetMapping("/education")
    public String education() {
        return "navMenu/education";
    }

    @GetMapping("/skills")
    public String skills() {
        return "navMenu/skills";
    }

    @GetMapping("/bodylog")
    public String bodylog() {
        return "navMenu/bodylog";
    }

    @GetMapping("/awards")
    public String awards() {
        return "navMenu/awards";
    }
}
