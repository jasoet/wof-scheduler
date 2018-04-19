package id.jasoet.wof.scheduler.infra.service.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.DailyShiftRepository;
import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyShiftServiceImpl implements DailyShiftService {
    private DailyShiftRepository dailyShiftRepository;
    private EngineerRepository engineerRepository;

    @Autowired
    public DailyShiftServiceImpl(DailyShiftRepository dailyShiftRepository, EngineerRepository engineerRepository) {
        this.dailyShiftRepository = dailyShiftRepository;
        this.engineerRepository = engineerRepository;
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

    @Override
    public List<DailyShift> findByEngineer(Integer engineerId) {
        final Engineer engineer = engineerRepository.findById(engineerId);

        return dailyShiftRepository.retrieveAll()
                .stream()
                .filter(d -> d.getFirstHalf() == engineer || d.getSecondHalf() == engineer)
                .collect(Collectors.toList());
    }

    @Override
    public DailyShift findByDate(LocalDate localDate) {
        return dailyShiftRepository.findByDate(localDate);
    }

    @Override
    public void replaceAll(List<DailyShift> shifts) {
        dailyShiftRepository.replaceAll(shifts);
    }
}
