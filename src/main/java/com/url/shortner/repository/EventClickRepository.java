package com.url.shortner.repository;

import com.url.shortner.model.EventClick;
import com.url.shortner.model.UrlMapping;
import com.url.shortner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventClickRepository extends JpaRepository<EventClick,Long> {
     List<EventClick> findByUrlMappingAndClickDateBetween(UrlMapping mapping ,
                                                          LocalDateTime startDate ,
                                                          LocalDateTime endDate);

     List<EventClick> findByUrlMappingInAndClickDateBetween(List<UrlMapping> urlMappings,
                                                                  LocalDateTime startDate,
                                                                  LocalDateTime endDate);


}
