package id.jasoet.wof.scheduler.domain.repository;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import lombok.val;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DailyShiftRepositoryTest {

    @Test
    public void givenGeneratedDataShouldAbleToSaveItInMemory() {
        val repository = new DailyShiftRepository();

        val dateParameter = LocalDate.of(2018, Month.APRIL, 1);

        val dailyShifts = repository.getNextTwoWeekShift(dateParameter);

        repository.replaceAll(dailyShifts);
        val retrievedShifts = repository.retrieveAll();

        dailyShiftsCheck(retrievedShifts);
        repository.removeAll();
    }

    @Test
    public void givenRemovedDataShouldReturnEmptyShifts() {
        val repository = new DailyShiftRepository();

        val dateParameter = LocalDate.of(2018, Month.APRIL, 1);

        val dailyShifts = repository.getNextTwoWeekShift(dateParameter);

        repository.replaceAll(dailyShifts);
        repository.removeAll();
        val retriedShifts = repository.retrieveAll();
        assertThat(retriedShifts).isEmpty();
    }

    @Test
    public void givenCertainDateShouldAbleToPopulateTwoWeeksDailyShifts() {
        val repository = new DailyShiftRepository();

        val dateParameter = LocalDate.of(2018, Month.APRIL, 1);

        assertThat(dateParameter.getDayOfWeek()).isEqualByComparingTo(DayOfWeek.SUNDAY);

        val dailyShifts = repository.getNextTwoWeekShift(dateParameter);
        dailyShiftsCheck(dailyShifts);
    }

    @Test
    public void givenTodayShouldAbleToPopulateTwoWeeksDailyShifts() {
        val repository = new DailyShiftRepository();

        val dailyShifts = repository.getNextTwoWeekShift();
        dailyShiftsCheck(dailyShifts);
    }

    @Test
    public void givenCertainDayOfWeekParameterShouldAbleToPopulateTwoWeeksDailyShifts() {
        for (val dayOfWeek : DayOfWeek.values()) {
            checkDayByDay(dayOfWeek);
        }
    }


    private void checkDayByDay(DayOfWeek dayOfWeek) {
        val repository = new DailyShiftRepository();

        val dateParameter = LocalDate.of(2018, Month.APRIL, 1).with(TemporalAdjusters.next(dayOfWeek));
        assertThat(dateParameter.getDayOfWeek()).isEqualByComparingTo(dayOfWeek);

        val dailyShifts = repository.getNextTwoWeekShift(dateParameter);
        dailyShiftsCheck(dailyShifts);
    }

    private void dailyShiftsCheck(List<DailyShift> dailyShifts) {
        assertThat(dailyShifts).isNotEmpty();
        assertThat(dailyShifts.size()).isEqualTo(10);

        val assignedEngineer = dailyShifts
                .stream()
                .flatMap(s -> Stream.of(s.getFirstHalf(), s.getSecondHalf()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        assertThat(assignedEngineer).isEmpty();

        assertThat(dailyShifts.get(0).getDate().getDayOfWeek()).isEqualByComparingTo(DayOfWeek.MONDAY);
        assertThat(dailyShifts.get(dailyShifts.size() - 1).getDate().getDayOfWeek()).isEqualByComparingTo(DayOfWeek.FRIDAY);

        val availableDays = dailyShifts.stream().map(d -> d.getDate().getDayOfWeek()).collect(Collectors.toList());
        assertThat(availableDays.stream().anyMatch(d -> d.equals(DayOfWeek.SATURDAY))).isFalse();
        assertThat(availableDays.stream().anyMatch(d -> d.equals(DayOfWeek.SUNDAY))).isFalse();

    }

}