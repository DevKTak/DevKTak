package com.devktak.modules.navMenu;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @EqualsAndHashCode(of ="id")
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BodyLog {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String contents;

    private String originFileName;

    private String saveFileName;

    private String extension;

    private Long fileSize;

    private String fullPath;
}
