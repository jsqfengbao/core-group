package org.jeecg.modules.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.finance.entity.FundFamily;
import org.jeecg.modules.finance.mapper.FundFamilyMapper;
import org.jeecg.modules.finance.service.IFundFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 家庭表
 * @Author: jeecg-boot
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Service
public class FundFamilyServiceImpl extends ServiceImpl<FundFamilyMapper, FundFamily> implements IFundFamilyService {

    @Autowired
    private FundFamilyMapper fundFamilyMapper;

    @Override
    public List<String> getUserIdForCommonFamily(String sysUserId) {
        QueryWrapper<FundFamily> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FundFamily::getCreateBy,sysUserId);
        String familyName = fundFamilyMapper.selectOne(queryWrapper).getFundFamilyName();
        Wrapper wrapper = new QueryWrapper<FundFamily>()
                .lambda()
                .eq(FundFamily::getFundFamilyName,familyName);
        List<FundFamily> fundFamilyList = fundFamilyMapper.selectList(wrapper);
        List<String> result = new ArrayList<>();
        for(FundFamily fundFamily : fundFamilyList){
            result.add(fundFamily.getCreateBy());
        }
        return result;
    }
}
