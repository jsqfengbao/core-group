package org.jeecg.modules.finance.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.finance.entity.FundYearBudget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 年预算
 * @Author: jeecg-boot
 * @Date:   2020-03-07
 * @Version: V1.0
 */
public interface FundYearBudgetMapper extends BaseMapper<FundYearBudget> {

    public FundYearBudget getFundYearBudgetByYear(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum);
}
