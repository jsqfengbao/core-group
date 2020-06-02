package org.jeecg.modules.blog.bo;

import lombok.Data;

/**
 * author jinsq
 *
 * @date 2020/4/18 15:34
 */
@Data
public class TagBo {

    /**主键ID*/
    private String id;

    /**标签名*/
    private String tagname;

    /**图片*/
    private String avatar;

    /**该标签对应的文章数量*/
    private int articlesNum;
}
