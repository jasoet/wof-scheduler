package id.jasoet.wof.scheduler.infra.command;

import org.reactivestreams.Publisher;

public interface Command<T extends Publisher, R> {
    T execute(R request);
}
