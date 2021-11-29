package com.example.blog2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class categoryDTO {
    private String name;
    private String user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
