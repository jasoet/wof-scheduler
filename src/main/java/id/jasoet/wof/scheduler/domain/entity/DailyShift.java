package id.jasoet.wof.scheduler.domain.entity;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DailyShift {
    private LocalDate date;
    private Engineer firstHalf;
    private Engineer secondHalf;
}
