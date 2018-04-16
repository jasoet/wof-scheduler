package id.jasoet.wof.scheduler.infra.command;

import reactor.core.publisher.Mono;

public interface EngineerDeleteCommand extends Command<Mono<Void
        >, Integer> {
}
