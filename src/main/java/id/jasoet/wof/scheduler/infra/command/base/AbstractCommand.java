package id.jasoet.wof.scheduler.infra.command.base;

import org.reactivestreams.Publisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractCommand<T, R>
        implements Command<T, R>, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public abstract Publisher<T> execute(R request);

}
