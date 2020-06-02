package org.jeecg.modules.finance.service.impl;

import org.jeecg.modules.finance.entity.FundType;
import org.jeecg.modules.finance.mapper.FundTypeMapper;
import org.jeecg.modules.finance.service.IFundTypeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 资金类型
 * @Author: jeecg-boot
 * @Date:   2020-03-04
 * @Version: V1.0
 */
@Service
public class FundTypeServiceImpl extends ServiceImpl<FundTypeMapper, FundType> implements IFundTypeService {

}
