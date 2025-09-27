package com.url.shortner.service;

import com.url.shortner.dtos.EventClickDto;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.model.EventClick;
import com.url.shortner.model.UrlMapping;
import com.url.shortner.model.User;
import com.url.shortner.repository.EventClickRepository;
import com.url.shortner.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private UrlMappingRepository urlMappingRepository;
    private EventClickRepository eventClickRepository;

    public UrlMappingDto createShortUrl(String orignalUrl, User user) {
        String shortUrl =generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setOriginalUrl(orignalUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }


    private UrlMappingDto convertToDto(UrlMapping urlMapping){
        UrlMappingDto urlMappingDto =new UrlMappingDto();
        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
        urlMappingDto.setId(urlMapping.getId());
        urlMappingDto.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDto.setOrignalUrl(urlMapping.getOriginalUrl());
        urlMappingDto.setClickCount(urlMapping.getClickCount());
        urlMappingDto.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDto;
    }

    private String generateShortUrl() {
        Random random=new Random();
        StringBuilder shortUrl= new StringBuilder(8);

        String characters= "ABCDEFGHIJKLMOPQURSTWUXYZabcdefhijklmnopqustwuxyzPRATHAMpratham";

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDto> getUserUrls(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToDto).toList();
    }

    public List<EventClickDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null){
            return eventClickRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        EventClickDto eventClickDto = new EventClickDto();
                        eventClickDto.setClickDate(entry.getKey());
                        eventClickDto.setCount(entry.getValue());
                        return eventClickDto;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<EventClick> clickEvents = eventClickRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));

    }

    public UrlMapping getOrignalUrl(String shortUrl){
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping != null) {
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            urlMappingRepository.save(urlMapping);
            EventClick clickEvent = new EventClick();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            eventClickRepository.save(clickEvent);
        }
        return urlMapping;
    }
}
