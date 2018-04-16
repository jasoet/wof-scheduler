package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.request.EngineerRequest;
import reactor.core.publisher.Mono;

public interface
EngineerAddCommand extends Command<Mono<Engineer>, EngineerRequest> {

}
