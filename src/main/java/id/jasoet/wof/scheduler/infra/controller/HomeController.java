package id.jasoet.wof.scheduler.infra.controller;

import id.jasoet.wof.scheduler.infra.command.PingCommand;
import id.jasoet.wof.scheduler.infra.command.executor.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class HomeController {
    private Executor executor;

    @Autowired
    public HomeController(Executor executor) {
        this.executor = executor;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> home() {

        return Mono.just("Welcome!");
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> ping(@RequestParam(name = "times", defaultValue = "1") Integer times) {

        return executor.execute(PingCommand.class, times)
                .subscribeOn(Schedulers.elastic());
    }
}
