package org.jeecg.modules.blog.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * author jinsq
 *
 * @date 2020/4/9 21:51
 */
@Data
public class ArticleBo {

    /**ID*/
    private String id;

    /**作者昵称*/
    private String authorName;

    /**评论总数*/
    private int commentCounts;

    /**创建时间*/
    private Date createTime;

    /**概要*/
    private String summary;

    /**标签名*/
    private List<String> tagNameList;

    /**标题*/
    private String title;

    /**阅读次数*/
    private int viewCounts;

    /**权重*/
    private int weight;

    /**分类ID*/
    private String categoryId;

    /**分类名*/
    private String categoryName;

}
