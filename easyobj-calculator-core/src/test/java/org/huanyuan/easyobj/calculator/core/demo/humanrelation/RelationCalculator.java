package org.huanyuan.easyobj.calculator.core.demo.humanrelation;

import com.google.common.collect.Lists;
import org.huanyuan.easyobj.calculator.core.api.DefaultRule;
import org.huanyuan.easyobj.calculator.core.api.Result;
import org.huanyuan.easyobj.calculator.core.api.SimpleCondition;
import org.huanyuan.easyobj.calculator.core.api.Step;
import org.huanyuan.easyobj.calculator.core.builder.RuleBuilder;
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
        DefaultRuleEngine engine = initEngine();
        String conditionStr = "LD;MT;O;OL";
        List<String> strList = Arrays.asList(conditionStr.split(RelationConstant.STEP_SEPERATOR));
        ArrayList<Step<SimpleCondition>> conditionList = Lists.newArrayList();
        strList.forEach(str -> conditionList.add(
                Step.<SimpleCondition>builder().type(RELATION_TYPE).condition(new SimpleCondition(str)).build())
        );
        Result<User> result = engine.execute(UserService.getUser(1), conditionList, User.class);
    }

    private static DefaultRuleEngine initEngine() {

        DefaultRule<User, User, SimpleCondition> LDRule = new RuleBuilder<User, User, SimpleCondition>()
                .name("leader rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.LD.name(), condition.get()))
                .action((target, condition) -> {
                    User leader = UserService.getUser(target.getId());
                    return Lists.newArrayList(leader);
                })
                .build();

        DefaultRule<User, Organization, SimpleCondition> OrgRule = new RuleBuilder<User, Organization, SimpleCondition>()
                .name("organization rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.O.name(), condition.get()))
                .action((target, condition) -> {
                    Organization org = OrganizationService.getOrg(target.getOrganization());
                    return Lists.newArrayList(org);
                })
                .build();

        DefaultRule<Organization, User, SimpleCondition> OrgLRule = new RuleBuilder<Organization, User, SimpleCondition>()
                .name("organization leader rule")
                .type(RELATION_TYPE)
                .description("relation calculator")
                .decider(condition -> Objects.equals(RelationConstant.RuleCategory.OL.name(), condition.get()))
                .action((target, condition) -> {
                    User user = UserService.getUser(target.getLeader());
                    return Lists.newArrayList(user);
                })
                .build();

        DefaultRule<User, User, SimpleCondition> MentorRule = new RuleBuilder<User, User, SimpleCondition>()
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
