package id.jasoet.wof.scheduler.infra.command.executor;

import id.jasoet.wof.scheduler.infra.command.base.Command;
import org.reactivestreams.Publisher;

public interface Executor {
    <T, R> Publisher<T> execute(Class<? extends Command<T, R>> commandClass, R request);
}
