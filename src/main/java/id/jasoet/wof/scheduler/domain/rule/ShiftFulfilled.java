package id.jasoet.wof.scheduler.domain.rule;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ShiftFulfilled implements SchedulePredicate {
    @Override
    public boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer) {
        return dailyShifts.isEmpty() || dailyShifts.stream().allMatch(s -> s.getFirstHalf() != null && s.getSecondHalf() != null);
    }
}
