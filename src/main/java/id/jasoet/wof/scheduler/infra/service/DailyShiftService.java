package id.jasoet.wof.scheduler.infra.service;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;

import java.time.LocalDate;
import java.util.List;

public interface DailyShiftService {
    List<DailyShift> getNextTwoWeekShift();

    List<DailyShift> getNextTwoWeekShift(LocalDate currentDate);

    List<DailyShift> retrieveAll();

    List<DailyShift> findByEngineer(Integer engineerId);

    DailyShift findByDate(LocalDate localDate);

    void replaceAll(List<DailyShift> shifts);
}
