package org.jeecg.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.blog.bo.CommentBo;
import org.jeecg.modules.blog.entity.BlogComment;

import java.util.List;

/**
 * @Description: 博客评论表
 * @Author: jeecg-boot
 * @Date:   2020-04-08
 * @Version: V1.0
 */
public interface IBlogCommentService extends IService<BlogComment> {

    public List<CommentBo> getCommentsByArticleId(String articleId);
}
