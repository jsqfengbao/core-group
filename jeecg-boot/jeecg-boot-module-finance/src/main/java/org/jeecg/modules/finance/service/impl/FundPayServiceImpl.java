package org.jeecg.modules.finance.service.impl;

import cn.hutool.json.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.finance.entity.FundPayType;
import org.jeecg.modules.finance.entity.PerMonthMoney;
import org.jeecg.modules.finance.entity.ThisMonthHead;
import org.jeecg.modules.finance.service.IFundFamilyService;
import org.jeecg.modules.finance.entity.FundPay;
import org.jeecg.modules.finance.mapper.FundPayMapper;
import org.jeecg.modules.finance.service.IFundPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 支出表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Service
public class FundPayServiceImpl extends ServiceImpl<FundPayMapper, FundPay> implements IFundPayService {


    @Autowired
    private IFundFamilyService fundFamilyService;
    @Autowired
    private FundPayMapper fundPayMapper;

    @Override
    public List<FundPay> getThisYearPay(String sysOrgCode, int yearNum) {
        return fundPayMapper.getThisYearPay(sysOrgCode,yearNum);
    }

    @Override
    public List<FundPay> getThisMonthPayHead5(String sysOrgCode, int yearNum, int monthNum) {
        return fundPayMapper.getThisMonthPayHead5(sysOrgCode,yearNum,monthNum);
    }

    @Override
    public List<FundPayType> getThisMonthPayType(String sysOrgCode, int yearNum, int monthNum) {
        return fundPayMapper.getThisMonthPayTypeData(sysOrgCode,yearNum,monthNum);
    }

    @Override
    public PerMonthMoney getThisYearPerMonthPayData(String sysOrgCode, int yearNum) {
        List<PerMonthMoney> thisYearPerMonthMoney =  fundPayMapper.getThisYearPerMonthPayData(sysOrgCode,yearNum);
        List<PerMonthMoney> preYearPerMonthMoney = fundPayMapper.getThisYearPerMonthPayData(sysOrgCode,yearNum-1);

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
    public List<ThisMonthHead> getThisMonthHeadPayMoney(String sysOrgCode, int yearNum, int monthNum) {
        return fundPayMapper.getThisMonthHeadPayMoney(sysOrgCode,yearNum,monthNum);
    }
}
