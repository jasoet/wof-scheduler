package id.jasoet.wof.scheduler.infra.controller;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.EngineerListCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftSchedulingCommand;
import id.jasoet.wof.scheduler.infra.command.executor.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/shifts")
public class ShiftController {
    private Executor executor;

    @Autowired
    public ShiftController(Executor executor) {
        this.executor = executor;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Engineer> list() {
        return executor.execute(EngineerListCommand.class, null).subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DailyShift> populate() {

        return executor.execute(ShiftSchedulingCommand.class, null)
                .subscribeOn(Schedulers.elastic());
    }

}
