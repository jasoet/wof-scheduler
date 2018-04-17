package id.jasoet.wof.scheduler.infra.controller;

import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.EngineerAddCommand;
import id.jasoet.wof.scheduler.infra.command.EngineerDeleteCommand;
import id.jasoet.wof.scheduler.infra.command.EngineerGenerateCommand;
import id.jasoet.wof.scheduler.infra.command.EngineerListCommand;
import id.jasoet.wof.scheduler.infra.command.executor.Executor;
import id.jasoet.wof.scheduler.infra.request.EngineerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/engineers")
public class EngineerController {
    private Executor executor;

    @Autowired
    public EngineerController(Executor executor) {
        this.executor = executor;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Engineer> list() {
        return executor.execute(EngineerListCommand.class, null).subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Engineer> add(@RequestBody EngineerRequest engineer) {

        return executor.execute(EngineerAddCommand.class, engineer)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Engineer> populate() {

        return executor.execute(EngineerGenerateCommand.class, null)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> remove(@PathVariable("id") Integer id) {

        return executor.execute(EngineerDeleteCommand.class, id)
                .subscribeOn(Schedulers.elastic());
    }
}
