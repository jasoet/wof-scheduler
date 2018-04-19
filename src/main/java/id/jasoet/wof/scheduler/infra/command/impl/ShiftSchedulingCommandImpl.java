package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.rule.CompletedShift;
import id.jasoet.wof.scheduler.domain.rule.ConsecutiveDay;
import id.jasoet.wof.scheduler.domain.rule.ShiftFulfilled;
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
    private ShiftFulfilled shiftFulfilled;

    @Autowired
    public ShiftSchedulingCommandImpl(EngineerService engineerService,
                                      DailyShiftService dailyShiftService,
                                      CompletedShift completedShiftFilter,
                                      SingleShiftDaily singleShiftDailyFilter,
                                      ConsecutiveDay consecutiveDayFilter,
                                      ShiftFulfilled shiftFulfilled) {
        this.engineerService = engineerService;
        this.dailyShiftService = dailyShiftService;
        this.completedShiftFilter = completedShiftFilter;
        this.singleShiftDailyFilter = singleShiftDailyFilter;
        this.consecutiveDayFilter = consecutiveDayFilter;
        this.shiftFulfilled = shiftFulfilled;
    }

    @Override
    public Flux<DailyShift> execute(Void request) {
        List<Engineer> engineers = engineerService.findAll();

        if (engineers.isEmpty()) {
            val generatedEngineers = engineerService.generateEngineers();
            engineerService.saveAll(generatedEngineers);
            engineers = engineerService.findAll();
        }

        val generateShift = dailyShiftService.getNextTwoWeekShift();
        dailyShiftService.replaceAll(generateShift);
        var shifts = populate(dailyShiftService.retrieveAll(), engineers);

        while (!fulfilled(shifts, engineers)) {
            shifts = populate(dailyShiftService.retrieveAll(), engineers);
        }

        dailyShiftService.replaceAll(shifts);

        return Flux.fromIterable(dailyShiftService.retrieveAll());
    }

    private List<DailyShift> populate(List<DailyShift> shifts, List<Engineer> engineers) {
        for (var index = 0; index < shifts.size(); index++) {
            var currentDay = shifts.get(index);

            var firstShiftCandidates = applyFilter(engineers, shifts, index);
            var selectedFirstShiftEngineer = getByRandomIndex(firstShiftCandidates);
            currentDay.setFirstHalf(selectedFirstShiftEngineer);

            var secondShiftCandidates = applyFilter(engineers, shifts, index);
            var selectedSecondShiftEngineer = getByRandomIndex(secondShiftCandidates);
            currentDay.setSecondHalf(selectedSecondShiftEngineer);
        }
        return shifts;
    }

    private Engineer getByRandomIndex(List<Engineer> engineers) {
        if (engineers.isEmpty()) {
            return null;
        }

        var randomIndex = ThreadLocalRandom.current().nextInt(0, engineers.size());
        return engineers.get(randomIndex);
    }

    private boolean fulfilled(List<DailyShift> dailyShifts, List<Engineer> engineers) {
        return shiftFulfilled.evaluate(dailyShifts, 0, null) ||
                engineers.stream()
                        .allMatch(e -> completedShiftFilter.evaluate(dailyShifts, 0, e));
    }

    private List<Engineer> applyFilter(List<Engineer> original, List<DailyShift> dailyShifts, int currentShiftIndex) {
        return original.stream()
                .filter(e -> !completedShiftFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .filter(e -> !consecutiveDayFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .filter(e -> !singleShiftDailyFilter.evaluate(dailyShifts, currentShiftIndex, e))
                .collect(Collectors.toList());
    }
}
