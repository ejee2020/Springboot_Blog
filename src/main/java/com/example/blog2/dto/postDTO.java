package com.example.blog2.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class postDTO implements Serializable {
    private String id;
    private String writer;
    private String title;
    private String post_text;
    private String category;
}
