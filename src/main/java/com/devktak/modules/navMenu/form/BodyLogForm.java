package com.devktak.modules.navMenu.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@ToString
public class BodyLogForm {

    private String title;

    private String contents;

    private MultipartFile bodyPicture;
}
