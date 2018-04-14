package id.jasoet.wof.scheduler.domain.entity;

import lombok.val;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class EntityTest {
    @Test
    public void engineerClassShouldImplementedCorrectly() {
        assertPojoMethodsFor(Engineer.class)
                .testing(Method.GETTER)
                .testing(Method.TO_STRING)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.CONSTRUCTOR)
                .areWellImplemented();
    }

    @Test
    public void dailyShiftClassShouldImplementedCorrectly() {
        val valueChanger = DefaultFieldValueChanger.INSTANCE
                .attachNext(new EngineerValueChanger());

        assertPojoMethodsFor(DailyShift.class)
                .using(valueChanger)
                .testing(Method.GETTER)
                .testing(Method.TO_STRING)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.CONSTRUCTOR)
                .areWellImplemented();
    }

    public class EngineerValueChanger extends AbstractFieldValueChanger<Engineer> {
        @Override
        protected Engineer increaseValue(Engineer value, Class<?> type) {
            return new Engineer(value.getId() + 1, value.getName() + "Changed");
        }

        @Override
        protected boolean canChange(Class<?> type) {
            return type.equals(Engineer.class);
        }
    }
}

