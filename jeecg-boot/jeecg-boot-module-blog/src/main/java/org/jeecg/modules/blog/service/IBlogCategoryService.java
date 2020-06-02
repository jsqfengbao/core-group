package org.jeecg.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.blog.entity.BlogCategory;

import java.util.List;

/**
 * @Description: 文章分类
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
public interface IBlogCategoryService extends IService<BlogCategory> {

    public List<BlogCategory> queryCategorysApiList();

    public BlogCategory queryCategoryApiById(String categoryId);
}
