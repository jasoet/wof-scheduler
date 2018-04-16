package id.jasoet.wof.scheduler.infra.command;

import id.jasoet.wof.scheduler.infra.command.base.Command;
import reactor.core.publisher.Mono;

public interface PingCommand extends Command<Mono<String>, Integer> {

    @Override
    Mono<String> execute(Integer request);
}
