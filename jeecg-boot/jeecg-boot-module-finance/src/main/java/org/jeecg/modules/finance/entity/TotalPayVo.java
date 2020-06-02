package org.jeecg.modules.finance.entity;

import lombok.Data;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/14 15:32
 * @Modified By
 **/
@Data
public class TotalPayVo {
    private String totalYearPay; //年度支出总额
    private String totalYearBudgetPay; //年度支出预算总额
    private String payBudgetRatio; //支出与预算比例
    private String everyMonthPayRatio; //平均每月支出额度；
}
