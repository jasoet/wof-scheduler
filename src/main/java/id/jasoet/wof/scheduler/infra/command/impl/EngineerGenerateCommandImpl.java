package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.EngineerGenerateCommand;
import id.jasoet.wof.scheduler.infra.service.EngineerService;
import lombok.experimental.var;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EngineerGenerateCommandImpl implements EngineerGenerateCommand {
    private EngineerService engineerService;

    public EngineerGenerateCommandImpl(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @Override
    public Flux<Engineer> execute(Void request) {
        var engineers = engineerService.generateEngineers();
        engineerService.deleteAll();
        engineerService.saveAll(engineers);
        return Flux.fromIterable(engineers);
    }
}
