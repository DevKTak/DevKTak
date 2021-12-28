package com.devktak.modules.navMenu;

import com.devktak.modules.member.CurrentMember;
import com.devktak.modules.member.Member;
import com.devktak.modules.navMenu.form.BodyLogForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NavMenuController {

    private final NavMenuService navMenuService;
    private final NavMenuRepository navMenuRepository;

    @GetMapping("/kyungtak")
    public String kyungtakForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);

        return "navMenu/kyungtak";
    }

    @GetMapping("/experience")
    public String experienceForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);

        return "navMenu/experience";
    }

    @GetMapping("/education")
    public String educationForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);

        return "navMenu/education";
    }

    @GetMapping("/skills")
    public String skillsForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);

        return "navMenu/skills";
    }

    @GetMapping("/bodyLog")
    public String bodyLogForm(@CurrentMember Member member,
                              @RequestParam(defaultValue = "") String keyword, Model model,
                              @PageableDefault(size = 6, sort = "title", direction = Sort.Direction.ASC)
                                      Pageable pageable) {
        Page<BodyLog> bodyLogPage = navMenuRepository.findByKeyword(keyword, pageable);
        model.addAttribute(new BodyLogForm());
        model.addAttribute("member", member);
        model.addAttribute("bodyLogPage", bodyLogPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortProperty", "title");

        return "navMenu/bodyLog";
    }

    @PostMapping("/bodyLog")
    public String bodyLogSubmit(@Valid @ModelAttribute BodyLogForm bodyLogForm, Errors errors,
                                RedirectAttributes attributes) throws IOException {
        if (errors.hasErrors()) {
            return "navMenu/bodyLog";
        }
        navMenuService.uploadBodyLogs(bodyLogForm);
        attributes.addFlashAttribute("message", "사진이 업로드 되었습니다.");
        return "redirect:/bodyLog";
    }

    @GetMapping("/awards")
    public String awardsForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);

        return "navMenu/awards";
    }
}
