package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.infra.command.ShiftListCommand;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Component
public class ShiftListCommandImpl implements ShiftListCommand {
    private DailyShiftService dailyShiftService;

    @Autowired
    public ShiftListCommandImpl(DailyShiftService dailyShiftService) {
        this.dailyShiftService = dailyShiftService;
    }

    @Override
    public Flux<DailyShift> execute(Void request) {
        return Flux.fromIterable(dailyShiftService.retrieveAll());
    }
}
