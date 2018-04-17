package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.rule.CompletedShift;
import id.jasoet.wof.scheduler.domain.rule.ConsecutiveDay;
import id.jasoet.wof.scheduler.domain.rule.SingleShiftDaily;
import id.jasoet.wof.scheduler.infra.command.ShiftSchedulingCommand;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import id.jasoet.wof.scheduler.infra.service.EngineerService;
import lombok.experimental.var;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class ShiftSchedulingCommandImpl implements ShiftSchedulingCommand {
    private EngineerService engineerService;
    private DailyShiftService dailyShiftService;
    private CompletedShift completedShiftFilter;
    private SingleShiftDaily singleShiftDailyFilter;
    private ConsecutiveDay consecutiveDayFilter;

    @Autowired
    public ShiftSchedulingCommandImpl(EngineerService engineerService,
                                      DailyShiftService dailyShiftService,
                                      CompletedShift completedShiftFilter,
                                      SingleShiftDaily singleShiftDailyFilter,
                                      ConsecutiveDay consecutiveDayFilter) {
        this.engineerService = engineerService;
        this.dailyShiftService = dailyShiftService;
        this.completedShiftFilter = completedShiftFilter;
        this.singleShiftDailyFilter = singleShiftDailyFilter;
        this.consecutiveDayFilter = consecutiveDayFilter;
    }

    @Override
    public Flux<DailyShift> execute(Void request) {
        List<Engineer> engineers = engineerService.findAll();

        if (engineers.isEmpty()) {
            val generatedEngineers = engineerService.generateEngineers();
            engineerService.saveAll(generatedEngineers);
            engineers = engineerService.findAll();
        }

        List<DailyShift> shifts = dailyShiftService.retrieveAll();
        if (shifts.isEmpty()) {
            val generateShifts = dailyShiftService.getNextTwoWeekShift();
            dailyShiftService.replaceAll(generateShifts);
            shifts = dailyShiftService.retrieveAll();
        }

        for (var index = 0; index < shifts.size(); index++) {
            var currentDay = shifts.get(index);

            var firstShiftCandidates = applyFilter(engineers, shifts, index);
            var selectedFirstShiftEngineer = getByRandomIndex(firstShiftCandidates);
            currentDay.setFirstHalf(selectedFirstShiftEngineer);

            var secondShiftCandidates = applyFilter(engineers, shifts, index);
            var selectedSecondShiftEngineer = getByRandomIndex(secondShiftCandidates);
            currentDay.setSecondHalf(selectedSecondShiftEngineer);
        }

        dailyShiftService.replaceAll(shifts);

        return Flux.fromIterable(dailyShiftService.retrieveAll());
    }

    private Engineer getByRandomIndex(List<Engineer> engineers) {
        if (engineers.isEmpty()) {
            return null;
        }

        var randomIndex = ThreadLocalRandom.current().nextInt(0, engineers.size());
        return engineers.get(randomIndex);
    }


    private List<Engineer> applyFilter(List<Engineer> original, List<DailyShift> dailyShifts, int currentShiftIndex) {
        return original.stream()
                .filter(e -> !completedShiftFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .filter(e -> !singleShiftDailyFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .filter(e -> !consecutiveDayFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .collect(Collectors.toList());
    }
}
