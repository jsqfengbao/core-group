package org.jeecg.modules.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.finance.entity.FundIncome;
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
public interface FundIncomeMapper extends BaseMapper<FundIncome> {

    List<FundIncome> getThisYearIncome(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum);

    List<FundIncome> getThisMonthIncomeHead5(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum, @Param("monthNum") int monthNum);

    List<FundIncomeType> getThisMonthIncomeType(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum, @Param("monthNum") int monthNum);

    List<PerMonthMoney> getThisYearPerMonthIncomeData(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum);

    List<ThisMonthHead> getThisMonthHeadIncomeMoney(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum, @Param("monthNum") int monthNum);
}
