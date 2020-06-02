package org.jeecg.modules.finance.entity;

import lombok.Data;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/15 11:14
 * @Modified By
 **/
@Data
public class FundPayType {

    private String fundTypeName;  //支出类型名
    private String fundTypeMoney; //该类型的金额
}
