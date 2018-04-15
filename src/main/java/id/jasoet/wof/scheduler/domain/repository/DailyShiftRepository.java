package id.jasoet.wof.scheduler.domain.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Repository to handle {@link DailyShift} generation based on certain date.
 * As assignment require, each engineer need to complete a full day shift in span of two weeks time with certain rules
 * <p>
 * This class leverage Spring {@link Repository} annotation and will be managed by Spring DI Context.
 * Using Caffeine cache library to save generated DailyShifts in memory.
 * Intentionally not designed as singleton or utility class so in the future if we can easily change Repository implementation
 * and add supports for database integration.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */

@Repository
public class DailyShiftRepository {

    private Cache<LocalDate, DailyShift> dailyShiftCache = Caffeine.newBuilder().build();

    /**
     * Populate Two weeks of {@link DailyShift} based on today date.
     * Shift will be start from next first working day (monday) in the upcoming week.
     * If today is monday, it will start from next monday.
     *
     * @return List of {@link DailyShift}
     */
    public List<DailyShift> getNextTwoWeekShift() {
        return getNextTwoWeekShift(LocalDate.now());
    }

    /**
     * Populate Two weeks of {@link DailyShift} based on certain date.
     * Shift will be start from next first working day (monday) in the upcoming week.
     * If current date is monday, it will start from next monday.
     *
     * @return List of {@link DailyShift}
     */
    public List<DailyShift> getNextTwoWeekShift(LocalDate currentDate) {
        val finalFirstDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return IntStream.range(0, 12)
                .filter(i -> finalFirstDay.plusDays(i).getDayOfWeek() != DayOfWeek.SATURDAY &&
                        finalFirstDay.plusDays(i).getDayOfWeek() != DayOfWeek.SUNDAY)
                .mapToObj(i -> new DailyShift(finalFirstDay.plusDays(i), null, null))
                .collect(Collectors.toList());
    }


    /**
     * Invalidate all saved data and replace with a new one
     *
     * @param newShifts List of new DailyShift that will be stored
     */
    public void replaceAll(List<DailyShift> newShifts) {
        removeAll();
        val recordMap = newShifts.stream().collect(Collectors.toMap(DailyShift::getDate, s -> s));
        dailyShiftCache.putAll(recordMap);
    }


    /**
     * Invalidate all saved data
     */
    public void removeAll() {
        dailyShiftCache.invalidateAll();
    }

    /**
     * Retrieve all saved data from memory, sorted by date
     *
     * @return List of DailyShift
     */
    public List<DailyShift> retrieveAll() {
        val values = dailyShiftCache.asMap().values().stream().sorted(Comparator.comparing(DailyShift::getDate));
        return values.collect(Collectors.toList());
    }

}
