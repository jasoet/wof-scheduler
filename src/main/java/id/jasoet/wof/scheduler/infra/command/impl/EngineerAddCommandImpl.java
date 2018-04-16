package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.EngineerAddCommand;
import id.jasoet.wof.scheduler.infra.request.EngineerRequest;
import id.jasoet.wof.scheduler.infra.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EngineerAddCommandImpl implements EngineerAddCommand {
    private EngineerService engineerService;

    @Autowired
    public EngineerAddCommandImpl(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @Override
    public Mono<Engineer> execute(EngineerRequest request) {
        engineerService.save(new Engineer(request.getId(), request.getName()));
        return Mono.just(engineerService.findById(request.getId()));
    }
}
