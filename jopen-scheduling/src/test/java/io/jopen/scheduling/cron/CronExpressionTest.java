package io.jopen.scheduling.cron;

import io.jopen.scheduling.CronExpression;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class CronExpressionTest {

    @Test
    public void testCronMatch() {

        for (int i = 0; i < 100; i++) {
            boolean validExpression = CronExpression.isValidExpression("* * * * * ?");
            System.err.println(validExpression);
        }
    }

    @Test
    public void testPredicateIsExecute() throws ParseException {
        CronExpression cronExpression = new CronExpression("* * * * * ?");

        // Date finalFireTime = cronExpression.getFinalFireTime();
        // System.err.println(DateFormatUtils.format(finalFireTime,"yyyy-MM-dd HH:mm:ss"));
        // Date after = cronExpression.getNextInvalidTimeAfter(new Date());

        // System.err.println(DateFormatUtils.format(after,"yyyy-MM-dd HH:mm:ss"));
        boolean satisfiedBy = cronExpression.isSatisfiedBy(new Date());
        System.err.println(satisfiedBy);

        // String expressionSummary = cronExpression.getExpressionSummary();
        // System.err.println(expressionSummary);

        Date timeAfter = cronExpression.getNextInvalidTimeAfter(new Date(new Date().getTime() - 1000));
        System.err.println(DateFormatUtils.format(timeAfter, "yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean isMatchRange(CronExpression cronExpression, Date date, int secondsRange) {
        Calendar testDateCal = Calendar.getInstance(cronExpression.getTimeZone());
        testDateCal.setTime(date);
        testDateCal.set(Calendar.MILLISECOND, 0);
        Date originalDate = testDateCal.getTime();
        testDateCal.add(Calendar.SECOND, -secondsRange - 1);
        Date timeAfter = cronExpression.getTimeAfter(testDateCal.getTime());
        return ((timeAfter != null) && (Math.abs(timeAfter.getTime() - originalDate.getTime()) <= secondsRange * 1000));
    }

    public static void main(String[] args) throws ParseException {
        CronExpression cronExpression = new CronExpression("* * * * * ?");

        Date date = DateUtils.parseDate("2019-10-22 10:10:10", "yyyy-MM-dd HH:mm:ss");

        boolean matchRange = isMatchRange(cronExpression, date, 10);

        System.err.println(matchRange);
    }
}
