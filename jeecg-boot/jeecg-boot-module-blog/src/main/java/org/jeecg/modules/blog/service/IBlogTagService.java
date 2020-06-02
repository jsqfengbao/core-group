package org.jeecg.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.blog.bo.TagBo;
import org.jeecg.modules.blog.entity.BlogTag;

import java.util.List;

/**
 * @Description: 博客标签
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
public interface IBlogTagService extends IService<BlogTag> {

    public List<BlogTag> listHotTagsByArticleUse();

    public List<BlogTag> listTagsByArticleUse();

    public TagBo getTagsDetailByTagId(String tagId);
}
