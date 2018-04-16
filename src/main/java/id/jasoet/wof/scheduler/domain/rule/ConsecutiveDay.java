package id.jasoet.wof.scheduler.domain.rule;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This rule checks whether an engineer already take support shift a day before.
 * This class leverage Spring {@link Component} annotation, will be handled by Spring Context
 */
@Component
public class ConsecutiveDay implements SchedulePredicate {
    /**
     * Get previous day shift and check whether engineer take shift in that day or not.
     *
     * @param dailyShifts       Evaluated DailyShifts
     * @param currentShiftIndex Evaluated index of DailyShifts
     * @param engineer          Evaluated Engineer
     * @return true if engineer already take support shift a day before, false if otherwise
     */
    @Override
    public boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer) {
        if (currentShiftIndex <= 0 || currentShiftIndex >= dailyShifts.size()) {
            return false;
        }

        DailyShift dayBeforeShift = dailyShifts.get(currentShiftIndex - 1);
        return dayBeforeShift.getFirstHalf() == engineer || dayBeforeShift.getSecondHalf() == engineer;
    }
}
