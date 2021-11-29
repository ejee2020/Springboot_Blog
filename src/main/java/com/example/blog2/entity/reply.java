package com.example.blog2.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Document
public class reply extends baseEntity{
    @Indexed
    String id;
    String post_id;
}
