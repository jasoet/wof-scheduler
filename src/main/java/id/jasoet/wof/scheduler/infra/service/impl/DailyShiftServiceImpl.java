package id.jasoet.wof.scheduler.infra.service.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.repository.DailyShiftRepository;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyShiftServiceImpl implements DailyShiftService {
    private DailyShiftRepository dailyShiftRepository;

    public DailyShiftServiceImpl(DailyShiftRepository dailyShiftRepository) {
        this.dailyShiftRepository = dailyShiftRepository;
    }

    @Override
    public List<DailyShift> getNextTwoWeekShift() {
        return dailyShiftRepository.getNextTwoWeekShift();
    }

    @Override
    public List<DailyShift> getNextTwoWeekShift(LocalDate currentDate) {
        return dailyShiftRepository.getNextTwoWeekShift(currentDate);
    }

    @Override
    public List<DailyShift> retrieveAll() {
        return dailyShiftRepository.retrieveAll();
    }
}
