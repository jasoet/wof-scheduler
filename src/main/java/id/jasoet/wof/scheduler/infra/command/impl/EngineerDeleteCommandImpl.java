package id.jasoet.wof.scheduler.infra.command.impl;

import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import id.jasoet.wof.scheduler.infra.command.EngineerDeleteCommand;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EngineerDeleteCommandImpl implements EngineerDeleteCommand {
    private EngineerRepository engineerRepository;

    public EngineerDeleteCommandImpl(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    @Override
    public Mono<Void> execute(Integer request) {
        engineerRepository.delete(request);
        return Mono.empty();
    }
}
