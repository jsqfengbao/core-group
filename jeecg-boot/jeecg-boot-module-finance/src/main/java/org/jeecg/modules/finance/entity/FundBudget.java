package org.jeecg.modules.finance.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 预算管理
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Data
@TableName("fund_budget")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="fund_budget对象", description="预算管理")
public class FundBudget implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**资金类型ID*/
	@Excel(name = "资金类型ID", width = 15)
    @ApiModelProperty(value = "资金类型ID")
    private String fundTypeId;
	/**资金类型名*/
	@Excel(name = "资金类型名", width = 15)
    @ApiModelProperty(value = "资金类型名")
    private String fundTypeName;
	/**预算金额*/
	@Excel(name = "预算金额", width = 15)
    @ApiModelProperty(value = "预算金额")
    private BigDecimal money;
	/**预算月份*/
	@Excel(name = "预算月份", width = 15)
    @ApiModelProperty(value = "预算月份")
    private Integer monthNum;
	/**预算年份*/
	@Excel(name = "预算年份", width = 15)
    @ApiModelProperty(value = "预算年份")
    private Integer yearNum;
}
