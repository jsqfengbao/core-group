package org.jeecg.modules.finance.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.finance.entity.FundIncome;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.finance.entity.FundIncomeType;
import org.jeecg.modules.finance.entity.PerMonthMoney;
import org.jeecg.modules.finance.entity.ThisMonthHead;

import java.util.List;

/**
 * @Description: 收入表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
public interface IFundIncomeService extends IService<FundIncome> {

    public List<FundIncome> getThisYearIncome(String userName, int yearNum);

    public List<FundIncome> getThisMonthIncomeHead5(String sysUserId, int yearNum, int monthNum);

    public List<FundIncomeType> getThisMonthIncomeType(String sysUserId,int yearNum,int monthNum);

    public PerMonthMoney getThisYearPerMonthIncomeData(String sysUserId, int yearNum);

    public List<ThisMonthHead> getThisMonthHeadIncomeMoney(String sysUserId, int yearNum,int monthNum);

}
