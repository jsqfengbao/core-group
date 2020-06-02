package org.jeecg.modules.finance.service.impl;

import cn.hutool.json.JSONObject;
import org.jeecg.modules.finance.entity.*;
import org.jeecg.modules.finance.mapper.FundFamilyMapper;
import org.jeecg.modules.finance.mapper.FundIncomeMapper;
import org.jeecg.modules.finance.service.IFundFamilyService;
import org.jeecg.modules.finance.service.IFundIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 收入表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Service
public class FundIncomeServiceImpl extends ServiceImpl<FundIncomeMapper, FundIncome> implements IFundIncomeService {

    @Autowired
    private IFundFamilyService fundFamilyService;
    @Autowired
    private FundIncomeMapper fundIncomeMapper;

    @Override
    public List<FundIncome> getThisYearIncome(String sysOrgCode, int yearNum){
        return fundIncomeMapper.getThisYearIncome(sysOrgCode,yearNum);
    }

    @Override
    public List<FundIncome> getThisMonthIncomeHead5(String sysOrgCode, int yearNum, int monthNum){
        return fundIncomeMapper.getThisMonthIncomeHead5(sysOrgCode,yearNum,monthNum);
    }

    @Override
    public List<FundIncomeType> getThisMonthIncomeType(String sysOrgCode, int yearNum, int monthNum) {
        return fundIncomeMapper.getThisMonthIncomeType(sysOrgCode,yearNum,monthNum);
    }

    @Override
    public PerMonthMoney getThisYearPerMonthIncomeData(String sysOrgCode, int yearNum) {
        List<PerMonthMoney> thisYearPerMonthMoney =  fundIncomeMapper.getThisYearPerMonthIncomeData(sysOrgCode,yearNum);
        List<PerMonthMoney> preYearPerMonthMoney = fundIncomeMapper.getThisYearPerMonthIncomeData(sysOrgCode,yearNum-1);

        JSONObject thisYearMonthMoneyObject = new JSONObject(true);
        thisYearMonthMoneyObject.put("type",yearNum+"");
        for(PerMonthMoney perMonthMoney : thisYearPerMonthMoney){
            thisYearMonthMoneyObject.put(perMonthMoney.getMonthNum()+"月",Double.parseDouble(perMonthMoney.getMonthMoney()));
        }
        JSONObject preYearMonthMoneyObject = new JSONObject(true);
        preYearMonthMoneyObject.put("type",(yearNum-1)+"");
        for(PerMonthMoney perMonthMoney : preYearPerMonthMoney){
            preYearMonthMoneyObject.put(perMonthMoney.getMonthNum()+"月",Double.parseDouble(perMonthMoney.getMonthMoney()));
        }
        PerMonthMoney result = new PerMonthMoney();
        result.setThisYearMonthMoney(thisYearMonthMoneyObject);
        result.setPreYearMonthMoney(preYearMonthMoneyObject);
        return result;
    }

    @Override
    public List<ThisMonthHead> getThisMonthHeadIncomeMoney(String sysOrgCode, int yearNum, int monthNum) {
        return fundIncomeMapper.getThisMonthHeadIncomeMoney(sysOrgCode,yearNum,monthNum);
    }
}
