package org.jeecg.modules.finance.service;

import org.jeecg.modules.finance.entity.FundPay;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.finance.entity.FundPayType;
import org.jeecg.modules.finance.entity.PerMonthMoney;
import org.jeecg.modules.finance.entity.ThisMonthHead;

import java.util.List;

/**
 * @Description: 支出表
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
public interface IFundPayService extends IService<FundPay> {

    public List<FundPay> getThisYearPay(String sysOrgCode, int yearNum);

    public List<FundPay> getThisMonthPayHead5(String sysOrgCode,int yearNum,int monthNum);

    public List<FundPayType> getThisMonthPayType(String sysOrgCode, int yearNum, int monthNum);

    public PerMonthMoney getThisYearPerMonthPayData(String sysOrgCode, int yearNum);

    public List<ThisMonthHead> getThisMonthHeadPayMoney(String sysOrgCode,int yearNum,int monthNum);

}
