package com.devktak.modules.navMenu.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
@ToString
public class BodyLogForm {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    private List<MultipartFile> bodyPictures;
}
