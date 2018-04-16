package id.jasoet.wof.scheduler.infra.command.executor;

import id.jasoet.wof.scheduler.infra.command.Command;
import org.reactivestreams.Publisher;

public interface Executor {
    <T extends Publisher, R> T execute(Class<? extends Command<T, R>> commandClass, R request);
}
