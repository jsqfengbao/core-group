package org.jeecg.modules.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.finance.entity.FundFamily;

import java.util.List;

/**
 * @Description: 家庭表
 * @Author: jeecg-boot
 * @Date:   2020-03-05
 * @Version: V1.0
 */
public interface IFundFamilyService extends IService<FundFamily> {

    List<String> getUserIdForCommonFamily(String sysUserId);
}
