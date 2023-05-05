package org.huanyuan.easyobj.calculator.demo.humanrelation;

import com.google.common.collect.Lists;
import org.huanyuan.easyobj.calculator.core.api.DefaultRule;
import org.huanyuan.easyobj.calculator.core.api.Result;
import org.huanyuan.easyobj.calculator.core.api.SimpleCondition;
import org.huanyuan.easyobj.calculator.core.api.Step;
import org.huanyuan.easyobj.calculator.core.constant.CalculateOperateEnum;
import org.huanyuan.easyobj.calculator.core.engine.DefaultRuleEngine;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-05-05 16:51
 */
public class MixedOperateCalculator {

    private static final String RELATION_TYPE = "RELATION_TYPE";

    public static void main(String[] args) {
        DefaultRuleEngine engine = initEngine();
        ArrayList<Step<SimpleCondition>> conditions = Lists.newArrayList(
                Step.<SimpleCondition>builder()
                        .condition(
                                Lists.newArrayList(
                                        new SimpleCondition(RELATION_TYPE, "A"),
                                        new SimpleCondition(RELATION_TYPE, "B"),
                                        new SimpleCondition(RELATION_TYPE, "C"),
                                        new SimpleCondition(RELATION_TYPE, "D")
                                )
                        ).operator(
                                Lists.newArrayList(CalculateOperateEnum.UNION,
                                        CalculateOperateEnum.INTERSECTION,
                                        CalculateOperateEnum.DIFFERENCE)
                        ).build()
        );
        Result<User> result = engine.execute(null, conditions, User.class);
        System.out.println();
    }

    private static DefaultRuleEngine initEngine() {

        DefaultRule<User, User, SimpleCondition> rule1 = DefaultRule.<User, User, SimpleCondition>builder()
                .name("test1")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals("A", condition.get()))
                .action((target, condition) -> {
                    return Lists.newArrayList(
                            new User(1, 0, 0, 0, "test", "test", 0),
                            new User(1, 0, 0, 0, "test", "test", 0),
                            new User(3, 0, 0, 0, "test", "test", 0),
                            new User(5, 0, 0, 0, "test", "test", 0)
                    );
                })
                .build();

        DefaultRule<User, User, SimpleCondition> rule2 = DefaultRule.<User, User, SimpleCondition>builder()
                .name("test2")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals("B", condition.get()))
                .action((target, condition) -> {
                    return Lists.newArrayList(
                            new User(1, 0, 0, 0, "test", "test", 0),
                            new User(2, 0, 0, 0, "test", "test", 0),
                            new User(4, 0, 0, 0, "test", "test", 0),
                            new User(7, 0, 0, 0, "test", "test", 0)
                    );
                })
                .build();
        DefaultRule<User, User, SimpleCondition> rule3 = DefaultRule.<User, User, SimpleCondition>builder()
                .name("test3")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals("C", condition.get()))
                .action((target, condition) -> {
                    return Lists.newArrayList(
                            new User(1, 0, 0, 0, "test", "test", 0),
                            new User(3, 0, 0, 0, "test", "test", 0),
                            new User(5, 0, 0, 0, "test", "test", 0),
                            new User(6, 0, 0, 0, "test", "test", 0)
                    );
                })
                .build();

        DefaultRule<User, User, SimpleCondition> rule4 = DefaultRule.<User, User, SimpleCondition>builder()
                .name("test4")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals("D", condition.get()))
                .action((target, condition) -> {
                    return Lists.newArrayList(
                            new User(1, 0, 0, 0, "test", "test", 0),
                            new User(2, 0, 0, 0, "test", "test", 0),
                            new User(3, 0, 0, 0, "test", "test", 0),
                            new User(4, 0, 0, 0, "test", "test", 0)
                    );
                })
                .build();


        DefaultRuleEngine ruleEngine = new DefaultRuleEngine();
        ruleEngine.registerRule(RELATION_TYPE, Lists.newArrayList(
                rule1, rule2, rule3, rule4
        ));
        ruleEngine.registerEqualKey(User.class, User::getId);
        return ruleEngine;
    }
}
