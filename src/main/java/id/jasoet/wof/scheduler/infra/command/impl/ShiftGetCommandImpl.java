package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.infra.command.ShiftGetCommand;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class ShiftGetCommandImpl implements ShiftGetCommand {
    private DailyShiftService dailyShiftService;

    @Autowired
    public ShiftGetCommandImpl(DailyShiftService dailyShiftService) {
        this.dailyShiftService = dailyShiftService;
    }

    @Override
    public Mono<DailyShift> execute(LocalDate request) {
        return Mono.just(dailyShiftService.findByDate(request));
    }
}
