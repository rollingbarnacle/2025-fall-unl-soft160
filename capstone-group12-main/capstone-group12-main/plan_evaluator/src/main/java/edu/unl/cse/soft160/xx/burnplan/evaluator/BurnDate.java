package edu.unl.cse.soft160.xx.burnplan.evaluator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class BurnDate {
    private LocalDate burnDate;

    public BurnDetermination checkDate(LocalDate burnDate) {
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, (Temporal) burnDate);
        if (days > 2 && days <= 5) {
            return BurnDetermination.ACCEPTABLE;
        } else {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }
    }
}
