package org.jeecg.modules.finance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/15 11:04
 * @Modified By
 **/
@Data
public class PayTypeMonthVo {
    private List<FundPayType> thisMonthPayTypeData;
}
