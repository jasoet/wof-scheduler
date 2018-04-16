package id.jasoet.wof.scheduler.infra.service.impl;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.EngineerRepository;
import id.jasoet.wof.scheduler.infra.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service class to handle {@link Engineer}operation.
 * <p>
 * This class leverage Spring {@link Service} annotation and will be managed by Spring DI Context.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */
@Service
public class EngineerServiceImpl implements EngineerService {
    private EngineerRepository engineerRepository;

    @Autowired
    public EngineerServiceImpl(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    @Override
    public List<Engineer> generateEngineers() {
        return Arrays.asList(
                new Engineer(0, "Engineer 0"),
                new Engineer(1, "Engineer 1"),
                new Engineer(2, "Engineer 2"),
                new Engineer(3, "Engineer 3"),
                new Engineer(4, "Engineer 4"),
                new Engineer(5, "Engineer 5"),
                new Engineer(6, "Engineer 6"),
                new Engineer(7, "Engineer 7"),
                new Engineer(8, "Engineer 8"),
                new Engineer(9, "Engineer 9")
        );
    }

    public void saveAll(List<Engineer> engineers) {
        engineerRepository.saveAll(engineers);
    }

    @Override
    public void deleteAll() {
        engineerRepository.deleteAll();
    }

}
