package com.url.shortner.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EventClickDto {
    private LocalDate clickDate;
    private Long count;
}
