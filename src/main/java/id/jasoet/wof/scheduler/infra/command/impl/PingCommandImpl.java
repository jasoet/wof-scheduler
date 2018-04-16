package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.infra.command.PingCommand;
import id.jasoet.wof.scheduler.infra.command.base.AbstractCommand;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PingCommandImpl extends AbstractCommand<Mono<String>, Integer> implements PingCommand {
    @Override
    public Mono<String> execute(Integer request) {
        return Mono.just("Pong " + request + " times");
    }
}
