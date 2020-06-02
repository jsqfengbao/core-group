package org.jeecg.modules.finance.service.impl;

import org.jeecg.modules.finance.entity.FundYearBudget;
import org.jeecg.modules.finance.mapper.FundYearBudgetMapper;
import org.jeecg.modules.finance.service.IFundYearBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 年预算
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Service
public class FundYearBudgetServiceImpl extends ServiceImpl<FundYearBudgetMapper, FundYearBudget> implements IFundYearBudgetService {

    @Autowired
    private FundYearBudgetMapper fundYearBudgetMapper;
    @Override
    public FundYearBudget getFundYearBudgetByYear(String sysOrgCode, int yearNum) {
        return fundYearBudgetMapper.getFundYearBudgetByYear(sysOrgCode,yearNum);
    }
}
