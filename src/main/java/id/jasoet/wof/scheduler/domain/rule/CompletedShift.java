package id.jasoet.wof.scheduler.domain.rule;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This rule check whether an engineer have completed his one whole day of support.
 * This class leverage Spring {@link Component} annotation, will be handled by Spring Context
 */
@Component
public class CompletedShift implements SchedulePredicate {

    /**
     * Break down first half shift and second half shift for all DailyShift in a single list.
     * Calculate engineer appearances in list and return true if appears equals or more than two.
     *
     * @param dailyShifts       Evaluated DailyShifts
     * @param currentShiftIndex Evaluated index of DailyShifts
     * @param engineer          Evaluated Engineer
     * @return true if engineer complete his whole day support, false if otherwise
     */
    @Override
    public boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer) {
        Stream<Engineer> allocatedEngineers = dailyShifts.stream()
                .flatMap(shift -> Stream.of(shift.getFirstHalf(), shift.getSecondHalf()))
                .filter(Objects::nonNull);

        return allocatedEngineers.filter(e -> e == engineer).count() >= 2;
    }
}
