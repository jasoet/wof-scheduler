package id.jasoet.wof.scheduler.domain.rule;


import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;

import java.util.List;

/**
 * This Functional Interface will be the base of Rules Class to filter data before generate process executed
 */
@FunctionalInterface
public interface SchedulePredicate {


    /**
     * This function will evaluate certain condition based on supplied parameters
     *
     * @param dailyShifts       Evaluated DailyShifts
     * @param currentShiftIndex Evaluated index of DailyShifts
     * @param engineer          Evaluated Engineer
     * @return true if condition meet, false if otherwise
     */
    boolean evaluate(List<DailyShift> dailyShifts, int currentShiftIndex, Engineer engineer);
}
