package org.jeecg.modules.finance.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.finance.entity.*;
import org.jeecg.modules.finance.service.IFundBudgetService;
import org.jeecg.modules.finance.service.IFundYearBudgetService;
import org.jeecg.modules.finance.service.IFundIncomeService;
import org.jeecg.modules.finance.service.IFundPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/8 10:29
 * @Modified By
 **/
@Api(tags="主页")
@RestController
@RequestMapping("/finance/statis")
@Slf4j
public class FundStatisController {

    @Autowired
    private IFundPayService fundPayService;
    @Autowired
    private IFundBudgetService fundBudgetService;
    @Autowired
    private IFundIncomeService fundIncomeService;
    @Autowired
    private IFundYearBudgetService fundYearBudgetService;

    private LoginUser getUser() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return sysUser;
    }

    @AutoLog(value = "主页-获取支出总额信息")
    @ApiOperation(value="主页-支出总额信息查询", notes="主页-支出总额信息查询")
    @GetMapping(value = "/getTotalPay")
    public Result<?> getTotalPay() {
        TotalPayVo totalPayVo = new TotalPayVo();
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundPay> fundPayList = fundPayService.getThisYearPay(sysOrgCode,year);
        DecimalFormat df = new DecimalFormat("#.00");
        double totalPayMoney = 0d;
        for(FundPay fundPay : fundPayList){
            totalPayMoney += Double.parseDouble(fundPay.getMoney()+"");
        }
        totalPayVo.setTotalYearPay(df.format(totalPayMoney));  //获取当年的总支出
        FundYearBudget fundYearBudget = fundYearBudgetService.getFundYearBudgetByYear(sysOrgCode,year);
        double totalYearBudgetPay = 0d;
        if(null != fundYearBudget){
            totalYearBudgetPay = Double.parseDouble(fundYearBudget.getFundYearPay()+"");  //获取年度预算总支出
        }
        totalPayVo.setTotalYearBudgetPay(df.format(totalYearBudgetPay));
        if(totalPayMoney > 0){
            totalPayVo.setEveryMonthPayRatio(df.format(totalPayMoney/month));  //获取每月平均支出
        } else {
            totalPayVo.setEveryMonthPayRatio("0%");
        }
        if(null != fundYearBudget){
            String ratio = getRatio(new BigDecimal(totalPayMoney),fundYearBudget.getFundYearPay());
            totalPayVo.setPayBudgetRatio(ratio);
        } else{
            totalPayVo.setPayBudgetRatio("0%");
        }
        return Result.ok(totalPayVo);
    }

    @AutoLog(value = "主页-获取收入总额信息")
    @ApiOperation(value="主页-收入总额信息查询", notes="主页-收入总额信息查询")
    @GetMapping(value = "/getTotalIncome")
    public Result<?> getTotalIncome(){
        TotalIncomeVo totalIncomeVo = new TotalIncomeVo();
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundIncome> fundIncomeList = fundIncomeService.getThisYearIncome(sysOrgCode,year);
        DecimalFormat df = new DecimalFormat("#.00");
        double totalIncomeMoney = 0d;
        for(FundIncome fundIncome : fundIncomeList){
            totalIncomeMoney += Double.parseDouble(fundIncome.getMoney()+"");
        }
        totalIncomeVo.setTotalYearIncome(df.format(totalIncomeMoney));  //获取当年的总支出
        FundYearBudget fundYearBudget = fundYearBudgetService.getFundYearBudgetByYear(sysOrgCode,year);
        double totalYearBudgetIncome = 0d;
        if(null != fundYearBudget){
            totalYearBudgetIncome = Double.parseDouble(fundYearBudget.getFundYearIncome()+"");  //获取年度预算总支出
        }
        totalIncomeVo.setTotalYearBudgetIncome(df.format(totalYearBudgetIncome));
        if(totalIncomeMoney > 0){
            totalIncomeVo.setEveryMonthIncomeRatio(df.format(totalIncomeMoney/month));  //获取每月平均支出
        } else {
            totalIncomeVo.setEveryMonthIncomeRatio("0%");
        }
        if(null != fundYearBudget){
            String ratio = getRatio(new BigDecimal(totalIncomeMoney),fundYearBudget.getFundYearIncome());
            totalIncomeVo.setIncomeBudgetRatio(ratio);
        } else{
            totalIncomeVo.setIncomeBudgetRatio("0%");
        }
        return Result.ok(totalIncomeVo);
    }

    @AutoLog(value = "主页-获取当月支出类型信息")
    @ApiOperation(value="主页-当月支出类型信息", notes="主页-当月支出类型信息查询")
    @GetMapping(value = "getThisMonthPayTypeData")
    public Result<?> getThisMonthPayTypeData(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundPayType> fundPayTypeList =  fundPayService.getThisMonthPayType(sysOrgCode,year,month);
        return Result.ok(fundPayTypeList);
    }

    @AutoLog(value="主页-获取当月支出总额")
    @ApiOperation(value="主页-当月支出总额", notes = "主页-当月支出总额信息查询")
    @GetMapping(value="getThisMonthPayTotalMoney")
    public Result<?> getThisMonthPayTotalMoney() {
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundPayType> fundPayTypeList =  fundPayService.getThisMonthPayType(sysOrgCode,year,month);
        double result = 0d;
        for(FundPayType fundPayType : fundPayTypeList){
            double fundTypeMoney = Double.parseDouble(fundPayType.getFundTypeMoney());
            result += fundTypeMoney;
        }
        return Result.ok(String.valueOf(result));
    }

    @AutoLog(value = "主页-获取当月收入类型信息")
    @ApiOperation(value="主页-当月收入类型信息", notes="主页-当月收入类型信息查询")
    @GetMapping(value = "getThisMonthIncomeTypeData")
    public Result<?> getThisMonthIncomeTypeData(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundIncomeType> fundIncomeTypeList =  fundIncomeService.getThisMonthIncomeType(sysOrgCode,year,month);
        return Result.ok(fundIncomeTypeList);
    }

    @AutoLog(value="主页-获取当月收入总额")
    @ApiOperation(value="主页-获取当月收入总额", notes = "主页-获取当月收入总额查询")
    @GetMapping(value="getThisMonthIncomeTotalMoney")
    public Result<?> getThisMonthIncomeTotalMoney(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<FundIncomeType> fundIncomeTypeList =  fundIncomeService.getThisMonthIncomeType(sysOrgCode,year,month);
        double result = 0d;
        for(FundIncomeType fundIncomeType : fundIncomeTypeList) {
            double fundIncomeMoney = Double.parseDouble(fundIncomeType.getFundTypeMoney());
            result += fundIncomeMoney;
        }
        return Result.ok(String.valueOf(result));
    }
    @AutoLog(value = "主页-获取当年每月支出信息")
    @ApiOperation(value="主页-获取当年每月支出信息", notes="主页-获取当年每月支出查询")
    @GetMapping(value = "getThisYearPerMonthPayData")
    public Result<?> getThisYearPerMonthPayData(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        PerMonthMoney perMonthMoneyList =  fundPayService.getThisYearPerMonthPayData(sysOrgCode,year);
        return Result.ok(perMonthMoneyList);
    }

    @AutoLog(value = "主页-获取当年每月收入信息")
    @ApiOperation(value="主页-获取当年每月收入信息", notes="主页-获取当年每月收入查询")
    @GetMapping(value = "getThisYearPerMonthIncomeData")
    public Result<?> getThisYearPerMonthIncomeData(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        PerMonthMoney perMonthMoneyList =  fundIncomeService.getThisYearPerMonthIncomeData(sysOrgCode,year);
        return Result.ok(perMonthMoneyList);
    }

    @AutoLog(value = "主页-获取当年每月支出排名信息")
    @ApiOperation(value="主页-获取当年每月支出排名信息", notes="主页-获取当年每月支出排名查询")
    @GetMapping(value = "getThisMonthHeadPayMoney")
    public Result<?> getThisMonthHeadPayMoney(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<ThisMonthHead> thisMonthHeadList =  fundPayService.getThisMonthHeadPayMoney(sysOrgCode,year,month);
        return Result.ok(thisMonthHeadList);
    }

    @AutoLog(value = "主页-获取当年每月收入排名信息")
    @ApiOperation(value="主页-获取当年每月收入排名信息", notes="主页-获取当年每月收入排名查询")
    @GetMapping(value = "getThisMonthHeadIncomeMoney")
    public Result<?> getThisMonthHeadIncomeMoney(){
        String sysOrgCode = getUser().getOrgCode();
        int year = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(0,4));
        int month = Integer.parseInt(DateUtils.formatDate(new Date(),"yyyy-MM-dd").substring(5,7));
        List<ThisMonthHead> thisMonthHeadList =  fundIncomeService.getThisMonthHeadIncomeMoney(sysOrgCode,year,month);
        return Result.ok(thisMonthHeadList);
    }

    /**
     * 获取百分比
     * @param one
     * @param two
     * @return
     */
    private String getRatio(BigDecimal one,BigDecimal two){
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        if(two.compareTo(BigDecimal.ZERO) == 0){
            return "0%";
        }
        BigDecimal d = one.divide(two,2,ROUND_HALF_UP).multiply(new BigDecimal(100.00));
        String num = df.format(d.doubleValue())+'%';//返回的是String类型
        return num;
    }
}
