package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import id.jasoet.wof.scheduler.infra.command.EngineerListCommand;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EngineerListCommandImpl implements EngineerListCommand {
    private EngineerRepository engineerRepository;

    public EngineerListCommandImpl(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    @Override
    public Flux<Engineer> execute(Void request) {
        return Flux.fromIterable(engineerRepository.findAll());
    }
}
