package org.jeecg.modules.blog.service.impl;

import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.blog.bo.TagBo;
import org.jeecg.modules.blog.entity.BlogTag;
import org.jeecg.modules.blog.mapper.BlogTagMapper;
import org.jeecg.modules.blog.service.IBlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 博客标签
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    public List<BlogTag> listHotTagsByArticleUse(){
        return blogTagMapper.listHotTagsByArticleUse();
    }

    public List<BlogTag> listTagsByArticleUse(){
        return blogTagMapper.listTagsByArticleUse();
    }

    public TagBo getTagsDetailByTagId(String tagId){
        return blogTagMapper.getTagsDetailByTagId(tagId);
    }
}
