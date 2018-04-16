package id.jasoet.wof.scheduler.infra.command.base;

import org.reactivestreams.Publisher;

public interface Command<T, R> {
    Publisher<T> execute(R request);
}
