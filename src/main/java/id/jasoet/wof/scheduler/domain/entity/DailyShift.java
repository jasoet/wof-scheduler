package id.jasoet.wof.scheduler.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/*
 * DailyShift class, to handle DailyShift Object.
 * This Class story Date and assigned engineer for first and second shift in a day.
 *
 * This class intentionally created as immutable using Lombok {@link Value}
 * Only minimal field available, for assignment purpose.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */

@Data
@AllArgsConstructor
public class DailyShift {
    private LocalDate date;
    private Engineer firstHalf;
    private Engineer secondHalf;
}
