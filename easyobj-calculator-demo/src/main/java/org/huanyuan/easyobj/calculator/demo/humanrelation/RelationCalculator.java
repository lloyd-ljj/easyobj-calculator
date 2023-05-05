package org.huanyuan.easyobj.calculator.demo.humanrelation;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import org.huanyuan.easyobj.calculator.core.api.DefaultRule;
import org.huanyuan.easyobj.calculator.core.api.Result;
import org.huanyuan.easyobj.calculator.core.api.SimpleCondition;
import org.huanyuan.easyobj.calculator.core.api.Step;
import org.huanyuan.easyobj.calculator.core.constant.CalculateOperateEnum;
import org.huanyuan.easyobj.calculator.core.engine.DefaultRuleEngine;

import java.util.*;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-17 14:40
 */
public class RelationCalculator {

    private static final String RELATION_TYPE = "RELATION_TYPE";

    public static void main(String[] args) {
        // demo1 for condition without result operate
        DefaultRuleEngine engine = initEngine();
        String conditionStr = "LD;MT;O;OL";
        List<String> strList = Arrays.asList(conditionStr.split(RelationConstant.STEP_SEPERATOR));
        ArrayList<Step<SimpleCondition>> conditionList = Lists.newArrayList();
        strList.forEach(str -> conditionList.add(
                Step.<SimpleCondition>builder()
                        .condition(Lists.newArrayList(new SimpleCondition(RELATION_TYPE, str)))
                        .build())
        );
        Result<User> result = engine.execute(UserService.getUser(1), conditionList, User.class);

        // demo1 for condition with result operate
        String conditionStr2 = "LD|MT;O;OL";
        engine.registerEqualKey(User.class, User::getId);
        List<String> strList2 = Arrays.asList(conditionStr2.split(RelationConstant.STEP_SEPERATOR));
        ArrayList<Step<SimpleCondition>> conditionList2 = Lists.newArrayList();
        strList2.forEach(str -> {
            if (str.contains("|")) {
                if (str.contains("|")) {
                    String[] params = str.split("\\|");
                    conditionList2.add(
                            Step.<SimpleCondition>builder()
                                    .condition(Lists.newArrayList(
                                            new SimpleCondition(RELATION_TYPE, params[0]),
                                            new SimpleCondition(RELATION_TYPE, params[1])))
                                    .operator(Lists.newArrayList(
                                            CalculateOperateEnum.UNION
                                    ))
                                    .build());
                }
            } else {
                conditionList2.add(
                        Step.<SimpleCondition>builder()
                                .condition(Lists.newArrayList(new SimpleCondition(RELATION_TYPE, str)))
                                .build());
            }
        });
        Result<User> result2 = engine.execute(UserService.getUser(1), conditionList2, User.class);
    }

    private static DefaultRuleEngine initEngine() {

        DefaultRule<User, User, SimpleCondition> LDRule = DefaultRule.<User, User, SimpleCondition>builder()
                .name("leader rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.LD.name(), condition.get()))
                .action((target, condition) -> {
                    User leader = UserService.getUser(target.getId());
                    return Lists.newArrayList(leader);
                })
                .build();

        DefaultRule<User, Organization, SimpleCondition> OrgRule = DefaultRule.<User, Organization, SimpleCondition>builder()
                .name("organization rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.O.name(), condition.get()))
                .action((target, condition) -> {
                    Organization org = OrganizationService.getOrg(target.getOrganization());
                    return Lists.newArrayList(org);
                })
                .build();

        DefaultRule<Organization, User, SimpleCondition> OrgLRule = DefaultRule.<Organization, User, SimpleCondition>builder()
                .name("organization leader rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.OL.name(), condition.get()))
                .action((target, condition) -> {
                    User user = UserService.getUser(target.getLeader());
                    return Lists.newArrayList(user);
                })
                .build();

        DefaultRule<User, User, SimpleCondition> MentorRule = DefaultRule.<User, User, SimpleCondition>builder()
                .name("mentor rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.MT.name(), condition.get()))
                .action((target, condition) -> {
                    User user = UserService.getUser(target.getLeader());
                    return Lists.newArrayList(user);
                })
                .build();

        DefaultRuleEngine ruleEngine = new DefaultRuleEngine();
        ruleEngine.registerRule(RELATION_TYPE, Lists.newArrayList(
                LDRule, OrgRule, OrgLRule, MentorRule
        ));
        return ruleEngine;
    }
}
