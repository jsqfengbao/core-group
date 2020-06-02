package org.jeecg.modules.blog.service.impl;

import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.blog.entity.BlogCategory;
import org.jeecg.modules.blog.mapper.BlogCategoryMapper;
import org.jeecg.modules.blog.service.IBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 文章分类
 * @Author: jeecg-boot
 * @Date:   2020-04-06
 * @Version: V1.0
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements IBlogCategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    public List<BlogCategory> queryCategorysApiList(){
        return blogCategoryMapper.queryCategorysApiList();
    }

    public BlogCategory queryCategoryApiById(String categoryId){
        return blogCategoryMapper.queryCategoryApiById(categoryId);
    }
}
