package org.jeecg.modules.finance.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.finance.entity.FundPay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface FundPayMapper extends BaseMapper<FundPay> {

    List<FundPay> getThisYearPay(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum);

    List<FundPay> getThisMonthPayHead5(@Param("sysOrgCode") String sysOrgCode, @Param("yearNum") int yearNum, @Param("monthNum") int monthNum);

    List<FundPayType> getThisMonthPayTypeData(@Param("sysOrgCode") String sysOrgCode,@Param("yearNum") int yearNum,@Param("monthNum") int monthNum);

    List<PerMonthMoney> getThisYearPerMonthPayData(@Param("sysOrgCode") String sysOrgCode,@Param("yearNum") int yearNum);

    List<ThisMonthHead> getThisMonthHeadPayMoney(@Param("sysOrgCode") String sysOrgCode,@Param("yearNum") int yearNum, @Param("monthNum") int monthNum);

}
