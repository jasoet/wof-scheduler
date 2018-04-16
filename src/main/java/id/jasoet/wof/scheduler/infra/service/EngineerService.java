package id.jasoet.wof.scheduler.infra.service;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EngineerService {
    List<Engineer> generateEngineers();

    void save(Engineer engineer);

    void saveAll(List<Engineer> engineers);

    void deleteAll();

    Engineer findById(int id);

    List<Engineer> findAll();

    void update(int id, Engineer engineer);

    void delete(int id);

}
