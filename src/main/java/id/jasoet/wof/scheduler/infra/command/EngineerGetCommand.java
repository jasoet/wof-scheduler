package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import reactor.core.publisher.Mono;

public interface EngineerGetCommand extends Command<Mono<Engineer>,Integer> {
}
