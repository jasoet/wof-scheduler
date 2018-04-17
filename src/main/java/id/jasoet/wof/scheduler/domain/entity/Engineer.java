package id.jasoet.wof.scheduler.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Engineer class, to handle Engineer Object.
 * This class intentionally created as immutable using Lombok {@link Value}
 * Only minimal field available, for assignment purpose.
 *
 * @author Deny Prasetyo
 * @since 0.1
 */

@Data
@AllArgsConstructor
public class Engineer {
    private int id;
    private String name;
}
