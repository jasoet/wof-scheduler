package id.jasoet.wof.scheduler.infra.command.executor;

import id.jasoet.wof.scheduler.infra.command.base.Command;
import org.reactivestreams.Publisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ExecutorImpl implements Executor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public <T extends Publisher, R> T execute(Class<? extends Command<T, R>> commandClass, R request) {
        Command<T, R> command = applicationContext.getBean(commandClass);
        return command.execute(request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
