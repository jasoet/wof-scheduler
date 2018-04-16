package id.jasoet.wof.scheduler.domain.rule;

import id.jasoet.wof.scheduler.domain.entity.DailyShift;
import id.jasoet.wof.scheduler.domain.entity.Engineer;
import id.jasoet.wof.scheduler.domain.repository.DailyShiftRepository;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleShiftDailyTest {

    @Test
    public void givenEmptyDailyShiftShouldFailedToEvaluate() {
        val dailyShifts = new ArrayList<DailyShift>();
        val engineer = new Engineer(42, "Test Engineer");

        val filter = new SingleShiftDaily();
        IntStream.range(0, 12).forEach(shiftIndex -> {
            val evaluationResult = filter.evaluate(dailyShifts, shiftIndex, engineer);
            assertThat(evaluationResult).isFalse();
        });
    }

    @Test
    public void givenPopulatedShiftWithNoAssignedEngineerShouldFailedToEvaluate() {
        val dailyShiftRepository = new DailyShiftRepository();
        val dailyShifts = dailyShiftRepository.getNextTwoWeekShift();
        val engineer = new Engineer(42, "Test Engineer");

        val filter = new SingleShiftDaily();
        IntStream.range(0, 12).forEach(shiftIndex -> {
            val evaluationResult = filter.evaluate(dailyShifts, shiftIndex, engineer);
            assertThat(evaluationResult).isFalse();
        });
    }

    @Test
    public void givenPopulatedShiftWithOneAssignedInSameDayShouldSuccessToEvaluate() {
        val dailyShiftRepository = new DailyShiftRepository();
        val dailyShifts = dailyShiftRepository.getNextTwoWeekShift();
        val engineer = new Engineer(42, "Test Engineer");

        val randomIndex = 4;
        val randomShift = dailyShifts.get(randomIndex);
        dailyShifts.add(randomIndex, new DailyShift(randomShift.getDate(), engineer, null));

        val filter = new SingleShiftDaily();
        IntStream.range(0, dailyShifts.size() + 3).forEach(shiftIndex -> {
            val evaluationResult = filter.evaluate(dailyShifts, shiftIndex, engineer);
            if (shiftIndex == randomIndex) {
                assertThat(evaluationResult).isTrue();
            } else {
                assertThat(evaluationResult).isFalse();
            }
        });
    }

}