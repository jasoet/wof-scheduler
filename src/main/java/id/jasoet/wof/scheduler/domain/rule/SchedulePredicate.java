package id.jasoet.wof.scheduler.domain.rule;


import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;

import java.util.List;

@FunctionalInterface
public interface SchedulePredicate {
    boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer);
}
