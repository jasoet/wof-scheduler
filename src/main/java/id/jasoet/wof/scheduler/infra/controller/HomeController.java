package id.jasoet.wof.scheduler.infra.controller;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.infra.command.EngineerListCommand;
import id.jasoet.wof.scheduler.infra.command.PingCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftGetByEngineerCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftListCommand;
import id.jasoet.wof.scheduler.infra.command.ShiftSchedulingCommand;
import id.jasoet.wof.scheduler.infra.command.executor.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Controller
public class HomeController {
    private Executor executor;

    @Autowired
    public HomeController(Executor executor) {
        this.executor = executor;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<String> home(@RequestParam(name = "engineer", required = false) Integer engineerId, Model model) {
        Mono<List<DailyShift>> dailyShifts = executor.execute(ShiftListCommand.class, null)
                .filter(DailyShift::fulfilled)
                .switchIfEmpty(executor.execute(ShiftSchedulingCommand.class, null))
                .collectList();

        Mono<List<Engineer>> engineers = executor.execute(EngineerListCommand.class, null)
                .collectList();

        return Mono.zip(dailyShifts, engineers)
                .map(tuple -> {
                    model.addAttribute("menuAll", true);
                    model.addAttribute("shifts", tuple.getT1());
                    model.addAttribute("engineers", tuple.getT2());
                    return "index";
                })
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "engineer")
    public Mono<String> byEngineer(@RequestParam(name = "engineer") Integer engineerId, Model model) {
        Mono<List<DailyShift>> dailyShifts = executor.execute(ShiftGetByEngineerCommand.class, engineerId)
                .collectList();

        Mono<List<Engineer>> engineers = executor.execute(EngineerListCommand.class, null)
                .collectList();

        return Mono.zip(dailyShifts, engineers)
                .map(tuple -> {
                    model.addAttribute("menuEngineer", true);
                    model.addAttribute("shifts", tuple.getT1());
                    model.addAttribute("engineers", tuple.getT2());
                    return "index";
                })
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public Mono<String> populate() {
        return executor.execute(ShiftSchedulingCommand.class, null)
                .collectList()
                .map(tuple -> "redirect:/")
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<String> ping(@RequestParam(name = "times", defaultValue = "1") Integer times) {

        return executor.execute(PingCommand.class, times)
                .subscribeOn(Schedulers.elastic());
    }
}
