package com.example.blog2.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Document
public class post extends baseEntity implements Serializable {
    @Indexed
    @Id
    private String id;
    private String name;
    private String writer;
    private String title;
    private String post_text;
    private String category;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
