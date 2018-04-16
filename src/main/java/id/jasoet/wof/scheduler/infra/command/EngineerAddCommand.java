package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import reactor.core.publisher.Mono;

public interface EngineerAddCommand extends Command<Mono<Engineer>, Engineer> {

}
