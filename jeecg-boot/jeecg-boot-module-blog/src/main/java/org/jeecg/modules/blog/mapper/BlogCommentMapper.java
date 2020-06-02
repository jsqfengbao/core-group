package org.jeecg.modules.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.blog.bo.CommentBo;
import org.jeecg.modules.blog.entity.BlogComment;

/**
 * @Description: 博客评论表
 * @Author: jeecg-boot
 * @Date:   2020-04-08
 * @Version: V1.0
 */
public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    public List<CommentBo> getCommentsByArticleId(String articleId);
}
