package id.jasoet.wof.scheduler.domain.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Repository to handle {@link Engineer} CRUD operation.
 * <p>
 * This class leverage Spring {@link Repository} annotation and will be managed by Spring DI Context.
 * Using Caffeine cache library to save Engineers data in memory.
 * Intentionally not designed as singleton or utility class so in the future if we can easily change Repository implementation
 * and add supports for database integration.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */

@Repository
public class EngineerRepository {
    private Cache<Integer, Engineer> engineerCache = Caffeine.newBuilder().build();

    public void save(Engineer engineer) {
        engineerCache.put(engineer.getId(), engineer);
    }

    public void saveAll(List<Engineer> engineers) {
        val engineerMap = engineers.stream().collect(Collectors.toMap(Engineer::getId, e -> e));
        engineerCache.putAll(engineerMap);
    }

    public Engineer findById(int id) {
        return engineerCache.getIfPresent(id);
    }

    public List<Engineer> findAll() {
        return new ArrayList<>(engineerCache.asMap().values());
    }

    public void update(int id, Engineer engineer) {
        engineerCache.put(id, engineer);
    }

    public void delete(int id) {
        engineerCache.invalidate(id);
    }

    public void deleteAll() {
        engineerCache.invalidateAll();
    }

}
