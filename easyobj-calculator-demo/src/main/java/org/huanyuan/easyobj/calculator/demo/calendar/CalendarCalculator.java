package org.huanyuan.easyobj.calculator.demo.calendar;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import org.huanyuan.easyobj.calculator.core.api.DefaultRule;
import org.huanyuan.easyobj.calculator.core.api.SimpleCondition;
import org.huanyuan.easyobj.calculator.core.api.Step;
import org.huanyuan.easyobj.calculator.core.engine.DefaultRuleEngine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 15:08
 */
public class CalendarCalculator {

    private static final String CALENDAR_TYPE = "CALENDAR";

    public static void main(String[] args) {
        DefaultRule<Calendar, Calendar, SimpleCondition> dayPlusRule = DefaultRule.<Calendar, Calendar, SimpleCondition>builder()
                .name("day plus rule")
                .type(CALENDAR_TYPE)
                .description("calendar calculator")
                .decider(condition -> Pattern.matches("^DAYPLUS\\d+$", condition.get()))
                .action((target, condition) -> {
                    target.add(Calendar.DATE, Integer.parseInt(condition.get().substring(7)));
                    return Lists.newArrayList(target);
                })
                .build();

        DefaultRule<Calendar, Calendar, SimpleCondition> dayMinusRule = DefaultRule.<Calendar, Calendar, SimpleCondition>builder()
                .name("day minus rule")
                .type(CALENDAR_TYPE)
                .description("calendar calculator")
                .decider(condition -> Pattern.matches("^DAYMINUS\\d+$", condition.get()))
                .action((target, condition) -> {
                    target.add(Calendar.DATE, Integer.parseInt(condition.get().substring(8)));
                    return Lists.newArrayList(target);
                })
                .build();

        DefaultRuleEngine engine = new DefaultRuleEngine();
        engine.registerRule(CALENDAR_TYPE, Lists.newArrayList(dayMinusRule, dayPlusRule));

        ArrayList<Step<SimpleCondition>> step = Lists.newArrayList(
                Step.<SimpleCondition>builder().type(CALENDAR_TYPE).condition(new SimpleCondition("DAYPLUS10")).build(),
                Step.<SimpleCondition>builder().type(CALENDAR_TYPE).condition(new SimpleCondition("DAYMINUS5")).build()
                );
        List<Calendar> resultList = engine.execute(Calendar.getInstance(), step, Calendar.class).getResultList();
        Assert.equals(resultList.size(), 1);
        System.out.println();
    }
}
