package org.jeecg.modules.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.blog.entity.BlogCategory;

/**
 * @Description: 文章分类
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

    public List<BlogCategory> queryCategorysApiList();

    public BlogCategory queryCategoryApiById(String categoryId);
}
