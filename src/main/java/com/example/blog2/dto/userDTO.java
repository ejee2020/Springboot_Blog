package com.example.blog2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class userDTO {
    private String id;
    private String password;
    private String email;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}