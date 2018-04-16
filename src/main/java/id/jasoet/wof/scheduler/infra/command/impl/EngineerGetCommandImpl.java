package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import id.jasoet.wof.scheduler.infra.command.EngineerGetCommand;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EngineerGetCommandImpl implements EngineerGetCommand {

    private EngineerRepository engineerRepository;

    public EngineerGetCommandImpl(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    @Override
    public Mono<Engineer> execute(Integer request) {
        return Mono.just(engineerRepository.findById(request));
    }
}
