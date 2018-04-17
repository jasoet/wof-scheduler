package id.jasoet.wof.scheduler.infra.service;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;

import java.time.LocalDate;
import java.util.List;

public interface DailyShiftService {
    List<DailyShift> getNextTwoWeekShift();

    List<DailyShift> getNextTwoWeekShift(LocalDate currentDate);

    List<DailyShift> retrieveAll();

    List<DailyShift> findByEngineer(Engineer engineer);

    void replaceAll(List<DailyShift> shifts);
}
