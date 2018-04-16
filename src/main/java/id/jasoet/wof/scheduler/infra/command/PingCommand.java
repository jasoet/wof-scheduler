package id.jasoet.wof.scheduler.infra.command;

import reactor.core.publisher.Mono;

public interface PingCommand extends Command<Mono<String>, Integer> {
}
