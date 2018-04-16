package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.GenerateEngineerCommand;
import id.jasoet.wof.scheduler.infra.command.base.AbstractCommand;
import id.jasoet.wof.scheduler.infra.service.EngineerService;
import lombok.experimental.var;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GenerateEngineerCommandImpl extends AbstractCommand<Flux<Engineer>, Integer> implements GenerateEngineerCommand {
    private EngineerService engineerService;

    public GenerateEngineerCommandImpl(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @Override
    public Flux<Engineer> execute(Integer request) {
        var engineers = engineerService.generateEngineers();
        engineerService.deleteAll();
        engineerService.saveAll(engineers);
        return Flux.fromIterable(engineers);
    }
}
