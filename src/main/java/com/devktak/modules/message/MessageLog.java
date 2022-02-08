package com.devktak.modules.message;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MessageLog {

    @Id @GeneratedValue
    private Long id;

    private String message;

    private LocalDateTime messageWritingTime;
}
