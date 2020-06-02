package org.jeecg.modules.finance.entity;

import lombok.Data;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/14 22:46
 * @Modified By
 **/
@Data
public class TotalIncomeVo {
    private String totalYearIncome; //年度收入总额
    private String totalYearBudgetIncome; //年度收入预算总额
    private String incomeBudgetRatio; //收入与预算比例
    private String everyMonthIncomeRatio; //平均每月收入额度；
}
