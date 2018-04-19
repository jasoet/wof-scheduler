package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.infra.command.ShiftGetByEngineerCommand;
import id.jasoet.wof.scheduler.infra.service.DailyShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ShiftGetByEngineerCommandImpl implements ShiftGetByEngineerCommand {
    private DailyShiftService dailyShiftService;

    @Autowired
    public ShiftGetByEngineerCommandImpl(DailyShiftService dailyShiftService) {
        this.dailyShiftService = dailyShiftService;
    }

    @Override
    public Flux<DailyShift> execute(Integer request) {
        return Flux.fromIterable(dailyShiftService.findByEngineer(request));
    }
}
