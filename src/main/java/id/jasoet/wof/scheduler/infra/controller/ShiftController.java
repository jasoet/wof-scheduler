package id.jasoet.wof.scheduler.infra.controller;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.infra.command.ShiftGetByEngineerCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftGetCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftListCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftSchedulingCommand;
import id.jasoet.wof.scheduler.infra.command.executor.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

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
    public Flux<DailyShift> list() {
        return executor.execute(ShiftListCommand.class, null)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(method = RequestMethod.GET,
            params = "engineerId",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DailyShift> listByEngineer(@RequestParam("engineerId") int engineerId) {
        return executor.execute(ShiftGetByEngineerCommand.class, engineerId)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/populate", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DailyShift> populate() {

        return executor.execute(ShiftSchedulingCommand.class, null)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{date}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DailyShift> get(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        return executor.execute(ShiftGetCommand.class, date)
                .subscribeOn(Schedulers.elastic());
    }
}
