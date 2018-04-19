package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import reactor.core.publisher.Flux;

public interface ShiftListCommand extends Command<Flux<DailyShift>, Void> {
}
