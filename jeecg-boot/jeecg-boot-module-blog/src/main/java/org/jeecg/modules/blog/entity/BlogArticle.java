package org.jeecg.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import javax.persistence.Transient;

/**
 * @Description: 文章
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
@Data
@TableName("blog_article")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="blog_article对象", description="文章")
public class BlogArticle implements Serializable {
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
	/**评论总数*/
	@Excel(name = "评论总数", width = 15)
    @ApiModelProperty(value = "评论总数")
    private Integer commentCounts;
	/**概要*/
	@Excel(name = "概要", width = 15)
    @ApiModelProperty(value = "概要")
    private String summary;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private String title;
	/**查看次数*/
	@Excel(name = "查看次数", width = 15)
    @ApiModelProperty(value = "查看次数")
    private Integer viewCounts;
	/**权重*/
	@Excel(name = "权重", width = 15)
    @ApiModelProperty(value = "权重")
    private Integer weight;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private String content;
	/**分类ID*/
	@Excel(name = "分类ID", width = 15)
    @ApiModelProperty(value = "分类ID")
    private String categoryId;
	/**是否有效*/
	@Excel(name = "是否有效", width = 15)
    @ApiModelProperty(value = "是否有效")
    private Integer isEnabled;

	@ApiModelProperty(value = "标签列表")
	@Transient
	@TableField(exist = false)
	private List<BlogTag> blogTagList;

	@ApiModelProperty(value = "文章分类")
	@TableField(exist = false)
	@Transient
	private BlogCategory blogCategory;
}
