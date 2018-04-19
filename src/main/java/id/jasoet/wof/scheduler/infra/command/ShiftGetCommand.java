package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ShiftGetCommand extends Command<Mono<DailyShift>, LocalDate> {
}
