package org.jeecg.modules.finance.service;

import org.jeecg.modules.finance.entity.FundYearBudget;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 年预算
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
public interface IFundYearBudgetService extends IService<FundYearBudget> {

    public FundYearBudget getFundYearBudgetByYear(String userName,int yearNum);

}
