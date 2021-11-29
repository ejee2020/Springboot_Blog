package com.example.blog2.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Document
public class category extends baseEntity{
    @Indexed
    private String id;
    private String name;
    private String user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
