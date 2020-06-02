package org.jeecg.modules.finance.entity;

import lombok.Data;

/**
 * @Author：jinshuangqi
 * @Date: Created in 2020/3/15 12:51
 * @Modified By
 **/
@Data
public class ThisMonthHead implements Comparable<ThisMonthHead>{

    private String content; //内容
    private String sumMoney;   //金额

    @Override
    public int compareTo(ThisMonthHead o) {
        return Integer.parseInt(this.sumMoney) -Integer.parseInt(o.sumMoney);
    }
}
