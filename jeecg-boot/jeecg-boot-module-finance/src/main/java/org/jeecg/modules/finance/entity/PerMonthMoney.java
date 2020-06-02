package org.jeecg.modules.finance.entity;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/15 12:02
 * @Modified By
 **/
@Data
public class PerMonthMoney {

    private String yearNum; //年份
    private String monthNum; //月份
    private String monthMoney; //该月份的金额
    private String preMonthMoney; //上年该月份的金额

    private JSONObject thisYearMonthMoney; //今年每月的金额
    private JSONObject preYearMonthMoney; //去年每月的金额
}
