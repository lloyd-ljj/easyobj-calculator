# Easy Obj Calculator
## 1. Introduction
Easyobj calculator is an object calculator that supports rule matching and behavior operations, supports chain computing, and supports simple logical calculations (intersection, union, difference) of calculation results

## 2. Main components
- **Target**: calculation target, objects used to perform behavioral operations
- **Result**: calculation result, the result obtained by the calculation engine after calculating the target
- **Rule**: rules, including decider and action
    - **Decider**: decision maker, used to determine whether a condition meets the current rule
    - **Action**: the component that performs behavioral calculations on the target after passing the decider, generating the calculation result
- **Step**: calculation steps, including condition and operate
    - **Condition**: conditions used to match applicable rules
    - **Operate**: operation, used for logical operations between calculated results
- **CalculateEngine**: a computing engine used to manage rules and perform calculations

## 3. demo
please refer to the easyobj-calculator-demo for details


# 简单对象计算器
## 1. 项目介绍
easyobj-calculator是一个支持规则匹配和行为运算的对象计算器， 支持链式计算，并且支持对计算结果进行简单的逻辑计算(交集、并集、差集)


## 2. 主要组件
- **Target**: 计算目标，用于执行行为运算的对象
- **Result**: 计算结果，计算引擎（CalculateEngine)对计算目标(Target)进行计算后得到的结果
- **Rule**: 规则，包含判决器(Decider)和执行器(Action)
  - **Decider**: 判决器，用于判断条件(Condition)是否符合当前规则(Rule)
  - **Action**: 执行器，判决器通过后，对计算目标(Target)进行行为计算的组件，生成计算结果(Result)
- **Step**: 计算步骤，包含条件(Condition)和操作(Operate）
  - **Condition**: 条件，用于匹配适用的规则(Rule)
  - **Operate**: 操作，用于计算结果(Result)间的逻辑操作
- **CalculateEngine**: 计算引擎，用于管理规则(Rule)并执行运算

## 3. demo
详情参照easyobj-calculator-demo