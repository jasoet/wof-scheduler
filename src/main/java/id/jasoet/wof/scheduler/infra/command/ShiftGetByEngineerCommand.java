package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import reactor.core.publisher.Flux;

public interface ShiftGetByEngineerCommand extends Command<Flux<DailyShift>, Integer> {
}
