package org.jeecg.modules.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.blog.bo.TagBo;
import org.jeecg.modules.blog.entity.BlogTag;

/**
 * @Description: 博客标签
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    public List<BlogTag> listHotTagsByArticleUse();

    public List<BlogTag> listTagsByArticleUse();

    public TagBo getTagsDetailByTagId(String tagId);
}
