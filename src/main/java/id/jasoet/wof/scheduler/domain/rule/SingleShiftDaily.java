package id.jasoet.wof.scheduler.domain.rule;


import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This rule checks whether an engineer already take support shift at current day.
 * This class leverage Spring {@link Component} annotation, will be handled by Spring Context
 */
@Component
public class SingleShiftDaily implements SchedulePredicate {
    /**
     * Get current day shift and check whether engineer already take shift or not.
     *
     * @param dailyShifts       Evaluated DailyShifts
     * @param currentShiftIndex Evaluated index of DailyShifts
     * @param engineer          Evaluated Engineer
     * @return true if engineer already take support shift at current day, false if otherwise
     */
    @Override
    public boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer) {
        if (currentShiftIndex < 0 || currentShiftIndex >= dailyShifts.size()) {
            return false;
        }

        final DailyShift currentShift = dailyShifts.get(currentShiftIndex);
        return currentShift.getFirstHalf() == engineer || currentShift.getSecondHalf() == engineer;
    }
}
