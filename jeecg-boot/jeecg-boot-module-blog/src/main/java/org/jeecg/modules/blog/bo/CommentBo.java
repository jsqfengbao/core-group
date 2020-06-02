package org.jeecg.modules.blog.bo;

import lombok.Data;
import org.jeecg.modules.blog.entity.SysUser;

import java.util.Date;
import java.util.List;

@Data
public class  CommentBo {

    /**评论ID*/
    private String id;

    /**评论类型*/
    private String level;

    /**评论内容*/
    private String content;

    /**评论时间*/
    private Date createDate;

    /**对谁的评论*/
    private SysUser toUser;

    /**文章作者*/
    private SysUser author;

    /**评论的评论*/
    private List<CommentBo> children;

}
