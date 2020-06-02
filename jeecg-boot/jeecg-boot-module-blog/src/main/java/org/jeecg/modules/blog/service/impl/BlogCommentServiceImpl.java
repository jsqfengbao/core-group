package org.jeecg.modules.blog.service.impl;

import org.jeecg.modules.blog.bo.CommentBo;
import org.jeecg.modules.blog.entity.BlogComment;
import org.jeecg.modules.blog.mapper.BlogCommentMapper;
import org.jeecg.modules.blog.service.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 博客评论表
 * @Author: jeecg-boot
 * @Date:   2020-04-08
 * @Version: V1.0
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    public List<CommentBo> getCommentsByArticleId(String articleId){
        return blogCommentMapper.getCommentsByArticleId(articleId);
    }
}
