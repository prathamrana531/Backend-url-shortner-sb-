package com.url.shortner.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDto {
    private Long id;
    private String orignalUrl;
    private String shortUrl;
    private LocalDateTime createdDate;
    private int clickCount;
    private String username;
}
