package com.url.shortner.controller;

import com.url.shortner.dtos.EventClickDto;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.model.EventClick;
import com.url.shortner.model.UrlMapping;
import com.url.shortner.model.User;
import com.url.shortner.service.UrlMappingService;
import com.url.shortner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls/")
@AllArgsConstructor
public class UrlMappingController {

    private UserService userService;
    private UrlMappingService urlMappingService;

     // { "originalUrl":"https://example.com"}

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String, String> request,
                                                        Principal principal) {
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDto urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDTO);
    }

        @GetMapping("/myUrls")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<List<UrlMappingDto>> getUserUrls(Principal principal){
            User user = userService.findByUsername(principal.getName());
            List<UrlMappingDto> userUrls = urlMappingService.getUserUrls(user);
            return ResponseEntity.ok(userUrls);

        }

        @GetMapping("/analytics/{shortUrl}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<List<EventClickDto>> getUrlAnalytics(@PathVariable String shortUrl,
                                                                   @RequestParam ("startDate") String startDate,
                                                                   @RequestParam ("endDate") String endDate){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime start = LocalDateTime.parse(startDate, formatter);
            LocalDateTime end = LocalDateTime.parse(endDate, formatter);
            List<EventClickDto> eventClickDtos = urlMappingService.getClickEventsByDate(shortUrl,start,end);
            return ResponseEntity.ok(eventClickDtos);

        }



        }
