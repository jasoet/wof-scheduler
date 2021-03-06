package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import reactor.core.publisher.Flux;

public interface EngineerListCommand extends Command<Flux<Engineer>, Void> {
}
