package id.jasoet.wof.scheduler.infra.service;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to handle {@link Engineer}operation.
 * <p>
 * This class leverage Spring {@link Service} annotation and will be managed by Spring DI Context.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */

@Service
public class EngineerService {

    private EngineerRepository engineerRepository;

    @Autowired
    public EngineerService(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

}
