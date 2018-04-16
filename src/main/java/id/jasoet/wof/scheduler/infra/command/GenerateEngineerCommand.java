package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.base.Command;
import reactor.core.publisher.Flux;

public interface GenerateEngineerCommand extends Command<Flux<Engineer>, Integer> {
    @Override
    Flux<Engineer> execute(Integer request);
}
