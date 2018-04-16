package id.jasoet.wof.scheduler.infra.service;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EngineerService {
    List<Engineer> generateEngineers();
    void saveAll(List<Engineer> engineers);
}
